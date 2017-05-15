package com.example.cloud.config;

import com.example.cloud.zuul.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 类CustomZuulConfig.java的功能描述:TODO 配置这个自定义的路由定位器：
 *
 * @author ssk 2017-05-15 下午 2:08 www.hnapay.com Inc.All rights reserved
 * @version v1.0
 */
@Configuration
public class CustomZuulConfig {
	@Autowired
	ZuulProperties zuulProperties;

	@Autowired
	ServerProperties server;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Bean
	public CustomRouteLocator routeLocator() {
		CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServletPrefix(), this.zuulProperties);
		routeLocator.setJdbcTemplate(jdbcTemplate);
		return routeLocator;
	}
}
