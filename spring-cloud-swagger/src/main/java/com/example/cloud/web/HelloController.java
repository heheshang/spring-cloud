package com.example.cloud.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by Administrator on 2017/6/1 0001.
 */
@RestController
public class HelloController {
	@ApiIgnore
	@RequestMapping(value = "/hello" ,method = RequestMethod.GET)
	public String index(){
		return "Hello World";
	}
}
