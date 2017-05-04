### linux 安装 rabbitmq
```
[root@localhost ~]# yum install erlang
[root@localhost share]# wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.5.0/rabbitmq-server-3.5.0-1.noarch.rpm
[root@localhost software]# rpm -ivh rabbitmq-server-3.5.0-1.noarch.rpm
[root@localhost software]# rabbitmq-server --detached & ps aux |grep rabbitmq

[root@localhost rabbitmq]# /sbin/iptables -I INPUT -p tcp --dport 15672 -j ACCEPT
[root@localhost rabbitmq]# /etc/init.d/iptables save
iptables: Saving firewall rules to /etc/sysconfig/iptables:[  OK  ]
[root@localhost rabbitmq]# service iptables restart
iptables: Setting chains to policy ACCEPT: nat filter      [  OK  ]
iptables: Flushing firewall rules:                         [  OK  ]
iptables: Unloading modules:                               [  OK  ]
iptables: Applying firewall rules:                         [  OK  ]
[root@localhost rabbitmq]# /etc/init.d/iptables status

[root@localhost software]# rabbitmq-plugins enable rabbitmq_management

[root@localhost rabbitmq]# service rabbitmq-server start
[root@localhost software]# service rabbitmq-server restart
[root@localhost rabbitmq]# service rabbitmq-server stop
```


[spring](http://lib.csdn.net/base/javaee "Java EE知识库") Cloud Bus 将分布式的节点和轻量的消息代理连接起来。这可以用于广播配置文件的更改或者其他的管理工作。一个关键的思想就是，消息总线可以为[微服务](http://lib.csdn.net/base/microservice "微服务知识库")做监控，也可以作为应用程序之间相互通讯。本文要讲述的是用AMQP实现通知微服务[架构](http://lib.csdn.net/base/architecture "大型网站架构知识库")的配置文件的更改。

### 一、准备工作

本文还是基于上一篇文章来实现。按照官方文档，我们只需要在配置文件中配置 spring-cloud-starter-bus-amqp ；这就是说我们需要装rabbitMq，点击[rabbitmq](http://www.rabbitmq.com/)下载。至于怎么使用 rabbitmq，[搜索引擎](http://lib.csdn.net/base/searchengine "搜索引擎知识库")下。

### 二、改造config-client

在pom文件加入spring-cloud-starter-bus-amqp，完整的配置文件如下：
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.forezp</groupId>
    <artifactId>config-client</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>config-client</name>
    <description>Demo project for Spring Boot</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.2.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.retry</groupId>
            <artifactId>spring-retry</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
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

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.RC1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

    <repositories>
        <repository>
            <id>spring-milestones</id>
            <name>Spring Milestones</name>
            <url>https://repo.spring.io/milestone</url>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>


</project>
```

在配置文件中加入：

```hljs avrasm has-numbering

spring:
  application:
    name: config-eureka-bus-client
  cloud:
    config:
      label: master
      profile: dev
      discovery:
        enabled: true
        service-id: config-server-eureka-client
  rabbitmq:
    host: 192.168.61.129
    port: 5672
    username: admin
    password: 123456
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
server:
  port: 8883
management:
  security:
    enabled: false


```
如果rabbitmq有用户名密码，输入即可。

依次启动eureka-server、config-server-rureka-client,启动两个config-eureka-bus-client，端口为：8882、8883。

访问http://localhost:8882/hi 或者http://localhost:8883/hi 浏览器显示：
>
> foo version 3

这时我们去[代码仓库](https://github.com/forezp/SpringcloudConfig/blob/master/respo/config-client-dev.properties)将foo的值改为"foo version 4"，即改变配置文件foo的值。如果是传统的做法，可以需要重启服务，才能达到配置文件的更新。此时，我们只需要用post请求：http://localhost:8883/bus/refresh：


这时我们再访问http://localhost:8881/hi 或者http://localhost:8882/hi 浏览器显示：
>
> foo version 4

另外，/bus/refresh接口可以指定服务，即使用"destination"参数，比如 "/bus/refresh?destination=customers:**" 即刷新服务名为customers的所有服务，不管ip。

### 三、分析

此时的架构图：
![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-config-eureka-bus-client\src\main\resources\static\2279594-9a119d83cf90069f.png)


当[Git](http://lib.csdn.net/base/git "Git知识库")文件更改的时候，通过pc端用post 向端口为8882的config-client发送请求/bus/refresh／；此时8882端口会发送一个消息，由消息总线向其他服务传递，从而使整个微服务集群都达到更新配置文件。

### 四、其他

可以用作自定义的Message Broker,只需要spring-cloud-starter-bus-amqp, 然后再配置文件写上配置即可，同上。

Tracing Bus Events：  
需要设置：spring.cloud.bus.trace.enabled=true，如果那样做的话，那么Spring Boot TraceRepository（如果存在）将显示每个服务实例发送的所有事件和所有的ack,比如：（来自官网）

``` json
{ "timestamp": "2015-11-26T10:24:44.411+0000", 
"info": { "signal": "spring.cloud.bus.ack", "type": "RefreshRemoteApplicationEvent", "id": "c4d374b7-58ea-4928-a312-31984def293b", 
"origin": "stores:8081", "destination": "*:**" } },
{ "timestamp": "2015-11-26T10:24:41.864+0000", 
"info": { "signal": "spring.cloud.bus.sent", "type": "RefreshRemoteApplicationEvent", "id": "c4d374b7-58ea-4928-a312-31984def293b", 
"origin": "customers:9000", "destination": "*:**" } }, 
{ "timestamp": "2015-11-26T10:24:41.862+0000", 
"info": { "signal": "spring.cloud.bus.ack", "type": "RefreshRemoteApplicationEvent", "id": "c4d374b7-58ea-4928-a312-31984def293b", 
"origin": "customers:9000", "destination": "*:**" } }

```