package com.sun.NoReentrant;

public class NonReentrantLock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            System.out.println("等待中......");
            wait(); // 等待别人释放锁（但如果是自己持有就永远等不到）
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}

