

public class Association {
    public Association() {
    }

    public static void main(String args) {
        Student s1 = new Student();
        Professor p1 = new Professor();
        s1.name = "홍길동";
        s1.sid = 2004001;
        System.out.println(s1.name + "   " + s1.sid);
        System.out.println(s1.getAge() + "   " + s1.getGrade());

        p1.name = "홍교수";
        p1.pid = 1016;
        System.out.println(p1.name + "   " + p1.pid);
        System.out.println(p1.getAge() + "   " + p1.getPayment());
    }

}