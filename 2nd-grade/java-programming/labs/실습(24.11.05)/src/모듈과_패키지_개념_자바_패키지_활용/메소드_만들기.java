package 모듈과_패키지_개념_자바_패키지_활용;

class Rect{
	int width, height;
	public Rect(int width, int height) {
		this.width = width; this.height = height;
	}
	public boolean equals(Rect p) {
//		Point p = (Point)obj; // obj를 Point 타입으로 
		if(width * height == p.width * p.height) return true;
		else return false;
	}
}

public class 메소드_만들기 {

	public static void main(String[] args) {
		Rect a = new Rect(2,3);
		Rect b = new Rect(3,2);
		Rect c = new Rect(3,4);
		
		if(a.equals(b)) System.out.println("a is equals to b");
		if(a.equals(c)) System.out.println("a is equals to c");
		if(b.equals(c)) System.out.println("b is equals to c");

	}

}
