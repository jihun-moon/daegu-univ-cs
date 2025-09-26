package 자바_GUI_스윙_기초;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
	public MyFrame(){
		setTitle("Swing JFream ex."); /////
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//0		Container cpane = getContentPane();
		getContentPane().setBackground(Color.ORANGE);
//1 	setLayout(new BorderLayout(30, 20);
//2  	setLayout(new GridLayout(2, 10, 5, 5));
		setLayout(null);
		
		JLabel la = new JLabel("Hello, Press Buttons!!");
		la.setLocation(130, 50);
		la.setSize(200, 20);
		add(la);
/*2
		for(int i = 0; i < 20; i++) {
			String text = Integer.toString(i);
			JButton bt = new JButton(text);
			add(bt);
		}
*/
		for(int i = 0; i < 9; i++) {
			JButton bt = new JButton(Integer.toString(i));
			bt.setLocation(i*15, i*15);
			bt.setSize(50, 20);
			add(bt);
		}
		
		/*	0 1	
		add(new JButton("OK"), BorderLayout.CENTER);
		add(new JButton("Cancel"), BorderLayout.SOUTH);
		add(new JButton("Ignore"), BorderLayout.NORTH);
		add(new JButton("red"), BorderLayout.EAST);
		add(new JButton("blue"), BorderLayout.WEST);
		*/
		
		setSize(600, 300); /////
		setVisible(true); /////
		
	}
	
	
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();

	}

}
