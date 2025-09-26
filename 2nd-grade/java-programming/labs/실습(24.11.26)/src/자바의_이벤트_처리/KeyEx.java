package 자바의_이벤트_처리;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class KeyEx extends JFrame{
	private JPanel cp = new JPanel();
	private JLabel la = new JLabel("HELLO");
	public KeyEx() {
		super("상, 하, 좌, 우 키를 이용해 텍스트 움직이기!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		Container c = getContentPane();
		setContentPane(cp);
		cp.setLayout(null);
		cp.addKeyListener(new MyListener());
		cp.addMouseListener(new MyListener());
		cp.addMouseMotionListener(new MyListener());
		la.setLocation(50, 50);
		la.setSize(200, 20);
		cp.add(la);
		setSize(400, 400);
		setVisible(true);
		cp.setFocusable(true);
		cp.requestFocus();
	}
	class MyListener implements KeyListener, MouseListener, MouseMotionListener{
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode) {
			case KeyEvent.VK_UP:
				la.setLocation(la.getX(), la.getY() - 10); break;
			case KeyEvent.VK_DOWN:
				la.setLocation(la.getX(), la.getY() + 10); break;
			case KeyEvent.VK_LEFT:
				la.setLocation(la.getX() - 10, la.getY()); break;
			case KeyEvent.VK_RIGHT:
				la.setLocation(la.getX() + 10, la.getY()); break;
				
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
		public void mousePressed(MouseEvent e) {
			la.setText("마우스 누름 (" + e.getX() + "," + e.getY() + ")");
		}
		public void mouseReleased(MouseEvent e) {
			la.setText("마우스 땜 (" + e.getX() + "," + e.getY() + ")");
		}
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				int r = (int)(Math.random()*256);
				int g = (int)(Math.random()*256);
				int b = (int)(Math.random()*256);
				JPanel p = (JPanel)e.getSource();
				p.setBackground(new Color(r, g, b));
			}
		}
		public void mouseEntered(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(Color.CYAN);
		}
		public void mouseExited(MouseEvent e) {
			JPanel p = (JPanel)e.getSource();
			p.setBackground(Color.YELLOW);
		}
		public void mouseDragged(MouseEvent e) {
			la.setText("마우스 드래그 (" + e.getX() + "," + e.getY() + ")");
		}
		public void mouseMoved(MouseEvent e) {
			la.setText("마우스 움직임 (" + e.getX() + "," + e.getY() + ")");
		}
		
	}


	public static void main(String[] args){
		new KeyEx();
	}
}
