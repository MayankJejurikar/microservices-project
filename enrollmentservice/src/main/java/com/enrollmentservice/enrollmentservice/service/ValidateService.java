package com.enrollmentservice.enrollmentservice.service;

import com.enrollmentservice.enrollmentservice.entity.Course;
import com.enrollmentservice.enrollmentservice.entity.Student;
import com.enrollmentservice.enrollmentservice.exception.ServiceUnavailableException;
import com.enrollmentservice.enrollmentservice.service.service.CourseService;
import com.enrollmentservice.enrollmentservice.service.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ValidateService {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;


    @TimeLimiter(name = "studentValidateTimeout", fallbackMethod = "studentValidateFallback")
    @Retry(name = "studentValidateRetry", fallbackMethod = "studentValidateFallback")
    @CircuitBreaker(name = "studentValidate", fallbackMethod = "studentValidateFallback")
    // Validating Student or course exist or not methods
    public CompletableFuture<Void> studentValidate(int studentId) {


        log.info("Retry Student Service..");
        log.info("Calling Student service to fetch Student by student ID:{}", studentId);
        return CompletableFuture.runAsync(() -> studentService.getStudentById(studentId));
    }

    // fallback method for studentvalidate method
    public CompletableFuture<Void> studentValidateFallback(int studentId, Throwable ex) {

        log.info("Exception class: {}", ex.getClass().getName());
        log.info("Student service is temporarily unavailable for student ID: " + studentId);
        return CompletableFuture.failedFuture(new ServiceUnavailableException("Student service is temporarily unavailable for student ID: " + studentId + ". Please try after sometime."));
    }


    @TimeLimiter(name = "courseValidateTimeout", fallbackMethod = "courseValidateFallback")
    @Retry(name = "courseValidateRetry", fallbackMethod = "courseValidateFallback")
    @CircuitBreaker(name = "courseValidate", fallbackMethod = "courseValidateFallback")
    public CompletableFuture<Void> courseValidate(int courseId) {


        log.info("Retry Course Service...");
        log.info("Calling Course service to fetch Course by course ID:{}", courseId);
        return CompletableFuture.runAsync(() -> courseService.getCourseById(courseId));

    }

    public CompletableFuture<Void> courseValidateFallback(int courseId, Throwable ex) {

        log.info("Exception class: {}", ex.getClass().getName());
        log.info("Course service is temporarily unavailable for course ID: {}", courseId);
        return CompletableFuture.failedFuture(new ServiceUnavailableException("Student service is temporarily unavailable for student ID:" + courseId + " . Please try after sometime."));
    }
}
