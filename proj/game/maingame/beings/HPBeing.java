package game.maingame.beings;

import game.maingame.beings.armours.Armour;
import game.maingame.beings.armours.ConstArmour;

public class HPBeing extends Being {
    public int hp;
    public Armour armour;

    public HPBeing(){
        super();
        hp = 100;
        armour = new ConstArmour();
    }

    public HPBeing(Being being){
        super(being);
    }

    public HPBeing(HPBeing another){
        super(another);
        this.hp = another.hp;
    }
}
