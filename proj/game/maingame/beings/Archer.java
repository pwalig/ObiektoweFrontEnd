package game.maingame.beings;

import org.json.JSONObject;

public class Archer extends MoveAble {
    public int damage;
    public int range;

    public Archer(){
        super();
        damage = 10;
        range = 3;
    }

    public Archer(JSONObject j){
        super(j);
        damage = j.getInt("damage");
        range = j.getInt("range");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = super.toJSONObject();
        j.put("type", getTypeChar());
        j.put("damage", damage);
        j.put("range", range);
        return j;
    }

    @Override
    public char getTypeChar(){return 'a';}
}
