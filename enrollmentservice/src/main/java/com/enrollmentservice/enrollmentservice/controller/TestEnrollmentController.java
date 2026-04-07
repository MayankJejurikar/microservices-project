package com.enrollmentservice.enrollmentservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestEnrollmentController {

	@Value("${message}")
	private String message;

	@GetMapping("/test")
	public String testingEnrollment() {

		return "message: " + message;
	}

}
