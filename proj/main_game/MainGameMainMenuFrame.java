package main_game;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.*;
//import java.awt.Graphics;
public class MainGameMainMenuFrame extends JFrame {
     public MainGameMainMenuFrame() {
        super("Main Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));

        JButton levels = new JButton("Levels");
        JButton freePlay = new JButton("FreePlay");

        GridLayout gl = new GridLayout(0, 1);
        JPanel actions = new JPanel(gl);
        actions.add(levels);
        actions.add(freePlay);

        add(BorderLayout.WEST, actions);

        pack();
        setVisible(true);
    }
}