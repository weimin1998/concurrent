import java.util.concurrent.locks.LockSupport;

public class ParkUnpark {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println(Util.now()+"t1开始执行...");
            try {
                Thread.sleep(3000);
                System.out.println(Util.now()+"t1被park打断了...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println(Util.now()+"t1继续执行...");
        }, "t1");


        t1.start();
        Thread.sleep(2000);

        /*
        * unpark() 可以在park之前执行，并且被unpark的线程，就不能被park暂停了
        * */
        System.out.println(Util.now()+"main unpark t1...");
        LockSupport.unpark(t1);
    }
}
