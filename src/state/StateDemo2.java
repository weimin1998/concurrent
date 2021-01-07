package state;

public class StateDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (StateDemo2.class) {
                try {
                    StateDemo2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t1..");
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (StateDemo2.class) {
                try {
                    StateDemo2.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("t2..");
            }
        }, "t2");

        t1.start();
        t2.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (StateDemo2.class){
            StateDemo2.class.notifyAll();
        }


    }
}
