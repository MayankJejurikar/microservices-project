package com.courseservice.courseservice;

import com.courseservice.courseservice.dto.CourseRequestDto;
import com.courseservice.courseservice.entity.Course;
import com.courseservice.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseserviceApplication implements CommandLineRunner {

   /* @Autowired
    private CourseService courseService;*/

    public static void main(String[] args) {
        SpringApplication.run(CourseserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

       /* courseService.addCourse(new CourseRequestDto("NDA 2027 Batch","This course covers all topics Of Mathematics, English, Genral Science,Economics,History Civics for NDA","Joseph Sir,Roy Sir,Dipti Maam","2 years",21000,"Defense Exam Preparation"));
        courseService.addCourse(new CourseRequestDto("Java Fundamentals", "Learn core Java concepts", "Rahul Sharma", "3 Months", 5000.0, "Programming"));
        courseService.addCourse(new CourseRequestDto("Spring Boot Mastery", "Build microservices using Spring Boot", "Anjali Verma", "2 Months", 7000.0, "Backend Development"));
        courseService.addCourse(new CourseRequestDto("Full Stack Web Development", "HTML, CSS, JS, React, Node", "Amit Gupta", "6 Months", 12000.0, "Web Development"));
        courseService.addCourse(new CourseRequestDto("Data Structures & Algorithms", "Master DSA for interviews", "Neha Singh", "4 Months", 8000.0, "Computer Science"));
        courseService.addCourse(new CourseRequestDto("Python for Beginners", "Learn Python from scratch", "Saurabh Mishra", "2.5 Months", 4500.0, "Programming"));
    */}
}
