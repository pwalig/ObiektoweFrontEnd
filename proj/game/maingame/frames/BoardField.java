package game.maingame.frames;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.json.JSONObject;

import game.maingame.beings.Being;
import game.maingame.beings.JSONAble;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardField extends JButton implements JSONAble {
    public int x;
    public int y;
    public Being being;

    private BoardField bf;
    private JFrame owner;

    final private class EmptyEditDialog extends JDialog {
        EmptyEditDialog(){
            super(owner, "empty field");
            JButton addButton = new JButton("add");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    being = new Being();
                    new EditFrame(owner, bf);
                    dispose();
                }
            });
            add(addButton);
            pack();
            setVisible(true);
        }
    }
    
    public static enum ButtonState { UNLOCKED, LOCKED };
    ButtonState buttonState = ButtonState.UNLOCKED;
    String fieldContent;

    public BoardField(int _x, int _y, JFrame owner){
        super();
        x = _x;
        y = _y;
        this.owner = owner;

        being = null;
        bf = this;

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (being == null) new EmptyEditDialog();
                else new EditFrame(owner, bf);
            }
        });
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject j = being.toJSONObject();
        j.put("x", x);
        j.put("y", y);
        return j;
    }

}
