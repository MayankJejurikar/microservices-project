package com.enrollmentservice.enrollmentservice.dto;

import com.enrollmentservice.enrollmentservice.entity.EnrollmentStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EnrollmentResponseDto {

    private int enrollmentId;
    private int studentId;
    private int courseId;
    private String enrollmentDate;
    private EnrollmentStatus status;

}
