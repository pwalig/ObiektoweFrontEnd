package game.maingame.frames;

import javax.swing.*;

import game.application.LabeledComponent;

import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Field;

public class EditFrame extends JDialog {

	public EditFrame(JFrame owner, Object o) {
		// setup
		super(owner, "Edit Being");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// fields
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
		Field[] fields = o.getClass().getDeclaredFields();
		JTextField[] tfs = new JTextField[fields.length];

		/* 
		for(Class cls = o.getClass(); cls!=null; cls = cls.getSuperclass()) {
			for(Field field : cls.getDeclaredFields()) {
				
			}
		}*/

		for (int i = 0; i < fields.length; i++){
			try {
				Field f = fields[i];
				f.setAccessible(true);
				tfs[i] = new JTextField(fields[i].get(o).toString());
				fieldsPanel.add(new LabeledComponent(tfs[i], fields[i].getName()));
			} catch (Exception e){
				e.printStackTrace();
			}
		}

		// actions
		JPanel actions = new JPanel(new GridLayout(1, 0));
		JButton confirm = new JButton("confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < fields.length; i++){
					int val;
					try {
						val = Integer.parseInt(tfs[i].getText());
					}
					catch (NumberFormatException ex) {
						val = 0;
					}
					try {
						fields[i].setInt(o, val);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
				dispose();
			}
		});
		JButton cancel = new JButton("cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		actions.add(confirm);
		actions.add(cancel);

		// compose
		add(BorderLayout.CENTER, fieldsPanel);
		add(BorderLayout.SOUTH, actions);
		pack();
		setVisible(true);
	}
}