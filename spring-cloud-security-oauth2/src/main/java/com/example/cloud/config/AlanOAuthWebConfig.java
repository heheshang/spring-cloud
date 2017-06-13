package com.example.cloud.config;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


/**
 * Created by wanghongfei(hongfei7@staff.sina.com.cn) on 9/12/16.
 */
@Configuration
public class AlanOAuthWebConfig extends WebSecurityConfigurerAdapter {



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/favor.ico");
	}
}