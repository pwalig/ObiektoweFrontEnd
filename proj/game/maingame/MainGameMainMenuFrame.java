package game.maingame;
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

public class MainGameMainMenuFrame extends JFrame {
    int playersCount = 1;
    ArrayList<Integer> playerBeingCounts = new ArrayList<Integer>();
    MainGameMainMenuFrame thisFrame = this;

    private class BoardField extends JButton {
        int x;
        int y;
        int playerOwner = 0;
        //public static enum UnitType {BEING, TEST_BEING, HP_BEING, ARMOURED, STRUCTURE};
        public static enum ButtonState { UNLOCKED, LOCKED };
        ButtonState buttonState = ButtonState.UNLOCKED;
        String fieldContent;

        private class FieldSetterDialog extends JDialog{

            /*private JComboBox<String> GetTypeSelectionJComboBox () {
                String[] typesList = { "Being", "TestBeing", "HPBeing", "Armoured", "Structure" };
                JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);
                unitTypeDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (unitTypeDropdown.getSelectedItem().toString()) {
                            case "Being":
                                break;
                        
                            default:
                                break;
                        }
                    }
                });
                return unitTypeDropdown;
            }*/

            FieldSetterDialog(){
                super(thisFrame, "Set Field: (" + x + ", " + y + ")");
                
                
                ArrayList<String> playerOwnersArray = new ArrayList<String>();
                playerOwnersArray.add("None");
                while (playerOwnersArray.size() <= playersCount) playerOwnersArray.add("Player " + playerOwnersArray.size());
                String[] playerOwnersStrings = playerOwnersArray.toArray(new String[playerOwnersArray.size()]);

                JComboBox<String> playerOwnerDropdown = new JComboBox<String>(playerOwnersStrings);
                playerOwnerDropdown.setSelectedIndex(playerOwner);
                playerOwnerDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (playerOwner > 0) playerBeingCounts.set(playerOwner - 1, playerBeingCounts.get(playerOwner - 1) - 1); // subtract 1 from old owner being counts
                        playerOwner = playerOwnerDropdown.getSelectedIndex(); // set owner
                        if (playerOwner > 0) playerBeingCounts.set(playerOwner - 1, playerBeingCounts.get(playerOwner - 1) + 1); // add 1 to new owner being counts
                    }
                });


                JTextField command = new JTextField(fieldContent);
                if (fieldContent == null || fieldContent.isEmpty()) command.setText("b <priority>");
                else command.setText(fieldContent);
                command.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fieldContent = command.getText();
                    }
                });
                
                
                String[] typesList = { "Being", "TestBeing", "HPBeing", "Structure", "Mage", "Fighter" };
                JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);
                unitTypeDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        switch (unitTypeDropdown.getSelectedItem().toString()) {
                            case "Being":
                                command.setText("b <priority>");
                                break;
                            
                            case "TestBeing":
                                command.setText("t <priority> <value>");
                                break;
                        
                            case "HPBeing":
                                command.setText("h <priority> <hp> <armour>");
                                break;
                                
                            case "Structure":
                                command.setText("s <priority> <hp> <armour>");
                                break;
                                
                            case "Mage":
                                command.setText("m <priority> <hp> <armour> <mana>");
                                break;
                                
                            case "Fighter":
                                command.setText("f <priority> <hp> <armour> <weapon>");
                                break;

                            default:
                                break;
                        }
                    }
                });
                

                JButton ok = new JButton("ok");
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        playerOwner = playerOwnerDropdown.getSelectedIndex();
                        fieldContent = command.getText();
                        if (playerOwner > 0) setText("" + playerOwner);
                        else {
                            setText("");
                            fieldContent = "";
                        }
                        dispose();
                    }
                });


                JPanel mainf = new JPanel(new GridLayout(0,1));
                mainf.add(new LabeledComponent(playerOwnerDropdown, "player owner"));
                mainf.add(new LabeledComponent(unitTypeDropdown, "unit type"));
                mainf.add(new LabeledComponent(command, "field contents"));
                add(BorderLayout.CENTER, mainf);
                add(BorderLayout.SOUTH, ok);

                setMinimumSize(new Dimension(600, 50));
                pack();
                setVisible(true);
            }
        }

        public BoardField(int _x, int _y){
            super();
            x = _x;
            y = _y;
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
        board.add(new BoardField(0, 0));
        board.setMinimumSize(new Dimension(1000, 1000));

        boardSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                board.removeAll();
                board.setLayout(new GridLayout((Integer) boardSize.getValue(), (Integer) boardSize.getValue()));
                for (int i = 0; i < (Integer) boardSize.getValue(); i++){
                    for (int j = 0; j < (Integer) boardSize.getValue(); j++){
                        board.add(new BoardField(i, j));
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
                        if (bf.fieldContent != null && !bf.fieldContent.isEmpty()) writer.write("" + bf.playerOwner + " " + bf.x + " " + " " + bf.y + bf.fieldContent + "\n");
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