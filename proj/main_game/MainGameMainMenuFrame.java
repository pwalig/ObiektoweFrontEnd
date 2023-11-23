package main_game;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import aplication.LabeledComponent;
import aplication.LabeledComponent.LabelPosition;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainGameMainMenuFrame extends JFrame {
    private class BoardField extends JButton {
        int playerOwner = -1;
        public static enum UnitType {BEING, TEST_BEING, HP_BEING, ARMOURED, STRUCTURE};
        UnitType unitType;
        String fieldContent;

        private class FieldSetterDialog extends JFrame{
            FieldSetterDialog(){
                super("Field Setup");

                JTextField command = new JTextField(fieldContent);
                command.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fieldContent = command.getText();
                    }
                });
                String[] typesList = { "Being", "TestBeing", "HPBeing", "Armoured"};
                JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);
                unitTypeDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (unitTypeDropdown.getSelectedItem().toString()) {
                            case "Being":
                                unitType = UnitType.BEING;
                                break;
                        
                            default:
                                break;
                        }
                    }
                });
                JSpinner playerId = new JSpinner(new SpinnerNumberModel(playerOwner, -1, 100, 1));
                JPanel mainf = new JPanel(new GridLayout(0,1));
                mainf.add(new LabeledComponent(playerId, "player owner"));
                mainf.add(new LabeledComponent(unitTypeDropdown, "unit type"));
                mainf.add(new LabeledComponent(command, "field contents"));
                add(BorderLayout.CENTER, mainf);

                JButton ok = new JButton("ok");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            playerId.commitEdit();
                        } catch ( java.text.ParseException ex ) { 
                            JOptionPane.showMessageDialog(ok, e.toString(), "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        playerOwner = (Integer) playerId.getValue();
                        fieldContent = command.getText();
                        setText("" + playerOwner);
                        dispose();
                    }
                });
                add(BorderLayout.SOUTH, ok);

                pack();
                setVisible(true);
            }
        }

        public BoardField(){
            super();
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new FieldSetterDialog();
                }
            });
        }
    }

     public MainGameMainMenuFrame() {
        super("Main Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));

        JButton levels = new JButton("Levels");
        JButton freePlay = new JButton("FreePlay");
        JButton simulate = new JButton("Simulate");
        JSpinner boardSize = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JSpinner playersAmount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        JPanel board = new JPanel(new GridLayout(1,1));
        board.add(new BoardField());

        boardSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.removeAll();
                board.setLayout(new GridLayout((Integer) boardSize.getValue(), (Integer) boardSize.getValue()));
                while (board.getComponentCount() < (Integer) boardSize.getValue() * (Integer) boardSize.getValue()) {
                    board.add(new BoardField());
                }
                board.repaint();
                setVisible(true);
            }
        });

        JFrame host = this;
        simulate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    File outf = new File("filename.txt");
                    outf.createNewFile();
                    FileWriter writer = new FileWriter("filename.txt");
                    writer.write(boardSize.getValue().toString() + " ");
                    writer.write(playersAmount.getValue().toString() + "\n");
                    for (Component c : board.getComponents()) {
                        BoardField bf = (BoardField) c;
                        if (bf.fieldContent != null && !bf.fieldContent.isEmpty()) writer.write(bf.fieldContent + "\n");
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
        actions.add(freePlay);
        actions.add(new LabeledComponent(playersAmount, "Players: ", LabelPosition.LEFT));
        actions.add(new LabeledComponent(boardSize, "Board Size: ", LabelPosition.LEFT));
        actions.add(simulate);

        add(BorderLayout.WEST, actions);
        add(BorderLayout.CENTER, board);

        pack();
        setVisible(true);
    }
}