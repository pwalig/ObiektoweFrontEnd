package game.maingame.beings;

import org.json.JSONObject;

import game.maingame.beings.armours.Armour;
import game.maingame.beings.armours.ConstArmour;
import game.maingame.beings.armours.MultiArmour;
import game.maingame.beings.armours.ProportionalArmour;

public class HPBeing extends Being {
    public int hp;
    public Armour armour;

    public HPBeing(){
        super();
        hp = 100;
        armour = new ConstArmour();
    }

    
    public HPBeing(JSONObject j){
        super(j);
        hp = j.getInt("hp");
        char type = (char)j.getJSONObject("armour").getInt("type");
        switch (type) {
            case 'p':
                armour = new ProportionalArmour(j.getJSONObject("armour"));
                break;

            case 'm':
                armour = new MultiArmour(j.getJSONObject("armour"));
                break;
        
            default:
                armour = new Armour();
                break;
        }
    }

    @Override
    public JSONObject toJSONObject(){
        JSONObject j = super.toJSONObject();
        j.remove("type");
        j.put("type", getTypeChar());
        j.put("hp", this.hp);
        j.put("armour", this.armour.toJSONObject());
        return j;
    }

    @Override
    public char getTypeChar() {
        return 'h';
    }
}
