package 실습;

public class 자율_주행_자동차 {

	public static void main(String[] args) {
		OperateCar obj = new AutoCar();
		obj.start();
		obj.setSpeed(30);
		obj.turn(15);
		obj.stop();
	}
}
