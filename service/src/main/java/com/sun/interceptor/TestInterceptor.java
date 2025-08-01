package com.sun.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

public class TestInterceptor implements HandlerInterceptor {
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle开始啦啦啦啦,header={}"+ request.getHeader("channel"));

        return true;
    }
}
