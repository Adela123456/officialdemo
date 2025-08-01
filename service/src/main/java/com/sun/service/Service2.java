package com.sun.service;

import com.sun.entity.Boy;
import com.sun.util.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class Service2 {
    @Autowired
    private Boy boy;
    @Async("testExecutor")
    public void async1() {
        log.info("进入异步方法中,threadName:{}", Thread.currentThread().getName());

        Map<String, String> headers = RequestContext.getHeaders();
        if (headers != null) {
            String categoryId = headers.get("channel");
            log.info("异步方法，请求头headers:{}",categoryId);
        }
    }

    @Async("asyncExecutor")
    public void async2() {
        log.info("异步方法2,threadName:{}",Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        new Thread(()-> System.out.println("runnable")){
            @Override
            public void run() {
                System.out.println("Thread run");
            }
        }.start();
    }

    public void testLookUp() {
        log.info("test====");
        boy.playToy();
    }





}
