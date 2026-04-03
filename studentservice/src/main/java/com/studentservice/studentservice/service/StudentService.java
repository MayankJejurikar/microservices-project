package com.studentservice.studentservice.service;

import com.studentservice.studentservice.dto.StudentRequestDto;
import com.studentservice.studentservice.dto.StudentResponse;
import com.studentservice.studentservice.dto.StudentResponseDto;
import com.studentservice.studentservice.entity.Student;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    public StudentResponse getStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    public Optional<StudentResponseDto> getStudent(int studentId);

    public StudentResponseDto addStudent(StudentRequestDto student);

    public StudentResponseDto updateStudent(int studentId, StudentRequestDto student);

    public void deleteStudent(int studentId);

    public StudentResponse getAllStudentByIDs(List<Integer> studentId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
