package game.application;

import javax.swing.*;
import java.awt.*;

/**
 * <code>LabeledComponent</code> is a <code>JPanel</code> subclass that contains any commponent that
 * you pass in the consturctor and a <code>JLabel</code> component all aranged in a <code>GridLayout</code>.
 * @see JPanel
 *
 * @author Paweł Waligóra
 */
public class LabeledComponent extends JPanel {
    public static enum LabelPosition{ LEFT, RIGHT, TOP, BOTTOM };

    /**
     * Creates a new JPanel with GridLayout layout manager Containing component and JLabel with label as label.
     *
     * @param component the JComponent to use
     * @param label  a text that will be displayed next to the component
     */
    public LabeledComponent(JComponent component, String label){
        super(new GridLayout(0,2));
        add(new JLabel(label));
        add(component);
    }

    /**
     * Creates a new JPanel with GridLayout layout manager Containing component and JLabel with label as label, positioned according to position argument.
     *
     * @param component the JComponent to use
     * @param label  a text that will be displayed next to the component
     * @param position  positioning of the label
     */
    public LabeledComponent(JComponent component, String label, LabelPosition position){
        super();

        if (position == LabelPosition.LEFT || position == LabelPosition.RIGHT) setLayout(new GridLayout(0,2));
        else setLayout(new GridLayout(2,1));

        if (position == LabelPosition.LEFT || position == LabelPosition.TOP){
            add(new JLabel(label));
            add(component);
        }
        else{
            add(component);
            add(new JLabel(label));
        }
    }
}