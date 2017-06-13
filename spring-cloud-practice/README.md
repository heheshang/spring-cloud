***一、构建 maven parent***

**1、pom.xml：**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.example.practice</groupId>
	<artifactId>spring-cloud-practice</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>pom</packaging>

	<name>spring-cloud-practice</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.3.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Dalston.SR1</spring-cloud.version>
	</properties>


	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>


	<modules>
		<module>spring-cloud-core</module>
		<module>spring-cloud-config</module>
		<module>spring-cloud-consumer</module>
		<module>spring-cloud-eureka</module>
		<module>spring-cloud-zuul</module>
        <module>spring-cloud-provider</module>
    </modules>

	<repositories>
		<repository>
			<id>nexus</id>
			<name>Team Nexus Repository</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public</url>
		</repository>
	</repositories>

	<pluginRepositories>
		<pluginRepository>
			<id>nexus</id>
			<name>Team Nexus Repository</name>
			<url>http://maven.aliyun.com/nexus/content/groups/public</url>
		</pluginRepository>
	</pluginRepositories>

</project>
```

***二、spring-cloud-config 配置中心***

**1、pom.xml:**

```xml
 <parent>
        <groupId>com.example.practice</groupId>
        <artifactId>spring-cloud-practice</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <artifactId>spring-cloud-config</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```
**2、配置文件**
```properties
spring.application.name=config
server.port=8888

# 使用文件系统
spring.profiles.active=native

```
**3、SpringCloudConfigApplication.java**
```java
package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigApplication.class, args);
	}
}

```
**4、其他服务配置**
```text
参考：spring-cloud-practice\spring-cloud-config\src\main\resources\config
```

***三、spring-cloud-core 框架核心包***

**1、pom.xml**
```xml
<parent>
		<groupId>com.example.practice</groupId>
		<artifactId>spring-cloud-practice</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>

	<artifactId>spring-cloud-core</artifactId>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>

		<!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>4.3.9.RELEASE</version>
		</dependency>

	</dependencies>
```
**2、CoreAutoConfiguration.java**
```java
package com.example.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/6/8 0008.
 *
 * https://github.com/charlesvhe/spring-cloud-practice/blob/master/core/pom.xml
 */
@Configuration
@EnableWebMvc
@RibbonClients(defaultConfiguration = DefaultRibbonConfiguration.class)
public class CoreAutoConfiguration extends WebMvcConfigurerAdapter {

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new CoreHttpRequestInterceptor());
		return restTemplate;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CoreHeaderInterceptor());
	}
}

```
**3、CoreHeaderInterceptor.java**
```java
package com.example.cloud.config;


import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableDefault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class CoreHeaderInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(CoreHeaderInterceptor.class);

	public static final String HEADER_LABEL = "x-label";
	public static final String HEADER_LABEL_SPLIT = ",";

	public static final HystrixRequestVariableDefault<List<String>> label = new HystrixRequestVariableDefault<>();


	public static void initHystrixRequestContext(String labels) {
		logger.info("label: " + labels);
		if (!HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.initializeContext();
		}

		if (!StringUtils.isEmpty(labels)) {
			CoreHeaderInterceptor.label.set(Arrays.asList(labels.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT)));
		} else {
			CoreHeaderInterceptor.label.set(Collections.emptyList());
		}
	}

	public static void shutdownHystrixRequestContext() {
		if (HystrixRequestContext.isCurrentThreadInitialized()) {
			HystrixRequestContext.getContextForCurrentThread().shutdown();
		}
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CoreHeaderInterceptor.initHystrixRequestContext(request.getHeader(CoreHeaderInterceptor.HEADER_LABEL));
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		CoreHeaderInterceptor.shutdownHystrixRequestContext();
	}
}

```
**4、CoreHttpRequestInterceptor.java**
```java
package com.example.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class CoreHttpRequestInterceptor implements ClientHttpRequestInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(CoreHttpRequestInterceptor.class);
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

		String header = StringUtils.collectionToDelimitedString(CoreHeaderInterceptor.label.get(), CoreHeaderInterceptor.HEADER_LABEL_SPLIT);
		logger.info("label: "+header);
		requestWrapper.getHeaders().add(CoreHeaderInterceptor.HEADER_LABEL, header);

		return execution.execute(requestWrapper, body);
	}
}

