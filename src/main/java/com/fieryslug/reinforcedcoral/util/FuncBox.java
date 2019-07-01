package com.fieryslug.reinforcedcoral.util;

import javax.swing.*;
import java.awt.*;

public class FuncBox {

    public static JLabel blankLabel(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    public static void addKeyBinding(JComponent c, String key, final Action action) {

        c.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key), key);
        c.getActionMap().put(key, action);
        c.setFocusable(true);

    }

}
