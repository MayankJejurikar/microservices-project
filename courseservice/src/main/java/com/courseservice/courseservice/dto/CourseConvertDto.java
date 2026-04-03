package com.courseservice.courseservice.dto;

import com.courseservice.courseservice.entity.Course;

public class CourseConvertDto {

    public static Course courseRequestDtoToCourse(CourseRequestDto courseRequestDto){

        Course newCourse=new Course();
        newCourse.setCourseName(courseRequestDto.getCourse_Name());
        newCourse.setCourseInstructorName(courseRequestDto.getCourse_Instructor_Name());
        newCourse.setCourseDescription(courseRequestDto.getCourse_description());
        newCourse.setCourseCategory(courseRequestDto.getCourse_category());
        newCourse.setCourseDuration(courseRequestDto.getCourse_duration());
        newCourse.setCoursePrice(courseRequestDto.getCourse_price());

        return newCourse;
    }

    public static CourseResponseDto courseToCourseResponseDto(Course course){

        CourseResponseDto responseDto=new CourseResponseDto();

        responseDto.setCourse_Id(course.getCourseId());
        responseDto.setCourse_Name(course.getCourseName());
        responseDto.setCourse_Instructor_Name(course.getCourseInstructorName());
        responseDto.setCourse_description(course.getCourseDescription());
        responseDto.setCourse_category(course.getCourseCategory());
        responseDto.setCourse_duration(course.getCourseDuration());
        responseDto.setCourse_price(course.getCoursePrice());

        return responseDto;
    }
}
