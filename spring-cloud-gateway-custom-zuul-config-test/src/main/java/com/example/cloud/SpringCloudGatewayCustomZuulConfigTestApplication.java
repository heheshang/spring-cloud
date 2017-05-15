package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringCloudGatewayCustomZuulConfigTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayCustomZuulConfigTestApplication.class, args);
	}

	@RequestMapping(value = "/available")
	public String available() {
		System.out.println("Spring in Action");
		return "Spring in Action";
	}

	@RequestMapping(value = "/checked-out")
	public String checkedOut() {
		return "Spring Boot in Action";
	}

	@RequestMapping(value = "/book")
	public String book(){
		return "book";
	}
}
