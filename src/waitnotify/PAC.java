package waitnotify;

import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

/**
 * 生产者和消费者
 */
public class PAC {

    public static int getID(){
        Random random = new Random();
        return random.nextInt();
    }
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(3);

        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                Object put = messageQueue.put(new Message(getID(), UUID.randomUUID().toString()));
                System.out.println(Thread.currentThread().getName()+":"+put);
            },"生产者"+i).start();
        }

        new Thread(()->{
            Message take = messageQueue.take();
            System.out.println(Thread.currentThread().getName()+":"+take.getValue());
        },"消费者").start();
    }
}

class MessageQueue{
    private LinkedList<Message> queue = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    //
    public Message take(){

        synchronized (queue){
            while (queue.isEmpty()){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;
        }

    }

    public Object put(Message message){
        synchronized (queue){
            while (queue.size()==capacity){
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(message);
            queue.notifyAll();
            return message.getValue();
        }

    }

}

class Message{
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }


    public Object getValue() {
        return value;
    }

}