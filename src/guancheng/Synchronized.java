package guancheng;
//javap -p -c Synchronized.class
public class Synchronized {
    static Object object = new Object();
    static int num = 0;
    public static void main(String[] args) {
        synchronized (object){
            num++;
        }
    }
}
