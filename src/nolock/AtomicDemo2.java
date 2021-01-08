package nolock;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
public class AtomicDemo2 {
    public static void main(String[] args) throws InterruptedException {
        DecimalAccountAtomic decimalAccountAtomic = new DecimalAccountAtomic();
        decimalAccountAtomic.setBalance(new BigDecimal("100000"));

        ArrayList<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new Thread(()->{decimalAccountAtomic.withdraw(new BigDecimal("10"));}));
        }

        for (Thread thread : list) {
            thread.start();
        }
        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(decimalAccountAtomic.getBalance());

    }
}

interface DecimalAccount{

    BigDecimal getBalance();

    void withdraw(BigDecimal money);
}

class DecimalAccountAtomic implements DecimalAccount{

    private AtomicReference<BigDecimal> balance;


    public void setBalance(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal money) {
        while (true){
            BigDecimal pre = balance.get();
            BigDecimal next = pre.subtract(money);

            if(balance.compareAndSet(pre,next)){
                break;
            }
        }
    }
}


