package 상속;

public interface PhoneInterface { // 인터페이스 선언
	final int TIMEOUT = 10000; // 상수 필드 선언
	void sendCall(); //추상 메소드
	void receiveCall(); //추상 메소드
	default void printLogo() { // default 메소드
		System.out.println("** Phone **");
	}
}
