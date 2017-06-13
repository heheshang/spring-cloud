package com.example.cloud;

import com.example.cloud.config.CoreHeaderInterceptor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class PreFilter extends ZuulFilter {

	private static final HashMap<String, String> TOKEN_LABEL_MAP = new HashMap<>();

	static {
		TOKEN_LABEL_MAP.put("emt", "EN,Male,Test");
		TOKEN_LABEL_MAP.put("eft", "EN,Female,Test");
		TOKEN_LABEL_MAP.put("cmt", "CN,Male,Test");
		TOKEN_LABEL_MAP.put("cft", "CN,Female,Test");
		TOKEN_LABEL_MAP.put("em", "EN,Male");
		TOKEN_LABEL_MAP.put("ef", "EN,Female");
		TOKEN_LABEL_MAP.put("cm", "CN,Male");
		TOKEN_LABEL_MAP.put("cf", "CN,Female");
	}

	private static final Logger logger = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		String token = ctx.getRequest().getHeader(HttpHeaders.AUTHORIZATION);

		String lables = TOKEN_LABEL_MAP.get(token);

		logger.info("lable: "+lables);

		CoreHeaderInterceptor.initHystrixRequestContext(lables); // zuul本身调用微服务
		ctx.addZuulRequestHeader(CoreHeaderInterceptor.HEADER_LABEL, lables); // 传递给后续微服务

		return null;

	}
}
