package 실습;

import java.util.*;

public class 주사위_던지기 {

   public static void main(String[] args) {
      final int SIZE = 6; 
      int dice[] = new int[SIZE]; 
      
      
      for(int i = 0; i < 10000; i++) {
    	 Random r = new Random();
         int index = r.nextInt(SIZE); 
         ++dice[index];
      }
      
      for(int i = 0; i < SIZE; i++) 
         System.out.println(" " + (i + 1) + "\t" + dice[i]);
         
   }
}