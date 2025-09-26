package 실습;

import javax.swing.*;
import java.awt.*;

public class Calculation extends JFrame{
	private JPanel panel;
	private JTextField tf;
	private JButton[] bt;
	private String[] labels = {
			"Backspace", "", "", "CE", "C",
			"7", "8", "9", "/", "sqrt",
			"4", "5", "6", "x", "%",
			"1", "2", "3", "-", "1/x",
			"0", "+/-", ".", "+", "=",
	};
	public Calculation() {
		tf = new JTextField(35);
		panel = new JPanel();
		tf.setText("0.");
		tf.setEnabled(false);
		panel.setLayout(new GridLayout(5,5,3,3));
		bt = new JButton[25];
		int index = 0;
		for(int rows = 0; rows < 5; rows++) {
			for(int cols = 0; cols < 5; cols++) {
				bt[index] = new JButton(labels[index]);
				if(cols >= 3)
					bt[index].setForeground(Color.RED);
				else
					bt[index].setForeground(Color.BLUE);
				bt[index].setBackground(Color.YELLOW);
				panel.add(bt[index]);
				index++;
			}
		}
		add(tf, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		
		setTitle("계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 250);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Calculation();

	}

}
