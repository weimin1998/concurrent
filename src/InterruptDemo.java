public class InterruptDemo {

    /*
    * interrupt() 如果在睡眠的时候打断，会抛出异常，(也就是以异常的方式被打断) 打断标记还是为false
    * */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");

        t1.start();

        Thread.sleep(1000);
        t1.interrupt();

        // 打断标记
        System.out.println(t1.isInterrupted());//false
    }
}
