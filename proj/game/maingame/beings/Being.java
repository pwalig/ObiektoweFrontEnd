package game.maingame.beings;

import org.json.JSONObject;

public class Being implements JSONAble {
    public int owner;
    public int priority;

    public Being(){
        owner = 1;
        priority = 10;
    }

    public Being(JSONObject j){
        owner = j.getInt("owner");
        priority = j.getInt("priority");
    }

    public JSONObject toJSONObject(){
        JSONObject j = new JSONObject();
        j.put("type", getTypeChar());
        j.put("owner", this.owner);
        j.put("priority", this.priority);
        return j;
    }

    public char getTypeChar() {
        return 'b';
    }
}