```
**5、DefaultRibbonConfiguration.java**
```java
package com.example.cloud.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.PropertiesFactory;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class DefaultRibbonConfiguration {
	@Value("${ribbon.client.name:#{null}}")
	private String name;

	@Autowired(required = false)
	private IClientConfig config;

	@Autowired
	private PropertiesFactory propertiesFactory;

	@Bean
	public IRule ribbonRule() {
		if ( StringUtils.isEmpty(name)) {
			return null;
		}

		if (this.propertiesFactory.isSet(IRule.class, name)) {
			return this.propertiesFactory.get(IRule.class, config, name);
		}

		// 默认配置
		LabelAndWeightMetadataRule rule = new LabelAndWeightMetadataRule();
		rule.initWithNiwsConfig(config);
		return rule;
	}
}

```
**6、LabelAndWeightMetadataRule.java**
```java
package com.example.cloud.config;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class LabelAndWeightMetadataRule extends ZoneAvoidanceRule {
	public static final String META_DATA_KEY_LABEL_AND = "labelAnd";
	public static final String META_DATA_KEY_LABEL_OR = "labelOr";

	public static final String META_DATA_KEY_WEIGHT = "weight";

	private Random random = new Random();

	@Override
	public Server choose(Object key) {
		List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
		if ( CollectionUtils.isEmpty(serverList)) {
			return null;
		}

		// 计算总值并剔除0权重节点
		int totalWeight = 0;
		Map<Server, Integer> serverWeightMap = new HashMap<>();
		for (Server server : serverList) {
			Map<String, String> metadata = ((DiscoveryEnabledServer ) server).getInstanceInfo().getMetadata();

			// 优先匹配label
			String labelOr = metadata.get(META_DATA_KEY_LABEL_OR);
			if(!StringUtils.isEmpty(labelOr)){
				List<String> metadataLabel = Arrays.asList(labelOr.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT));
				for (String label : metadataLabel) {
					if(CoreHeaderInterceptor.label.get().contains(label)){
						return server;
					}
				}
			}

			String labelAnd = metadata.get(META_DATA_KEY_LABEL_AND);
			if(!StringUtils.isEmpty(labelAnd)){
				List<String> metadataLabel = Arrays.asList(labelAnd.split(CoreHeaderInterceptor.HEADER_LABEL_SPLIT));
				if(CoreHeaderInterceptor.label.get().containsAll(metadataLabel)){
					return server;
				}
			}

			String strWeight = metadata.get(META_DATA_KEY_WEIGHT);

			int weight = 100;
			try {
				weight = Integer.parseInt(strWeight);
			} catch (Exception e) {
				// 无需处理
			}

			if (weight <= 0) {
				continue;
			}

			serverWeightMap.put(server, weight);
			totalWeight += weight;
		}

		// 权重随机
		int randomWight = this.random.nextInt(totalWeight);
		int current = 0;
		for (Map.Entry<Server, Integer> entry : serverWeightMap.entrySet()) {
			current += entry.getValue();
			if (randomWight <= current) {
				return entry.getKey();
			}
		}

		return null;
	}
}

```
**7、配置文件: 在 resources\META-INF 下 添加spring.factories 中添加**
```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.example.cloud.config.CoreAutoConfiguration
```

***四、服务注册中心 spring-cloud-eureka***

**1、pom.xml**
```xml
	<parent>
		<groupId>com.example.practice</groupId>
		<artifactId>spring-cloud-practice</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
	<modelVersion>4.0.0</modelVersion>

	<artifactId>spring-cloud-eureka</artifactId>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```
**2、启动类 SpringCloudEurekaApplication.java**
```java
package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudEurekaApplication.class, args);
	}
}

```
**3、配置文件**
```yml
spring:
  application:
    name: eureka
  cloud:
    config:
      uri: http://localhost:8888
      profile: dev
      fail-fast: true
server:
  port: 8761
```

**4、其他静态资源文件**
```text
参考：spring-cloud-practice\spring-cloud-eureka\src\main\resources\static
```

***五、服务提供者 spring-cloud-provider or spring-cloud-provider-one***
**1、pom.xml**
```xml
 <parent>
        <artifactId>spring-cloud-practice</artifactId>
        <groupId>com.example.practice</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-cloud-provider</artifactId>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-ribbon</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>

    <dependency>
        <groupId>com.example.practice</groupId>
        <artifactId>spring-cloud-core</artifactId>
        <version>${parent.version}</version>
    </dependency>
