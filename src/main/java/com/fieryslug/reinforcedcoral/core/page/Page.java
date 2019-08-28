package com.fieryslug.reinforcedcoral.core.page;

import com.fieryslug.reinforcedcoral.core.problem.Problem;
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

    private Problem parentProb;

    public Page(String path) {

        //this.htmlText = FuncBox.readFile(PATH + path);
        this.res = new ArrayList<>();

        String s = FuncBox.readFile(PATH + path);
        JSONObject object = new JSONObject(s);
        this.type = object.getInt("type");
        JSONArray array = object.getJSONArray("res");

        for (int i = 0; i < array.length(); ++i) {

            this.res.add(array.getString(i));

        }
    }

    public Page(JSONObject object) {

        this.res = new ArrayList<>();
        this.type = object.getInt("type");
        this.widgets = null;
        this.isFinal = this.type == -1;

        if (this.type == Reference.MAGIC_PRIME) {
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
                for (String s : properties.keySet()) {
                    p.put(s, properties.getString(s));
                }

                this.widgets.add(new Widget(name, content, constraints, p));
            }

        } else {
            JSONArray array = object.getJSONArray("res");

            for (int i = 0; i < array.length(); ++i) {

                this.res.add(array.getString(i));

            }
        }

    }

    public boolean isFinal() {
        return this.isFinal;
    }

    /*
    public JSONObject exportAsJson() {

        JSONObject json = new JSONObject();
        json.put("type", this.type);

        if (this.type == Reference.MAGIC_PRIME) {

            json.put("final", this.isFinal);
            JSONArray arrayWidgets = new JSONArray();
            for (Widget widget : this.widgets) {
                arrayWidgets.put(widget.exportAsJson());
            }

            json.put("elements", arrayWidgets);

        } else {

            JSONArray arrayRes = new JSONArray();

            for (String str : this.res) {
                arrayRes.put(str);
            }

            json.put("res", arrayRes);

        }

        return json;

    }
    */

    public JSONObject exportAsJson() {

        JSONObject jsonPage = new JSONObject();
        JSONArray arrayWidgets = new JSONArray();
        Widget[] widgets = new Widget[]{};

        if(this.type == Reference.MAGIC_PRIME) {
            jsonPage.put("final", this.isFinal);

            for (Widget widget : this.widgets) {
                arrayWidgets.put(widget.exportAsJson());
            }
        }
        else if (this.type == -1) {
            jsonPage.put("final", true);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");
            propTitle.put("center", "true");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 19", propArea)
            };



        } else if (this.type == 0) {

            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");
            propTitle.put("center", "true");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 19", propArea)
            };


        } else if (this.type == 1) {

            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "80");
            propTitle.put("textsizefull", "120");
            propTitle.put("center", "true");
            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 19", propTitle)
            };


        } else if (this.type == 2) {
            jsonPage.put("final", false);

            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");
            propTitle.put("center", "true");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            Map<String, String> propImage = new HashMap<>();


            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 7, 11, 19", propArea),
                    new Widget("image", this.res.get(2), "12, 5, 19, 19", propImage)
            };


        } else if (this.type == 3) {
            jsonPage.put("final", false);

            Map<String, String> propImage = new HashMap<>();

            widgets = new Widget[]{
                    new Widget("image", this.res.get(0), "0, 0, 19, 19", propImage)
            };


        } else if (this.type == 4) {
            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");
            propTitle.put("center", "true");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            Map<String, String> propImage = new HashMap<>();

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 9", propArea),
                    new Widget("image", this.res.get(2), "0, 10, 19, 19", propImage)
            };

        }

        for (Widget widget : widgets) {
            arrayWidgets.put(widget.exportAsJson());
        }
        jsonPage.put("type", Reference.MAGIC_PRIME);
        jsonPage.put("elements", arrayWidgets);


        return jsonPage;
    }

    public Page toNormalForm() {
        return new Page(exportAsJson());
    }

    public Problem getParentProb() {
        return parentProb;
    }

    public void setParentProb(Problem parentProb) {
        this.parentProb = parentProb;
    }

    public static Page createEmptyPage(boolean isFinal) {

        JSONObject json = new JSONObject();
        json.put("type", Reference.MAGIC_PRIME);
        json.put("elements", new JSONArray());
        json.put("final", isFinal);

        return new Page(json);

    }

    /*
    public JSONObject toNormalForm() {
        if(this.type == Reference.MAGIC_PRIME)
            return exportAsJson();
        JSONObject jsonPage = new JSONObject();
        JSONArray arrayWidgets = new JSONArray();
        Widget[] widgets = new Widget[]{};

        if (this.type == -1) {
            jsonPage.put("final", true);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 19", propArea)
            };



        } else if (this.type == 0) {

            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 19", propArea)
            };


        } else if (this.type == 1) {

            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "80");
            propTitle.put("textsizefull", "120");
            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 19", propTitle)
            };


        } else if (this.type == 2) {
            jsonPage.put("final", false);

            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            Map<String, String> propImage = new HashMap<>();


            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 7, 11, 19", propArea),
                    new Widget("image", this.res.get(2), "12, 5, 19, 19", propImage)
            };


        } else if (this.type == 3) {
            jsonPage.put("final", false);

            Map<String, String> propImage = new HashMap<>();

            widgets = new Widget[]{
                    new Widget("image", this.res.get(0), "0, 0, 19, 19", propImage)
            };


        } else if (this.type == 4) {
            jsonPage.put("final", false);
            Map<String, String> propTitle = new HashMap<>();
            propTitle.put("textbold", "true");
            propTitle.put("textsize", "40");
            propTitle.put("textsizefull", "60");

            Map<String, String> propArea = new HashMap<>();
            propArea.put("textbold", "true");
            propArea.put("textsize", "30");
            propArea.put("textsizefull", "45");

            Map<String, String> propImage = new HashMap<>();

            widgets = new Widget[]{
                    new Widget("jlabel", this.res.get(0), "0, 0, 19, 4", propTitle),
                    new Widget("jtextarea", this.res.get(1), "1, 5, 18, 6", propArea),
                    new Widget("image", this.res.get(2), "0, 10, 19, 19", propImage)
            };

        }

        for (Widget widget : widgets) {
            arrayWidgets.put(widget.exportAsJson());
        }
        jsonPage.put("type", Reference.MAGIC_PRIME);
        jsonPage.put("elements", arrayWidgets);


        return jsonPage;
    }
    */
}
