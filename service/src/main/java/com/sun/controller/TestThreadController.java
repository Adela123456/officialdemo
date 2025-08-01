package com.sun.controller;

import com.bailian.scloud.common.util.redis.JedisUtils;
import com.sun.service.ThreadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/testThread")
@Slf4j
public class TestThreadController {
    @Autowired
    private ThreadService threadService;

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/test")
    public void test() {
        log.info("test方法llll");
        threadService.test();
    }

    @RequestMapping("/testJedis")
    public void testJedis() {
        log.info("testjedis...");
        String redisKey = "testJedis";
        Set<String> set = new LinkedHashSet<>();
        jedisUtils.sadd(redisKey, set, 60*60);
        jedisUtils.srem(redisKey, "1");
    }

    @RequestMapping("/testRedis")
    public void testRedis() {
        log.info("testRedis...");
        String redisKey = "testRedis";
        Set<Object> set = new LinkedHashSet<>();
        long i = redisTemplate.opsForValue().increment(redisKey, 1);
        log.info("res={}", i);
        if (i==4) {
            redisTemplate.expire(redisKey,15, TimeUnit.SECONDS);
        }
        if (i>4) {
            throw new RuntimeException("操作频繁");
        }
    }


}
