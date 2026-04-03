package com.courseservice.courseservice.service;

import com.courseservice.courseservice.dto.*;
import com.courseservice.courseservice.entity.Course;
import com.courseservice.courseservice.exception.CourseNotFoundException;
import com.courseservice.courseservice.repository.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImplementation implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CustomSorting sorting;


    @Override
    public CourseResponse getCourses(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {


        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting.sorting(sortBy, sortDir));

        Page<Course> pageCourses = courseDao.findAll(pageable);

        List<CourseResponseDto> courses = pageCourses
                .stream()
                .map(CourseConvertDto::courseToCourseResponseDto)
                .toList();

        CourseResponse courseResponse = new CourseResponse(
                courses,
                pageCourses.getNumber(),
                pageCourses.getSize(),
                pageCourses.getNumberOfElements(),
                pageCourses.getTotalPages(),
                pageCourses.isLast(),
                pageCourses.getSort().toString()
        );

        return courseResponse;
    }

    @Override
    public Optional<CourseResponseDto> getCourse(int courseId) {

        Optional<CourseResponseDto> courseResponseDto = courseDao.findById(courseId).map(CourseConvertDto::courseToCourseResponseDto);

        return courseResponseDto;
    }

    @Override
    public CourseResponseDto addCourse(CourseRequestDto course) {

        // Converting CourseRequestDto to Course
        Course newCourse = CourseConvertDto.courseRequestDtoToCourse(course);

        // Saving new Course
        courseDao.save(newCourse);

        // Converting Course to CourseResponseDto

        return CourseConvertDto.courseToCourseResponseDto(newCourse);
    }

    @Override
    public CourseResponseDto updateCourse(int courseId, CourseRequestDto course) {

        Course existingCourse = courseDao.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Course Not Found by ID :" + courseId));

        existingCourse.setCourseName(course.getCourse_Name());
        existingCourse.setCourseInstructorName(course.getCourse_Instructor_Name());
        existingCourse.setCourseDescription(course.getCourse_description());
        existingCourse.setCourseCategory(course.getCourse_category());
        existingCourse.setCourseDuration(course.getCourse_duration());
        existingCourse.setCoursePrice(course.getCourse_price());

        Course courseUpdated = courseDao.save(existingCourse);

        return CourseConvertDto.courseToCourseResponseDto(courseUpdated);
    }

    @Override
    public void deleteCourse(int courseId) {

        Course course = courseDao.findById(courseId).orElseThrow(() -> new CourseNotFoundException("Course Not Found by ID :" + courseId));
        courseDao.delete(course);
    }

    @Override
    public CourseResponse getAllCourseByIds(List<Integer> courseId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting.sorting(sortBy, sortDir));

        Page<Course> page = courseDao.findByCourseIdIn(courseId, pageable);

        List<CourseResponseDto> courses = page.getContent().stream().map(CourseConvertDto::courseToCourseResponseDto).toList();


        CourseResponse response = new CourseResponse(
                courses,
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getSort().toString()
        );

        return response;
    }
}
