package com.sun.reEntrantLock;

public class MyReentrantLock {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(()->0);
    private boolean isLocked = false;
    private Thread owner = null;

    public MyReentrantLock() {

    }

    /**
     * 为了模仿  可重入锁
     * 不同的线程对同一个对象的操作，这也是并发的本质
     *
     * @throws InterruptedException
     */

    public synchronized  void lock() throws InterruptedException {
        // 我首先要判断是不是一个线程。。。，怎么判断呢，  第一次请求就把当前线程赋给owner，后面再来的请求和owner作对比，就知道是不是自己了
        // 这个wait和notify都是针对一个对象做的，是不同的线程对一个对象的操作。。。 一个线程等待，另一个叫醒他
        while (isLocked && owner != Thread.currentThread()) {
            System.out.println(Thread.currentThread().getName() + "等待中......");
            wait(); // 等待别人释放锁（但如果是自己持有就永远等不到）
        }
        isLocked = true;
        owner = Thread.currentThread();
        threadLocal.set(threadLocal.get() +1);
        System.out.println(Thread.currentThread().getName() + " 获取锁，当前计数：" + threadLocal.get());
    }
    public synchronized void unlock() throws InterruptedException {

        // 我首先要判断是不是一个线程。。。，怎么判断呢，
        if (owner == Thread.currentThread()) {
            threadLocal.set(threadLocal.get() - 1);
            System.out.println(Thread.currentThread().getName() + " 释放一次锁，剩余次数：" + threadLocal.get());
        } else {
            throw new IllegalArgumentException("当前线程不是锁的拥有者，不能释放锁！");
        }
        if (threadLocal.get().equals(0)) {
            isLocked = false;
            owner = null;
            notify();  // 当做完全部的操作，这时把钥匙归还，后面被锁的人可以继续进来
            // notify执行时并不是立刻释放锁，而是随机先唤醒一个线程，唤醒的线程进入竞争锁的队列，当前线程退出synchronized代码块，才会释放锁，才会重新抢锁
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "释放完锁。。。"); // 也就是说这块才真正释放掉锁，此时wait才会真正竞争得到锁
        }
        // 他要在哪里判断最终减为0了哇,应该是一个最后的地方，类似于finally，就是上面说的这个啦
    }
}
