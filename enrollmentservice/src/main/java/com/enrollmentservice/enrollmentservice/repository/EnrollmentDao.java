package com.enrollmentservice.enrollmentservice.repository;

import com.enrollmentservice.enrollmentservice.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentDao extends JpaRepository<Enrollment, Integer> {

    public boolean existsByStudentIdAndCourseId(int studentId, int courseId);

    @Query(value = "select course_id from Enrollment where student_id= :studentId", nativeQuery = true)
    public List<Integer> findCourseIdsByStudentId(@Param("studentId") int studentId);

    @Query(value = "select student_id from Enrollment where course_id= :courseId",nativeQuery = true)
    public List<Integer> findStudentIdsByCourseId(@Param("courseId") int courseId);
}
