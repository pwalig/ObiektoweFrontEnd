package game.maingame.beings.armours;

import org.json.JSONObject;

public class ProportionalArmour extends Armour {
    public double multiplier;

    public ProportionalArmour(){
        multiplier = 0.1;
    }

    public ProportionalArmour(JSONObject j){
        multiplier = j.getDouble("multiplier");
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = new JSONObject();
        j.put("multiplier", multiplier);
        j.put("type", 'p');
        return j;
    }
}
