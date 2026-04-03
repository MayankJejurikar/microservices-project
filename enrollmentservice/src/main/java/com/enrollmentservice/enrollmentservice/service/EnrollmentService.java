package com.enrollmentservice.enrollmentservice.service;

import com.enrollmentservice.enrollmentservice.dto.EnrollmentRequestDTO;
import com.enrollmentservice.enrollmentservice.dto.EnrollmentResponseDto;
import com.enrollmentservice.enrollmentservice.dto.PageResponse;
import com.enrollmentservice.enrollmentservice.entity.Course;
import com.enrollmentservice.enrollmentservice.entity.Student;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface EnrollmentService {

    public void addEnrollment(EnrollmentRequestDTO enrollmentRequestDTO);

    public PageResponse<EnrollmentResponseDto> getEnrollments(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public Optional<EnrollmentResponseDto> getEnrollment(int enrollment_id);

    public CompletableFuture<PageResponse<Course>> getAllCoursesByStudentID(int student_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public CompletableFuture<PageResponse<Student>> getAllStudentsByCourseId(int course_id, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public void deleteEnrollment(int enrollment_id);
}
