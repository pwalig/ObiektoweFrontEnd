package game.maingame.beings;

import org.json.JSONObject;

public class Fighter extends MoveAble {
    public int damage;

    public Fighter(){
        super();
        damage = 10;
    }

    public Fighter(JSONObject j){
        super(j);
        damage = j.getInt("damage");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = super.toJSONObject();
        j.put("type", getTypeChar());
        j.put("damage", damage);
        return j;
    }

    @Override
    public char getTypeChar(){return 'f';}
}
