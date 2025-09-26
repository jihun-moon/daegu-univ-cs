package 자바_기본_프로그래밍_1;

public class 타입_변환 {
	public static void main(String[] args) {
		byte b = 127;
		int i = 100;
		
		System.out.println(b+i);       // b가 int 타입으로 자동 변환
		System.out.println(10/4);
		System.out.println(10.0/4);    // 4가 4.0으로 자동 변환
		System.out.println((char)0x12340041);
		System.out.println((byte)(b+i));
		System.out.println((int)2.9 + 1.8);
		System.out.println((int)(2.9 + 1.8));
		System.out.println((int)2.9 + (int)1.8);
	}
}
