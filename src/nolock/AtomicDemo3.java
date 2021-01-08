package nolock;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用
 * AtomicStampedReference 可以感知共享变量是否被修改过  （A->B,B->A）
 * 通过int版本号的方式
 *
 */
public class AtomicDemo3 {
    public static void main(String[] args) throws InterruptedException {
        // 版本号 0
        AtomicStampedReference<String> atomicReference = new AtomicStampedReference<>("A",0);

        int stamp = atomicReference.getStamp();
        System.out.println(stamp);
        String reference = atomicReference.getReference();
        System.out.println(reference);

        other(atomicReference);
        Thread.sleep(1000);

        boolean b = atomicReference.compareAndSet("A", "C", 0, 1);
        System.out.println(Thread.currentThread().getName()+"从A改为C："+b);
        System.out.println(atomicReference.getReference());
        System.out.println(atomicReference.getStamp());
    }

    private static void other(AtomicStampedReference<String> atomicReference){

        new Thread(()->{
            int stamp = atomicReference.getStamp();
            boolean b = atomicReference.compareAndSet("A", "B", stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"从A改为B："+b);
        },"t1").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            int stamp = atomicReference.getStamp();
            boolean b = atomicReference.compareAndSet("B", "A", stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()+"从B改为A："+b);
        },"t2").start();
    }
}
