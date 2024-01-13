package game.maingame.frames;

import javax.swing.JButton;
import javax.swing.JFrame;

import game.maingame.beings.Being;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardField extends JButton {
    int x;
    int y;
    Being being;
    
    public static enum ButtonState { UNLOCKED, LOCKED };
    ButtonState buttonState = ButtonState.UNLOCKED;
    String fieldContent;

    public void ChangeBeingType(Class<?> clazz){
        Object o;
        try {
            o = clazz.getConstructor().newInstance(new Object[] {});
            being = (Being)o;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BoardField(int _x, int _y, JFrame owner){
        super();
        x = _x;
        y = _y;
        being = null;
        BoardField bf = this;
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (being == null) new ChoseFrame(owner, bf);
                else new EditFrame(owner, bf);
            }
        });
    }
}
