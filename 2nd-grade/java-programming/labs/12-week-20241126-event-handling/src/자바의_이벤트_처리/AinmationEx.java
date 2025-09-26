package 자바의_이벤트_처리;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.*;

 

public class AinmationEx extends JFrame implements ActionListener{
	private int pieces = 4;
	private int totalPieces = pieces * pieces;
	private int[] pieceNumber;
	private BufferedImage img;
	
	
	public AinmationEx() {
		setTitle("Image Draw Test");
		
		try {img = ImageIO.read(new File("src/hubble.jpg"));} 
		
		catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);}
		
		pieceNumber = new int[totalPieces];
		
		for (int i = 0; i < totalPieces; i++) {
			pieceNumber[i] = i;}
		
		add(new MyPanel(), BorderLayout.CENTER);
		JButton button = new JButton("DIVIDE");
		button.addActionListener(this);
		add(button, BorderLayout.SOUTH);
		setSize(img.getWidth(null), img.getHeight(null));
		setVisible(true);
}

 
void divide() {
	Random rand = new Random();
	int ri;
	
	for (int i = 0; i < totalPieces; i++) {
		ri = rand.nextInt(totalPieces);
		int tmp = pieceNumber[i];
		pieceNumber[i] = pieceNumber[ri];
		pieceNumber[ri] = tmp;
		}
	}

class MyPanel extends JPanel {
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int pieceWidth = img.getWidth(null) / pieces;
		int pieceHeight = img.getHeight(null) / pieces;
		for (int x = 0; x < pieces; x++) {
			int sx = x * pieceWidth;
			for (int y = 0; y < pieces; y++) {
				int sy = y * pieceHeight;
				int number = pieceNumber[x * pieces + y];
				int dx = (number / pieces) * pieceWidth;
				int dy = (number % pieces) * pieceHeight;
				g.drawImage(img, dx, dy, dx + pieceWidth, dy + pieceHeight,
						sx, sy, sx + pieceWidth, sy + pieceHeight, null);
				}
			}
		}
	}

public void actionPerformed(ActionEvent e) {
	divide();
	repaint();
}

public static void main(String[] args) {
	new AinmationEx();
}

}
