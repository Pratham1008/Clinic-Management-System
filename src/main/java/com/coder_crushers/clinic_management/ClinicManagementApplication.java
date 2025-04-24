package com.coder_crushers.clinic_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class ClinicManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagementApplication.class, args);
	}

	@GetMapping("/ping")
	public String ping() {
		return "pong";
	}

}
