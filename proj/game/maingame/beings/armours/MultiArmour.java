package game.maingame.beings.armours;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class MultiArmour extends Armour {
    public Armour[] subArmours;

    public MultiArmour() {
        subArmours = new Armour[0];
    }

    public MultiArmour(JSONObject jsonObject) {
        ArrayList<Armour> tempArmours = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray("subArmours");
        for (int i = 0; i < ja.length(); i++){
            JSONObject j = ja.getJSONObject(i);
            switch ((char)j.getInt("type")) {
                case 'm':
                    tempArmours.add(new MultiArmour(j));
                    break;
                case 'p':
                    tempArmours.add(new ProportionalArmour(j));
                    break;
                case 'c':
                    tempArmours.add(new ConstArmour(j));
                    break;
            
                default:
                    break;
            }
        }
        subArmours = tempArmours.toArray(new Armour[tempArmours.size()]);
    }

    @Override
    public JSONObject toJSONObject() {
        JSONObject j = new JSONObject();
        JSONArray ja = new JSONArray();
        for (Armour a : subArmours){
            ja.put(a.toJSONObject());
        }
        j.put("subArmours", ja);
        j.put("type", 'm');
        return j;
    }
}
