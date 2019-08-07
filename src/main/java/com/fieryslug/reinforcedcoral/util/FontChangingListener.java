package com.fieryslug.reinforcedcoral.util;

import com.fieryslug.reinforcedcoral.widget.FontChangerTextArea;

import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

public class FontChangingListener implements MouseWheelListener {

    public JComponent component;

    public FontChangingListener(JComponent component) {
        this.component = component;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

        double amount = mouseWheelEvent.getPreciseWheelRotation();
        Font font = this.component.getFont();
        int size = font.getSize() + (int)(amount * 5);
        int fontStyle = font.getStyle();
        String fontName = font.getFontName();
        if (size >= 3) {
            this.component.setFont(FontRef.getFont(fontName, fontStyle, size));
            System.out.println("font changed: " + size);
            this.component.repaint();
        }

    }
}
