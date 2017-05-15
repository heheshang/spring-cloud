package com.example.cloud.web;

import com.example.cloud.event.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/15 0015.
 */
@RestController
public class DemoController {
	@Autowired
	RefreshRouteService refreshRouteService;


	@RequestMapping("/refreshRoute")
	public String refreshRoute(){
		refreshRouteService.refreshRoute();
		return "refreshRoute";
	}

	@Autowired
	ZuulHandlerMapping zuulHandlerMapping;

	@RequestMapping("/watchNowRoute")
	public String watchNowRoute(){
		//可以用debug模式看里面具体是什么
		Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
		return "watchNowRoute";
	}

}
