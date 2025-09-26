package 실습문제;

import java.util.*;

public class 월의_일수_출력하기 {

	public static void main(String[] args) {
		int month;
		int days = 0;
		
		System.out.print("일수를 알고 싶은 월을 입력하시오:");
		Scanner scan = new Scanner(System.in);
		
		month = scan.nextInt();
		switch (month) {
		case 4: case 6: case 9: case 11:
			days = 30;
			break;
		
		case 2:
			days = 28;
			break;
			
		default:
			days = 31;
			break;
		}
		System.out.println("월의 날수는 " + days);
		
		scan.close();
	}

}
