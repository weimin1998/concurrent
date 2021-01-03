import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/*
* Callable
*
* FutureTask是Runnable的实现
* */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                return 100;
            }
        });

        new Thread(integerFutureTask,"线程一").start();


        // 这个get会阻塞，等待线程执行完方法，返回结果
        System.out.println(integerFutureTask.get());

        System.out.println("完成");
    }
}
