package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudServerZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServerZipkinApplication.class, args);
	}
}
