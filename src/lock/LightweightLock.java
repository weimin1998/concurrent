package lock;

/**
 * 轻量级锁
 */
public class LightweightLock {
    static Object object = new Object();
    public static void main(String[] args) {
        synchronized (object){
            method();
        }
    }

    private static void method() {
        synchronized (object){

        }
    }
}
