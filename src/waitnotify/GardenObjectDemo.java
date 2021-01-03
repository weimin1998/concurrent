package waitnotify;

import java.text.SimpleDateFormat;
import java.util.*;

/*
*设计模式 保护性暂停
* 注意和join()的区别
* join的原理  就是保护性暂停
*
* */
public class GardenObjectDemo {
    public static String now(){
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"=====>";
    }
    public static void main(String[] args) throws InterruptedException {


        GardenObject gardenObject = new GardenObject();

        new Thread(()->{
            System.out.println(now()+Thread.currentThread().getName()+"begin");
            Object object = gardenObject.getObject(4000L);

            System.out.println(now()+Thread.currentThread().getName()+"获得结果："+object);
        },"t1").start();


        new Thread(()->{
            System.out.println(now()+Thread.currentThread().getName()+"begin");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gardenObject.setObject("完成");
            //gardenObject.setObject(null);
            System.out.println(now()+Thread.currentThread().getName()+"返回结果");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(now()+Thread.currentThread().getName()+"继续其他任务");
        },"t2").start();

        /*for (int i = 0; i < 3; i++) {
            new People().start();
        }

        Thread.sleep(1000);

        for (Integer id : MailBox.getIds()) {
            new PostMan(id,"mail"+id).start();
        }*/
    }
}

class GardenObject{
    private Object object;

    private int id;

    public GardenObject() {

    }
    public GardenObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Object getObject()  {
        synchronized (this){
            while (object==null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return object;
        }
    }

    public Object getObject(long timeout)  {
        synchronized (this){
            long startTime = System.currentTimeMillis();
            long passTime = 0L;
            while (object==null){
                if(passTime>=timeout){
                    break;
                }
                try {
                    this.wait(timeout-passTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis()-startTime;
            }
            return object;
        }
    }

    public void setObject(Object object) {
        synchronized (this){
            this.object = object;
            this.notifyAll();
        }
    }
}


/*
* 信箱类
* */
class MailBox{
    private static Map<Integer,GardenObject> GardenObjects = new Hashtable<>();

    private static int id;

    public synchronized static int getId(){
        return id++;
    }

    public static GardenObject createGardenObject(){
        GardenObject gardenObject = new GardenObject(getId());
        GardenObjects.put(gardenObject.getId(),gardenObject);
        return gardenObject;
    }

    public static GardenObject getGardenObject(Integer id){
        return GardenObjects.remove(id);
    }

    public static Set<Integer> getIds(){
        return GardenObjects.keySet();
    }
}

/*
* 居民
* */
class People extends Thread{
    @Override
    public void run() {
        GardenObject gardenObject = MailBox.createGardenObject();
        System.out.println(GardenObjectDemo.now()+Thread.currentThread().getName()+"收信id："+gardenObject.getId());
        Object mail = gardenObject.getObject();
        System.out.println(GardenObjectDemo.now()+Thread.currentThread().getName()+"收信内容："+mail);
    }
}

/*
* 邮递员
* */
class PostMan extends Thread{

    private Integer id;
    private String mail;

    public PostMan(Integer id,String mail){
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GardenObject gardenObject = MailBox.getGardenObject(id);
        System.out.println(GardenObjectDemo.now()+Thread.currentThread().getName()+"送信id："+id);
        System.out.println(GardenObjectDemo.now()+Thread.currentThread().getName()+"送信内容："+mail);
        gardenObject.setObject(mail);
    }
}