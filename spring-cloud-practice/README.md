***一、构建 maven parent***

**1、pom.xml：**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.example.practice</groupId>
	<artifactId>spring-cloud-practice</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<name>spring-cloud-practice</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Dalston.SR1</spring-cloud.version>
	</properties>


	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>


	<modules>
		<module>spring-cloud-core</module>
		<module>spring-cloud-config</module>
		<module>spring-cloud-consumer</module>
		<module>spring-cloud-eureka</module>
		<module>spring-cloud-zuul</module>
        <module>spring-cloud-provider</module>
    </modules>

	<repositories>
		<repository>
			<id>nexus</id>
			<name>Team Nexus Repository</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public</url>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>nexus</id>
			<name>Team Nexus Repository</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public</url>
		</pluginRepository>
	</pluginRepositories>

</project>
```
***二、spring-cloud-config 配置中心***

**1、pom.xml:**

```xml
 <parent>
        <groupId>com.example.practice</groupId>
        <artifactId>spring-cloud-practice</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <artifactId>spring-cloud-config</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
**2、配置文件**
```properties
spring.application.name=config
server.port=8888

# 使用文件系统
spring.profiles.active=native

```
**3、SpringCloudConfigApplication.java**
```java
package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigApplication.class, args);
	}
}

```
***三、spring-cloud-core 框架核心包***

**1、pom.xml**
```xml
<parent>
		<groupId>com.example.practice</groupId>
		<artifactId>spring-cloud-practice</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<artifactId>spring-cloud-core</artifactId>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>4.3.9.RELEASE</version>
		</dependency>

	</dependencies>
```
**2、CoreAutoConfiguration.java**
```java
package com.example.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/6/8 0008.
 *
 * https://github.com/charlesvhe/spring-cloud-practice/blob/master/core/pom.xml
 */
@Configuration
@EnableWebMvc
@RibbonClients(defaultConfiguration = DefaultRibbonConfiguration.class)
public class CoreAutoConfiguration extends WebMvcConfigurerAdapter {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new CoreHttpRequestInterceptor());
		return restTemplate;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CoreHeaderInterceptor());
	}
}

```
**3、CoreHeaderInterceptor.java**
```java
package com.example.cloud.config;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class CoreHeaderInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CoreHeaderInterceptor.class);

	public static final String HEADER_LABEL = "x-label";
	public static final String HEADER_LABEL_SPLIT = ",";

	public static final HystrixRequestVariableDefault<List<String>> label = new HystrixRequestVariableDefault<>();


	public static void initHystrixRequestContext(String labels) {
		logger.info("label: " + labels);
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}

		if (!StringUtils.isEmpty(labels)) {
			CoreHeaderInterceptor.label.set(Arrays.asList(labels.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT)));
		} else {
			CoreHeaderInterceptor.label.set(Collections.emptyList());
		}
	}

	public static void shutdownHystrixRequestContext() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CoreHeaderInterceptor.initHystrixRequestContext(request.getHeader(CoreHeaderInterceptor.HEADER_LABEL));
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		CoreHeaderInterceptor.shutdownHystrixRequestContext();
	}
}

```
**4、CoreHttpRequestInterceptor.java**












































![jpg](http://github.com/heheshang/spring-cloud/blob/master/spring-cloud-practice/static/QQ%E6%88%AA%E5%9B%BE20170613111731.jpg)