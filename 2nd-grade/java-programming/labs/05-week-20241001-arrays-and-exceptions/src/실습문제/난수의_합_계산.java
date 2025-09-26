package 실습문제;

import java.util.Random;
import java.util.Scanner;

public class 난수의_합_계산 {

	public static void main(String[] args) {
		
		Random generator = new Random();
		
		System.out.print("난수의 개수: ");
		Scanner scanner = new Scanner(System.in);
		int count = scanner.nextInt(); // 이거는 scanner에 있는 nextInt임. 아래와 다름.
		int sum = 0;
		
		for (int i = 0; i < count; i++) {
			int number = generator.nextInt(100); // 랜덤에 있는 nextInt 100의 값보다 1작은 0 ~ 99까지 무작위 수
			System.out.print(" " + number);
			sum += number;
		}
		System.out.print("난수 " + count + "개의 합은 " + sum);
		
		scanner.close();

	}

}
