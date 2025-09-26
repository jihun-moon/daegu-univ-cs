//Scanner를 이용한 키 입력 연습
package 자바_기본_프로그래밍_1;

import java.util.Scanner;

 public class ScannerEx{
	 
	 public static void main(String args[]) {
		 
		@SuppressWarnings("resource")
		
		Scanner a = new Scanner(System.in);
		System.out.println("나이, 체중, 신장을 빈칸으로 분리하여 순서대로 입력하세요.");
		System.out.println("당신의 나이는 " + a.nextInt() + "살입니다.");
		System.out.println("당신의 체중은 " + a.nextDouble() + "kg입니다.");
		System.out.println("당신의 신장은 " + a.nextDouble() + "cm입니다.");

	 }
 }