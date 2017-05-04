### [第七篇: 高可用的分布式配置中心(Spring Cloud Config)](http://blog.csdn.net/forezp/article/details/70037513)

上一篇内容讲述，一个服务如何从配置中心读取文件，配置中心如何从远程[Git](http://lib.csdn.net/base/git "Git知识库")读取配置文件，当服务很多时，都需要同时从配置中心读取文件的时候，这时我们可以考虑将配置中心做成一个[微服务](http://lib.csdn.net/base/microservice "微服务知识库")，并且将其集群化，从而达到高可用，[架构](http://lib.csdn.net/base/architecture "大型网站架构知识库")图如下：

![架构图](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-config-server-eureka-client\src\main\resources\static\2279594-babe706075d72c58.png)

### 一、准备工作

继续使用上一篇文章的工程，创建一个eureka-server工程，用作服务中心。


``` xml
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
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

配置文件：
``` yaml
spring:
  application:
    name: config-server-eureka-client
  cloud:
    config:
      server:
        git:
          uri: https://github.com/forezp/SpringcloudConfig/
          search-paths: respo
          username: heheshang
          password: shangak47
      label: master
server:
  port: 8889
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/


```
客户端配置详见 spring-cloud-config-eureka-client


