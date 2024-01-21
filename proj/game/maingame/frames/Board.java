package game.maingame.frames;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONObject;

import game.maingame.beings.Being;
import game.maingame.beings.HPBeing;

public class Board extends JPanel {
    private JFrame owner;
    private int size;
    public int playersCount;

    public Board(JFrame owner){
        super(new GridLayout(1,1));
        this.owner = owner;
        this.size = 1;
        add(new BoardField(0, 0, owner));
        setMinimumSize(new Dimension(1000, 1000));
    }
    
    public Board(JFrame owner, JSONObject j){
        this(owner);
        this.importJSON(j);
    }

    public void importJSON(JSONObject j){
        this.resize(j.getInt("size"));
        this.playersCount = j.getInt("players");

        // fill the board
        JSONArray ja = j.getJSONArray("beings");
        for (int i = 0; i < ja.length(); i++){
            JSONObject jo = ja.getJSONObject(i);

            Component c = getComponent(((jo.getInt("x")%size) * size) + jo.getInt("y"));
            BoardField bf;
            if (c instanceof BoardField){
                System.out.println("found");
                bf = (BoardField) c;
            }
            else continue;

            switch ((char)jo.getInt("type")) {
                case 'b':
                    bf.being = new Being(jo);
                    break;
                case 'h':
                    bf.being = new HPBeing(jo);
                    break;
            
                default:
                    break;
            }
        }
        
        //repaint(); // unnecessary for some reason
        owner.setVisible(true); // necessary for some reason
    }

    public BoardField[] getBoardFields(){
        Component[] components = getComponents();
        BoardField[] boardFields = new BoardField[components.length];
        for (int i = 0; i < components.length; i++) {
            boardFields[i] = (BoardField) components[i];
        }
        return boardFields;
    }

    public void resize(int size){
        removeAll();
        setLayout(new GridLayout(size, size));
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                add(new BoardField(i, j, owner));
            }
        }
        this.size = size;
        
        //repaint(); // unnecessary for some reason
        owner.setVisible(true); // necessary for some reason
    }
    
    public JSONObject toJSONObject(){
        JSONObject j = new JSONObject();
        JSONArray ja = new JSONArray();
        for (BoardField bf : getBoardFields()){
            if (bf.being != null) {
                ja.put(bf.toJSONObject());
            }
        }
        j.put("beings", ja);
        j.put("size", size);
        j.put("players", playersCount);
        return j;
    }
    
}
