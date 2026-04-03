package com.enrollmentservice.enrollmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private String enrollmentDate;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}