</dependencies>
```
**2、配置文件**
```properties
spring.application.name=provider
server.port=18080

spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.profile=dev
# 连不上配置中心不启动
spring.cloud.config.fail-fast=true
```

**3、启动类 ProviderApplication.java**
```java
package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProviderApplication.class);
	}
}

```
**4、其他**
```text
参考：com.example.cloud 包下的vo 和 web
```
***六、服务消费者 spring-cloud-consumer***
**1、pom.xml**
```xml
<parent>
		<groupId>com.example.practice</groupId>
		<artifactId>spring-cloud-practice</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</parent>
	<artifactId>spring-cloud-consumer</artifactId>



	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-ribbon</artifactId>
		</dependency>
		<dependency>
			<groupId>com.example.practice</groupId>
			<artifactId>spring-cloud-core</artifactId>
			<version>${parent.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```
**2、配置文件**

```properties
spring.application.name=consumer
server.port=18090

spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.profile=dev
# 连不上配置中心不启动
spring.cloud.config.fail-fast=true
```
**3、启动类 SpringCloudConsumerApplication.java**
 ```java
 package com.example.cloud;
 
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.autoconfigure.SpringBootApplication;
 import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 
 @SpringBootApplication
 @EnableDiscoveryClient
 public class SpringCloudConsumerApplication {
 
 	public static void main(String[] args) {
 		SpringApplication.run(SpringCloudConsumerApplication.class, args);
 	}
 }

 ```
**4、其他配置**
```text
参考：com.example.cloud.web 包下的
```

***七、网关 spring-cloud-zuul***
**1、pom.xml**
```xml
  <parent>
        <groupId>com.example.practice</groupId>
        <artifactId>spring-cloud-practice</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>spring-cloud-zuul</artifactId>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zuul</artifactId>
        </dependency>


        <dependency>
            <groupId>com.example.practice</groupId>
            <artifactId>spring-cloud-core</artifactId>
            <version>${parent.version}</version>
        </dependency>

        <!--<dependency>-->
        <!--<groupId>org.springframework.cloud</groupId>-->
        <!--<artifactId>spring-cloud-starter-sleuth</artifactId>-->
        <!--</dependency>-->
    </dependencies>
```
**2、启动类：ZuulApplication.java**
```java
package com.example.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulApplication {
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
}

```
**3、配置文件**
```properties
spring.application.name=zuul
server.port=8080

spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.profile=dev
# 连不上配置中心不启动
spring.cloud.config.fail-fast=true
```
**4、自定义过滤器**
```java
package com.example.cloud;

import com.example.cloud.config.CoreHeaderInterceptor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class PreFilter extends ZuulFilter {

	private static final HashMap<String, String> TOKEN_LABEL_MAP = new HashMap<>();

	static {
		TOKEN_LABEL_MAP.put("emt", "EN,Male,Test");
		TOKEN_LABEL_MAP.put("eft", "EN,Female,Test");
		TOKEN_LABEL_MAP.put("cmt", "CN,Male,Test");
		TOKEN_LABEL_MAP.put("cft", "CN,Female,Test");
		TOKEN_LABEL_MAP.put("em", "EN,Male");
		TOKEN_LABEL_MAP.put("ef", "EN,Female");
		TOKEN_LABEL_MAP.put("cm", "CN,Male");
		TOKEN_LABEL_MAP.put("cf", "CN,Female");
	}

	private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

		String lables = TOKEN_LABEL_MAP.get(token);

		logger.info("lable: "+lables);

		CoreHeaderInterceptor.initHystrixRequestContext(lables); // zuul本身调用微服务
		ctx.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, lables); // 传递给后续微服务

		return null;

	}
}

