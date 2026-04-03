package com.studentservice.studentservice;

import com.studentservice.studentservice.dto.StudentRequestDto;
import com.studentservice.studentservice.entity.Student;
import com.studentservice.studentservice.service.StudentService;
import com.studentservice.studentservice.service.StudentServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentserviceApplication implements CommandLineRunner {

/*	@Autowired
	private StudentService service;*/

	public static void main(String[] args) {

		SpringApplication.run(StudentserviceApplication.class, args);

		System.out.println("Student Service started...");

	}


	@Override
	public void run(String... args) throws Exception {

		/*service.addStudent(new StudentRequestDto("Muskan Pateriya","muskanPateriya@gmail.com",84673892992L,"04/01/2000","Amravati"));
		service.addStudent(new StudentRequestDto( "Rahul Sharma", "rahulSharma@gmail.com", 9123456780L, "15/08/1999", "Bhopal"));
		service.addStudent(new StudentRequestDto( "Priya Verma", "priyaVerma@gmail.com", 9876543210L, "22/03/2001", "Indore"));
		service.addStudent(new StudentRequestDto( "Aman Gupta", "amanGupta@gmail.com", 9012345678L, "10/12/1998", "Jabalpur"));
	*/}
}
