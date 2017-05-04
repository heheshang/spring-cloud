package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SpringCloudServiceFeignHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudServiceFeignHystrixApplication.class, args);
	}

	private static String sys(String a,String b,String c,int ss){
		return a+b+c+ss;
	}
	protected static int sss(){
		return 1;
	}
}
