package com.sun.synchronize;

public class MySynchronized {
    public  synchronized void method1() throws InterruptedException {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "进入method1方法");
        this.method2();
        System.out.println(thread.getName() + "退出method1方法");
    }
    public  synchronized void method2() throws InterruptedException {
        // ...
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "进入method2方法");
        Thread.sleep(1000);
        System.out.println(thread.getName() + "退出method2方法");

    }
}
