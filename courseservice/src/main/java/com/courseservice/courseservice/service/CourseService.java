package com.courseservice.courseservice.service;

import com.courseservice.courseservice.dto.CourseRequestDto;
import com.courseservice.courseservice.dto.CourseResponse;
import com.courseservice.courseservice.dto.CourseResponseDto;
import com.courseservice.courseservice.entity.Course;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    public CourseResponse getCourses(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public Optional<CourseResponseDto> getCourse(int courseId);

    public CourseResponseDto addCourse(CourseRequestDto course);

    public CourseResponseDto updateCourse(int courseId, CourseRequestDto course);

    public void deleteCourse(int courseId);

    public CourseResponse getAllCourseByIds(List<Integer> courseId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
