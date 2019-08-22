package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import info.clearthought.layout.TableLayout;

import javax.swing.*;

public class PanelEdit extends PanelPrime {

    JPanel[] panels;
    PanelInterior panelEditTitle;
    PanelEditGame panelEditGame;

    PanelInterior currentPanelInterior;

    int currInd;
    boolean dirty = false;


    public PanelEdit(FrameCoral parent) {
        super(parent);
        initialize();

    }

    @Override
    protected void initialize() {
        double[][] size = new double[][]{{0.5, 0.5}, {0.2, 0.6, 0.2}};
        setLayout(new TableLayout(size));

        this.panels = new JPanel[4];
        double[][] size1 = new double[][]{FuncBox.createDivisionArray(4), {0.4, 0.3, 0.3}};
        for (int i = 0; i < 4; ++i) {
            this.panels[i] = new JPanel();
            this.panels[i].setLayout(new TableLayout(size1));
        }

        this.panelEditTitle = new PanelEditTitle(this);
        this.panelEditGame = new PanelEditGame(this);

        this.currentPanelInterior = this.panelEditTitle;

        add(this.panels[0], "0, 0");
        add(this.panels[1], "1, 0");
        add(this.panels[2], "0, 2");
        add(this.panels[3], "1, 2");
        add(this.panelEditTitle, "0, 1, 1, 1");
    }

    @Override
    public void enter() {
        this.dirty = false;
        this.currentPanelInterior.enter();
        add(this.currentPanelInterior, "0, 1, 1, 1");
        System.out.println("in panel edit: width " + getWidth());
        applyTexture(TextureHolder.getInstance());
        refresh();
    }

    @Override
    public void exit() {

        remove(this.currentPanelInterior);
        this.currentPanelInterior.exit();
    }

    @Override
    public void refresh() {
        System.out.println("in edit refresh: width " + getWidth());
        this.currentPanelInterior.refresh(parent.isFullScreen);
    }

    @Override
    public void applyTexture(TextureHolder holder) {
        setBackground(holder.getColor("background"));

        for (int i = 0; i < 4; ++i) {
            if (i < 2) {
                this.panels[i].setBorder(FuncBox.getLineBorder(holder.getColor("teamu_border"), 3));
                this.panels[i].setBackground(holder.getColor("teamu"));
            } else {
                this.panels[i].setBorder(FuncBox.getLineBorder(holder.getColor("teamd_border"), 3));
                this.panels[i].setBackground(holder.getColor("teamd"));
            }
        }
        this.currentPanelInterior.applyTexture(holder);
    }
}
