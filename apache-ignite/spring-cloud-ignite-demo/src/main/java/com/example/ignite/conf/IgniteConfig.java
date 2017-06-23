package com.example.ignite.conf;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
@Configuration
public class IgniteConfig {

	@Bean
	public Ignite ignite(){
		return Ignition.start();
	}
}
