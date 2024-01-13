package game.maingame;

import java.util.ArrayList;

import game.maingame.beings.Being;
import game.maingame.beings.HPBeing;

public class GameUtils {

    private GameUtils(){}

    public static Class<?>[] beingClasses = {
        Being.class,
        HPBeing.class
    };

    public static String[] getBeingClassesNames (){
        ArrayList<String> tempFields = new ArrayList<>();
		for(Class<?> cls : beingClasses) {
			tempFields.add(cls.getName());
		}
		return tempFields.toArray(new String[tempFields.size()]);
    }
}
