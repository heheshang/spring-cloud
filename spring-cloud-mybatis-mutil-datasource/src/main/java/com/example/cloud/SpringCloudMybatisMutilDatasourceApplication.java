package com.example.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.example.cloud.dao.cluster")
public class SpringCloudMybatisMutilDatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudMybatisMutilDatasourceApplication.class, args);
	}
}
