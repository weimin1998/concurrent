package activity;

/**
 * 活跃性-活锁
 */
public class LiveLock {
    static int num = 10;

    public static void main(String[] args) {
        new Thread(()->{
            while (num<20){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num++;
                System.out.println(num);
            }
        }).start();
        new Thread(()->{
            while (num>0){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                num--;
                System.out.println(num);
            }
        }).start();
    }
}
