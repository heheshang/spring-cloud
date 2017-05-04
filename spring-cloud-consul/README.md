这篇文章主要介绍 [spring](http://lib.csdn.net/base/javaee "Java EE知识库") cloud consul 组件，它是一个提供服务发现和配置的工具。consul具有分布式、高可用、高扩展性。

一、consul 简介
-----------

consul 具有以下性质：
* 服务发现：consul通过http 方式注册服务，并且服务与服务之间相互感应。
* 服务健康监测
* key/value 存储
* 多数据中心

consul可运行在mac windows [Linux](http://lib.csdn.net/base/linux "Linux知识库") 等机器上。

二、consul安装
----------

linux

```hljs smalltalk has-numbering
$ mkdir -p $GOPATH/src/github.com/hashicorp && cd $!
$ git clone https://github.com/hashicorp/consul.git
$ cd consul
$ make bootstrap
$ make bootstrap
```

windows下安装：

解压 文件：
```
F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-consul\src\main\resources\static
```
设置环境变量：

```hljs tex has-numbering
计算机  右键  属性 高级属性设置环境变量设置

在path下加上：E:\\programfiles\\consul；
```



cmd启动：
>
> consul agent -dev
![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-consul\src\main\resources\static\QQ截图20170504111016.jpg)

三、构建工程
------

构建一个consul-miya的springboot工程，导入依赖pring-cloud-starter-consul-discovery，其依赖文件：

``` xml
<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-consul-discovery</artifactId>
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
在其入口文件ConsulMiyaApplication加入注解@EnableDiscoveryClient，开启服务发现：
``` java
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SpringCloudConsulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsulApplication.class, args);
	}
	@RequestMapping("/hi")
	public String home() {
		return "hi ,i'm miya";
	}
}

```
在其配置文件application.yml指定consul服务的端口为8500：

``` yml
spring:
  cloud:
    consul:
      host: localhost
      port: 8500
      discovery:
        health-check-path: ${management.contextPath}/health
        health-check-interval: 15s
        instance-id: consul-miya
  application:
    name: consul-miya
server:
  port: 8502
```
打开网址：[http://localhost:8500](http://localhost:8500/) ，可以看到界面，相关服务发现的界面。

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-consul\src\main\resources\static\QQ截图20170504111152.jpg)