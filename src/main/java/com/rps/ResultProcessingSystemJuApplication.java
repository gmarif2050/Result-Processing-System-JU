package com.rps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResultProcessingSystemJuApplication {

	
	
	public static void main(String[] args) {
		SpringApplication.run(ResultProcessingSystemJuApplication.class, args);
		
		System.out.println(System.getProperty("user.home"));
	}

}
