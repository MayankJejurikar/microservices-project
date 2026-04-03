package com.courseservice.courseservice.repository;

import com.courseservice.courseservice.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Integer> {

    Page<Course> findByCourseIdIn(List<Integer> courseIds, Pageable pageable);
}
