package 상속;

class SuperObject{
	protected String name;
	public void paint() {
		draw();
	}
	public void draw() {
		System.out.println(name);
	}
}
public class 슈퍼_클래스의_멤버_접근 extends SuperObject{
	protected String name;
	public void draw() {
		name = "Sub";
		super.name = "Super";
		super.draw();
		System.out.println(name);
	}
	public static void main(String[] args) {
		SuperObject b = new 슈퍼_클래스의_멤버_접근();
		b.paint();

	}

}
