public class JoinDemo {
    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test();

        System.out.println(r);
    }

    private static void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
        }, "t1");

        t1.start();
        /*
        * join(),让其他的线程加入，先执行
        * 不带参数，表示一直等，等其他的线程执行完
        * */
        t1.join();


    }
}
