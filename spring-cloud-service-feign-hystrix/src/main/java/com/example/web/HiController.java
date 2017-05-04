package com.example.web;

import com.example.SchedualServiceHi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@RestController
public class HiController {
	protected  final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	SchedualServiceHi schedualServiceHi;

	@RequestMapping(value = "/hi" ,method = RequestMethod.GET)
	public String syaHi(@RequestParam  String name){
		logger.info("schedualServiceHi.sayHiFormClientOne({})",name);
		return schedualServiceHi.sayHiFormClientOne(name);
	}
}
