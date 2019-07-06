package com.fieryslug.reinforcedcoral.core.page;

import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Page {

    public static final String PATH = "/res/problems/";
    public String htmlText;

    public int type = 0;
    private boolean isFinal;
    public ArrayList<String> res;

    public ArrayList<Widget> widgets;

    public Page(String path) {

        //this.htmlText = FuncBox.readFile(PATH + path);
        this.res = new ArrayList<>();

        String s = FuncBox.readFile(PATH + path);
        JSONObject object = new JSONObject(s);
        this.type = object.getInt("type");
        JSONArray array = object.getJSONArray("res");

        for(int i=0; i<array.length(); ++i) {

            this.res.add(array.getString(i));

        }
    }

    public Page(JSONObject object) {

        this.res = new ArrayList<>();
        this.type = object.getInt("type");
        this.widgets = null;
        this.isFinal = this.type==-1;

        if(this.type == Reference.MAGIC_PRIME) {
            boolean b = object.getBoolean("final");
            this.isFinal = b;
            JSONArray elements = object.getJSONArray("elements");
            this.widgets = new ArrayList<>();
            for (int i = 0; i < elements.length(); ++i) {
                JSONObject objectWidget = elements.getJSONObject(i);
                String name = objectWidget.getString("widget");
                String content = objectWidget.getString("content");
                String constraints = objectWidget.getString("constraints");
                JSONObject properties = objectWidget.getJSONObject("properties");

                Map<String, String> p = new HashMap<>();
                for(String s : properties.keySet()) {
                    p.put(s, properties.getString(s));
                }

                this.widgets.add(new Widget(name, content, constraints, p));
            }

        }
        else {
            JSONArray array = object.getJSONArray("res");

            for (int i = 0; i < array.length(); ++i) {

                this.res.add(array.getString(i));

            }
        }

    }

    public boolean isFinal() {
        return this.isFinal;
    }


}
