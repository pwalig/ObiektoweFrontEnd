package game.maingame.frames;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.application.LabeledComponent;
import game.application.LabeledComponent.LabelPosition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainGameFrame extends JFrame {
    int playersCount = 1;
    ArrayList<Integer> playerBeingCounts = new ArrayList<Integer>();
    MainGameFrame thisFrame = this;

     public MainGameFrame() {
        super("Main Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));

        playerBeingCounts.clear();
        playerBeingCounts.add(0);

        JButton levels = new JButton("Levels");
        JButton clearBoard = new JButton("Clear Board");
        JSpinner boardSize = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner playersAmountPicker = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton export = new JButton("Export");
        JButton exportJSON = new JButton("Export JSON");
        JButton simulate = new JButton("Simulate");

        JPanel board = new JPanel(new GridLayout(1,1));
        board.add(new BoardField(0, 0, this));
        board.setMinimumSize(new Dimension(1000, 1000));

        boardSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.removeAll();
                board.setLayout(new GridLayout((Integer) boardSize.getValue(), (Integer) boardSize.getValue()));
                for (int i = 0; i < (Integer) boardSize.getValue(); i++){
                    for (int j = 0; j < (Integer) boardSize.getValue(); j++){
                        board.add(new BoardField(i, j, thisFrame));
                    }
                }
                board.repaint();
                setVisible(true);
            }
        });

        playersAmountPicker.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                playersCount = (Integer) playersAmountPicker.getValue();
                while(playerBeingCounts.size() < playersCount){
                    playerBeingCounts.add(0);
                }
                while (playerBeingCounts.size() > playersCount) {
                    if (playerBeingCounts.getLast() == 0){
                        playerBeingCounts.removeLast();
                    }
                    else {
                        playersCount = playerBeingCounts.size();
                        playersAmountPicker.setValue((Object) playerBeingCounts.size());
                    }
                }
            }
        });

        JFrame host = this;
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    File outf = new File("boardState.txt");
                    outf.createNewFile();
                    FileWriter writer = new FileWriter("boardState.txt");
                    writer.write(boardSize.getValue().toString() + " ");
                    writer.write(playersAmountPicker.getValue().toString() + "\n");
                    for (Component c : board.getComponents()) {
                        BoardField bf = (BoardField) c;
                        if (bf.fieldContent != null && !bf.fieldContent.isEmpty()) writer.write("" + bf.being.priority + " " + bf.x + " " + " " + bf.y + bf.fieldContent + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(host, ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        JPanel actions = new JPanel(new GridLayout(0, 1));
        actions.add(levels);
        actions.add(clearBoard);
        actions.add(new LabeledComponent(playersAmountPicker, "Players: ", LabelPosition.LEFT));
        actions.add(new LabeledComponent(boardSize, "Board Size: ", LabelPosition.LEFT));
        actions.add(export);
        actions.add(exportJSON);
        actions.add(simulate);

        add(BorderLayout.WEST, actions);
        add(BorderLayout.CENTER, board);

        pack();
        setVisible(true);
    }
}