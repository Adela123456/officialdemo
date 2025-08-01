package com.sun.reEntrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class MyReentrantLockTest {
    /**
     * 为了模仿  可重入锁
     *      * 不同的线程对同一个对象的操作，这也是并发的本质
     *      如果这个地方不是创建了一个对象，MyReentrantLock lock = new MyReentrantLock();
     *      而是MyReentrantLock lock2 = new MyReentrantLock();
     *      这时候就不会出现竞争的问题了
     * @param args
     */
    /**
     *         ReentrantLock reentrantLock = new ReentrantLock(); 默认不公平锁
     * @param args
     */
    public static void main(String[] args) {
        MyReentrantLock lock = new MyReentrantLock();

        Runnable task = () -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 进入 outer");
                Thread.sleep(100); // 模拟做点事

                lock.lock();  // 第二次加锁（可重入）
                System.out.println(Thread.currentThread().getName() + " 进入 inner");
                Thread.sleep(100); // 模拟 inner 逻辑
                lock.unlock();

                lock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread t1 = new Thread(task, "线程-A");
        Thread t2 = new Thread(task, "线程-B");
        Thread t3 = new Thread(task, "线程-C");

        t1.start();
        t2.start();
        t3.start();
    }
}

