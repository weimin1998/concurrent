package reentrantLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件变量
 */
public class Demo5 {
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
    private boolean flag;
    private  ReentrantLock lock = new ReentrantLock();
    public Condition number = lock.newCondition();
    public Condition letter = lock.newCondition();


    public void printNumber(){
        lock.lock();
        try {
            for (int i = 1; i <= 26; i++) {
                while (flag){
                    number.await();
                }

                System.out.print(2*i-1);
                System.out.print(2*i);
                flag = true;
                letter.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printLetter(){
        lock.lock();
        try {
            for (int i = 1; i <= 26; i++) {
                while (!flag){
                    letter.await();
                }

                System.out.print((char) (i+96));
                flag = false;
                number.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
