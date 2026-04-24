package com.studentservice.studentservice.controller;

import com.studentservice.studentservice.dto.StudentRequestDto;
import com.studentservice.studentservice.dto.StudentResponse;
import com.studentservice.studentservice.dto.StudentResponseDto;
import com.studentservice.studentservice.entity.Student;
import com.studentservice.studentservice.exception.StudentNotFoundException;
import com.studentservice.studentservice.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

	@Autowired
	private StudentService service;

	@GetMapping
	public ResponseEntity<StudentResponse> getStudents(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "stuId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {

		StudentResponse studentsResponse = service.getStudents(pageNumber, pageSize, sortBy, sortDir);

		return ResponseEntity.ok(studentsResponse);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<?> getStudent(
			@PathVariable @Positive(message = "Student ID must be positive") Integer studentId)
			throws InterruptedException {

		// Thread.sleep(7000);
		StudentResponseDto student = service.getStudent(studentId)
				.orElseThrow(() -> new StudentNotFoundException("Student Not found with id :" + studentId));
		return ResponseEntity.ok(student);
	}

	@PostMapping
	public ResponseEntity<StudentResponseDto> addStudent(@Valid @RequestBody StudentRequestDto student,
			@RequestHeader("X-Role") String role) {

		checkAdminAccess(role);

		StudentResponseDto responseDto = service.addStudent(student);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	@PostMapping("/students")
	public ResponseEntity<StudentResponse> getAllStudentByIDs(@RequestBody List<Integer> studentId,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = "stuId", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
			@RequestHeader("X-Role") String role) throws InterruptedException {

		checkAdminAccess(role);
		StudentResponse response = service.getAllStudentByIDs(studentId, pageNumber, pageSize, sortBy, sortDir);

		log.info("Students by IDS ={}", response.getContent());

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping("/{studentId}")
	public ResponseEntity<StudentResponseDto> updateStudent(
			@PathVariable @Positive(message = "Student ID must be positive") Integer studentId,
			@Valid @RequestBody StudentRequestDto student, @RequestHeader("X-Role") String role) {

		checkAdminAccess(role);

		StudentResponseDto responseDto = service.updateStudent(studentId, student);
		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}

	@DeleteMapping("/{studentId}")
	public ResponseEntity deleteStudent(
			@PathVariable @Positive(message = "Student ID must be positive") Integer studentId,
			@RequestHeader("X-Role") String role) {

		checkAdminAccess(role);
		service.deleteStudent(studentId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	private void checkAdminAccess(String role) {
		if (!"ADMIN".equals(role)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
		}
	}

}
