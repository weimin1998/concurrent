package finalDemo;

import util.MyUtil;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 享元模式
 *
 */
public class Xiangyuan {

    public static void main(String[] args) {
        Pool pool = new Pool(2);

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Connection connection = pool.getConnection();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                pool.free(connection);
            },"t"+i).start();
        }
    }
}

class Pool{

    // 连接池大小
    private int size;

    //
    private Connection[] connections;

    //
    private AtomicIntegerArray states;

    public Pool(int size){
        this.size = size;
        this.connections = new Connection[size];
        this.states = new AtomicIntegerArray(new int[size]);

        for (int i = 0; i < size; i++) {
            connections[i] = new Connection();
        }
    }

    // 获取连接
    public Connection getConnection() {
        while (true){
            for (int i = 0; i < size; i++) {
                if(states.get(i)==0){
                    if (states.compareAndSet(i,0,1)) {
                        System.out.println(MyUtil.now()+"获取连接："+connections[i]);
                        return connections[i];
                    }
                }
            }
            // 如果
            synchronized (this){
                try {
                    System.out.println(MyUtil.now()+"等待");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    // 归还连接
    public void free(Connection connection){
        for (int i = 0; i < size; i++) {
            if(connections[i]==connection){
                states.set(i,0);
                System.out.println(MyUtil.now()+"归还连接："+connection);
                synchronized (this){
                    notifyAll();
                }
                break;
            }
        }

    }
}

// 连接
class Connection{

}
