package com.cognizant.app.lms.courses;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class LmCoursesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmCoursesApplication.class, args);
		System.out.println("LMS Courses Service Started");
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
