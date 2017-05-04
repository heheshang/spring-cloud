_讲述了通过restTemplate+ribbon去消费服务_


**1** 向服务注册中心注册一个新的服务，这时service-ribbon既是服务提供者，也是服务消费者。配置文件application.yml如下：

**2** 在工程的启动类中,通过@EnableDiscoveryClient向服务中心注册；并且注册了一个bean: restTemplate;通过@ LoadBalanced注册表明，这个restRemplate是负载均衡的。


**3** 通过restTemplate.getForObject方法，service-ribbon 就可以调用service-hi的方法了。并且在调用的工程中并之需要写服务的名，而不是具体的ip.

**4** 访问http://localhost:8764/hi?name=forezp ,浏览器交替显示：
      >
      > hi forezp,i am from port:8762
      >
      > hi forezp,i am from port:8763

      这说明当我们通过调用restTemplate.getForObject("[http://SERVICE-HI/hi?name=](http://service-hi/hi?name=)"+name,String.class)，获取service-hi的方法时，已经做了负载均衡，访问了不同的端口的服务。



**此时架构：**


![此时的架构](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-ribbon\src\main\resources\static\2279594-9f10b702188a129d.png)

* 一个服务注册中心，eureka server,端口为8761
* service-hi工程跑了两个副本，端口分别为8762,8763，分别向服务注册中心注册
* sercvice-ribbon端口为8764,向服务注册中心注册
* 当sercvice-ribbon通过restTemplate调用service-hi的hi接口时，因为用ribbon进行了负载均衡，会轮流的调用service-hi：8762和8763 两个端口的hi接口；


