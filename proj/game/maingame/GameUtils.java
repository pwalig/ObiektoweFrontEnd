package game.maingame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

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

    public static Being getNewBeingOfType(Class<?> type){
        try {
            Object o = type.getConstructor().newInstance(new Object[] {});
            return (Being)o;
        } catch (Exception e) {
            e.printStackTrace();
            return new Being();
        }
    }

    public static void ExportJSONObject(JSONObject jo, String filename){
        JSONObject json;
        File f = new File(filename);
        if (f.exists()){
            InputStream is;
            try {
                is = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }
            String jsonTxt;
            try (Scanner scanner = new Scanner(is, StandardCharsets.UTF_8.name())) {
                jsonTxt = scanner.useDelimiter("\\A").next();
            }
            json = new JSONObject(jsonTxt);
        }else {
            json = new JSONObject();
        }
        json.put(String.valueOf(Math.random()), jo);
        try (FileWriter file = new FileWriter(filename)) {
            file.write(json.toString());
            file.flush();

        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
