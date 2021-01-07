package jmm;

public class DCL {
    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    System.out.println(Singleton.getInstance());
                }
            }).start();
        }

    }

}
class Singleton{
    private Singleton(){}

    private int id;
    private static volatile Singleton instance;

    public static Singleton getInstance() {
        if(instance==null){
            synchronized (Singleton.class){
                if (instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
