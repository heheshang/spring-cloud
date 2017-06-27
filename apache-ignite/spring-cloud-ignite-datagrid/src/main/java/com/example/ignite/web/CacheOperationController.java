package com.example.ignite.web;

import com.example.ignite.service.CacheOperationService;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.Cache;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
@RestController
public class CacheOperationController {

	@Autowired
	CacheOperationService service;

	@RequestMapping("/operation")
	public IgniteCache<Integer, String> operation(){

		return service.operation();

	}


	@RequestMapping("/operation1")
	public IgniteCache<Integer, String> operation1(){

		IgniteCache<Integer, String> cache = service.operationEntryProcessor();

		for ( Cache.Entry<Integer, String> integerStringEntry : cache ) {
			System.out.println(integerStringEntry);
		}

		return service.operationEntryProcessor();
	}


	@RequestMapping("/asyncOperation")
	public IgniteCache<Integer, String> asyncOperation(){

		return service.asyncOperation();

	}

}
