import java.util.concurrent.locks.LockSupport;

public class ParkDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            System.out.println("park..");
            LockSupport.park(); // 挂起当前线程

            //LockSupport.unpark(Thread.currentThread());
            System.out.println("unpark");
            System.out.println("打断标记："+Thread.currentThread().isInterrupted());
            Thread.interrupted();
            LockSupport.park();
            System.out.println("park");


        }, "t1");

        t1.start();

        Thread.sleep(1000);

        t1.interrupt();

    }
}
