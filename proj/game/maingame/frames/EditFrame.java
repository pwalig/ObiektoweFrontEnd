package game.maingame.frames;

import javax.swing.*;

import game.application.LabeledComponent;
import game.maingame.GameUtils;
import game.maingame.beings.armours.Armour;

import java.awt.event.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class EditFrame extends JDialog {

	private void applyField(Field field, JTextField tf, Object o){
		Class<?> type = field.getType();
		if (type == int.class) {
			int val = 0;
			try {val = Integer.parseInt(tf.getText());}
			catch (NumberFormatException ex) {}
			try {field.setInt(o, val);}
			catch (Exception e1) {e1.printStackTrace();}
		}
		else if (type == double.class){
			double val = 0.0;
			try {Double.parseDouble(tf.getText());}
			catch (Exception e) {}
			try {field.setDouble(o, val);}
			catch (Exception e1) {e1.printStackTrace();}
		}
	}

	private void addArmourFields(Object o, JPanel panel){
		ArrayList<Field> tempFields = new ArrayList<>();
		for(Class<?> cls = o.getClass(); cls!=null && cls!=Object.class; cls = cls.getSuperclass()) {
			for(Field field : cls.getDeclaredFields()) {
				tempFields.add(field);
			}
		}
		Field[] fields = tempFields.toArray(new Field[tempFields.size()]);
		JTextField[] tfs = new JTextField[fields.length];

		for (int i = 0; i < fields.length; i++){
			if (Armour[].class.isAssignableFrom(fields[i].getType())){
				try {
					addArmourFields(fields[i].get(o), panel);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				continue;
			}
			try {
				fields[i].setAccessible(true);
				tfs[i] = new JTextField(fields[i].get(o).toString());

				// on value change
				Field f = fields[i];
				JTextField tf = tfs[i];
				tf.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e){
						applyField(f, tf, o);
					}
				});

				// add to panel
				panel.add(new LabeledComponent(tfs[i], fields[i].getName()));

			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public EditFrame(JFrame owner, BoardField host) {
		// setup
		super(owner, "Edit Being");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// fields
		JPanel fieldsPanel = new JPanel(new GridLayout(0, 1));
		ArrayList<Field> tempFields = new ArrayList<>();
		for(Class<?> cls = host.being.getClass(); cls!=null && cls!=Object.class; cls = cls.getSuperclass()) {
			for(Field field : cls.getDeclaredFields()) {
				tempFields.add(field);
			}
		}
		Field[] fields = tempFields.toArray(new Field[tempFields.size()]);
		JTextField[] tfs = new JTextField[fields.length];

		for (int i = 0; i < fields.length; i++){
			if (Armour.class.isAssignableFrom(fields[i].getType())){
				try {
					fieldsPanel.add(new JLabel(fields[i].getName()));
					addArmourFields(fields[i].get(host.being), fieldsPanel);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				continue;
			}

			try {
				fields[i].setAccessible(true);
				tfs[i] = new JTextField(fields[i].get(host.being).toString());

				// on value change
				Field f = fields[i];
				JTextField tf = tfs[i];
				tf.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e){
						applyField(f, tf, host.being);
					}
				});

				// add to panel
				fieldsPanel.add(new LabeledComponent(tfs[i], fields[i].getName()));

			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		// type
		String[] typesList = GameUtils.getBeingClassesNames();
		JComboBox<String> unitTypeDropdown = new JComboBox<String>(typesList);
		unitTypeDropdown.setSelectedItem(host.being.getClass().getName());

        unitTypeDropdown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
                try {
					host.being = GameUtils.getNewBeingOfType(Class.forName(unitTypeDropdown.getSelectedItem().toString()));
					for (int i = 0; i < fields.length; i++){
						applyField(fields[i], tfs[i], host.being);
					}
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
                new EditFrame(owner, host);
				dispose();
			}
		});

		// actions
		JPanel actions = new JPanel(new GridLayout(1, 0));
		JButton confirm = new JButton("ok");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				for (int i = 0; i < fields.length; i++){
					applyField(fields[i], tfs[i], host.being);
				}
				System.out.print(host.being.getClass().getName());
				dispose();
			}
		});

		actions.add(confirm);

		// compose
		add(BorderLayout.NORTH, unitTypeDropdown);
		add(BorderLayout.CENTER, fieldsPanel);
		add(BorderLayout.SOUTH, actions);
		pack();
		setVisible(true);
	}
}