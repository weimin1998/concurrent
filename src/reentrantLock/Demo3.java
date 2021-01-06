package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁超时
 */
public class Demo3 {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            // 尝试获得锁
            boolean b = lock.tryLock();

            if(b){
                System.out.println("子线程获得了锁");
            }else {
                System.out.println("子线程没有获得锁");
            }
        });

        lock.lock();
        thread.start();
    }
}
