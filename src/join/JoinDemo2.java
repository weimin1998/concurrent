package join;

public class JoinDemo2 {

    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;
        }, "t1");
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        }, "t2");

        t1.start();
        t2.start();

        long start = System.currentTimeMillis();
        /*
        *
        * 带参数，表示只等指定的时间，过后就不等了，
        * 如果在等待的时候线程执行完了，就也不等了
        * */
        t2.join(3000);
        t1.join();
        long end = System.currentTimeMillis();

        System.out.println("等待的时间："+(end-start)+"ms");
        System.out.println("r1="+r1);
        System.out.println("r2="+r2);

    }
}
