package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.util.TextureHolder;

import javax.swing.JPanel;

public abstract class PanelInterior extends JPanel {



    public void enter() {

    }

    public void exit() {

    }

    public void applyTexture(TextureHolder holder) {

    }

    public void refresh(boolean isFullScreen) {

    }

    public PanelPrime getPanelParent() {
        return null;
    }

    public boolean isFullScreen() {
        return getPanelParent().parent.isFullScreen;
    }

}
