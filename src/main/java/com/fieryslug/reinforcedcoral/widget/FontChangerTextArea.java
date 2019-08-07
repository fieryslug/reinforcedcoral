package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.FontChangingListener;

import javax.swing.JTextArea;

public class FontChangerTextArea extends JTextArea {

    public FontChangerTextArea() {
        super();
        addMouseWheelListener(new FontChangingListener(this));
    }

}
