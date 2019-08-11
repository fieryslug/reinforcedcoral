package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.FontChangingListener;
import com.fieryslug.reinforcedcoral.util.FontRef;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;

public class FontChangerLabel extends JLabel {

    public FontChangerLabel() {

        super();
        setFocusable(true);

        addMouseWheelListener(new FontChangingListener(this));
    }



}
