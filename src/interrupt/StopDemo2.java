package interrupt;

public class StopDemo2 {
    /*
    * volatile 实现两阶段终止
    * */
    public static void main(String[] args) throws InterruptedException {
        Monitor2 monitor2 = new Monitor2();

        monitor2.start();
        monitor2.start();
        monitor2.start();
        monitor2.start();
        monitor2.start();
        monitor2.start();

        Thread.sleep(6600);

        monitor2.stop();
    }
}

class Monitor2{
    private volatile boolean run = true;
    private Thread monitorThread;

    private boolean starting = false;
    public  void start(){
        synchronized (this){
            // 犹豫模式 balking
            // 这样的监控线程只需要一个，不能多次start
            if(starting){
                return;
            }
            starting = true;
        }

        monitorThread = new Thread(()->{
            while (true){
                if(run){
                    try {
                        Thread.sleep(1000);
                        System.out.println("监控。。。");
                    } catch (InterruptedException e) {

                    }
                }else {
                    System.out.println("处理后事。。");
                    break;
                }
            }
        });

        monitorThread.start();
    }

    public void stop(){
        run = false;
        monitorThread.interrupt();
    }
}
