### 三、改造config-client

将其注册微 eureka服务，pom文件配置：
``` xml
       <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
```

配置文件：bootstrap.yml
``` yml
spring:
  application:
    name: config-client
  cloud:
    config:
      label: master
      profile: dev
      discovery:
        enabled: true
        service-id: config-server-eureka-client
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8882
```
* spring.cloud.config.discovery.enabled 是非从配置中心读取文件
* spring.cloud.config.discovery.serviceId 配置中心的servieId，即服务名

这时发现，在读取配置文件不再写ip地址，而是服务名，这时如果配置服务部署多份，通过负载均衡，从而高可用。

一次启动eureka-servr,config-server-eureka-client,config-client  
访问网址：http://localhost:8761

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-config-eureka-client\src\main\resources\static\2279594-1639fdb713faa405.png)


访问http://localhost:8881/hi， 浏览器显示：
>
> foo version 3
