package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入
 */
public class Demo {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {

        try {
            lock.lock();
            m1();
        }finally {
            lock.unlock();
        }
    }

    private static void m1() {
        try {
            lock.lock();
            m2();
        }finally {
            lock.unlock();
        }
    }

    private static void m2() {
        try {
            lock.lock();
            m3();
        }finally {
            lock.unlock();
        }
    }

    private static void m3() {
        System.out.println("可重入锁");
    }
}
