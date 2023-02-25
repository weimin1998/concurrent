package jiaoti

public class ABC {


    static Thread a = null;
    static Thread b = null;
    static Thread c = null;
    /*
     * ABCABCABCABCABC
     * */
    public static void main(String[] args) {


        Printer printer = new Printer();



        a = new Thread("A") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.print("A",b);
                }
            }
        };

        b = new Thread("B") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.print("B",c);
                }
            }
        };

        c = new Thread("C") {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    printer.print("C",a);
                }
            }
        };

        a.start();
        b.start();
        c.start();

        //a先执行
        LockSupport.unpark(a);

    }
}

class Printer {

    public void print(String s, Thread next) {
        LockSupport.park();
        System.out.println(s);
        LockSupport.unpark(next);
    }

}
