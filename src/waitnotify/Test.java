package waitnotify;

public class Test {
    static final Object obj = new Object();
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"获得锁");
                try {
                    Thread.sleep(5000);
                    //obj.wait(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);

        synchronized (obj){
            System.out.println(Thread.currentThread().getName()+"获得锁");
        }
    }
}
