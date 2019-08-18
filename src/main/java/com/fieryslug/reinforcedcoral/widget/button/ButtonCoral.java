package com.fieryslug.reinforcedcoral.widget.button;

import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Pair;
import com.fieryslug.reinforcedcoral.widget.Direction;

import org.omg.CORBA.Bounds;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    protected MouseAdapter mouseClickListener;
    protected MouseAdapter mouseListener;
    protected MouseAdapter mouseMotionListener;

    private ArrayList<ActionListener> listeners;

    public static Map<Pair<Image, Dimension>, ImageIcon> iconCache = new HashMap<>();

    private Map<Direction, ButtonCoral> neighbors;

    public ButtonCoral(Image imageDefault, Image imageHover, Image imagePress) {
        this(imageDefault, imageHover, imagePress, true);
    }

    public ButtonCoral(Image imageDefault, Image imageHover, Image imagePress, boolean irregularShape) {

        setImages(imageDefault, imageHover, imagePress);
        this.listeners = new ArrayList<ActionListener>();

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

        this.mouseClickListener = new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (isMouseInside && isEnabled()) {
                    onPressed();
                }
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (ButtonCoral.this.isMouseInside && isEnabled()) {
                    onHover();
                    ActionEvent event = new ActionEvent(ButtonCoral.this, ActionEvent.ACTION_PERFORMED, "", mouseEvent.getWhen(), mouseEvent.getModifiers());

                    for (ActionListener listener : listeners) {
                        listener.actionPerformed(event);
                    }

                } else
                    toDefault();

            }

        };

        this.mouseListener = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                onEntered();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                onExited();
            }
        };


        this.mouseMotionListener = new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                Rectangle bounds = getImageBounds();
                boolean inside = bounds.contains(mouseEvent.getPoint());

                if (inside) {
                    inside = false;
                    BufferedImage bimage = FuncBox.toBufferedImage(ButtonCoral.this.imageDefault);
                    int ix = mouseEvent.getX() - bounds.x;
                    int iy = mouseEvent.getY() - bounds.y;
                    int[] arr = bimage.getData().getPixel(ix, iy, new int[4]);

                    //System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3]);

                    if (arr[3] > 0) {
                        inside = true;
                    }

                }

                if (inside) {
                    if (!isMouseInside) {
                        onPressed();
                    }
                } else {
                    if (isMouseInside) {
                        onExited();
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {

                Rectangle bounds = getImageBounds();
                boolean inside = bounds.contains(mouseEvent.getPoint());

                if (inside) {
                    inside = false;
                    BufferedImage bimage = FuncBox.toBufferedImage(ButtonCoral.this.imageDefault);
                    int ix = mouseEvent.getX() - bounds.x;
                    int iy = mouseEvent.getY() - bounds.y;
                    int[] arr = bimage.getData().getPixel(ix, iy, new int[4]);

                    //System.out.println(arr[0] + ", " + arr[1] + ", " + arr[2] + ", " + arr[3]);

                    if (arr[3] > 0) {
                        inside = true;
                    }

                }

                if (inside) {
                    if (!isMouseInside || true) {
                        onEntered();
                    }
                } else {
                    if (isMouseInside) {
                        onExited();
                    }
                }

            }
        };

        addMouseListener(this.mouseClickListener);
        if (irregularShape)
            addMouseMotionListener(this.mouseMotionListener);
        else
            addMouseListener(this.mouseListener);


    }

    @Override
    public void doClick() {
        super.doClick();
        ActionEvent event = new ActionEvent(ButtonCoral.this, ActionEvent.ACTION_PERFORMED, "", System.currentTimeMillis(), 0);

        for (ActionListener listener : listeners) {
            listener.actionPerformed(event);
        }
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        //super.addActionListener(actionListener);
        this.listeners.add(actionListener);
    }


    public void refreshRendering() {
        if (this.isMouseInside) {
            onEntered();
        } else {
            toDefault();
        }
    }

    public void setImages(Image imageDefault, Image imageHover, Image imagePress) {
        //System.out.println("images set");
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
        this.isMouseInside = true;
        setIcon(this.iconPress);
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

        this.imageDefault = FuncBox.resizeImage(this.imageDefault, x, y);
        this.imageHover = FuncBox.resizeImage(this.imageHover, x, y);
        this.imagePress = FuncBox.resizeImage(this.imagePress, x, y);

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

        if (icon != null) return icon;

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

    private Rectangle getImageBounds() {
        // TODO Add in proper handling if component size < image size.

        BufferedImage image = FuncBox.toBufferedImage(this.imageDefault);
        return new Rectangle((int) ((getBounds().width - image.getWidth()) / 2), (int) ((getBounds().height - image.getHeight()) / 2), image.getWidth(), image.getHeight());
    }


}
