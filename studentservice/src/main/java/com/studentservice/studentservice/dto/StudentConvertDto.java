package com.studentservice.studentservice.dto;

import com.studentservice.studentservice.entity.Student;

public class StudentConvertDto {

    public static Student studentRequestDtoToStudententity(StudentRequestDto studentRequestDto) {

        Student student = new Student();
        student.setStuName(studentRequestDto.getStu_Name());
        student.setStuEmail(studentRequestDto.getStu_email());
        student.setStuAddress(studentRequestDto.getStu_Address());
        student.setStuMobileNo(studentRequestDto.getStu_Mobile_no());
        student.setStuDOB(studentRequestDto.getStu_DOB());

        return student;
    }

    public static StudentResponseDto studentToStudentResponseDto(Student student) {

        StudentResponseDto studentResponseDto = new StudentResponseDto();
        studentResponseDto.setStu_Id(student.getStuId());
        studentResponseDto.setStu_Name(student.getStuName());
        studentResponseDto.setStu_Address(student.getStuAddress());
        studentResponseDto.setStu_email(student.getStuEmail());
        studentResponseDto.setStu_Mobile_no(student.getStuMobileNo());
        studentResponseDto.setStu_DOB(student.getStuDOB());

        return studentResponseDto;
    }
}
