package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class MyAtomicInteger {

    private volatile int value;
    private static Unsafe unsafe = null;
    private static long offset;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
            offset = unsafe.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getValue() {
        return value;
    }

    public void decrement(int i){
        while (true){
            int pre = getValue();
            int next = pre - i;
            if(unsafe.compareAndSwapInt(this,offset,pre,next)){
                break;
            }

        }
    }

    public static void main(String[] args) {
        MyAtomicInteger myAtomicInteger = new MyAtomicInteger();
        myAtomicInteger.decrement(100);
        System.out.println(myAtomicInteger.getValue());
    }
}
