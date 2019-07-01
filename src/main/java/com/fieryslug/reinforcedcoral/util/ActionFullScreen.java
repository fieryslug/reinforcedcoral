package com.fieryslug.reinforcedcoral.util;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ActionFullScreen extends AbstractAction {

    private FrameCoral frame;
    private GraphicsDevice device;

    public ActionFullScreen(FrameCoral frame) {
        this(frame, GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
    }

    public ActionFullScreen(FrameCoral frame, GraphicsDevice device) {
        this.frame = frame;
        this.device = device;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        if(frame.isUndecorated()) {
            device.setFullScreenWindow(null);
            frame.setUndecorated(false);
        }
        else {
            frame.setUndecorated(true);
            device.setFullScreenWindow(frame);
        }

        frame.setVisible(true);

        frame.refresh();

        frame.repaint();
    }
}
