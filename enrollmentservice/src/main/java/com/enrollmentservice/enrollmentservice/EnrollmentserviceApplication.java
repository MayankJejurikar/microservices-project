package com.enrollmentservice.enrollmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class EnrollmentserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnrollmentserviceApplication.class, args);
    }

}
