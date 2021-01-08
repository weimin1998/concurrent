package nolock;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * 原子数组
 */
public class AtomicArray {
    public static void main(String[] args) {

        Integer[] array = {1,2,3,4,5};
        AtomicReferenceArray<Integer> atomicReferenceArray = new AtomicReferenceArray<>(array);

        boolean b = atomicReferenceArray.compareAndSet(2, 3, 9);
        System.out.println(b);
        System.out.println(atomicReferenceArray.get(0));
        System.out.println(atomicReferenceArray.get(1));
        System.out.println(atomicReferenceArray.get(2));
        System.out.println(atomicReferenceArray.get(3));
        System.out.println(atomicReferenceArray.get(4));
        System.out.println(Arrays.toString(array));

        atomicReferenceArray.updateAndGet(2, x -> x - 2);
        System.out.println(atomicReferenceArray.get(0));
        System.out.println(atomicReferenceArray.get(1));
        System.out.println(atomicReferenceArray.get(2));
        System.out.println(atomicReferenceArray.get(3));
        System.out.println(atomicReferenceArray.get(4));

    }
}
