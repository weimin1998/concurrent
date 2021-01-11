package juc;

import util.MyUtil;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2,1000,TimeUnit.MILLISECONDS,2,(task,queue)->{
            // queue.put(task,1500L,TimeUnit.MILLISECONDS);
            // System.out.println(MyUtil.now()+"放弃");

            //throw new RuntimeException("放弃任务");

            task.run();
        });

        for (int i = 0; i < 5; i++) {
            int j=i;
            threadPool.execute(()->{
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(j);
            });
        }
    }
}


class ThreadPool{
    // 任务队列
    private BlockingQueue<Runnable> blockingQueue;

    // 线程
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    // 超时时间
    private long timeout;
    private TimeUnit timeUnit;

    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;

    class Worker extends Thread{
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            while (task!=null||(task = blockingQueue.take(timeout,timeUnit))!=null){
                try {
                    System.out.println(MyUtil.now()+"真正执行任务");
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task =null;
                }
            }

            synchronized (workers){
                System.out.println(MyUtil.now()+"被移除");
                workers.remove(this);
            }
        }
    }

    public ThreadPool(  int coreSize, long timeout, TimeUnit timeUnit,int queueCapacity,RejectPolicy<Runnable> rejectPolicy) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = new BlockingQueue(queueCapacity);
        this.rejectPolicy = rejectPolicy;
    }

    public void execute(Runnable task){
        System.out.println(MyUtil.now()+"即将开始执行任务");
        synchronized (workers){
            if(workers.size()<coreSize){
                Worker worker = new Worker(task);

                System.out.println(MyUtil.now()+"新开一个线程");

                workers.add(worker);
                System.out.println(MyUtil.now()+"执行任务");
                worker.start();
            }else {
                System.out.println(MyUtil.now()+"线程数已达核心数，尝试将任务放入队列");



                // 如果队列满了怎么办？
                // 拒绝策略
                // 1 死等
                //blockingQueue.put(task);
                // 2 带超时的等待
                blockingQueue.tryPut(task,rejectPolicy);
                // 3 放弃这个任务
                // 4 抛异常
                // 5 调用者自己去执行任务
            }
        }
    }
}

/**
 * 拒绝策略
 */
interface RejectPolicy<T>{
    void reject(T task,BlockingQueue<T> blockingQueue);
}

/**
 * 任务的阻塞队列
 * @param <T>
 */
class BlockingQueue<T>{
    // 存放任务的队列
    private final ArrayDeque<T> tasks = new ArrayDeque<>();

    // 锁
    private final ReentrantLock lock = new ReentrantLock();

    // 消费者的条件变量
    private final Condition consumer = lock.newCondition();

    // 生产者的条件变量
    private final Condition productor = lock.newCondition();

    // 阻塞队列的容量
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 获取任务
    public T take(){
        try {
            lock.lock();
            while (tasks.isEmpty()){
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = tasks.removeFirst();
            productor.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
    // 获取任务 （带超时时间的）
    public T take(long time, TimeUnit timeUnit){
        try {
            long nanos = timeUnit.toNanos(time);
            lock.lock();
            while (tasks.isEmpty()){
                try {

                    if(nanos<=0){
                        return null;
                    }
                    nanos = consumer.awaitNanos(nanos);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = tasks.removeFirst();
            productor.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    // 放任务
    public void put(T t){
        try {
            lock.lock();
            while (tasks.size() == capacity){
                try {
                    System.out.println(MyUtil.now()+"等待将任务放入队列");
                    productor.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(MyUtil.now()+"将任务放入队列");
            tasks.addLast(t);
            consumer.signal();
        }finally {
            lock.unlock();
        }
    }

    public boolean put(T t,long timeout,TimeUnit timeUnit){
        try {
            long nanos = timeUnit.toNanos(timeout);
            lock.lock();
            while (tasks.size() == capacity){
                try {
                    if(nanos<=0){
                        return false;
                    }
                    System.out.println(MyUtil.now()+"等待将任务放入队列");
                    nanos = productor.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(MyUtil.now()+"将任务放入队列");
            tasks.addLast(t);
            consumer.signal();
            return true;
        }finally {
            lock.unlock();
        }
    }

    public int size(){
        try {
            lock.lock();
            return tasks.size();
        }finally {
            lock.unlock();
        }
    }

    //
    public void tryPut(T task, RejectPolicy<T> rejectPolicy) {
        try {
            lock.lock();
            if(tasks.size()==capacity){
                // 满了
                rejectPolicy.reject(task,this);
            }else {
                // 队列没满
                tasks.addLast(task);
                consumer.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}
