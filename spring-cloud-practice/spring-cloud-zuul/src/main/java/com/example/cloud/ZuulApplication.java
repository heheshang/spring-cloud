package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulApplication {
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}
