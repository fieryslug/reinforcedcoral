package com.fieryslug.reinforcedcoral.util;

import org.json.JSONObject;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TextureHolder {

    private Map<String, Color> colorMap;
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
            "problem_text"};

    public TextureHolder() {
        this.colorMap = new HashMap<>();
    }

    public void read(String textureName) {
        this.colorMap.clear();

        try {
            String path = "/res/texturepack/" + textureName + "/";
            String res = FuncBox.readFile(path + "colorscheme.json");
            JSONObject scheme = new JSONObject(res);
            for (String key : KEYS) {
                JSONObject rgb = scheme.getJSONObject(key);
                int r = rgb.getInt("r");
                int g = rgb.getInt("g");
                int b = rgb.getInt("b");
                this.colorMap.put(key, new Color(r, g, b));
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
}
