package com.fieryslug.reinforcedcoral.core.page;

//import com.sun.org.apache.regexp.internal.RECompiler;
//import layout.TableLayoutConstraints;
import com.fieryslug.reinforcedcoral.util.FontRef;

import org.json.JSONObject;

import info.clearthought.layout.TableLayoutConstraints;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

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
    public static final String KEY_FONT_NAME = "font";

    public Widget(String name, String content, String constraint, Map<String, String> properties) {

        this.widgetType = EnumWidget.NAME_MAP.get(name);
        this.content = content;
        this.constraints = constraint;
        this.properties = properties;
    }


    public int getTextSize() {
        String s = properties.get(KEY_TEXT_SIZE);
        if(s == null) return 40;
        return Integer.parseInt(s);
    }

    public int getTextSizeFull() {
        String s = properties.get(KEY_TEXT_SIZE_FULL);
        if(s == null) return 60;
        return Integer.parseInt(s);
    }

    public boolean getBold() {
        String s = properties.get(KEY_TEXT_BOLD);
        //System.out.println("BOLD: " + s);
        if(s == null) return true;
        return Boolean.parseBoolean(s);
    }

    public boolean getCenter() {
        String s = properties.get(KEY_CENTER);
        if(s == null) return false;
        return Boolean.parseBoolean(s);
    }

    public Color getTextColor() {
        String s = properties.get(KEY_TEXT_COLOR);
        if(s == null) return new Color(255, 255, 255);
        return new Color(Integer.valueOf(s, 16));
    }

    public String getFontName() {
        String s = properties.get(KEY_FONT_NAME);
        if(s == null) return "Taipei Sans TC Beta Regular";
        return s;
    }

    public Font getFont(boolean full) {

        String fontName = getFontName();
        int size = full ? getTextSizeFull() : getTextSize();
        if (getBold())
            return FontRef.getFont(getFontName(), Font.BOLD, size);
        else
            return FontRef.getFont(getFontName(), Font.PLAIN, size);
        /*
        if (getBold()) {
            String boldFontName = FontRef.BOLD_FONT_MAP.get(fontName);
            if(boldFontName == null)
                return FontRef.getFont(fontName, Font.BOLD, size);
            else
                return FontRef.getFont(boldFontName, Font.PLAIN, size);

        }
        return FontRef.getFont(fontName, Font.PLAIN, size);

        */
    }

    public TableLayoutConstraints getConstraints() {
        return new TableLayoutConstraints(this.constraints);
    }

    public boolean isAbstract() {
        return this.widgetType.isAbstract();
    }

    public JSONObject exportAsJson() {

        JSONObject json = new JSONObject();
        json.put("widget", this.widgetType.getName());
        json.put("constraints", this.constraints);
        json.put("content", this.content);

        JSONObject jsonProperties = new JSONObject();
        for (String key : this.properties.keySet()) {
            jsonProperties.put(key, this.properties.get(key));
        }

        json.put("properties", jsonProperties);
        return json;
    }


    public enum EnumWidget {

        JLABEL("jlabel", false),
        JTEXTAREA("jtextarea", false),
        IMAGE("image", false),
        AUDIO("audio", true),
        AUDIOSTOP("audiostop", true),;

        public String name;
        private boolean isAbstract;
        public static final Map<String, EnumWidget> NAME_MAP = new HashMap<>();

        static {
            NAME_MAP.put("jlabel", JLABEL);
            NAME_MAP.put("jtextarea", JTEXTAREA);
            NAME_MAP.put("image", IMAGE);
            NAME_MAP.put("audio", AUDIO);
            NAME_MAP.put("audiostop", AUDIOSTOP);
        }

        EnumWidget(String name, boolean isAbstract) {
            this.name = name;
            this.isAbstract = isAbstract;
        }

        public String getName() {
            return this.name;
        }



        public boolean isAbstract() {
            return this.isAbstract;
        }

        @Override
        public String toString() {

            switch (this) {
                case JLABEL:
                    return "label";
                case JTEXTAREA:
                    return "text area";
                case IMAGE:
                    return "image";
                case AUDIO:
                    return "audio";
                case AUDIOSTOP:
                    return "audiostop";
            }
            return "null";

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
