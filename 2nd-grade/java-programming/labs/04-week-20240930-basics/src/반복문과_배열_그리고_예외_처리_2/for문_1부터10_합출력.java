package 반복문과_배열_그리고_예외_처리_2;

public class for문_1부터10_합출력 {

	public static void main(String[] args) {
		int i, sum = 0;
		
		for(i=1; i <= 10; i++) {
			sum += i;
			System.out.print(i);
			
			if(i<=9)
				System.out.print("+");
			else {
				System.out.print("=");
				System.out.print(sum);
			}
		}
	}
}
