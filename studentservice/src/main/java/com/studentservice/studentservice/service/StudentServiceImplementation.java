package com.studentservice.studentservice.service;

import com.studentservice.studentservice.dto.*;
import com.studentservice.studentservice.entity.Student;
import com.studentservice.studentservice.exception.StudentNotFoundException;
import com.studentservice.studentservice.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StudentServiceImplementation implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CustomStudentSorting sorting;

    @Override
    public StudentResponse getStudents(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting.sorting(sortBy, sortDir));

        Page<Student> page = studentDao.findAll(pageable);

        List<StudentResponseDto> allStudents = page
                .getContent()
                .stream()
                .map(StudentConvertDto::studentToStudentResponseDto).toList();
        
        StudentResponse response = new StudentResponse(
                allStudents,
                page.getNumber() + 1,
                page.getSize(), page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getSort().toString());

        return response;
    }

    @Override
    public Optional<StudentResponseDto> getStudent(int studentId) {

        Optional<StudentResponseDto> studentResponseDto = studentDao.findById(studentId).map(StudentConvertDto::studentToStudentResponseDto);

        return studentResponseDto;
    }

    @Override
    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {

        //Converting StudentRequestDto to Student
        Student student = StudentConvertDto.studentRequestDtoToStudententity(studentRequestDto);

        studentDao.save(student);

        //Converting Student to StudentResponseDto
        StudentResponseDto responseDto = StudentConvertDto.studentToStudentResponseDto(student);

        return responseDto;
    }

    @Override
    public StudentResponseDto updateStudent(int studentId, StudentRequestDto studentRequestDto) {

        Student existingStudent = studentDao.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Student Not Found by ID :" + studentId));

        existingStudent.setStuName(studentRequestDto.getStu_Name());
        existingStudent.setStuEmail(studentRequestDto.getStu_email());
        existingStudent.setStuAddress(studentRequestDto.getStu_Address());
        existingStudent.setStuMobileNo(studentRequestDto.getStu_Mobile_no());
        existingStudent.setStuDOB(studentRequestDto.getStu_DOB());

        studentDao.save(existingStudent);

        //Converting Student to StudentResponseDto
        StudentResponseDto responseDto = StudentConvertDto.studentToStudentResponseDto(existingStudent);

        return responseDto;
    }

    @Override
    public void deleteStudent(int studentId) {

        Student student = studentDao.findById(studentId).orElseThrow(() -> new StudentNotFoundException("Student not found by ID :" + studentId));

        studentDao.delete(student);

    }

    @Override
    public StudentResponse getAllStudentByIDs(List<Integer> studentId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        if (studentId.isEmpty() || studentId == null) {

            return new StudentResponse(
                    Collections.emptyList(),
                    0,
                    0,
                    0,
                    0,
                    true,
                    ""
            );
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sorting.sorting(sortBy, sortDir));

        Page<Student> page = studentDao.findByStuIdIn(studentId, pageable);

        List<StudentResponseDto> studentsById = page
                .getContent()
                .stream()
                .map(StudentConvertDto::studentToStudentResponseDto)
                .toList();

        StudentResponse response = new StudentResponse(
                studentsById,
                page.getNumber(),
                page.getSize(),
                page.getNumberOfElements(),
                page.getTotalPages(),
                page.isLast(),
                page.getSort().toString()
        );

        // List<StudentResponseDto> students = studentDao.findAllById(studentId).stream().map(StudentConvertDto::studentToStudentResponseDto).toList();
        return response;
    }

}
