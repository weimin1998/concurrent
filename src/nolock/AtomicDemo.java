package nolock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 原子整型
 */
public class AtomicDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(7);

        //System.out.println(atomicInteger.incrementAndGet());
        //System.out.println(atomicInteger.getAndIncrement());
        //System.out.println(atomicInteger.addAndGet(4));
        int i = atomicInteger.updateAndGet(x -> x * 10);
        System.out.println(i);
    }
}
