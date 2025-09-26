package 반복문과_배열_그리고_예외_처리_2;

public class do_while문 {

	public static void main(String[] args) {
		char a = 'a';
		
		do {
			System.out.print(a);
			a = (char)(a + 1);
		}while(a <= 'z');
	}
}
