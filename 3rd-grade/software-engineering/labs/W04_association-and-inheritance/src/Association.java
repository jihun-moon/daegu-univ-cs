class Person{
    public String name;
    public int getAge(){
        return 49;
    }
}
class Student extends Person{
    public int sid;
    public int getGrade(){
        return sid - 200400;
    }
}

class Professor extends Person {
    public int pid;
    public int getPayment() {
        return pid + 10000;
    }
}

public class Association{

    public static void main(String[] args) {
        Student s1 = new Student();
        Professor p1 = new Professor();
        s1.name = "홍길동";
        s1.sid = 200401;
        System.out.println("Student name: " + s1.name + "--Student ID: "+ s1.sid);
        System.out.println("Student age: " + s1.getAge() + "--Student Grade: " + s1.getGrade());
        p1.name = "홍교수";
        p1.pid = 1016;
        System.out.println("Professor name: " + p1.name + "--Professor ID: " + p1.pid);
        System.out.println("Professor age: " + p1.getAge() + "--Professor Grade: " + p1.getPayment());
    }
}