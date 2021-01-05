package activity;

import util.MyUtil;

/**
 * 活跃性-死锁
 */
public class DieLock {
    static Object A = new Object();
    static Object B = new Object();
    public static void main(String[] args) {

        new Thread(()->{
            synchronized (A){
                System.out.println(Thread.currentThread().getName()+":"+MyUtil.now()+"获得A锁");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B){
                    System.out.println(Thread.currentThread().getName()+":"+MyUtil.now()+"获得B锁");
                }
            }
        },"t1").start();
        new Thread(()->{
            synchronized (B){
                System.out.println(Thread.currentThread().getName()+":"+MyUtil.now()+"获得B锁");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A){
                    System.out.println(Thread.currentThread().getName()+":"+MyUtil.now()+"获得A锁");
                }
            }
        },"t2").start();
    }
}
