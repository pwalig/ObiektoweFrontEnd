package game.maingame;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
//import java.awt.Graphics;
public class MainGameFrame extends JFrame {
     public MainGameFrame() {
        super("Main Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));

        pack();
        setVisible(true);
    }
}