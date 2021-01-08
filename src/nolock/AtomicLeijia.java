package nolock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子累加
 */
public class AtomicLeijia {
    public static void main(String[] args) throws InterruptedException {

        System.out.println(longAdder());
        System.out.println(atomicLong());

//        longAdder:42671900
//        5000000
//        AtomicLong:83607500
//        5000000

    }

    private static long longAdder() throws InterruptedException {
        LongAdder longAdder = new LongAdder();
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Thread(()->{
                for (int j = 0; j < 500000; j++) {
                    longAdder.increment();

                }
            }));
        }

        long start = System.nanoTime();
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        long end = System.nanoTime();

        long time = end-start;
        System.out.println("longAdder:"+time);
        return longAdder.sum();
        // sum() 就是 longValue()
        //return longAdder.longValue();
    }

    private static long atomicLong() throws InterruptedException {
        AtomicLong atomicLong = new AtomicLong(0);

        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Thread(()->{
                for (int j = 0; j < 500000; j++) {
                    atomicLong.getAndIncrement();
                }
            }));
        }

        long start = System.nanoTime();
        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        long end = System.nanoTime();

        long time = end-start;
        System.out.println("AtomicLong:"+time);

        // 同理 longValue()
        return atomicLong.get();

    }
}
