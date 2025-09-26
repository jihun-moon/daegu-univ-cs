package 반복문과_배열_그리고_예외_처리_2;

import java.util.Scanner;

public class While문 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		int count = 0, n = 0;
		double sum = 0;
		
		System.out.println("정수를 입력하고 마지막에 0을 입력하세요.");
		while((n = scanner.nextInt()) != 0) {
			sum = sum + n;
			count++;
		}
		System.out.print("수의 개수는 " + count + "개이며 ");
		System.out.println("평균은 " + sum/count + "입니다.");
		scanner.close();
	}

}
