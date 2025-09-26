package 실습문제;

import java.util.*;

public class 가위_바위_보 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		System.out.println("가위 바위 보 게임. 하나를 입력!");
		System.out.println("철수>>");
		String a = s.next();
		System.out.println("영희>>");
		String b = s.next();
		
		if(a.equals("가위")) {  // a가 스트링 타입일 때는 객체 참조를 비교하지만, 문자열 내용을 비교하려면 a.equals("가위")를 사용해야 합니다.
			if(b.equals("가위")) System.out.println("비겼습니다.");
			else if(b.equals("바위")) System.out.println("영희가 이겼습니다.");
			else System.out.println("철수가 이겼습니다.");
		}
		else if(a.equals("바위")) {
			if(b.equals("가위")) System.out.println("철수가 이겼습니다.");
			else if(b.equals("바위")) System.out.println("비겼습니다.");
			else System.out.println("영희가 이겼습니다.");
		}
		else {
			if(b.equals("가위")) System.out.println("영희가 이겼습니다.");
			else if(b.equals("바위")) System.out.println("철수가 이겼습니다.");
			else System.out.println("비겼습니다.");
		}
		s.close();
	}

}
