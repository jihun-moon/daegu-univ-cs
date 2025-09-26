package 반복문과_배열_그리고_예외_처리_2;

public class 배열과_for_each문 {

	public static void main(String[] args) {
		int n[] = {1,2,3,4,5};
		int sum = 0;
		for(int k : n) { // k는 n[0], n[1], ..., n[4]로 반복
			System.out.print(k + " "); // 반복되는 k 값 출력
			sum += k;
		}
		System.out.println("합은 " + sum);
		
		String f[] = {"사과", "배", "바나나", "체리", "딸기", "포도"};
		for(String s : f) {// s는 n[0], n[1], ..., n[4]로 반복
			System.out.print(s + " ");
		}
		
	}

}
