package game.maingame.frames;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.json.JSONObject;

import game.application.LabeledComponent;
import game.application.LabeledComponent.LabelPosition;
import game.maingame.GameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainGameFrame extends JFrame {
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
        JButton importJSON = new JButton("Import JSON");
        JButton previous = new JButton("previous");
        JButton next = new JButton("next");
        JLabel frameNo = new JLabel("0", SwingConstants.CENTER);
        JButton simulate = new JButton("Simulate");

        Board board = new Board(this);

        boardSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.resize((Integer) boardSize.getValue());
            }
        });

        playersAmountPicker.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.playersCount = (Integer) playersAmountPicker.getValue();
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
                    for (BoardField bf : board.getBoardFields()) {
                        if (bf.fieldContent != null && !bf.fieldContent.isEmpty()) writer.write("" + bf.being.priority + " " + bf.x + " " + " " + bf.y + bf.fieldContent + "\n");
                    }
                    writer.close();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(host, ex.toString(), "File Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        exportJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameUtils.ExportJSONObject(board.toJSONObject(), "test-board.json");
            }
        });

        importJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject j = GameUtils.ImportJSONObject("test-board.json");
                playersAmountPicker.setValue(j.getInt("players"));
                boardSize.setValue(j.getInt("size"));
                board.importJSON(j);
            }
        });

        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newFrameNo = Integer.valueOf(frameNo.getText()).intValue() - 1;
                if (newFrameNo < 0) newFrameNo = 0;
                frameNo.setText(String.valueOf(newFrameNo));
                JSONObject j = GameUtils.ImportJSONObject("sim/" + frameNo.getText() + ".json");
                board.importJSON(j);
            }
        });
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int newFrameNo = Integer.valueOf(frameNo.getText()).intValue() + 1;
                frameNo.setText(String.valueOf(newFrameNo));
                JSONObject j = GameUtils.ImportJSONObject("sim/" + frameNo.getText() + ".json");
                board.importJSON(j);
            }
        });
        simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> args = new ArrayList<String>();
                args.add ("./backend.exe"); // command name
                //args.add (); // optional args added as separate list items
                ProcessBuilder pb = new ProcessBuilder (args);
                Process p;
                try {
                    p = pb.start();
                    p.waitFor();
                } catch (Exception e1) {
                    e1.printStackTrace();
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
        actions.add(importJSON);

        JPanel simPlayer = new JPanel(new GridLayout(1, 3));
        simPlayer.add(previous);
        simPlayer.add(frameNo);
        simPlayer.add(next);
        actions.add(simPlayer);
        actions.add(importJSON);
        actions.add(simulate);

        add(BorderLayout.WEST, actions);
        add(BorderLayout.CENTER, board);

        pack();
        setVisible(true);
    }
}