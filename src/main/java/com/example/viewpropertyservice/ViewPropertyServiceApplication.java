package com.example.viewpropertyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ViewPropertyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ViewPropertyServiceApplication.class, args);
	}

}
