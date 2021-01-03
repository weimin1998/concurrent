package guancheng;

public class Demo {
    static  int num = 0;

    static final Object lock = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                    num++;
                }
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (lock){
                    num--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(num);
    }
}
