package com.himashana.dkn.dkn_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DknBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(DknBackendApplication.class, args);
	}

}
