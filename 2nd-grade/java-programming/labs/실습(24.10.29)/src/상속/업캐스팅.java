package 상속;

/*
class Shape{
	public void draw() {
		System.out.println("Shape");
	}
}
*/
abstract class Shape{
	abstract public void draw();
}

class Line extends Shape{
	public void draw() { // 메소드 오버라이딩
		System.out.println("Line");
	}
}
class Rect extends Shape{
	public void draw() { // 메소드 오버라이딩
		System.out.println("Rect");
	}
}
class Circle extends Shape{
	public void draw() { // 메소드 오버라이딩
		System.out.println("Circle");
	}
}

public class 업캐스팅 {
	static void paint(Shape p) {// Shape을 상속받은 객체들이
								// 매개 변수로 넘어올 수있음
		p.draw();   // p가 가리키는 객체에 오버라이딩된 draw() 호출.
		 			// 동적바인딩
	}
	
	public static void main(String[] args) {
		Line line = new Line();
		paint(line);  // Line의 draw() 실행. "Line" 출력
		
//		paint(new Shape()); // Shape의 draw() 실행. "Shape" 출력
		paint(new Line()); // 오버라이딩된 메소드 Line의 draw() 실행
		paint(new Rect()); // 오버라이딩된 메소드 Rect의 draw() 실행
		paint(new Circle()); // 오버라이딩된 메소드 Circle의 draw() 실행
	}
}
