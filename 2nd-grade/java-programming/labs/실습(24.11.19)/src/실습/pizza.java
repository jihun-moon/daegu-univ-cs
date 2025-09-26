package 실습;

import javax.swing.*;
import java.awt.*;

public class pizza extends JFrame{
	public pizza() {
		JPanel panel = new JPanel();
		JPanel panelA = new JPanel();
		JPanel panelB = new JPanel();
		panelA.setBackground(Color.CYAN);
		panelB.setBackground(Color.YELLOW);
		JLabel la1 = new JLabel("자바 피자에 오신 것을 환영합니다. 피자 종류 선택!");
		panelA.add(la1);
		
		JButton bt1 = new JButton("콤보피자");
		JButton bt2 = new JButton("포테이토피자");
		JButton bt3 = new JButton("불고기피자");
		panelB.add(bt1);
		panelB.add(bt2);
		panelB.add(bt3);
		
		JLabel la2 = new JLabel("개수: ");
		JTextField f1 = new JTextField(10);
		panelB.add(la2);
		panelB.add(f1);
		
		panel.add(panelA);
		panel.add(panelB);
		add(panel);
		
		setTitle("자바피자");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new pizza();

	}

}
