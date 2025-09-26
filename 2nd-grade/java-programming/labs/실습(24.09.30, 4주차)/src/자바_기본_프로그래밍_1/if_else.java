package 자바_기본_프로그래밍_1;

import java.util.Scanner;

public class if_else {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("나이를 입력하시오:");
		int age = scanner.nextInt();
		if((age >= 20) && (age < 30)) {
			System.out.print("20대입니다. ");
			System.out.println("20대라서 행복합니다!");
		}
		else
			System.out.println("20대가 아닙니다.");
		
		scanner.close();
	}
	
}


/*
import java.util.Scanner;

public class NestedIf {
	public static void main(String[] args) {
	Scanner scanner= new Scanner(System.in);
 	
 	System.out.print("점수를 입력하세요(0~100):");
 	int score = scanner.nextInt(); 
	
	System.out.print("학년을 입력하세요(1~4):");
 	int year = scanner.nextInt(); 
	
	if(score >= 60) { // 60점 이상
		if(year != 4)
			System.out.println("합격!"); // 4학년 아니면 합격
		else if(score >= 70)
 			System.out.println("합격!"); // 4학년이 70점 이상이면 합격
	else
 		System.out.println("불합격!"); // 4학년이 70점 미만이면 불합격
	}
 	else // 60점 미만 불합격
		System.out.println("불합격!");
 	
 	scanner.close();
 	}
}
*/