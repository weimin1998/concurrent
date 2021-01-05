package Jiaoti;

/**
 * 交替打印字母 数字
 */
public class NAL {
    public static void main(String[] args) {
        PrintNL printNL = new PrintNL();

        new Thread(()->{
            printNL.printNumber();
        }).start();
        new Thread(()->{
            printNL.printLetter();
        }).start();

    }
}

class PrintNL{
    private boolean flag = false;

    public synchronized void printNumber(){
        for (int i = 1; i <= 26; i++) {
            while (flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.print(2*i-1);
            System.out.print(2*i);
            flag = true;
            notify();
        }


    }


    public synchronized void printLetter(){
        for (int i = 1; i <= 26; i++) {
            while (!flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.print((char) (i+96));
            flag = false;
            notify();
        }
    }
}
