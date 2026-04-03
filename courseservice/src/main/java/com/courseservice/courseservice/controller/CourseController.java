package com.courseservice.courseservice.controller;

import com.courseservice.courseservice.dto.CourseRequestDto;
import com.courseservice.courseservice.dto.CourseResponse;
import com.courseservice.courseservice.dto.CourseResponseDto;
import com.courseservice.courseservice.entity.Course;
import com.courseservice.courseservice.exception.CourseNotFoundException;
import com.courseservice.courseservice.service.CourseService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<CourseResponse> getCourses(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "courseId", required = false) String sortBy,
                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CourseResponse courses = courseService.getCourses(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> getCourse(@PathVariable
                                                       @Positive(message = "Course ID must not be zero it should be postive number")
                                                       Integer courseId) {

        CourseResponseDto course = courseService.getCourse(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course Not Found by ID :" + courseId));

        return ResponseEntity.status(HttpStatus.OK).body(course);
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> addCourse(@Valid @RequestBody CourseRequestDto courseRequestDto) {

        CourseResponseDto responseDto = courseService.addCourse(courseRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable
                                                          @Positive(message = "Course ID must not be zero it should be postive number")
                                                          Integer courseId, @Valid @RequestBody CourseRequestDto courseRequestDto) {

        CourseResponseDto responseDto = courseService.updateCourse(courseId, courseRequestDto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // Get All the courses by multiple courseId
    @PostMapping("/courses")
    public ResponseEntity<CourseResponse> getAllCoursesByIDs(@RequestBody List<Integer> courseId, @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = "courseId", required = false) String sortBy,
                                                             @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

        CourseResponse allCourseByIds = courseService.getAllCourseByIds(courseId, pageNumber, pageSize, sortBy, sortDir);

        log.info("Courses:{}", allCourseByIds);

        return new ResponseEntity<>(allCourseByIds, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity deleteCourse(@PathVariable
                                       @Positive(message = "Course ID must not be zero it should be postive number")
                                       Integer courseId) {

        courseService.deleteCourse(courseId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
