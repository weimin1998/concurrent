package unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // Unsafe对象只能反射获取
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher teacher = new Teacher();

        unsafe.compareAndSwapInt(teacher,idOffset,0,1);
        unsafe.compareAndSwapObject(teacher,nameOffset,null,"张三");

        System.out.println(teacher);

    }
}

class Teacher{
    volatile int id;
    volatile String name;


    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
