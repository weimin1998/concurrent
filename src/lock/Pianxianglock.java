package lock;

import org.openjdk.jol.info.ClassLayout;

/*
* 偏向锁
*
* -XX:BiasedLockingStartupDelay=0
* */
public class Pianxianglock {
    public static void main(String[] args) throws InterruptedException {
        Dog dog = new Dog();

        System.out.println(ClassLayout.parseInstance(dog).toPrintable());

        synchronized (dog){
            System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(dog).toPrintable());
        /*Thread.sleep(4000);

        System.out.println(ClassLayout.parseInstance(new Dog()).toPrintable());*/
    }
}

class Dog{}
