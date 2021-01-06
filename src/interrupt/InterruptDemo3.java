package interrupt;

public class InterruptDemo3 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            synchronized (InterruptDemo3.class){
                try {
                    InterruptDemo3.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1");

        t1.start();


        t1.interrupt();

    }
}
