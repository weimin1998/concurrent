package finalDemo;

public class Demo3 {
    final  static int A = 10;
    final  static int B = Short.MAX_VALUE+1;

    final  int a = 20;
    final  int b = Integer.MAX_VALUE;

    public void test(){
        System.out.println(A);
        System.out.println(B);
        System.out.println(new Demo3().a);
        System.out.println(new Demo3().b);
    }
}
