package reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁超时2
 */
public class Demo4 {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            // 尝试获得锁
            boolean b = false;
            try {
                b = lock.tryLock(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(b){
                System.out.println("子线程获得了锁");
            }else {
                System.out.println("子线程没有获得锁");
            }
        });

        lock.lock();
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
