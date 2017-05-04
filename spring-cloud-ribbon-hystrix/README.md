_讲述了通过restTemplate+ribbon去消费服务_


**1** 向服务注册中心注册一个新的服务，这时service-ribbon既是服务提供者，也是服务消费者。配置文件application.yml如下：

**2** 在工程的启动类中,通过@EnableDiscoveryClient向服务中心注册；并且注册了一个bean: restTemplate;通过@ LoadBalanced注册表明，这个restRemplate是负载均衡的。


**3** 通过restTemplate.getForObject方法，service-ribbon-hystrix 就可以调用service-hi的方法了。并且在调用的工程中并之需要写服务的名，而不是具体的ip.

**4** 访问http://localhost:8768/hi?name=forezp ,浏览器交替显示：

 > > hi forezp,i am from port:8762

 > > hi forezp,i am from port:8763

      这说明当我们通过调用restTemplate.getForObject("[http://SERVICE-HI/hi?name=](http://service-hi/hi?name=)"+name,String.class)，获取service-hi的方法时，已经做了负载均衡，访问了不同的端口的服务。



**此时架构：**


![此时的架构](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-ribbon\src\main\resources\static\2279594-9f10b702188a129d.png)

* 一个服务注册中心，eureka server,端口为8761
* service-hi工程跑了两个副本，端口分别为8762,8763，分别向服务注册中心注册
* service-ribbon-hystrix端口为8768,向服务注册中心注册
* 当service-ribbon-hystrix通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；

### 五、Circuit Breaker: Hystrix Dashboard (断路器：hystrix 仪表盘)

基于service-ribbon 改造下：

pom.xml加入：

```hljs xml has-numbering
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
        </dependency>
```

在主程序入口中加入@EnableHystrixDashboard注解，开启hystrixDashboard：

```hljs java has-numbering
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
public class ServiceRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
```

打开浏览器：访问 http://localhost:8768/hystrix ,界面如下：

![jpg](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-ribbon-hystrix\src\main\resources\static\2279594-64f5fa9d0d96ee21.png)

点击monitor
stream，进入下一个界面，访问：http://localhost:8768/hi?name=forezp

此时会出现监控界面：

![jpg](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-ribbon-hystrix\src\main\resources\static\2279594-755cd7ce5c066649.png)