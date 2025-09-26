package 상속;

class Point{
	private int x, y; 				// 한 점을 구성하는 x, y 좌표
	public Point() {
		this.x = this.y = 0;
	}
	public Point(int x, int y){
		this.x = x; this.y = y;
	} 		
	public void showPoint() { // 점의 좌표 출력
		System.out.println("(" + x + "," + y + ")");
	}
}

// Point를 상속 받은 ColorPoint 선언
class ColorPoint extends Point{
	private String color; 			// 점의 색
	public ColorPoint(int x, int y, String color) {
		super(x, y);
		this.color = color;
	}
	public void showColorPoint() { 	// 컬러 점의 좌표 출력
		System.out.print(color);
		showPoint(); 				// Point의 showPoint() 호출
	}
}

public class 클래스_상속 {
	public static void main(String[] args) {
		ColorPoint cp = new ColorPoint(5, 6, "blue"); // 컬러와 좌표 출력
		cp.showColorPoint();
	}

}
