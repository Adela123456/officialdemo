package com.sun.reEntrantLock;

public class MyReentrantLockDemo {
    MyReentrantLock lock = new MyReentrantLock();
    public void outer() throws InterruptedException {
        lock.lock(); // 第一次加锁
        System.out.println("进入outer");
        inner();
        lock.unlock();
    }

    public void inner() throws InterruptedException {
        lock.lock();
        System.out.println("进入inner");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        MyReentrantLockDemo demo = new MyReentrantLockDemo();
        demo.outer();
    }
}
