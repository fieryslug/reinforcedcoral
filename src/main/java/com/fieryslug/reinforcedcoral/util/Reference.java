package com.fieryslug.reinforcedcoral.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Reference {

    public static final String PROJECT_NAME = "Project Reinforced Coral";
    //public static final String

    public static final int MAGIC_PRIME = 62;

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color AQUA = new Color(85, 221, 209);
    public static final Color DARKRED = new Color(130, 32, 0);
    public static final Color RED = new Color(205, 10, 10);
    public static final Color BROWN = new Color(138, 89, 41);
    public static final Color GRAY = new Color(153, 153, 153);
    public static final Color DARKGRAY = new Color(60, 60, 60);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color BLAZE = new Color(216, 154, 93);
    public static final Color ORANGE = new Color(239, 115, 66);
    public static final Color LIME = new Color(90, 224, 83);
    public static final Color GREEN = new Color(56, 142, 52);
    public static final Color DARKGREEN = new Color(33, 83, 30);
    public static final Color DARKBLUE = new Color(39, 69, 115);
    public static final Color TRANSPARENT = new Color(0f, 0f, 0f, 0f);

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



    public static final Border BEVEL1 = BorderFactory.createBevelBorder(0, AQUA, WHITE);
    public static final Border BEVEL2 = BorderFactory.createBevelBorder(1, BLAZE, ORANGE);
    public static final Border BEVELGREEN = BorderFactory.createBevelBorder(0, LIME, GREEN);
    public static final Border STROKE1 = BorderFactory.createStrokeBorder(new BasicStroke(3));
    public static final Border COMPOUND1 = BorderFactory.createCompoundBorder(BEVELGREEN, STROKE1);


}
