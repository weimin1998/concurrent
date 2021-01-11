package finalDemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * SimpleDateFormat是 线程不安全的
 * DateTimeFormatter是 线程安全的 他是一个final类
 *
 */
public class Demo {
    public static void main(String[] args) {

        test();

        //test2();
    }

    private static void test2() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                TemporalAccessor temporalAccessor = dateTimeFormatter.parse("2021-01-08");
                System.out.println(temporalAccessor);
            }).start();
        }
    }

    private static void test() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    synchronized (simpleDateFormat) {
                        Date parse = simpleDateFormat.parse("2021-01-08");
                        System.out.println(parse);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
