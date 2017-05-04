在我的第四篇文章[断路器](http://blog.csdn.net/forezp/article/details/69934399)讲述了如何使用断路器，并简单的介绍了下Hystrix Dashboard组件，这篇文章更加详细的介绍Hystrix Dashboard。

一、Hystrix Dashboard简介
---------------------

在微服务架构中为例保证程序的可用性，防止程序出错导致网络阻塞，出现了断路器模型。断路器的状况反应了一个程序的可用性和健壮性，它是一个重要指标。Hystrix Dashboard是作为断路器状态的一个组件，提供了数据监控和友好的图形化界面。

二、准备工作
------

本文的的工程栗子，来源于[第一篇文章](http://blog.csdn.net/forezp/article/details/69696915)的栗子，在它的基础上进行改造。

三、开始改造 spring-cloud-euraka-client
---------------------------------
在pom的工程文件引入相应的依赖：
其中，这三个依赖是必须的，缺一不可。

``` xml
<dependencies>
        <!--必须-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<!--必须-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<!--必须-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

**注：** 在程序的入口ServiceHiApplication类，加上@EnableHystrix注解开启断路器，这个是必须的，并且需要在程序中声明断路点HystrixCommand；加上@EnableHystrixDashboard注解，开启HystrixDashboard
``` java
package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableHystrix
@EnableHystrixDashboard
public class SpringCloudEurekaClientHystrixDashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurekaClientHystrixDashboardApplication.class, args);
	}

	@Value("${server.port}")
	String port;
	@RequestMapping("/hi")
	@HystrixCommand(fallbackMethod = "hiError")
	public String home(@RequestParam String name) {
		return "hi "+name+",i am from port:" +port;
	}

	public String hiError(String name) {
		return "hi,"+name+",sorry,error!";
	}
}

```
运行程序： 依次开启eureka-server 和spring-cloud-eureka-client-hystrix-dashboard.

四、Hystrix Dashboard图形展示
-----------------------

打开http://127.0.0.1:8762/hystrix.stream， 可以看到一些具体的数据：

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-eureka-client-hystrix-dashboard\src\main\resources\static\QQ截图20170504095300.jpg)

打开 http://127.0.0.1:8762/hystrix 可以看见以下界面：

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-eureka-client-hystrix-dashboard\src\main\resources\static\QQ截图20170504095634.jpg)

在界面依次输入：http://127.0.0.1:8762/hystrix.stream 、2000 、miya  
；点确定。

在另一个窗口输入： http://127.0.0.1:8762/hi?name=forezp

重新刷新hystrix.stream网页，你会看到良好的图形化界面：

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-eureka-client-hystrix-dashboard\src\main\resources\static\QQ截图20170504095817.jpg)