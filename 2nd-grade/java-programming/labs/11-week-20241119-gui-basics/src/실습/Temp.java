package 실습;

import javax.swing.*;
import java.awt.*;

public class Temp extends JFrame{
	public Temp() {
		JPanel panel = new JPanel();
		add(panel);
		
		JLabel la1 = new JLabel("화씨온도");
		JLabel la2 = new JLabel("섭씨온도");
		JTextField f1 = new JTextField(15);
		JTextField f2 = new JTextField(15);
		JButton bt = new JButton("변환");
		panel.add(la1);
		panel.add(f1);
		panel.add(la2);
		panel.add(f2);
		panel.add(bt);
		
		setTitle("온도변환기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Temp();

	}

}
