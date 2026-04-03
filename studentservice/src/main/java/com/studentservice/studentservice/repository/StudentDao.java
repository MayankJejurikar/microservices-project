package com.studentservice.studentservice.repository;

import com.studentservice.studentservice.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface StudentDao extends JpaRepository<Student, Integer> {

    Page<Student> findByStuIdIn(List<Integer> stuIds, Pageable pageable);
}
