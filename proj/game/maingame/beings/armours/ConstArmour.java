package game.maingame.beings.armours;

import org.json.JSONObject;

public class ConstArmour extends Armour {
    public int reduction;

    public ConstArmour(){
        reduction = 10;
    }
    
    public ConstArmour(JSONObject j) {
        reduction = j.getInt("reduction");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = new JSONObject();
        j.put("reduction", reduction);
        j.put("type", 'c');
        return j;
    }
}