```
##### 实践：降级、限流、滚动、灰度、AB、金丝雀等等等等
经常看到社区里面拿dubbo和spring-cloud做对比，一对比就提到dubbo所谓的降级、限流功能。spring-cloud默认没有这个能力，让我们来扩展spring-cloud，使她具备比dubbo更牛逼的各种能力。

所谓的降级、限流、滚动、灰度、AB、金丝雀等等等等，在我看来无非就是扩展了服务路由能力而已。这里说的服务降级，说的是服务A部署多个实例，实例级别的降级限流。如果要做整个服务A的降级，直接采用docker自动扩容缩容即可。

我们先来看应用场景：

服务A 发布了1.0版，部署了3个实例A1、A2、A3，现在要对服务A进行升级，由1.0升级到2.0。先将A1服务流量关闭，使A2、A3负担；升级A1代码版本到2.0；将A1流量调整为1%，观察新版本运行情况，如果运行稳定，则逐步提升流量5%、10%直到完全放开流量控制。A2、A3重复上述步骤。

在上述步骤中，我们想让特别的人使用2.0，其他人还是使用1.0版，稳定后再全员开放。

我们想不依赖sleuth做链路跟踪，想自己实现一套基于ELK的链路跟踪。

我们还有各种千奇百怪的想法。。。

实现思路
----

要实现这些想法，我们需要对spring-cloud的各个组件、数据流非常熟悉，这样才能知道该在哪里做扩展。一个典型的调用：外网-》Zuul网关-》服务A-》服务B。。。

spring-cloud跟dubbo一样都是客户端负载均衡，所有调用均由Ribbon来做负载均衡选择服务器，所有调用前后会套一层hystrix做隔离、熔断。服务间调用均用带LoadBalanced注解的RestTemplate发出。RestTemplate-》Ribbon-》hystrix

通过上述分析我们可以看到，我们的扩展点就在Ribbon，Ribbon根据我们的规则，选择正确的服务器即可。

我们先来一个dubbo自带的功能：基于权重的流量控制。dubbo自带的控制台可以设置服务实例粒度的半权，倍权。其实就是在客户端负载均衡时，选择服务器带上权重即可，spring-cloud默认是ZoneAvoidanceRule，优先选择相同Zone下的实例，实例间采用轮询方式做负载均衡。我们的想把基于轮询改为基于权重即可。接下来的问题是，每个实例的权重信息保存在哪里？从哪里取？dubbo放在zookeeper中，spring-cloud放在eureka中。我们只需从eureka拿每个实例的权重信息，然后根据权重来选择服务器即可。具体代码LabelAndWeightMetadataRule（先忽略里面的优先匹配label相关代码）。

放入核心框架
------

LabelAndWeightMetadataRule写好了，那么我们如何使用它，使之生效呢？有3种方式。

1）写个AutoConfig将LabelAndWeightMetadataRule声明成@Bean，用来替换默认的ZoneAvoidanceRule。这种方式在技术验证、开发测试阶段使用短平快。但是这种方式是强制全局设置，无法个性化。

2）由于spring-cloud的Ribbon并没有实现netflix Ribbon的所有配置项。netflix配置全局rule方式为：ribbon.NFLoadBalancerRuleClassName=package.YourRule，spring-cloud并不支持，spring-cloud直接到服务粒度，只支持SERVICE_ID.ribbon.NFLoadBalancerRuleClassName=package.YourRule。我们可以扩展org.springframework.cloud.netflix.ribbon.PropertiesFactory修正spring cloud ribbon未能完全支持netflix ribbon配置的问题。这样我们可以将全局配置写到配置中心的application-dev.properties全局配置中，然后各个微服务还可以根据自身情况做个性化定制。但是PropertiesFactory属性均为私有，应该是spring cloud不建议在此扩展。参见[https://github.com/spring-cloud/spring-cloud-netflix/issues/1741。](https://github.com/spring-cloud/spring-cloud-netflix/issues/1741%E3%80%82)

3）使用spring cloud官方建议的@RibbonClient方式。该方式仅存在于spring-cloud单元测试中（在我提问后，现在还存在于spring-cloud issue list）。具体代码参见DefaultRibbonConfiguration、CoreAutoConfiguration。

实际测试
----

依次开启 config eureka provide（开两个实例，通过启动参数server.port指定不同端口区分） consumer zuul

访问 http://localhost:8761/metadata.html 这是我手写的一个简单的metadata管理界面，分别设置两个provider实例的weight值（设置完需要一段2分钟才能生效），然后访问 http://localhost:8080/provider/user 多刷几次来测试zuul是否按权重发送请求，也可以访问 http://localhost:8080/consumer/test 多刷几次来测试consumer是否按权重来调用provide服务。

进阶，基于标签
-------

基于权重的搞定之后，接下来才是重头戏：基于标签的路由。入口请求含有各种标签，然后我们可以根据标签幻化出各种各样的路由规则。例如只有标注为粉丝的用户才使用新版本（灰度、AB、金丝雀），例如标注为中国的用户请求必须发送到中国的服务器（全球部署），例如标注为写的请求必须发送到专门的写服务实例（读写分离），等等等等，唯一限制你的就是你的想象力。

实现思路
----

根据标签的控制，我们当然放到之前写的Ribbon的rule中，每个实例配置的不同规则也是跟之前一样放到注册中心的metadata中，关键是标签数据如何传过来。权重随机的实现思路里面有答案，请求都通过zuul进来，因此我们可以在zuul里面给请求打标签，基于用户，IP或其他看你的需求，然后将标签信息放入ThreadLocal中，然后在Ribbon Rule中从ThreadLocal拿出来使用就可以了。然而，按照这个方式去实验时，发现有问题，拿不到ThreadLocal。原因是有hystrix这个东西，回忆下hystrix的原理，为了做到故障隔离，hystrix启用了自己的线程，不在同一个线程ThreadLocal失效。那么还有什么办法能够将标签信息一传到底呢，想想之前有没有人实现过类似的东西，没错sleuth，他的链路跟踪就能够将spam传递下去，翻翻sleuth源码，找找其他资料，发现可以使用HystrixRequestVariableDefault，这里不建议直接使用HystrixConcurrencyStrategy，会和sleuth的strategy冲突。代码参见CoreHeaderInterceptor。现在可以测试zuul里面的rule，看能否拿到标签内容了。

这里还不是终点，解决了zuul的路由，服务A调服务B这里的路由怎么处理呢？zuul算出来的标签如何往后面依次传递下去呢，我们还是抄sleuth：把标签放入header，服务A调服务B时，将服务A header里面的标签放到服务B的header里，依次传递下去。这里的关键点就是：内部的微服务在接收到发来的请求时（zuul-》A，A-》B都是这种情况）我们将请求放入ThreadLocal，哦，不对，是HystrixRequestVariableDefault，还记得上面说的原因么：）。这个容易处理，写一个spring mvc拦截器即可，代码参见CoreHeaderInterceptor。然后发送请求时自动带上这个里面保存的标签信息，参见RestTemplate的拦截器CoreHttpRequestInterceptor。到此为止，技术上全部走通实现。

总结一下：zuul依据用户或IP等计算标签，并将标签放入header里向后传递，后续的微服务通过拦截器，将header里的标签放入RestTemplate请求的header里继续向后接力传递。标签的内容通过放入类似于ThreadLocal的全局变量（HystrixRequestVariableDefault），使Ribbon Rule可以使用。

测试
---

参见PreFilter源码，模拟了几个用户的标签，参见LabelAndWeightMetadataRule源码，模拟了OR AND两种标签处理策略。依次开启 config eureka provide（开两个实例，通过启动参数server.port指定不同端口区分） consumer zuul

访问 http://localhost:8761/metadata.html 设置第一个provide 实例 orLabel为 CN,Test 发送请求头带入Authorization: emt 访问http://localhost:8080/provider/user 多刷几次，可以看到zuul所有请求均路由给了第一个实例。访问http://localhost:8080/consumer/test 多刷几次，可以看到，consumer调用均路由给了第一个实例。

设置第二个provide 实例 andLabel为 EN,Male 发送请求头带入Authorization: em 访问http://localhost:8080/provider/user 多刷几次，可以看到zuul所有请求均路由给了第二个实例。访问http://localhost:8080/consumer/test 多刷几次，可以看到，consumer调用均路由给了第二个实例。

Authorization头还可以设置为PreFilter里面的模拟token来做测试，至此所有内容讲解完毕，技术路线拉通，剩下的就是根据需求来完善你自己的路由策略啦。





































![jpg](file:///F:\spring-cloud-7simple\简单的spring-cloud\spring-cloud-practice\static\20170613111731.jpg)