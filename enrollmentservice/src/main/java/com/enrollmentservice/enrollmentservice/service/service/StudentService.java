package com.enrollmentservice.enrollmentservice.service.service;

import com.enrollmentservice.enrollmentservice.dto.PageResponse;
import com.enrollmentservice.enrollmentservice.entity.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service")
public interface StudentService {

    @PostMapping("/student/students")
    public PageResponse<Student> getStudentsByIds(@RequestBody List<Integer> studentIds,
                                                  @RequestParam(value = "pageNumber") Integer pageNumber,
                                                  @RequestParam(value = "pageSize") Integer pageSize,
                                                  @RequestParam(value = "sortBy") String sortBy,
                                                  @RequestParam(value = "sortDir") String sortDir);

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer studentId);
}
