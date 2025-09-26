package 자바의_이벤트_처리;

import java.awt.*;
import javax.swing.*;

public class DrawTree extends JFrame {
    public DrawTree() {
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        TreePanel treePanel = new TreePanel();
        add(treePanel);
        setVisible(true);
    }

    // Custom JPanel for tree drawing
    class TreePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GREEN);
            drawTree(g, 400, 600, -90, 10);
        }

        private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
            if (depth == 0) return;
            int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 10.0);
            int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 10.0);
            g.drawLine(x1, y1, x2, y2);
            drawTree(g, x2, y2, angle - 20, depth - 1);
            drawTree(g, x2, y2, angle + 20, depth - 1);
        }
    }

    public static void main(String[] args) {
        new DrawTree();
    }
}
