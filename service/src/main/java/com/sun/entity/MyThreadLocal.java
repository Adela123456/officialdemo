package com.sun.entity;

import java.util.HashMap;
import java.util.Map;

public class MyThreadLocal {
    private  Map<Thread, Object> map = new HashMap<>();
    public  void set(Object object){
        map.put(Thread.currentThread(), object);
    }

    public  Object get(){
        return map.get(Thread.currentThread());
    }

    public  void remove(){
        map.remove(Thread.currentThread());
    }
}
