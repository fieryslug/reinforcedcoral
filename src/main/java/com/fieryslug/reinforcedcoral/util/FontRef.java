package com.fieryslug.reinforcedcoral.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FontRef {

    static {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.fileFromPath("/res/fonts/TaipeiSansTCBeta-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.fileFromPath("/res/fonts/TaipeiSansTCBeta-Bold.ttf")));
            System.out.println("Fonts loaded");
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    public static final Font MONOSPACED30 = new Font("Monospaced", Font.PLAIN, 30);
    public static final Font MONOSPACED30BOLD = new Font("Monospaced", Font.BOLD, 30);
    public static final Font MONOSPACED45 = new Font("Monospaced", Font.PLAIN, 45);
    public static final Font MONOSPACED60BOLD = new Font("Monospaced", Font.BOLD, 60);

    public static final Font JHENGHEI30 = new Font("Microsoft JhengHei", Font.PLAIN, 30);
    public static final Font JHENGHEI40BOLD = new Font("Microsoft JhengHei", Font.BOLD, 40);
    public static final Font JHENGHEI45 = new Font("Microsoft JhengHei", Font.PLAIN, 45);
    public static final Font JHENGHEI60BOLD = new Font("Microsoft JhengHei", Font.BOLD, 60);
    public static final Font JHENGHEI80BOLD = new Font("Microsoft JhengHei", Font.BOLD, 80);
    public static final Font JHENGHEI120BOLD = new Font("Microsoft JhengHei", Font.BOLD, 120);

    public static final Font TAIPEI30 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 30);
    public static final Font TAIPEI40 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 40);
    public static final Font TAIPEI40BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 40);
    public static final Font TAIPEI45 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 45);
    public static final Font TAIPEI60 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 60);
    public static final Font TAIPEI60BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 60);
    public static final Font TAIPEI80 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 80);
    public static final Font TAIPEI80BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 80);
    public static final Font TAIPEI120 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 120);
    public static final Font TAIPEI120BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 120);

}
