package com.apigatewayservice.apigatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.apigatewayservice.apigatewayservice.util.JwtUtil;

//@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServiceApplication implements CommandLineRunner {

	@Autowired
	private JwtUtil jwtUtil;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String token1 = jwtUtil.generateToken("Karan Sharma", "USER");
		String token2 = jwtUtil.generateToken("Rama", "ADMIN");
		System.out.println(token1);
		System.out.println(token2);

	}

}
