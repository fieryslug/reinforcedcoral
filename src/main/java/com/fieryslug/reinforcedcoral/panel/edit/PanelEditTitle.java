package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.title.PanelTitleBeautified;
import com.fieryslug.reinforcedcoral.util.*;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelEditTitle extends PanelInterior {
    ButtonCoral buttonEdit;
    JLabel labelEdit;
    ButtonCoral buttonBack;
    JLabel labelBack;




    JLabel labelPrev;
    JLabel labelTitle;
    JLabel labelNext;

    private PanelEdit panelEdit;

    int currInd;

    public PanelEditTitle(PanelEdit panelEdit) {
        TextureHolder holder = TextureHolder.getInstance();
        this.panelEdit = panelEdit;

        double[][] size = new double[][]{FuncBox.createDivisionArray(5), {1.0d / 3, 1.0d / 3, 1.0d / 6, 1.0d / 6}};
        setLayout(new TableLayout(size));

        setBackground(holder.getColor("interior"));


        this.labelTitle = new JLabel("test", SwingConstants.CENTER);
        this.labelTitle.setForeground(holder.getColor("text"));
        this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));

        this.labelPrev = new JLabel("test", SwingConstants.CENTER);
        this.labelPrev.setForeground(holder.getColor("text_light_2"));
        this.labelPrev.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));

        this.labelNext = new JLabel("test", SwingConstants.CENTER);
        this.labelNext.setForeground(holder.getColor("text_light_2"));
        this.labelNext.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));

        this.labelBack = new JLabel("back", SwingConstants.CENTER);
        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelEdit.parent.isFullScreen ? 60 : 40));
        this.labelBack.setForeground(holder.getColor("text_light"));


        this.labelEdit = new JLabel("edit", SwingConstants.CENTER);
        this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelEdit.parent.isFullScreen ? 60 : 40));
        this.labelEdit.setForeground(holder.getColor("text_light"));


        this.buttonEdit = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonBack = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));


        this.labelNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(labelNext.getText().length() > 0) {
                    currInd++;
                    exit();
                    enter();
                    refresh(panelEdit.parent.isFullScreen);
                }
            }
        });

        this.labelPrev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(labelPrev.getText().length() > 0) {
                    currInd--;
                    exit();
                    enter();
                    refresh(panelEdit.parent.isFullScreen);
                }
            }
        });
        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ((PanelTitleBeautified)(panelEdit.parent.panelTitleBeautified)).setLazy();
                panelEdit.parent.switchPanel(panelEdit, panelEdit.parent.panelTitleBeautified);
            }
        });
        this.buttonEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelEdit.exit();
                panelEdit.currentPanelInterior = panelEdit.panelEditGame;
                panelEdit.panelEditGame.setPhase(EditPhase.MENU);
                panelEdit.enter();
                panelEdit.refresh();
                panelEdit.repaint();
            }
        });

        add(this.labelBack, "1, 2");
        add(this.buttonBack, "1, 3");
        add(this.labelEdit, "2, 2");
        add(this.buttonEdit, "2, 3");

        refresh(panelEdit.parent.isFullScreen);

    }

    @Override
    public void enter() {
        setVisible(false);
        DataLoader loader = DataLoader.getInstance();
        panelEdit.currInd = this.currInd;

        String prevProblemSet = this.currInd > 0 ? loader.getProblemSets().get(this.currInd -1).getName() : "";
        String currentProblemSet = loader.getProblemSets().get(this.currInd).getName();
        String nextProblemSet = this.currInd + 1 < loader.getProblemSets().size() ? loader.getProblemSets().get(this.currInd + 1).getName() : "";

        this.labelNext.setText(nextProblemSet);
        this.labelTitle.setText(currentProblemSet);
        this.labelPrev.setText(prevProblemSet);

        add(this.labelTitle, "1, 0, 3, 1");
        add(this.labelPrev, "0, 0, 0, 1");
        add(this.labelNext, "4, 0, 4, 1");
        System.out.println(this.labelTitle.getWidth());



        setVisible(true);
    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("interior"));

        this.labelTitle.setForeground(holder.getColor("text"));
        this.labelPrev.setForeground(holder.getColor("text_light_2"));
        this.labelNext.setForeground(holder.getColor("text_light_2"));

        this.labelEdit.setForeground(holder.getColor("text_light"));
        this.labelBack.setForeground(holder.getColor("text_light"));
        this.labelEdit.setForeground(holder.getColor("text_light"));
        this.buttonEdit.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonBack.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

    }

    @Override
    public void refresh(boolean isFullScreen) {

        System.out.println("in edit title refresh: width " + getWidth());

        this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 150 : 100));
        this.labelPrev.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 60 : 40));
        this.labelNext.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 60 : 40));

        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 42 : 28));
        this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 60 : 40));

        int buttonX = (int) (this.panelEdit.getWidth() / (3));
        int buttonY = (int) (this.panelEdit.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.6);

        if(buttonSize != 0) {
            this.buttonEdit.resizeImageForIcons(buttonSize * 5 / 4, buttonSize * 5 / 4);
            this.buttonBack.resizeImageForIcons(buttonSize, buttonSize);

        }
        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.labelBack);
            FontRef.scaleFont(this.labelEdit);
            FontRef.scaleFont(this.labelNext);
            FontRef.scaleFont(this.labelPrev);
        }
    }
}
