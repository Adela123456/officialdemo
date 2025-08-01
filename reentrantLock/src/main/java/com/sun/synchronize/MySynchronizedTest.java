package com.sun.synchronize;

public class MySynchronizedTest {
    public static void main(String[] args) {
        MySynchronized lock = new MySynchronized();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    lock.method1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        Thread t1 = new Thread(task, "线程A");
        Thread t2 = new Thread(task, "线程B");
        t1.start();
        t2.start();
    }
}
