package com.studentservice.studentservice.controller;

import com.studentservice.studentservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentExistController {

    @Autowired
    private StudentService service;

    @GetMapping("/exist/{student_id}")
    public void getStudent(@PathVariable String student_id)
    {

    }
}
