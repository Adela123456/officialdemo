package com.sun.controller;

import com.alibaba.fastjson.JSON;
import com.sun.feign.CloudServiceClient;
import com.sun.service.Service2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sun.entity.User;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private CloudServiceClient cloudServiceClient;
    @Autowired
    private Service2 service2;
    @GetMapping("/get")
    public String get() {
        return "hello world";
    }

    @PostMapping("/post")
    public String post(@RequestBody User user){
        return JSON.toJSONString(user);

    }
    @PostMapping("/test")
    public void test() {
        log.info("商品测试环境start");
        Map<String,Object> param = new HashMap<>();
        param.put("mktPromotionId", "1");
        param.put("pageNo",1);
        param.put("pageSize",10);
        try {
            Map<String, Object> map = cloudServiceClient.getGoodsByActivityId(param);
            log.info("商品测试环境返回结果：{}", JSON.toJSONString(map));

        } catch (Exception e) {
            log.error("商品测试环境error",e);
        }
        log.info("商品测试环境end");
    }

    @PostMapping("/testLookUp")
    public void testLookUp() {
        service2.testLookUp();
    }

    @GetMapping("/info")
    public String getUserInfo(){
        return "hello, this is user info!";
    }

}
