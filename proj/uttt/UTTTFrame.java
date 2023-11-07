package uttt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.awt.Graphics;
public class UTTTFrame extends JFrame {
     public UTTTFrame() {
        super("Ultimate-Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //setSize(640, 400);

        JButton send = new JButton("Send");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(UTTTFrame.this, send.getWidth(), "send1", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        JButton send2 = new JButton("Send2") {
            private void doDrawing(Graphics g) {
        
                g.drawLine(0, 10, 50, 10);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawString("Java 2D", 10, 10);
            }

            @Override
            public void paint(Graphics g) {
        
                super.paint(g);
                doDrawing(g);
            }

            @Override
            public void update(Graphics g){
                
                super.paint(g);
                doDrawing(g);
            }
        };
        send2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e1) {
            try {
                String line;
                Process p = new ProcessBuilder("C:\\D\\Politechnika\\Sem3\\Programowanie_Obiektowe\\builds\\test_main.exe").start();
                BufferedReader bri = new BufferedReader (new InputStreamReader(p.getInputStream()));
                BufferedReader bre = new BufferedReader (new InputStreamReader(p.getErrorStream()));
                while ((line = bri.readLine()) != null) {
                  System.out.println(line);
                }
                bri.close();
                while ((line = bre.readLine()) != null) {
                  System.out.println(line);
                }
                bre.close();
                p.waitFor();
                System.out.println("Done.");
                }
                catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });

        Canvas jpanle = new Canvas() {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(200, 200);
            }
            
            @Override
            public Dimension getMinimumSize() {
                return new Dimension(200, 200);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }

            private void doDrawing(Graphics g) {
        
                g.drawLine(20, 100, 120, 100);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawString("Java 2D", 50, 50);
            }

            @Override
            public void paint(Graphics g) {
        
                super.paint(g);
                doDrawing(g);
            }
        };

        //JPanel mian = new JPanel();

        //mian.add(send);
        //mian.add(jpanle);
        //add(mian);
        add(send);
        getContentPane().add(BorderLayout.NORTH, send);
        getContentPane().add(BorderLayout.CENTER, jpanle);
        getContentPane().add(BorderLayout.SOUTH, send2);
        //getContentPane().add(BorderLayout.EAST, send);

        pack();
        setVisible(true);
    }
}