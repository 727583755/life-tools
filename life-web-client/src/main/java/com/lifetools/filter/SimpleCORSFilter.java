package com.lifetools.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 服务器接口支持跨域功能过滤器
 * @author zk
 * @date 2015年11月24日 下午5:39:30
 */
@Component
public class SimpleCORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Boolean isCrossOrigin = true;
		if (isCrossOrigin != null && isCrossOrigin) {
			HttpServletResponse  resp = (HttpServletResponse) response;
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
			resp.setHeader("Access-Control-Max-Age", "3600");
			resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		}
	    chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}
