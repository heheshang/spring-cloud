package com.example.ignite.web;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@RestController
public class IndexController {

	@Autowired
	Ignite ignite;

	@Autowired
	IgniteCache<Integer, String> cache;


	@RequestMapping("/")
	public String index() {
		cache.put(1, "sssss");
		return "ssss";
	}

	@RequestMapping("/get")
	public String get() {
		return cache.get(1);
	}
}
