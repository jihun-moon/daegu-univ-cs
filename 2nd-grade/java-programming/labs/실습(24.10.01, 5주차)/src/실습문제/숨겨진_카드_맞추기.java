package 실습문제;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class 숨겨진_카드_맞추기 { // 중간고사에 나온다고 함. 중요!!

	public static void main(String[] args) {
		int low, high;
		int card;
		Random r = new Random();
		Scanner scanner = new Scanner(System.in);

		while(true) {
			int i = 0;
			low = 0;
			high = 99;
			card = r.nextInt(100);
			System.out.println("UP & Down, Type your guess number!");	
			
			while(true) {
				System.out.println(low + "-" + high);
				System.out.print(i + 1 + ">>");
				int n = 0;
				
				try {
					n = scanner.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Only Integer!!");
					scanner.nextLine();
					continue;
				}
				
				if(n > high || n < low)
					System.out.println("Out of range!");
				else {
					if(n == card) {
						System.out.println("Good! Correct!");
						break;
					}
					else if(n > card) {
						System.out.println("Down!");
						high = n;
					}
					else {
						System.out.println("Up!");
						low = n;
					}
				}
				i++;
			}
			System.out.println("One more?(y/n)>>");
			if(scanner.next().equals("n"))
				break;
		}		
		scanner.close();
	}
}
