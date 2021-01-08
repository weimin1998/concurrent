package nolock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 原子更新
 * 字段更新器
 */
public class AtomicField {
    public static void main(String[] args) {
        Student student = new Student("weimin");
        AtomicReferenceFieldUpdater<Student, String> fieldUpdater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        boolean b = fieldUpdater.compareAndSet(student, "weimin", "魏敏");
        System.out.println(b);
        System.out.println(student.name);
    }
}

class Student{
    public volatile String name;

    public Student(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
