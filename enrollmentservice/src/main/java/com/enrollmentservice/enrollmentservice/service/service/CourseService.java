package com.enrollmentservice.enrollmentservice.service.service;

import com.enrollmentservice.enrollmentservice.dto.PageResponse;
import com.enrollmentservice.enrollmentservice.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "course-service")
public interface CourseService {

    @PostMapping("/course/courses")
    public PageResponse<Course> getCoursesByIds(@RequestBody List<Integer> courseIds,
                                        @RequestParam(value = "pageNumber") Integer pageNumber,
                                        @RequestParam(value = "pageSize") Integer pageSize,
                                        @RequestParam(value = "sortBy") String sortBy,
                                        @RequestParam(value = "sortDir") String sortDir);

    @GetMapping("/course/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer courseId);
}
