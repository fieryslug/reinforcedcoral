package com.fieryslug.reinforcedcoral.util;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FontRef {


    private static Map<Pair<Integer, Integer>, Font> taipeiFontCache = new HashMap<>();
    private static Map<Pair<String, Pair<Integer, Integer>>, Font> fontCache = new HashMap<>();
    public static final Map<String, String> BOLD_FONT_MAP = new HashMap<>();

    public static final String TAIPEI = "Taipei Sans TC Beta Regular";
    public static final String MONOSPACE = "Monospace Regular";
    public static final String TAIPEI_BOLD = "Taipei Sans TC Beta Bold";
    public static final String TIMES_NEW_ROMAN = "Times New Roman";
    public static final String LAST_RESORT = "Literate Web LastResort";

    static {
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/TaipeiSansTCBeta-Regular.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/TaipeiSansTCBeta-Bold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/kaiu.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/cmunss.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/Times New Roman.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/Times_New_Roman_Bold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/DejaVuSans.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/DejaVuSans-Bold.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/Monospace.ttf")));
            //ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/arialuni.ttf")));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, FuncBox.inputStreamFromPath("/res/fonts/LastResort.ttf")));

            System.out.println("Fonts loaded");
            FuncBox.listAllFonts();
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    static {
        BOLD_FONT_MAP.put("Taipei Sans TC Beta Regular", "Taipei Sans TC Beta Bold");
        BOLD_FONT_MAP.put("Times New Roman", "Times New Roman Bold");
        BOLD_FONT_MAP.put("DejaVu Sans", "DejaVu Sans Bold");
    }

    @Deprecated
    public static Font getTaipeiFont(int b, int s) {

        Pair<Integer, Integer> pair = new Pair<>(b, s);
        Font font = taipeiFontCache.get(pair);
        if (font != null)
            return font;
        font = new Font("Taipei Sans TC Beta Regular", b, s);
        taipeiFontCache.put(pair, font);
        return font;

    }

    public static Font getFont(String fontName, int b, int s) {

        Pair<String, Pair<Integer, Integer>> info = new Pair<>(fontName, new Pair<>(b, s));
        Font font = fontCache.get(info);
        if (font != null) {
            return font;
        }
        if (b == Font.BOLD) {
            String boldFontName = BOLD_FONT_MAP.get(fontName);
            if (boldFontName != null) {
                font = new Font(boldFontName, Font.PLAIN, s);
                fontCache.put(info, font);
                return font;
            }
        }
        font = new Font(fontName, b, s);
        fontCache.put(info, font);
        return font;

    }

    public static final Font MONOSPACED30 = new Font("Monospaced", Font.PLAIN, 30);
    public static final Font MONOSPACED30BOLD = new Font("Monospaced", Font.BOLD, 30);
    public static final Font MONOSPACED45 = new Font("Monospaced", Font.PLAIN, 45);
    public static final Font MONOSPACED45BOLD = new Font("Monospaced", Font.BOLD, 45);
    public static final Font MONOSPACED60BOLD = new Font("Monospaced", Font.BOLD, 60);

    public static final Font JHENGHEI30 = new Font("Microsoft JhengHei", Font.PLAIN, 30);
    public static final Font JHENGHEI40BOLD = new Font("Microsoft JhengHei", Font.BOLD, 40);
    public static final Font JHENGHEI45 = new Font("Microsoft JhengHei", Font.PLAIN, 45);
    public static final Font JHENGHEI60BOLD = new Font("Microsoft JhengHei", Font.BOLD, 60);
    public static final Font JHENGHEI80BOLD = new Font("Microsoft JhengHei", Font.BOLD, 80);
    public static final Font JHENGHEI120BOLD = new Font("Microsoft JhengHei", Font.BOLD, 120);

    public static final Font TAIPEI30 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 30);
    public static final Font TAIPEI35 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 35);
    public static final Font TAIPEI40 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 40);
    public static final Font TAIPEI40BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 40);
    public static final Font TAIPEI45 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 45);
    public static final Font TAIPEI60 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 60);
    public static final Font TAIPEI60BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 60);
    public static final Font TAIPEI80 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 80);
    public static final Font TAIPEI90 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 90);
    public static final Font TAIPEI80BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 80);
    public static final Font TAIPEI120 = new Font("Taipei Sans TC Beta Regular", Font.PLAIN, 120);
    public static final Font TAIPEI120BOLD = new Font("Taipei Sans TC Beta Bold", Font.PLAIN, 120);

}
