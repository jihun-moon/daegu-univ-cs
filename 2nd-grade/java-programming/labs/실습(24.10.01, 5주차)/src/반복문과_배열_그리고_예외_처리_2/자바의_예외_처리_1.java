package 반복문과_배열_그리고_예외_처리_2;

import java.util.Scanner;
import java.util.InputMismatchException;

public class 자바의_예외_처리_1 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("정수 3개를 입력하세요");
		
		int sum = 0, n = 0;
		for(int i = 0; i < 3; i++) {  // <----여기로 다시 반복 루프로 이동함.
			System.out.print(i + ">>");
			try {
				n = scanner.nextInt(); // 정수 입력
			}
			catch(InputMismatchException e){
				System.out.println("정수가 아닙니다. 다시 입력하세요!");
				scanner.next(); // 입력 스트림에 있는 정수가 아닌 토큰을 버린다.
				i--; // 인덱스가 증가하지 않도록 미리 감소
				continue; // 다음 반복 루프 쪽으로
			}
			sum += n; // 합하기
		}
		System.out.println("합은 " + sum);
		scanner.close();
	}
}
