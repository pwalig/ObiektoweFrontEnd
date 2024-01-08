package game.application;
import javax.swing.*;

import game.maingame.*;
import game.uttt.*;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
     public MainFrame() {
        super("Games and AI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 100));

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenu m2 = new JMenu("Settings");
        JMenu m3 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m12 = new JMenuItem("Save as");
        JMenuItem m21 = new JMenuItem("Preferences");
        m1.add(m11);
        m1.add(m12);
        m2.add(m21);

        //Creating the panel at bottom and adding components
        JPanel GamesPanel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Play a game: ");
        JButton mgb = new JButton("MainGame");
        mgb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainGameMainMenuFrame();
            }
        });

        JButton utttb = new JButton("UltimateTicTacToe");
        utttb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UTTTFrame();
            }
        });
        GamesPanel.add(label); // Components Added using Flow Layout
        GamesPanel.add(mgb);
        GamesPanel.add(utttb);

        // Text Area at the Center

        //Adding Components to the frame.
        getContentPane().add(BorderLayout.SOUTH, GamesPanel);
        getContentPane().add(BorderLayout.NORTH, mb);

        pack();
        setVisible(true);
    }
}