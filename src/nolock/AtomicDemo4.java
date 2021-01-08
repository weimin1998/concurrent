package nolock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 *  AtomicReference 不能感知共享变量是否被修改过  （A->B,B->A）
 *  只能感知共享变量是否与最初值相等
 *
 */
public class AtomicDemo4 {
    public static void main(String[] args) throws InterruptedException {
        AtomicReference<String> atomicReference = new AtomicReference<>("A");

        other(atomicReference);
        Thread.sleep(1000);
        boolean b = atomicReference.compareAndSet("A", "C");

        System.out.println(Thread.currentThread().getName()+"从A改为C："+b);
        System.out.println(atomicReference.get());
    }

    private static void other(AtomicReference<String> atomicReference){
        new Thread(()->{
            boolean b = atomicReference.compareAndSet("A", "B");
            System.out.println(Thread.currentThread().getName()+"从A改为B："+b);
        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            boolean b = atomicReference.compareAndSet("B", "A");
            System.out.println(Thread.currentThread().getName()+"从B改为A："+b);
        },"t2").start();

    }
}
