package com.courseservice.courseservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "course_Id")
    private int courseId;

    @Column(name = "course_Name")
    private String courseName;

    @Column(name = "course_description")
    private String courseDescription;

    @Column(name = "course_Instructor_Name")
    private String courseInstructorName;

    @Column(name = "course_duration")
    private String courseDuration;

    @Column(name = "course_price")
    private double coursePrice;

    @Column(name = "course_category")
    private String courseCategory;
}
