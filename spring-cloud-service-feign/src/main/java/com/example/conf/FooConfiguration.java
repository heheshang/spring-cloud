package com.example.conf;

import feign.Contract;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
//@Configuration
public class FooConfiguration  {
	@Bean
	public Contract feignContrats() {
		return new feign.Contract.Default();
	}

	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor("user", "password");
	}

}
