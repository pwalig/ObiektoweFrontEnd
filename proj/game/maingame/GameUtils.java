package game.maingame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
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

    public static Being getNewBeing(Class<?> type){
        try {
            Object o = type.getConstructor().newInstance(new Object[] {});
            return (Being)o;
        } catch (Exception e) {
            e.printStackTrace();
            return new Being();
        }
    }

    public static Being getNewBeing(JSONObject j){
        switch ((char)j.getInt("type")) {
            case 'b':
                return new Being(j);
            case 'h':
                return new HPBeing(j);
        
            default:
                return null;
        }
    }

    public static void ExportJSONObject(JSONObject jo, String filename){
        try {
            FileWriter file = new FileWriter(filename);
            file.write(jo.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void ExportJSONArray(JSONArray ja, String filename){
        try {
            FileWriter file = new FileWriter(filename);
            file.write(ja.toString(2));
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static JSONObject ImportJSONObject(String filename){
        String data = new String();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
        JSONObject j = new JSONObject(data);
        return j;
    }
}
