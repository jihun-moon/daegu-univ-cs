package 모듈과_패키지_개념_자바_패키지_활용;

public class StringBufferEx { 

	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("This");
		System.out.println(sb.hashCode());
		sb.append(" is pencil");
		System.out.println(sb);
		
		sb.insert(7, " my");
		System.out.println(sb);
		
		sb.replace(8, 10, "your");
		System.out.println(sb);
		
		sb.setLength(5);
		System.out.println(sb);
		
		System.out.println(sb.hashCode());
	}

}
