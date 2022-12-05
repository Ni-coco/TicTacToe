import java.awt.event.*;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;

import java.util.*;
import javax.swing.*;

public class frame extends JFrame implements MouseMotionListener, MouseListener {

    List<Integer> x = new ArrayList<Integer>();
    List<Integer> y = new ArrayList<Integer>();
    List<Integer> take = new ArrayList<Integer>();
    char[] arr = new char[9];
    ImageIcon cross = new ImageIcon(new ImageIcon(".\\img\\cross.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    ImageIcon round = new ImageIcon(new ImageIcon(".\\img\\round.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
    JFrame win = new JFrame("TicTacToe Game");
    JPanel[] pn = new JPanel[2];
    JLabel[] player = new JLabel[2];
    JLabel[] space = new JLabel[9];
    int turn = 0;
    
    public frame() {
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setPreferredSize(new Dimension(1080, 720));
        win.setLayout(new BorderLayout());
        win.setResizable(false);

        pn[0] = new JPanel();
        pn[1] = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Stroke stroke = new BasicStroke(6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(stroke);
                g.setColor(Color.WHITE);
                int i = (int)Math.round(win.getWidth()/2.42);
                int j = (int)Math.round(win.getHeight()/14.4);
                g.drawLine(i, j, i, (int)Math.round(j*11.6));
                g.drawLine((int)Math.round(i*1.405), j, (int)Math.round(i*1.405), (int)Math.round(j*11.6));
                g.drawLine((int)Math.round(i*0.615), (int)Math.round(j*4.44), (int)Math.round(i*1.753), (int)Math.round(j*4.44));
                g.drawLine((int)Math.round(i*0.615), (int)Math.round(j*8.16), (int)Math.round(i*1.753), (int)Math.round(j*8.16));
            }
        };

        resetall();

        //Panel Background and Layout
        pn[0].setBackground(Color.WHITE);
        pn[0].setLayout(new FlowLayout(1, 200, 10));
        pn[1].setBackground(Color.BLACK);
        //pn[1].setLayout(new GridBagLayout());
        pn[1].setLayout(null);


        //Panel[0]
        player[0] = new JLabel("Player 1");
        player[1] = new JLabel("Player 2");
        pn[0].add(player[0]);
        pn[0].add(player[1]);

        //Labels for panel[1]
        for(int i = 0; i < space.length; i++)
            space[i] = new JLabel();
        int i = 300;
        int j = 95;
        for (int k = 0; k < space.length; k++) {
            if (k % 3 == 0 && k != 0) {
                i = 300;
                j += 180;
            }
            space[k].setBounds(i, j, 100, 100);
            space[k].setVisible(false);
            x.add(i);
            y.add(j);
            pn[1].add(space[k]);
            i += 185;
        }

        //Adding Panels
        win.add(pn[0], BorderLayout.NORTH);
        win.add(pn[1], BorderLayout.CENTER);

        //MouseListener
        pn[1].addMouseMotionListener(this);
        pn[1].addMouseListener(this);
        win.addMouseMotionListener(this);
        win.addMouseListener(this);

        //Frame visiblity and pack
        win.pack();
        win.setVisible(true);

        for (;;) {
            player[turn%2].setText("Player " + (turn%2 == 0 ? 1 : 2) + " turn!");
            player[turn%2].setForeground(Color.RED);
            player[(turn%2 == 0 ? 1 : 0)].setText("Player " + (turn%2 == 0 ? 2 : 1));
            player[(turn%2 == 0 ? 1 : 0)].setForeground(Color.BLACK);

            int win = check_win(arr);
            if (win == 1) {
                System.out.println("Player 1 win");
                resetall();
            }
            else if (win == 2) {
                System.out.println("Player 2 win");
                resetall();
            }
            else if (win == 0 && take.size() == 9) {
                System.out.println("Draw");
                resetall();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int closest = getclosest(e, x, y);
        if (turn == 0 && !take.contains(closest)) {
            space[closest].setIcon(cross);
            space[closest].setVisible(true);
        }
        else if (turn == 1 && !take.contains(closest)) {
            space[closest].setIcon(round);
            space[closest].setVisible(true);
        }
        for (int i = 0; i < space.length; i++)
            if (i != closest && !take.contains(i))
                space[i].setVisible(false);
    }

    public void mousePressed(MouseEvent e) {
        int closest = getclosest(e, x, y);
        if (turn == 0 && !take.contains(closest)) {
            space[closest].setIcon(cross);
            arr[closest] = 'x';
            turn++;
        }
        else if (turn == 1 && !take.contains(closest)) {
            space[closest].setIcon(round);
            arr[closest] = 'o';
            turn--;
        }
        space[closest].setVisible(true);
        take.add(closest);
    }

    public int DistSquared(MouseEvent e, int x, int y) {
        int diffX = e.getX() - x;
        int diffY = e.getY() - y;
        return (diffX*diffX+diffY*diffY);
    }

    public int getclosest(MouseEvent e, List<Integer> x, List<Integer> y) {
        int closest = 0;
        int ShortestDistance = DistSquared(e, x.get(0), y.get(0));
        for (int i = 0; i < space.length; i++) {
            int d = DistSquared(e, x.get(i), y.get(i));
            if (d < ShortestDistance) {
                closest = i;
                ShortestDistance = d;
            }
        }
        return closest;
    }

    public int check_win(char[] arr) {
        if (arr[0] == arr[1] && arr[0] == arr[2])
            return arr[0] == 'x' ? 1 : 2;
        else if (arr[3] == arr[4] && arr[3] == arr[5])
            return arr[3] == 'x' ? 1 : 2;
        else if (arr[6] == arr[7] && arr[6] == arr[8])
            return arr[6] == 'x' ? 1 : 2;
        else if (arr[0] == arr[3] && arr[0] == arr[6])
            return arr[0] == 'x' ? 1 : 2;
        else if (arr[1] == arr[4] && arr[1] == arr[7])
            return arr[1] == 'x' ? 1 : 2;
        else if (arr[2] == arr[5] && arr[2] == arr[8])
            return arr[2] == 'x' ? 1 : 2;
        else if (arr[0] == arr[4] && arr[0] == arr[8])
            return arr[0] == 'x' ? 1 : 2;
        else if (arr[2] == arr[4] && arr[2] == arr[6])
            return arr[2] == 'x' ? 1 : 2;
        return 0;
    }
    public void resetall() {
        take.clear();
        for (int i = 0; i < 9; i++) {
            arr[i] = (char)i;
            if (space[i] != null)
                space[i].setVisible(false);
        }
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}