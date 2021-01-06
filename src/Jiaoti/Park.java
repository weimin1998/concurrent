package Jiaoti;

import java.util.concurrent.locks.LockSupport;

public class Park {
    public static void main(String[] args) {

        Base base = new Base();
        ParkPrintNumber parkPrintNumber = new ParkPrintNumber();
        ParkPrintLetter parkPrintLetter = new ParkPrintLetter();
        parkPrintNumber.setBase(base);
        parkPrintLetter.setBase(base);

        parkPrintNumber.setNext(parkPrintLetter);
        parkPrintLetter.setNext(parkPrintNumber);

        parkPrintLetter.start();
        parkPrintNumber.start();
    }
}

class ParkPrintNumber extends Thread {

    private Base base;

    public void setBase(Base base) {
        this.base = base;
    }

    public Thread next;

    public void setNext(Thread next) {
        this.next = next;
    }

    @Override
    public void run() {
        printNumber(next);
    }

    public void printNumber(Thread next) {
        for (int i = 1; i <= 26; i++) {
            while (base.isFlag()) {
                LockSupport.park();
            }
            System.out.print(2 * i - 1);
            System.out.print(2 * i);
            base.setFlag(true); ;
            LockSupport.unpark(next);
        }
    }


}

class ParkPrintLetter extends Thread {

    private Base base;

    public void setBase(Base base) {
        this.base = base;
    }

    public Thread next;

    public void setNext(Thread next) {
        this.next = next;
    }
    @Override
    public void run() {
        printLetter(next);
    }

    public void printLetter(Thread next) {

        for (int i = 1; i <= 26; i++) {
            while (!base.isFlag()) {
                LockSupport.park();
            }
            System.out.print((char) (i+96));

            base.setFlag(false);
            LockSupport.unpark(next);
        }

    }
}

class Base{
    private boolean flag;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
