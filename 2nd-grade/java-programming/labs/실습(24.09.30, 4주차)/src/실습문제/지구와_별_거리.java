package 실습문제;

//지구에서 가장 가까운 별까지의 거리 계산하기
public class 지구와_별_거리 {

	public static void main(String[] args) {
		final double light_speed = 30e4;
		double distance = 40e12;
		
		double secs;
		
		secs = distance / light_speed;
		
		double light_year  = secs/(60.0 * 60.0 * 24.0 * 365.0);
		
		System.out.print("걸리는 시간은 " + light_year + " 광년입니다.");

	}

}
