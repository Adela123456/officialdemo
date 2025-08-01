package com.sun.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ThreadService {
    @Autowired
    private Service2 service2;
    public void test() {
        log.info("test方法，threadName:{}",Thread.currentThread().getName());
        String categoryId = null;
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();
            if (request != null) {
                categoryId = request.getHeader("channel");
                log.info("test方法，请求头headers:{}",categoryId);

            }
        }
        service2.async1();
        log.info("第一个异步方法完结。。。。。。");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log.info("test方法，线程休眠异常:{}",e.getMessage());
            throw new RuntimeException(e);
        }
        service2.async2();
    }

    public void testThreadLocal() {
        log.info("测试线程方法,thread:{}",Thread.currentThread().getName());
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        ExecutorService mainThreadPool = Executors.newFixedThreadPool(16);

        ThreadPoolTaskExecutor childThreadPool = new ThreadPoolTaskExecutor();
        childThreadPool.setCorePoolSize(2);
        childThreadPool.setMaxPoolSize(2);

        childThreadPool.setTaskDecorator(runnable -> {
            int v = threadLocal.get();
            System.out.println("装饰器中获取到主线程=" + Thread.currentThread().getName() + " 获取上下文=" + v);
            return () -> {
                try {
                    //重新copy传递给子线程
                    threadLocal.set(v);
                    runnable.run();
                } finally {
                    threadLocal.remove();
                }
            };
        });
        childThreadPool.initialize();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            mainThreadPool.execute(() -> {
                //模拟在主线程设置上下文变量
                threadLocal.set(finalI);
                childThreadPool.execute(() -> System.out.println("子线程" + Thread.currentThread().getName() + " 获取上下文变量=" + threadLocal.get()));
                threadLocal.remove();
            });
        }
        try {
            childThreadPool.getThreadPoolExecutor().awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        Map<String, Integer> map = list.stream().collect(Collectors.toMap(s->s, s->Integer.valueOf(s), (v1, v2) -> v1 ));
//        System.out.println("this is map,{}"+ JSON.toJSONString(map));

        Map<String, Object> map = Maps.newHashMap();
        map.put("number", 9.34);


        String s = JSON.toJSONString(map);
        Map<String, Object> map1 =  JSON.parseObject(s,Map.class);
        System.out.println("返回值是：" + map1.get("number"));


    }

    public void testAsync() {
        log.info("测试异步方法...,thread:{}",Thread.currentThread().getName());
        try {
            asyncMethod();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @Async
    public Future<Integer> asyncMethod() throws InterruptedException {
        log.info("AsyncMethod thread: {}", Thread.currentThread().getName());
        // 异步线程延时 5s 返回结果
        Thread.sleep(5000);
        return new AsyncResult<>(2);
    }
}
