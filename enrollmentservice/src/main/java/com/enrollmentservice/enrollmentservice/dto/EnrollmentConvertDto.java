package com.enrollmentservice.enrollmentservice.dto;

import com.enrollmentservice.enrollmentservice.entity.Enrollment;

public class EnrollmentConvertDto {

    public static EnrollmentResponseDto enrollmentToResponseDto(Enrollment enrollment) {

        EnrollmentResponseDto responseDto = new EnrollmentResponseDto();

        responseDto.setEnrollmentId(enrollment.getEnrollmentId());
        responseDto.setStudentId(enrollment.getStudentId());
        responseDto.setCourseId(enrollment.getCourseId());
        responseDto.setEnrollmentDate(enrollment.getEnrollmentDate());
        responseDto.setStatus(enrollment.getStatus());

        return responseDto;
    }
}
