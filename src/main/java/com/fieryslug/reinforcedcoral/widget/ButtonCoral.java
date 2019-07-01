package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.MediaRef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ButtonCoral extends JButton {

    public boolean isMouseInside = false;
    public Image imageDefault;
    public Image imageHover;
    public Image imagePress;

    protected ImageIcon iconDefault;
    private ImageIcon iconHover;
    private ImageIcon iconPress;
    protected MouseListener mouseListener;

    private Map<Direction, ButtonCoral> neighbors;

    public ButtonCoral(Image imageDefault, Image imageHover, Image imagePress) {

        this.imageDefault = imageDefault;
        this.imageHover = imageHover;
        this.imagePress = imagePress;

        this.iconDefault = new ImageIcon(imageDefault);
        this.iconHover = new ImageIcon(imageHover);
        this.iconPress = new ImageIcon(imagePress);

        this.neighbors = new HashMap<>();

        setIcon(new ImageIcon(this.imageDefault));


        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        this.mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                setIcon(ButtonCoral.this.iconPress);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if(ButtonCoral.this.isMouseInside)
                    setIcon(ButtonCoral.this.iconHover);
                else
                    setIcon(ButtonCoral.this.iconDefault);
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                setIcon(ButtonCoral.this.iconHover);
                ButtonCoral.this.isMouseInside = true;
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                setIcon(ButtonCoral.this.iconDefault);
                ButtonCoral.this.isMouseInside = false;
            }
        };

        this.addMouseListener(this.mouseListener);

    }

    public void resizeImageForIcons(int x, int y) {

        this.iconDefault = resizeImage(this.imageDefault, x, y);
        this.iconHover = resizeImage(this.imageHover, x, y);
        this.iconPress = resizeImage(this.imagePress, x, y);
        setIcon(iconDefault);

    }

    public static ImageIcon resizeImage(Image image, int x, int y) {

        BufferedImage bimage = MediaRef.toBufferedImage(image);
        Image dimg = bimage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        return new ImageIcon(dimg);

    }

    public void setNeighbor(Direction direction, ButtonCoral buttonCoral) {
        this.neighbors.put(direction, buttonCoral);
    }

    public ButtonCoral getNeighbor(Direction direction) {
        return this.neighbors.get(direction);
    }

}
