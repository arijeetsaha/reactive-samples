package com.arijeet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@ComponentScan("com.arijeet")
public class ReactiveProgrammingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingApplication.class, args);
	}

}
