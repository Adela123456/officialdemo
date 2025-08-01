package com.sun.dao;

import com.sun.entity.MyThreadLocal;
import com.sun.util.MyThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class MyThreadLocalDao {
    public void test()
    {
        log.info("MyThreadLocalDao test={}", MyThreadLocalUtil.getMan());
    }
}
