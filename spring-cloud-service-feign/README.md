### 一、Feign简介

Feign是一个声明式的web服务客户端，它使得写web服务变得更简单。使用Feign,只需要创建一个接口并注解。它具有可插拔的注解特性，包括Feign 注解和JAX-RS注解。Feign同时支持可插拔的编码器和解码器。[spring](http://lib.csdn.net/base/javaee "Java EE知识库") cloud对Spring mvc添加了支持，同时在spring web中次用相同的HttpMessageConverter。当我们使用feign的时候，spring cloud 整和了Ribbon和eureka去提供负载均衡。

简而言之：
* feign采用的是接口加注解
* feign 整合了ribbon

**1** 向服务注册中心注册它自己，这时service-ribbon既是服务提供者，也是服务消费者,配置文件application.yml

**2** 在程序的入口类，需要通过注解@EnableFeignClients来开启feign:
**3** 定义一个feign接口类,通过@ FeignClient（"服务名"），来指定调用哪个服务：

**4** 访问http://localhost:8765/hi?name=forezp,浏览器交替显示：

      >
      > hi forezp,i am from port:8762
      >
      > hi forezp,i am from port:8763

#### 四、更改feign的配置

**1** 在声明feignclient的时候，不仅要指定服务名，同时需要制定服务配置类：

    @FeignClient(name = "stores", configuration = FooConfiguration.class) 
    public interface StoreClient { //.. }



**2** 重写配置，需要加@Configuration注解，并重写下面的两个bean,栗子：


    @Configuration 
    public class FooConfiguration     { 
        @Bean 
        public Contract feignContractg() { 
            return new feign.Contract.Default(); 
         } 
        @Bean 
        public BasicAuthRequestInterceptor basicAuthRequestInterceptor() { 
        return new BasicAuthRequestInterceptor("user", "password"); } 
    }

