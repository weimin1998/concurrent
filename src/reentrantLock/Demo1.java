package reentrantLock;

import java.util.concurrent.locks.ReentrantLock;


public class Demo1 {
    public static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();
        System.out.println("hello");
        lock.unlock();


    }


}
