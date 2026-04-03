package com.enrollmentservice.enrollmentservice.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {

    private int course_Id;
    private String course_Name;
    private String course_description;
    private String course_Instructor_Name;
    private String course_duration;
    private double course_price;
    private String course_category;
}
