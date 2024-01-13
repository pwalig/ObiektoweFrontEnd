package game.maingame.frames;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import game.maingame.GameUtils;

import java.awt.event.*;
import java.awt.*;

public class ChoseFrame extends JDialog {
    public ChoseFrame(JFrame owner, BoardField host){
        super(owner);
        
		// type select
		String[] typesList = GameUtils.getBeingClassesNames();
		JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
                try {
					host.ChangeBeingType(Class.forName(unitTypeDropdown.getSelectedItem().toString()));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
                new EditFrame(owner, host);
				dispose();
			}
		});

        add(BorderLayout.CENTER, unitTypeDropdown);
		add(BorderLayout.SOUTH, confirm);
		pack();
		setVisible(true);
    }
}
