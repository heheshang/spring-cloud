package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@RestController
public class HelloController {
	@Autowired
	HelloService service;
	@RequestMapping(value = "/hi")
	public String hi(@RequestParam String name){
		return service.hiService(name);
	}
}
