package interrupt;

public class StopDemo2 {
    /*
    * volatile 实现两阶段终止
    * */
    public static void main(String[] args) throws InterruptedException {
        Monitor2 monitor2 = new Monitor2();

        monitor2.start();

        Thread.sleep(6000);

        monitor2.stop();
    }
}

class Monitor2{
    private volatile boolean run = true;
    private Thread monitorThread;

    public void start(){
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
