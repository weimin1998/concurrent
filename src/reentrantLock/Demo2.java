package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可打断
 */
public class Demo2 {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("子线程尝试获得锁");
                // 如果获取不到锁，就阻塞，但是该阻塞可以被打断
                lock.lockInterruptibly();
                System.out.println("子线程获得了锁");
            } catch (InterruptedException e) {
                System.out.println("子线程没有获得锁");
                e.printStackTrace();
            }
        });

        lock.lock();
        thread.start();
        thread.interrupt();
    }
}
