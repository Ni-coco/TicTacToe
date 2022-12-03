//import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class frame extends JFrame {

    JFrame win = new JFrame("TicTacToe Game");
    JPanel[] pn = new JPanel[2];
    JLabel[] label = new JLabel[10];
    
    public frame() {
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setPreferredSize(new Dimension(1080, 720));
        win.setLayout(new BorderLayout());

        pn[0] = new JPanel();
        pn[1] = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Stroke stroke = new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(stroke);
                g.setColor(Color.MAGENTA);
                g.drawLine(50, 50, 100, 100);
                g.drawLine(150, 150, 200, 200);
            }
        };

        pn[0].setBackground(Color.WHITE);
        pn[0].setLayout(new FlowLayout(1, 200, 10));
        pn[1].setBackground(Color.BLACK);

        label[0] = new JLabel("Player 1");
        label[1] = new JLabel("Player 2");

        pn[0].add(label[0]);
        pn[0].add(label[1]);

        win.add(pn[0], BorderLayout.NORTH);
        win.add(pn[1], BorderLayout.CENTER);

        win.pack();
        win.setVisible(true);
    }
}
