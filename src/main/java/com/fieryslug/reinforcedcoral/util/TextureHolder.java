package com.fieryslug.reinforcedcoral.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextureHolder {

    private Map<String, Color> colorMap;
    private Map<String, Image> imageMap;
    private static TextureHolder instance;

    private ArrayList<String> keys;
    private ArrayList<String> imageKeys;
    private Map<String, ArrayList<String>> wildcardMap;


    public TextureHolder() {
        this.colorMap = new HashMap<>();
        this.imageMap = new HashMap<>();
        this.keys = new ArrayList<>();
        this.wildcardMap = new HashMap<>();
        this.imageKeys = new ArrayList<>();

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

        JSONArray arrayImageKeys = object.getJSONArray("image_keys");
        for (int i = 0; i < arrayImageKeys.length(); ++i) {
            this.imageKeys.add(arrayImageKeys.getString(i));
        }

        //System.out.println(this.wildcardMap);

        instance = this;
    }

    public void read(String textureName) {
        this.colorMap.clear();
        this.imageMap.clear();

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
                            //System.out.println(otherKey + " " + color);
                        }
                    }
                } catch (Exception e) {
                   // e.printStackTrace();
                }
            }

            for (String imageKey : this.imageKeys) {

                System.out.println("[texture holder]:/res/texturepack/" + textureName + "/" + imageKey + ".png");
                Image image = MediaRef.getImage("/res/texturepack/" + textureName + "/" + imageKey + ".png");
                this.imageMap.put(imageKey, image);

            }
            System.out.println("[texture holder]:loaded texturepack " + textureName);
        } catch (Exception e) {
            System.out.println("[texture holder]:error occurred while loading texturepack " + textureName + ":");
            e.printStackTrace();
        }

    }

    public Color getColor(String key) {

        Color color = this.colorMap.get(key);
        if (color != null) return color;
        return Reference.WHITE;

    }

    public Image getImage(String key) {
        return this.imageMap.get(key);
    }

    public static TextureHolder getInstance() {

        return instance;

    }
}
