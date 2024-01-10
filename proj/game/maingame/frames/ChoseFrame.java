package game.maingame.frames;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.*;
import java.awt.*;

public class ChoseFrame extends JDialog {
    public ChoseFrame(JFrame owner, BoardField host){
        super(owner);
        
		// type select
		String[] typesList = { "Being", "TestBeing", "HPBeing", "Armoured", "Structure" };
		JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
                host.ChangeBeingType(unitTypeDropdown.getSelectedItem().toString());
                new EditFrame(owner, host.being);
				dispose();
			}
		});

        add(BorderLayout.CENTER, unitTypeDropdown);
		add(BorderLayout.SOUTH, confirm);
		pack();
		setVisible(true);
    }
}
