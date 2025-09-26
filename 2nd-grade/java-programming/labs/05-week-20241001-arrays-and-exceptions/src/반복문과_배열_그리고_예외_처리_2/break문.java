package 반복문과_배열_그리고_예외_처리_2;

import java.util.Scanner;

public class break문 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("exit을 입력하면 종료합니다.");
		
		while(true) {
			System.out.print(">>");
			String text = scanner.nextLine();
			if(text.equals("exit")) // "exit"이 입력되면 반복 종료
				break; //while 문을 벗어남.
		}
		System.out.println("종료합니다....");
		scanner.close();
	}

}
