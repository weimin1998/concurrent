package activity;

/**
 * 死锁-哲学家就餐
 *
 * 在终端jps,然后jstack 进程id
 */
public class Zhexuejiajiucan {
    public static void main(String[] args) {
        Kuaizi k1 = new Kuaizi("1");
        Kuaizi k2 = new Kuaizi("2");
        Kuaizi k3 = new Kuaizi("3");
        Kuaizi k4 = new Kuaizi("4");
        Kuaizi k5 = new Kuaizi("5");

        Zhexuejia z1 = new Zhexuejia("亚里士多德",k1,k2);
        Zhexuejia z2 = new Zhexuejia("阿基米德",k2,k3);
        Zhexuejia z3 = new Zhexuejia("苏格拉底",k3,k4);
        Zhexuejia z4 = new Zhexuejia("柏拉图",k4,k5);
        Zhexuejia z5 = new Zhexuejia("香蕉君",k5,k1);

        z1.start();
        z2.start();
        z3.start();
        z4.start();
        z5.start();


    }
}



class Zhexuejia extends Thread{
    private String name;
    private Kuaizi left;
    private Kuaizi right;

    public Zhexuejia(String name, Kuaizi left, Kuaizi right) {
        super(name);
        this.name = name;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true){
            synchronized (left){
                synchronized (right){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    eat();
                }
            }
        }
    }

    private void eat(){
        System.out.println(name);
    }
}

class Kuaizi{
    private String name;

    public Kuaizi(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
