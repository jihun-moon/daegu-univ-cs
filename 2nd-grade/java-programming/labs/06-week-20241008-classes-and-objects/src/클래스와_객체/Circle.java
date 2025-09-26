package 클래스와_객체;

import java.util.*;
public class Circle {
	int radius;
	String name;
	
	public Circle() { // 생성자 2
		radius = 1; name = "";
	}	
	public Circle(int radius, String name) { // 생성자 1 
		this.radius = radius; this.name = name; // * this 래퍼런스 *
	}	
	public double getArea() {
		return 3.14 * radius * radius;
	}

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Circle pizza = new Circle(10, "자바피자"); // 생성자 1
//	    System.out.print("pizza>> ");
//		pizza.radius = scan.nextInt();
//		pizza.name = "자바피자";
		double area = pizza.getArea();
		System.out.println(pizza.name + "의 면적은 " + area);
		
		Circle donut = new Circle(); // 생성자 2
		System.out.print("donut>> ");
		donut.radius = scan.nextInt();
		donut.name = "자바도넛";
		area = donut.getArea();
		System.out.println(donut.name + "의 면적은 " + area);
		
		scan.close();
	}

}
