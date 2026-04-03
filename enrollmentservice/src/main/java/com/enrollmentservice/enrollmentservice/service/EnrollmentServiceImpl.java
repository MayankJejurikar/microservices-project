package com.enrollmentservice.enrollmentservice.service;

import com.enrollmentservice.enrollmentservice.dto.*;
import com.enrollmentservice.enrollmentservice.entity.*;
import com.enrollmentservice.enrollmentservice.exception.AlreadyEnrolledException;
import com.enrollmentservice.enrollmentservice.exception.NotFoundException;
import com.enrollmentservice.enrollmentservice.exception.ServiceUnavailableException;
import com.enrollmentservice.enrollmentservice.repository.EnrollmentDao;
import com.enrollmentservice.enrollmentservice.service.service.CourseService;
import com.enrollmentservice.enrollmentservice.service.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentDao enrollmentDao;

    /*@Autowired
    private RestTemplate restTemplate;*/

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CustomSorting sorting;

    @Autowired
    private ValidateService service;

    @Override
    public void addEnrollment(EnrollmentRequestDTO enrollmentRequestDTO) {

        int studentId = enrollmentRequestDTO.getStudent_Id();
        int courseId = enrollmentRequestDTO.getCourse_Id();

        log.info("Enrollment request received for studentId={} and courseId={}", studentId, courseId);

        service.studentValidate(studentId);
        service.courseValidate(courseId);

        if (enrollmentDao.existsByStudentIdAndCourseId(studentId, courseId)) {

            log.warn("Enrollment Already exist! with studentId={} and courseId={}", studentId, courseId);
            throw new AlreadyEnrolledException("Enrollment Already Exist by Student ID:" + studentId + " and Course ID :" + courseId);
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrollmentDate(LocalDate.now().toString());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        enrollmentDao.save(enrollment);

        log.info("Enrollment successful for studentId={} and courseId={}", studentId, courseId);

    }

    @Override
    public PageResponse<EnrollmentResponseDto> getEnrollments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting.customSorting(sortBy, sortDir));

        Page<Enrollment> page = enrollmentDao.findAll(pageable);

        List<EnrollmentResponseDto> enrollmentsResponse = page
                .getContent()
                .stream()
                .map(EnrollmentConvertDto::enrollmentToResponseDto)
                .toList();

        PageResponse<EnrollmentResponseDto> response = new PageResponse<EnrollmentResponseDto>(
                enrollmentsResponse,
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getSort().toString()
        );

        return response;
    }

    @Override
    public Optional<EnrollmentResponseDto> getEnrollment(int enrollment_id) {

        Optional<EnrollmentResponseDto> enrollmentResponseDto = enrollmentDao
                .findById(enrollment_id)
                .map(EnrollmentConvertDto::enrollmentToResponseDto);

        return enrollmentResponseDto;
    }

    @TimeLimiter(name = "getAllCourseTimeout", fallbackMethod = "getAllCoursesBreakerFallback")
    @Retry(name = "getAllCourseRetry", fallbackMethod = "getAllCoursesBreakerFallback")
    @CircuitBreaker(name = "getAllCoursesBreaker", fallbackMethod = "getAllCoursesBreakerFallback")
    @Override
    public CompletableFuture<PageResponse<Course>> getAllCoursesByStudentID(int student_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        List<Integer> coursesId = enrollmentDao.findCourseIdsByStudentId(student_id);

        if (coursesId.isEmpty()) {
            return CompletableFuture.completedFuture(new PageResponse(
                    Collections.emptyList(),
                    0,
                    0,
                    0,
                    0,
                    true,
                    ""
            ));
        }

        log.info("Retry Course Service..");
        return CompletableFuture.supplyAsync(()->{

            PageResponse<Course> coursesByIds = courseService.getCoursesByIds(coursesId, pageNumber, pageSize, sortBy, sortDir);

            log.info("Student with ID:{} Enrolled in Courses:{}", student_id, coursesByIds.getContent());

            return coursesByIds;
        });
    }

    //Fallback for getAllCoursesBreaker
    public CompletableFuture<PageResponse<Course>> getAllCoursesBreakerFallback(int student_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Throwable ex) {

        log.info("Exception class: {}", ex.getClass().getName());
        log.info("Unable to fetch All courses for student ID:{}. Course-Service is down currently. Please try after sometime.", student_id);
        return CompletableFuture
                .failedFuture(new ServiceUnavailableException("Unable to fetch All courses for student ID:" + student_id + ". Course-Service is down currently. Please try after sometime."));
    }


    @TimeLimiter(name = "getAllStudentTimeout", fallbackMethod = "getAllStudentsFallback")
    @Retry(name = "getAllStudentRetry", fallbackMethod = "getAllStudentsFallback")
    @CircuitBreaker(name = "getAllStudentsBreaker", fallbackMethod = "getAllStudentsFallback")
    @Override
    public CompletableFuture<PageResponse<Student>> getAllStudentsByCourseId(int course_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        List<Integer> studentIds = enrollmentDao.findStudentIdsByCourseId(course_id);

        if (studentIds.isEmpty()) {
            return CompletableFuture.completedFuture(new PageResponse(
                    Collections.emptyList(),
                    0,
                    0,
                    0,
                    0,
                    true,
                    ""
            ));
        }

        log.info("Retry Student Service..");
        //Using Feign Client

        return CompletableFuture.supplyAsync(() -> {

            PageResponse<Student> studentsByIds = studentService.getStudentsByIds(studentIds, pageNumber, pageSize, sortBy, sortDir);

            log.info("Students Enrolled in course ID={} are {} sorted by={}", course_id, studentsByIds.getContent(), studentsByIds.getSortedBy());

            return studentsByIds;
        });

    }

    public CompletableFuture<PageResponse<Student>> getAllStudentsFallback(int course_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir, Throwable ex) {

        log.info("Exception class: {}", ex.getClass().getName());
        log.info("Unable to fetch All Students for course ID:{}. Student-Service is down currently. Please try after sometime.", course_id);
        return CompletableFuture
                .failedFuture(new ServiceUnavailableException("Unable to fetch All studnets for course ID:" + course_id + ". Student-Service is down currently. Please try after sometime."));

    }

    @Override
    public void deleteEnrollment(int enrollment_id) {

        Enrollment enroll = enrollmentDao.findById(enrollment_id).orElseThrow(() -> new NotFoundException("Enrollment Not Found by ID:" + enrollment_id + " .Hence,Cannot delete Enrollment"));
        enrollmentDao.delete(enroll);

    }
}
