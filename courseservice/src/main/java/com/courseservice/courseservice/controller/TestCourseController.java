package com.courseservice.courseservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestCourseController {

	@Value("${message}")
	private String message;

	@GetMapping("/course-test")
	public String testing() {

		return "message: " + message;
	}

}
