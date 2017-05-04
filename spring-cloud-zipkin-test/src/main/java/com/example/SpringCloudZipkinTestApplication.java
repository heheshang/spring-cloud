package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class SpringCloudZipkinTestApplication {
	private static Logger logger = LoggerFactory.getLogger(SpringCloudZipkinTestApplication.class);

	@Autowired
	private RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZipkinTestApplication.class, args);
	}

	@RequestMapping("/hi")
	public String home() {
		logger.info(" hi is being called");
		return "hi i'm miya ！";
	}

	@RequestMapping("/miya")
	public String info() {
		logger.info(" info is being called");
		return restTemplate.getForObject("http://localhost:8988/info", String.class);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
