package game.maingame.beings;

import org.json.JSONObject;

public class Knight extends HPBeing {
    public int baseDamage;

    public Knight(){
        super();
        baseDamage = 50;
    }

    
    public Knight(JSONObject j){
        super(j);
        baseDamage = j.getInt("baseDamage");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = super.toJSONObject();
        j.remove("type");
        j.put("type", getTypeChar());
        j.put("baseDamage", this.baseDamage);
        return j;
    }

    @Override
    public char getTypeChar() {
        return 'k';
    }
}
