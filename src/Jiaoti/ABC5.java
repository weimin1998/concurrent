package Jiaoti;

/**
 * 交替打印 ABC ABC ABC ABC ABC
 */
public class ABC5 {
    public static void main(String[] args) {
        Print print = new Print();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                print.soutA();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                print.soutB();
            }
        }).start();
        new Thread(()->{
            for (int i = 0; i < 5; i++) {
                print.soutC();
            }
        }).start();
    }
}

class Print{
    private int flag = 1;

    public synchronized void soutA(){
        while (flag!=1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.print("A");
            flag = 2;
            notifyAll();

    }

    public synchronized void soutB(){
        while (flag!=2){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            System.out.print("B");
            flag = 3;
            notifyAll();

    }

    public synchronized void soutC(){
        while (flag!=3){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            System.out.print("C");
            flag = 1;
            notifyAll();

    }
}
