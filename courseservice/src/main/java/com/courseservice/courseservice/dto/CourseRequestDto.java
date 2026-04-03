package com.courseservice.courseservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CourseRequestDto {

    @NotBlank
    @Size(min = 4, message = "Course name must be at least 4 characters")
    private String course_Name;

    @NotBlank(message = "Course Description could not be Empty!")
    private String course_description;

    @NotBlank(message = "Instructor name could not be Empty!")
    private String course_Instructor_Name;

    @NotBlank(message = "Course description could not be Empty!")
    private String course_duration;

    @NotNull(message = "Price cannot be null!")
    @Positive(message = "Price must be greater than 0")
    private double course_price;

    @NotBlank(message = "Course Category cannot be Empty!")
    private String course_category;
}
