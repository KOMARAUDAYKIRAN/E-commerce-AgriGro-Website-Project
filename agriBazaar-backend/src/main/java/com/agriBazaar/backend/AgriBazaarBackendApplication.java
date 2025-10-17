package com.agriBazaar.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgriBazaarBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(AgriBazaarBackendApplication.class, args);
	}
}
