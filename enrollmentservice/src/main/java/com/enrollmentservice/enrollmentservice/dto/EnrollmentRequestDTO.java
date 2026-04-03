package com.enrollmentservice.enrollmentservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EnrollmentRequestDTO {

    @NotNull(message = "Student ID is required")
    @Positive(message = "Student ID must be positive")
    private Integer student_Id;

    @NotNull(message = "Course ID is required")
    @Positive(message = "Course ID must be positive")
    private Integer course_Id;
}
