package 반복문과_배열_그리고_예외_처리_2;

import java.util.Scanner;

public class 배열의_length_필드_활용 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("5개의 정수를 입력하세요.");
		int intArray[] = new int[5];
		
		double sum = 0.0;
		for(int i = 0; i < intArray.length; i++) {
			intArray[i] = scanner.nextInt(); //키보드에서 입력 받은 정수 저장
		}
		
		for(int i = 0; i < intArray.length; i++) {
			sum += intArray[i]; // 배열에 저장된 정수 값을 더하기
		}
		
		System.out.println("평균은 " + sum/intArray.length);
		scanner.close();
	}

}
