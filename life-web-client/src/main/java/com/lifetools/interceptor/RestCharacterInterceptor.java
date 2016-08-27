package com.lifetools.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * rest接口字符编码拦截器
 * Created by Zheng.Ke
 * Date 2016/8/25.
 */
public class RestCharacterInterceptor  extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        return true;
    }


}
