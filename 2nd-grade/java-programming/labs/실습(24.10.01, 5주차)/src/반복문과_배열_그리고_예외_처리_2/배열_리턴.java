package 반복문과_배열_그리고_예외_처리_2;

public class 배열_리턴 {
	static int[] makeArray() {
		int temp[] = new int[4];
		for(int i = 0; i < temp.length; i++)
			temp[i] = i; // 배열 초기화, 0,1,2,3
		return temp; //배열 리턴
	}
	
	public static void main(String[] args) {
		int intArray[];
		intArray = makeArray();// 메소드가 리턴한 배열 참조
		for(int i = 0; i<intArray.length; i++)
			System.out.print(intArray[i] + " ");

	}

}
