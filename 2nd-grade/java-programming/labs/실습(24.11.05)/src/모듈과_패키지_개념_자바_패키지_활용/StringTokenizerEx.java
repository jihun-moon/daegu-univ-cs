package 모듈과_패키지_개념_자바_패키지_활용;

import java.util.*;

public class StringTokenizerEx {

	public static void main(String[] args) {
		String query = "name=jihun&address=Daegu&age=23";
		StringTokenizer st = new StringTokenizer(query, "&");
		
		int n = st.countTokens();
		System.out.println("토큰 개수= " + n);
		
		StringTokenizer st1;
		while(st.hasMoreTokens()) {
			String token = st.nextToken();
			System.out.println(token);
			
			st1 = new StringTokenizer(token, "=");
			if(st1.nextToken().equals("name")) {
				System.out.println("my name is " + st1.nextToken());
			}
		}

	}

}
