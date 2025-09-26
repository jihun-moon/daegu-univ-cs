//변수, 리터럴, 상수 사용하기
package 자바_기본_프로그래밍_1;

public class CircleArea {
	public static void main(String[] args) {
		final double PI = 3.14;  // 원주율을 상수로
		double radius = 10;    // 원의 반지름
		double circleArea = 0;   // 원의 면적
		
		circleArea = radius*radius*PI;  // 원의 면적 계산
		
		// 원의 면적을 화면에 출력한다.
		System.out.print("반지름 " + radius + ", ");
		System.out.println("원의 면적 = " + circleArea);
	}
}
