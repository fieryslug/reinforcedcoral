package com.fieryslug.reinforcedcoral.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextureHolder {

    private Map<String, Color> colorMap;
    private static TextureHolder instance;
    public static final String[] KEYS = {
            "background",
            "team",
            "team_privilege",
            "team_locked",
            "team_border",
            "team_text",
            "title",
            "title_border",
            "title_text",
            "problem",
            "problem_disabled",
            "problem_border",
            "problem_text",
            "team1",
            "team2",
            "team3",
            "team4",
            "team1_border",
            "team2_border",
            "team3_border",
            "team4_border",
            "team1_text",
            "team2_text",
            "team3_text",
            "team4_text"
    };

    private ArrayList<String> keys;
    private Map<String, ArrayList<String>> wildcardMap;


    public TextureHolder() {
        this.colorMap = new HashMap<>();
        this.keys = new ArrayList<>();
        this.wildcardMap = new HashMap<>();

        JSONObject object = new JSONObject(FuncBox.readFile("/res/texturepack/keys.json"));
        JSONArray array = object.getJSONArray("keys");

        for (int i = 0; i < array.length(); ++i) {
            this.keys.add(array.getString(i));
        }

        JSONObject wildcardObj = object.getJSONObject("wildcard");

        for (String key : wildcardObj.keySet()) {

            JSONArray arrayOtherKeys = wildcardObj.getJSONArray(key);
            ArrayList<String> otherKeys = new ArrayList<>();
            for (int i = 0; i < arrayOtherKeys.length(); ++i) {
                otherKeys.add(arrayOtherKeys.getString(i));
            }
            this.wildcardMap.put(key, otherKeys);
        }



        instance = this;
    }

    public void read(String textureName) {
        this.colorMap.clear();

        try {
            String path = "/res/texturepack/" + textureName + "/";
            String res = FuncBox.readFile(path + "colorscheme.json");
            JSONObject scheme = new JSONObject(res);
            for (String key : this.keys) {
                try {
                    JSONObject rgb = scheme.getJSONObject(key);
                    int r = rgb.getInt("r");
                    int g = rgb.getInt("g");
                    int b = rgb.getInt("b");
                    Color color = new Color(r, g, b);
                    this.colorMap.put(key, color);
                    ArrayList<String> otherKeys = this.wildcardMap.get(key);
                    if (otherKeys != null) {
                        for (String otherKey : otherKeys) {
                            this.colorMap.put(otherKey, color);
                        }
                    }
                } catch (Exception e) {

                }

            }
            System.out.println("loaded texturepack " + textureName);
        } catch (Exception e) {
            System.out.println("error occurred while loading texturepack " + textureName + ":");
            e.printStackTrace();
        }

    }

    public Color getColor(String key) {

        Color color = this.colorMap.get(key);
        if (color != null) return color;
        return Reference.WHITE;

    }

    public static TextureHolder getInstance() {

        return instance;

    }
}
