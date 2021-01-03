public class StateDemo {


    /*
    * java层面 线程的六个状态
    *
    * */
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {}, "t1");




        Thread t2 = new Thread(() -> {
            while (true){

            }
        }, "t2");
        t2.start();




        Thread t3 = new Thread(() -> {
            System.out.println("running...");
        }, "t3");
        t3.start();





        Thread t4 = new Thread(() -> {
            synchronized (StateDemo.class){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }, "t4");
        t4.start();



        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t5");
        t5.start();


        Thread t6 = new Thread(() -> {
            synchronized (StateDemo.class){
                System.out.println("");
            }
        }, "t6");

        t6.start();



        Thread.sleep(500);
        System.out.println("t1:"+t1.getState());
        System.out.println("t2:"+t2.getState());
        System.out.println("t3:"+t3.getState());
        System.out.println("t4:"+t4.getState());
        System.out.println("t5:"+t5.getState());
        System.out.println("t6:"+t6.getState());
    }
}
