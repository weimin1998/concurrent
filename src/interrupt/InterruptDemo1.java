package interrupt;

public class InterruptDemo1 {

    /*
    * interrupt(),不是在睡眠的时候打断，就是真正打断了，打断标记为true，不会抛异常
    * */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    // 有人让你停下来
                    break;
                }
            }
        }, "t1");

        t1.start();
        Thread.sleep(1000);
        t1.interrupt();

        // 打断标记
        System.out.println(t1.isInterrupted());//true
    }
}
