package com.sun.service;

import com.sun.dao.MyThreadLocalDao;
import com.sun.entity.MyThreadLocal;
import com.sun.util.MyThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyThreadLocalService {
    @Autowired
    private MyThreadLocalDao myThreadLocalDao;
    public void test(){
        log.info("MyThreadLocalService...中的value={}", MyThreadLocalUtil.getMan());
        myThreadLocalDao.test();
//        log.info("test static method ={}", MyThreadLocalUtil.testStaticMethod());

    }

}
