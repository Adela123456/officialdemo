package com.sun.NoReentrant;

public class DeadlockDemo {
    private final NonReentrantLock lock = new NonReentrantLock();

    public void outer() throws InterruptedException {
        lock.lock(); // 第一次加锁
        System.out.println("进入outer");
        inner();     // 第二次加锁，死锁发生！
        lock.unlock(); // 永远走不到这里
    }

    public void inner() throws InterruptedException {
        lock.lock();  // 第二次加锁，阻塞在这里
        System.out.println("进入inner");
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        new DeadlockDemo().outer();
    }
}

