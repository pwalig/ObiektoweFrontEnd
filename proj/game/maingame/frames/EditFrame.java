package game.maingame.frames;

import javax.swing.*;

import game.application.LabeledComponent;

import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class EditFrame extends JDialog {

	public EditFrame(JFrame owner, BoardField host) {
		// setup
		super(owner, "Edit Being");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Object o = host.being;

		// fields
		ArrayList<Field> tempFields = new ArrayList<>();
		for(Class<?> cls = o.getClass(); cls!=null && cls!=Object.class; cls = cls.getSuperclass()) {
			for(Field field : cls.getDeclaredFields()) {
				tempFields.add(field);
			}
		}
		Field[] fields = tempFields.toArray(new Field[tempFields.size()]);
		JTextField[] tfs = new JTextField[fields.length];
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));

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
		JButton changeType = new JButton("change type");
		changeType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				new ChoseFrame(owner, host);
				dispose();
			}
		});
		actions.add(confirm);
		actions.add(cancel);
		actions.add(changeType);

		// compose
		add(BorderLayout.CENTER, fieldsPanel);
		add(BorderLayout.SOUTH, actions);
		pack();
		setVisible(true);
	}
}