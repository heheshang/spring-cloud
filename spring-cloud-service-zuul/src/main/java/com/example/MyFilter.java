package com.example;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Administrator on 2017/4/27 0027.
 */
@Component
public class MyFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(MyFilter.class);
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
		RequestContext cxt = RequestContext.getCurrentContext();
		HttpServletRequest request = cxt.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
		Object accessToken = request.getParameter("token");
		if ( accessToken == null ) {
			log.warn("token is empty");
			cxt.setSendZuulResponse(false);
			cxt.setResponseStatusCode(401);
			try {
				cxt.getResponse().getWriter().write("token is empty");
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			return null;
		}
		log.info("ok");
		return null;
	}
}
