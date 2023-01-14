package com.rest.service.consumingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.rest.service.consumingservice.proxy")
public class ConsumingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingServiceApplication.class, args);
	}

}
