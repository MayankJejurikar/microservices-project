package com.courseservice.courseservice.dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CourseResponseDto {

    private int course_Id;
    private String course_Name;
    private String course_description;
    private String course_Instructor_Name;
    private String course_duration;
    private double course_price;
    private String course_category;
}
