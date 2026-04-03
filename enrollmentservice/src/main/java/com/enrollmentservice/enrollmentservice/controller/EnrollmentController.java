package com.enrollmentservice.enrollmentservice.controller;

import com.enrollmentservice.enrollmentservice.dto.EnrollmentRequestDTO;
import com.enrollmentservice.enrollmentservice.dto.EnrollmentResponseDto;
import com.enrollmentservice.enrollmentservice.dto.PageResponse;
import com.enrollmentservice.enrollmentservice.entity.Course;
import com.enrollmentservice.enrollmentservice.entity.Student;
import com.enrollmentservice.enrollmentservice.exception.NotFoundException;
import com.enrollmentservice.enrollmentservice.service.EnrollmentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Validated
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity addEnrollment(@Valid @RequestBody EnrollmentRequestDTO enrollmentRequestDTO) {

        enrollmentService.addEnrollment(enrollmentRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageResponse<EnrollmentResponseDto>> getEnrollments(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                              @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                              @RequestParam(value = "sortBy", defaultValue = "enrollmentId", required = false) String sortBy,
                                                                              @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        PageResponse<EnrollmentResponseDto> enrollments = enrollmentService.getEnrollments(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(enrollments);
    }

    @GetMapping("/{enrollment_id}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollment(@PathVariable
                                                               @Positive(message = "Enrollment ID must be positive and Greater then 0")
                                                               Integer enrollment_id) {

        EnrollmentResponseDto enrollment = enrollmentService
                .getEnrollment(enrollment_id)
                .orElseThrow(() -> new NotFoundException("Enrollment Not Found By ID :" + enrollment_id));

        return ResponseEntity.status(HttpStatus.OK).body(enrollment);
    }

    // Get All courses in which particular student is enrolled
    @GetMapping("/student/{student_id}")
    public ResponseEntity<CompletableFuture<PageResponse<Course>>> getCoursesForStudent(@PathVariable
                                                                     @Positive(message = "Student ID must be positive and greater then 0") Integer student_id,
                                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = "studentId", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CompletableFuture<PageResponse<Course>> allCoursesByStudentID = enrollmentService.getAllCoursesByStudentID(student_id, pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(allCoursesByStudentID);
    }

    // Get All students who enrolled in particular course
    @GetMapping("/course/{course_id}")
    public ResponseEntity<CompletableFuture<PageResponse<Student>>> getStudentsOfCourse(@PathVariable
                                                                     @Positive(message = "Course ID must be positive and greater then 0") Integer course_id,
                                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = "courseId", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CompletableFuture<PageResponse<Student>> allStudentsByCourseId = enrollmentService.getAllStudentsByCourseId(course_id, pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(allStudentsByCourseId);
    }

    @DeleteMapping("/{enrollment_id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable
                                              @Positive(message = "Enrollment ID must be positive and greater then 0")
                                              Integer enrollment_id) {

        enrollmentService.deleteEnrollment(enrollment_id);

        return new ResponseEntity<>("Enrollment Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
