### 一、简介

在分布式系统中，spring cloud config 提供一个服务端和客户端去提供可扩展的配置服务。我们可用用配置服务中心区集中的管理所有的服务的各种环境配置文件。配置服务中心采用[Git](http://lib.csdn.net/base/git "Git知识库")的方式存储配置文件，因此我们很容易部署修改，有助于对环境配置进行版本管理。

**1** 在程序的入口Application类加上@EnableConfigServer注解开启配置服务器。


需要在配置中心配置下：

```hljs avrasm has-numbering
spring.application.name=config-server
server.port=8888


spring.cloud.config.server.git.uri=https://github.com/forezp/SpringcloudConfig/
spring.cloud.config.server.git.searchPaths=respo
spring.cloud.config.label=master
spring.cloud.config.server.git.username=your username
spring.cloud.config.server.git.password=your password
```
* spring.cloud.config.server.git.uri：配置git仓库地址
* spring.cloud.config.server.git.searchPaths：配置仓库路径
* spring.cloud.config.label：配置仓库的分支
* spring.cloud.config.server.git.username：访问git仓库的用户名
* spring.cloud.config.server.git.password：访问git仓库的用户密码

远程仓库https://github.com/forezp/SpringcloudConfig/ 中又个文件config-client-dev.properties文件中有一个属性：
>
> foo = foo version 3

启动程序：访问http://localhost:8888/foo/dev

```hljs json has-numbering
{"name":"foo","profiles":["dev"],"label":"master",
"version":"792ffc77c03f4b138d28e89b576900ac5e01a44b","state":null,"propertySources":[]}
```

证明配置服务中心可以从远程程序获取配置信息。

http请求地址和资源文件映射如下:
* /{application}/{profile}[/{label}]
* /{application}-{profile}.yml
* /{label}/{application}-{profile}.yml
* /{application}-{profile}.properties
* /{label}/{application}-{profile}.properties

### 三、构建一个config client

重新创建一个springboot项目，取名为config-client,其pom文件：

其配置文件：

```hljs avrasm has-numbering
spring.application.name=config-client
spring.cloud.config.label=master
spring.cloud.config.profile=dev
spring.cloud.config.uri= http://localhost:8888/
server.port=8881
```

* spring.cloud.config.label 指明远程仓库的分支
* spring.cloud.config.profile

  * dev开发环境配置文件
  * test测试环境
  * pro正式环境

* spring.cloud.config.uri= http://localhost:8888/ 指明配置服务中心的网址。

打开网址访问：http://localhost:8881/hi， 网页显示：
>
> foo version 3

这就说明，config-client从config-server获取了foo的属性，而config-server是从git仓库读取的,如图：

![](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-config-client\src\main\resources\static\2279594-40ecbed6d38573d9.png )
