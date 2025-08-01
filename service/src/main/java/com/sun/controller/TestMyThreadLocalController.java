package com.sun.controller;

import com.sun.entity.Man;
import com.sun.entity.MyThreadLocal;
import com.sun.service.MyThreadLocalService;
import com.sun.util.MyThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;

@RestController
@Slf4j
public class TestMyThreadLocalController {
    @Autowired
    private MyThreadLocalService myThreadLocalService;
    @PostMapping("/testMyThreadLocal")
    public void test() {
        log.info("test 方法在测试。。。");
//        MyThreadLocalUtil myThreadLocalUtil = new MyThreadLocalUtil();
//        myThreadLocalUtil.test();
        MyThreadLocalUtil.getMan();
//        myThreadLocalService.test();
        /**
         * 你理解线程这个事么，你的程序是运行在线程上的，
         * 所以你可以把你想传递给另一个方法的参数放在一个map集合里，
         * 通过线程这个key来获取，因为你的代码是被线程联系在一串上的
         */
    }
}
