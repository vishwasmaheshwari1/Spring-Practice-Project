package com.vmspring.schoolapp;

//https://github.com/eazybytes/spring

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.vmspring.schoolapp.repository")
@EntityScan("com.vmspring.schoolapp.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class SchoolAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(SchoolAppApplication.class, args);
	}

}
