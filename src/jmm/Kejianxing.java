package jmm;

/**
 * 可见性
 */
public class Kejianxing {
    static  boolean run = true;
    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(() -> {
            while (run){

            }
            System.out.println("stop");
        });
        thread.start();

        Thread.sleep(1000);
        run = false;


    }
}
