package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {

	protected final Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public String sayHiFormClientOne(String name) {
		logger.info(getClass() + "---- name,{}", name);
		return "sorry " + name;
	}

	public static void main(String[] args) {
		System.out.println("ssss");
	}

}
