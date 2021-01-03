public class Xianxiang {
    public static void main(String[] args) {
        new Thread(()->{
            while (true){
                System.out.println(Thread.currentThread().getName()+"running");
            }
        },"t1").start();
        new Thread(()->{
            while (true){
                System.out.println(Thread.currentThread().getName()+"running");
            }
        },"t2").start();
    }
}