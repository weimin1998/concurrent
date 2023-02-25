public class ABC {

    static boolean printA = true;
    static boolean printB = false;
    static boolean printC = false;

    /*
     * ABCABCABCABCABC
     * */
    public static void main(String[] args) {


        Printer printer = new Printer();


        Thread a = new Thread("A") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.printA();
                }
            }
        };

        Thread b = new Thread("B") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.printB();
                }
            }
        };

        Thread c = new Thread("C") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.printC();
                }
            }
        };

        a.start();
        b.start();
        c.start();


    }
}

class Printer {
    private int flag = 1;

    public synchronized void printA() {
        while (flag!=1) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("A");
        flag = 2;
        this.notifyAll();
    }

    public synchronized void printB() {
        while (flag!=2) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("B");
        flag = 3;
        this.notifyAll();
    }

    public synchronized void printC() {
        while (flag!=3) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("C");
        flag = 1;
        this.notifyAll();
    }

}
