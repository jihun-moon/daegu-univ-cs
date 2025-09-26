package 실습;

import java.util.*;

public class 지뢰찾기_게임 {

	public static void main(String[] args) {
		boolean board[][] = new boolean[10][10];
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				Random r = new Random();
				if(r.nextInt(100) < 30)
					board[i][j] = true;
			}
		}
		
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if(board[i][j]) System.out.print("# ");
				else			System.out.print(". ");
			}
			System.out.println();
		}	
	}
}
