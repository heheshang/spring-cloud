- **1** 通过注解@EnableEurekaClient 表明自己是一个eurekaclient.

- **2**
  仅仅@EnableEurekaClient是不够的，还需要在配置文件中注明自己的服务注册中心的地址，application.yml配置文件如下：

        eureka: 
            client: 
                serviceUrl: 
                    defaultZone: http://localhost:8761/eureka/ 
        server: 
            port: 8762 
        spring: 
            application: 
                name: service-hi


- **3**
  需要指明spring.application.name,这个很重要，这在以后的服务与服务之间相互调用一般都是根据这个name
  。  
  启动工程，打开[http://localhost:8761](http://localhost:8761/)
  ，即eureka server 的网址：

- **4** 你会发现一个服务已经注册在服务中了，服务名为SERVICE-HI ,端口为7862：
        这时打开[链接](http://localhost:8762/hi?name=forezp) ，你会在浏览器上看到 :



