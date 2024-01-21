package game.maingame.beings.armours;

import org.json.JSONObject;

import game.maingame.beings.JSONAble;

public class Armour implements JSONAble {

    @Override
    public JSONObject toJSONObject() {
        JSONObject j = new JSONObject();
        j.put("type", 'a');
        return j;
    }

}
