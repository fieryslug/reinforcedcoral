package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Pair;

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

    public static Map<Pair<Image, Dimension>, ImageIcon> iconCache = new HashMap<>();

    private Map<Direction, ButtonCoral> neighbors;

    public ButtonCoral(Image imageDefault, Image imageHover, Image imagePress) {

        setImages(imageDefault, imageHover, imagePress);

        this.imageDefault = imageDefault;
        this.imageHover = imageHover;
        this.imagePress = imagePress;

        /*
        try {
            this.iconDefault = new ImageIcon(imageDefault);
            this.iconHover = new ImageIcon(imageHover);
            this.iconPress = new ImageIcon(imagePress);
        } catch (NullPointerException e) {

        }
        */

        this.neighbors = new HashMap<>();

        toDefault();


        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        this.mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                onPressed();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (ButtonCoral.this.isMouseInside)
                    onHover();
                else
                    toDefault();
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                onEntered();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                onExited();
            }
        };

        this.addMouseListener(this.mouseListener);

    }

    public void setImages(Image imageDefault, Image imageHover, Image imagePress) {
        System.out.println("images set");
        this.imageDefault = imageDefault;
        this.imageHover = imageHover;
        this.imagePress = imagePress;

        try {
            this.iconDefault = new ImageIcon(imageDefault);
            this.iconHover = new ImageIcon(imageHover);
            this.iconPress = new ImageIcon(imagePress);
        } catch (NullPointerException e) {

        }
    }

    public void onPressed() {
        setIcon(ButtonCoral.this.iconPress);
    }

    public void onHover() {
        setIcon(this.iconHover);
    }

    public void toDefault() {
        setIcon(this.iconDefault);
    }

    public void onEntered() {
        setIcon(ButtonCoral.this.iconHover);
        this.isMouseInside = true;
    }

    public void onExited() {
        setIcon(ButtonCoral.this.iconDefault);
        this.isMouseInside = false;
    }

    public void resizeImageForIcons(int x, int y) {

        this.iconDefault = resizeImage(this.imageDefault, x, y);
        this.iconHover = resizeImage(this.imageHover, x, y);
        this.iconPress = resizeImage(this.imagePress, x, y);
        setIcon(iconDefault);

    }

    public void resizePreservingRatio(int x, int y) {
        this.imageDefault = FuncBox.resizeImagePreservingRatio(this.imageDefault, x, y);
        this.imageHover = FuncBox.resizeImagePreservingRatio(this.imageHover, x, y);
        this.imagePress = FuncBox.resizeImagePreservingRatio(this.imagePress, x, y);

        this.iconDefault = new ImageIcon(this.imageDefault);
        this.iconHover = new ImageIcon(this.imageHover);
        this.iconPress = new ImageIcon(this.imagePress);

        setIcon(this.iconDefault);


    }

    public static ImageIcon resizeImage(Image image, int x, int y) {
        Pair<Image, Dimension> key = new Pair<Image, Dimension>(image, new Dimension(x, y));

        ImageIcon icon = iconCache.get(key);

        if(icon != null) return icon;

        BufferedImage bimage = MediaRef.toBufferedImage(image);
        Image dimg = bimage.getScaledInstance(x, y, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(dimg);
        iconCache.put(key, imageIcon);
        return imageIcon;

    }

    public void setNeighbor(Direction direction, ButtonCoral buttonCoral) {
        this.neighbors.put(direction, buttonCoral);
    }

    public ButtonCoral getNeighbor(Direction direction) {
        return this.neighbors.get(direction);
    }
}
