package finalDemo;

/**
 * String
 * 保护性拷贝，通过创建副本对象，来避免共享
 *
 */
public class Demo2 {

    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.a);

    }
}

class Test{
    public final int a = 0;
}
