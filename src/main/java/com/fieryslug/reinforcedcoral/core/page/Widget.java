package com.fieryslug.reinforcedcoral.core.page;

import java.util.HashMap;
import java.util.Map;

public class Widget {

    public EnumWidget widgetType;
    public String content;
    public String constraint;

    public Widget(String name, String content, String constraint) {

        this.widgetType = EnumWidget.NAME_MAP.get(name);
        this.content = content;
        this.constraint = constraint;


    }


    enum EnumWidget {

        JLABEL("jlabel"), JTEXTAREA("jtextarea");

        public String name;
        public static final Map<String, EnumWidget> NAME_MAP = new HashMap<>();
        static {
            NAME_MAP.put("jlabel", JLABEL);
            NAME_MAP.put("jtextarea", JTEXTAREA);
        }

        EnumWidget(String name) {
            this.name = name;
        }

    }
}
