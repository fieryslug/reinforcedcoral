package com.fieryslug.reinforcedcoral.core.page;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

public class Widget {

    public EnumWidget widgetType;
    public String content;
    public String constraints;
    public Map<String, String> properties;

    public static final String KEY_TEXT_SIZE = "textsize";
    public static final String KEY_TEXT_SIZE_FULL = "textsizefull";
    public static final String KEY_TEXT_COLOR = "textcolor";
    public static final String KEY_TEXT_BOLD = "textbold";
    public static final String KEY_CENTER = "center";

    public Widget(String name, String content, String constraint, Map<String, String> properties) {

        this.widgetType = EnumWidget.NAME_MAP.get(name);
        this.content = content;
        this.constraints = constraint;
        this.properties = properties;
    }


    public int getTextSize() {
        String s = properties.get(KEY_TEXT_SIZE);
        if(s == null) return 40;
        return Integer.valueOf(s);
    }

    public int getTextSizeFull() {
        String s = properties.get(KEY_TEXT_SIZE_FULL);
        if(s == null) return 60;
        return Integer.valueOf(s);
    }

    public boolean getBold() {
        String s = properties.get(KEY_TEXT_BOLD);
        if(s == null) return false;
        return Boolean.valueOf(s);
    }

    public boolean getCenter() {
        String s = properties.get(KEY_CENTER);
        if(s == null) return false;
        return Boolean.valueOf(s);
    }

    public Color getTextColor() {
        String s = properties.get(KEY_TEXT_COLOR);
        if(s == null) return new Color(255, 255, 255);

        return new Color(Integer.valueOf(s));
    }


    public enum EnumWidget {

        JLABEL("jlabel"), JTEXTAREA("jtextarea"), IMAGE("image");

        public String name;
        public static final Map<String, EnumWidget> NAME_MAP = new HashMap<>();

        static {
            NAME_MAP.put("jlabel", JLABEL);
            NAME_MAP.put("jtextarea", JTEXTAREA);
            NAME_MAP.put("image", IMAGE);
        }

        EnumWidget(String name) {
            this.name = name;
        }

    }

    enum EnumWidgetProperty {

        TEXT_SIZE(0), TEXT_SIZE_FULL(1), TEXT_COLOR(2);


        private int id;

        EnumWidgetProperty(int id) {

            this.id = id;

        }

    }
}
