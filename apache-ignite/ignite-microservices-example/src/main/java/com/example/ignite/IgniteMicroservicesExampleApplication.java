package com.example.ignite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:config/client-node-config.xml",
		"classpath:config/data-node-config.xml",
		"classpath:config/maintenance-service-node-config.xml",
		"classpath:config/vehicle-service-node-config.xml"

})
public class IgniteMicroservicesExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgniteMicroservicesExampleApplication.class, args);
	}
}
