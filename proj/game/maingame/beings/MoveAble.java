package game.maingame.beings;

import org.json.JSONObject;

public class MoveAble extends HPBeing {
    public int moveX;
    public int moveY;

    public MoveAble(){
        super();
        moveX = 0;
        moveY = 0;
    }

    public MoveAble(JSONObject j){
        super(j);
        moveX = j.getInt("moveX");
        moveY = j.getInt("moveY");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = super.toJSONObject();
        j.put("type", getTypeChar());
        j.put("moveX", moveX);
        j.put("moveY", moveY);
        return j;
    }

    @Override
    public char getTypeChar(){return 'm';}
}
