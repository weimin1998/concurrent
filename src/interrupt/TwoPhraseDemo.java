package interrupt;

public class TwoPhraseDemo {


    // 两阶段终止模式
    // 用打断标记 实现
    public static void main(String[] args) throws InterruptedException {
        // 在一个线程中优雅的停止其他线程（给一个料理后事的机会）

        // 两阶段终止模式

        Monitor monitor = new Monitor();

        monitor.start();

        Thread.sleep(6700);

        monitor.stop();
    }
}

class Monitor{

    private Thread monitorThread;

    public void start(){
        monitorThread = new Thread(()->{
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println("料理后事");
                    break;
                }else {
                    try {
                        // 每1秒执行一次监控

                        // 如果是在睡眠的时候被打断，就会进入catch
                        Thread.sleep(1000);

                        // 在监控的时候被打断，就真正被打断
                        System.out.println("监控。。");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                        monitorThread.interrupt();
                    }
                }
            }
        });

        monitorThread.start();
    }

    public void stop(){
        monitorThread.interrupt();
    }

}
