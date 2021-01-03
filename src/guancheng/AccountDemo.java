package guancheng;

import java.util.Random;

/*
转账
共享变量是两个账户的余额
* */
public class AccountDemo {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account();
        Account account2 = new Account();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account1.transfer(account2, new Random().nextInt(100)+1);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account2.transfer(account1, new Random().nextInt(100)+1);
            }
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();


        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println(account1.getBalance()+account2.getBalance());

        //System.out.println(new Random().nextInt(100)+1);
    }
}


class Account{

    private int balance = 1000;

    public void transfer(Account target,int money){
        synchronized (Account.class){
            if(balance>money){
                balance -= money;
                target.balance += money;
            }
        }


    }

    public int getBalance() {
        return balance;
    }
}

