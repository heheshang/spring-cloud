package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@RestController
public class HelloController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${server.port}")
	String port;
	@RequestMapping("/hi")
	public String home(@RequestParam String name){
		logger.info( "hi name {},i am form {}",name,port);
		return "hi"+name+",i am form port"+port;
	}

	@RequestMapping("/info")
	public String info(){
		logger.info( "hi name {},i am form {}",port);
		return port;
	}
}
