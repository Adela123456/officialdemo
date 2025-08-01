package com.sun.config;

import com.sun.interceptor.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置拦截条件
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //参数为我们自定义类，实现了HandlerInterceptor接口重写了三个方法

        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns("/goodsResource/test")   //拦截所有的路径
                .excludePathPatterns("/login/**"); //放行login目录下的,因为生成token拦截就无法生成了
    }
}


