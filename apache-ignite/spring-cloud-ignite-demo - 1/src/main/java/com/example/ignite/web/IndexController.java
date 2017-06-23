package com.example.ignite.web;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
@RestController
public class IndexController {
	@Autowired
	Ignite ignite;

	@RequestMapping("/")
	public String index(){
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache("userInfo");
		cache.put(1, "小明");
		return "success";
	}

	@RequestMapping("/get")
	public String get(){
		IgniteCache<Integer, String> cache = ignite.getOrCreateCache("userInfo");
		return cache.get(1);
	}
}
