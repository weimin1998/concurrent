package jmm;

/**
 * volatile 只能保证看到最新值，不能解决指令交错（只能保证可见性，不能保证原子性）
 * synchronized 可以保证可见性和原子性，但是是重量级锁，代价大
 */
public class Atomic {
    static volatile int num = 0;
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            new Thread(()->{
                for (int j = 0; j < 40; j++) {
                    //synchronized (Atomic.class){
                        num++;
                    //}
                }
            }).start();
        }


        Thread.sleep(2000);

        System.out.println(num);
    }
}
