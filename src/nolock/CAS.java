package nolock;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CAS {
    public static void main(String[] args) throws InterruptedException {


        //unsafe();

        //safe();

        safecas();

    }

    private static void safecas() throws InterruptedException {
        AccountCAS accountCAS = new AccountCAS();
        accountCAS.setBalance(new AtomicInteger(100000));

        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(()->{
                accountCAS.withdraw(10);
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(accountCAS.getBalance());
    }

    private static void unsafe() throws InterruptedException {
        AccountUnsafe account = new AccountUnsafe();
        account.setBalance(100000);
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(()->{
                account.withdraw(10);
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(account.getBalance());
    }

    private static void safe() throws InterruptedException {
        AccountSafe accountSafe = new AccountSafe();
        accountSafe.setBalance(100000);
        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(()->{
                accountSafe.withdraw(10);
            }));
        }

        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }
        System.out.println(accountSafe.getBalance());
    }
}

interface Account{
    int getBalance();


    void withdraw(int money);
}

class AccountUnsafe implements Account{

    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void withdraw(int money) {
        balance -= money;
    }
}

class AccountSafe implements Account{

    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        synchronized (this){
            return balance;
        }
    }

    @Override
    public synchronized void withdraw(int money) {
            balance -= money;
    }
}

class AccountCAS implements Account{

    private AtomicInteger balance;

    public void setBalance(AtomicInteger balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(int money) {
        /*while (true){
            // 获取余额
            int pre = getBalance();
            //
            int next = pre -money;

            if(balance.compareAndSet(pre,next)){
                break;
            }
        }*/

        // 这一行可以代替上面的
        balance.getAndAdd(-1*money);
    }
}
