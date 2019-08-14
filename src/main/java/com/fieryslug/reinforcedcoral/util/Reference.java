package com.fieryslug.reinforcedcoral.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Reference {

    public static final String PROJECT_NAME = "Project Reinforced Coral";
    public static final String VERSION = "gradle_7.1";
    public static final int DEFAULT_GAME = 1;

    public static final String[] TEXTURE_PACKS = {"eggplant", "caramel", "classic", "balloon", "ocean", "jungle", "punk"};
    //public static final String

    public static final int MAGIC_PRIME = 62;

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color AQUA = new Color(76, 173, 171);
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
    public static final Color DARKDARKBLUE = new Color(15, 21, 43);
    public static final Color TRANSPARENT = new Color(0f, 0f, 0f, 0f);
    public static final Color MAGENTA = new Color(217, 26, 132, 255);
    public static final Color PURPLE = new Color(125, 31, 175);
    public static final Color YELLOW = new Color(255, 188, 0);
    public static final Color DARKAQUA = new Color(9, 79, 70);
    public static final Color DARKERGREEN = new Color(0, 32, 3);
    public static final Color TRANSPARENT_BLUE = new Color(72, 91, 146, 64);


    public static final Border BEVEL1 = BorderFactory.createBevelBorder(0, AQUA, WHITE);
    public static final Border BEVEL2 = BorderFactory.createBevelBorder(1, BLAZE, ORANGE);
    public static final Border BEVELGREEN = BorderFactory.createBevelBorder(0, LIME, GREEN);
    public static final Border STROKE1 = BorderFactory.createStrokeBorder(new BasicStroke(3));
    public static final Border COMPOUND1 = BorderFactory.createCompoundBorder(BEVELGREEN, STROKE1);
    public static final Border PLAIN1 = BorderFactory.createLineBorder(BROWN, 1);




}
