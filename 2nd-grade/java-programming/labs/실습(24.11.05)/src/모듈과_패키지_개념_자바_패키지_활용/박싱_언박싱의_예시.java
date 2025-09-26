package 모듈과_패키지_개념_자바_패키지_활용;

public class 박싱_언박싱의_예시 { //★★★★★

	public static void main(String[] args) {
		int i = 10;
		Integer intObject = i; //auto boxing
		System.out.println("intObject = " + intObject);
		
		i = intObject + 10; //auto unboxing
		System.out.println("i = " + i);

	}

}
