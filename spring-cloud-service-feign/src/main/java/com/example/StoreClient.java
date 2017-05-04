package com.example;

import com.example.conf.FooConfiguration;
import org.apache.catalina.Store;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@FeignClient(name = "stores",configuration = FooConfiguration.class)
public interface StoreClient {
	@RequestMapping(method = RequestMethod.GET, value = "/stores")
	List<Store> getStores();

	@RequestMapping(method = RequestMethod.POST, value = "/stores/{storeId}", consumes = "application/json")
	Store update(@PathVariable("storeId") Long storeId, Store store);
}