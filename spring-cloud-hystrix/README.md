在[微服务](http://lib.csdn.net/base/microservice "微服务知识库")[架构](http://lib.csdn.net/base/architecture "大型网站架构知识库")中，我们将业务拆分成一个个的服务，服务与服务之间可以相互调用（RPC）。为了保证其高可用，单个服务又必须集群部署。由于网络原因或者自身的原因，服务并不能保证服务的100%可用，如果单个服务出现问题，调用这个服务就会出现网络延迟，此时若有大量的网络涌入，会形成任务累计，导致服务瘫痪，甚至导致服务"雪崩"。

为了解决这个问题，就出现断路器模型。

为了解决这个问题，就出现断路器模型。
### 一、断路器简介
>
> Netflix has created a library called Hystrix that implements the circuit breaker pattern. In a microservice architecture it is common to have multiple layers of service calls.
>
> . ----摘自官网

Netflix已经创建了一个名为Hystrix的库来实现断路器模式。 在微服务架构中，多层服务调用是非常常见的。

![www](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-hystrix\src\main\resources\static\2279594-08d8d524c312c27d.png)

较底层的服务如果出现故障，会导致连锁故障。当对特定的服务的调用达到一个阀值（hystric 是5秒20次） 断路器将会被打开。

![www](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-hystrix\src\main\resources\static\2279594-8dcb1f208d62046f.png)

断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值。

### 二、准备工作

基于上一篇文章的工程，首先启动：  
基于上一节的工程，启动eureka-server 工程；启动service-hi工程，它的端口为8762;

### 三、在ribbon使用断路器

改造serice-ribbon 工程的代码：

在pox.xml文件中加入：

```hljs xml has-numbering
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-hystrix</artifactId>
</dependency>
```
在程序的入口类加@EnableHystrix：

```hljs java has-numbering
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
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
改造HelloService类，加上@HystrixCommand，并指定fallbackMethod方法。

```hljs java has-numbering

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://SERVICE-HI/hi?name="+name,String.class);
    }

    public String hiError(String name) {
        return "hi,"+name+",sorry,error!";
    }
}
```
启动：service-hystrix 工程，当我们访问http://localhost:8766/hi?name=forezp,
浏览器显示：
>
> hi forezp,i am from port:8762

此时关闭 service-hi
,工程，当我们再访问http://localhost:8766/hi?name=forezp 浏览器会显示：
>
> hi ,forezp,orry,error!

这就证明断路器起作用了。
