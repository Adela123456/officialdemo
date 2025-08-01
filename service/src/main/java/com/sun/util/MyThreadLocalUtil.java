package com.sun.util;

import com.sun.entity.Man;
import com.sun.entity.MyThreadLocal;

public class MyThreadLocalUtil {
    private static MyThreadLocal myThreadLocal = new MyThreadLocal();
    private  MyThreadLocal myThreadLocal1 = new MyThreadLocal();

    public static Object getMan(){

        Object o = myThreadLocal.get();
        if (o == null) {
            Man user = new Man();
            myThreadLocal.set(user);
            o = user;
        }
        return o;
    }
    public void test(){
        myThreadLocal.set("test");
        myThreadLocal1.set("hhhhhhhhhh");
    }
    public  Object getTest(){
        Object o = myThreadLocal1.get();
        if (o == null) {
            Man user = new Man();
            myThreadLocal1.set(user);
            o = user;
        }
        return o;
    }

    public static Object testStaticMethod(){
        return getMan();
    }
}
