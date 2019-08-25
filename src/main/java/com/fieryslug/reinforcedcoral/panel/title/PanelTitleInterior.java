package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.*;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import info.clearthought.layout.TableLayout;

public class PanelTitleInterior extends PanelInterior {

    JLabel labelPrev;
    JLabel labelTitle;
    JLabel labelNext;

    JLabel labelSettings;
    JLabel labelStart;
    JLabel labelEdit;
    JLabel labelThemes;
    JLabel labelInfo;

    ButtonCoral buttonSettings;
    ButtonCoral buttonStart;
    ButtonCoral buttonEdit;
    ButtonCoral buttonThemes;
    ButtonCoral buttonInfo;


    private PanelTitleBeautified panelTitle;

    private String prevProblemSet;
    private String currentProblemSet;
    private String nextProblemSet;
    int currInd;


    public PanelTitleInterior(PanelTitleBeautified panelTitle) {

        DataLoader loader = DataLoader.getInstance();
        this.prevProblemSet = "";
        this.currInd = 0;
        this.currentProblemSet = loader.getProblemSets().get(0).getName();
        this.nextProblemSet = loader.getProblemSets().get(1).getName();

        this.panelTitle = panelTitle;

        TextureHolder holder = TextureHolder.getInstance();

        double sixth = 1.0d/6;

        double[][] size = {FuncBox.createDivisionArray(5), {1.0d/3, 1.0d/3, 1.0d/6, 1.0d/6}};
        setLayout(new TableLayout(size));



        this.labelTitle = new JLabel("test", SwingConstants.CENTER);
        this.labelTitle.setForeground(holder.getColor("text"));
        this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));

        this.labelPrev = new JLabel("test", SwingConstants.CENTER);
        this.labelPrev.setForeground(holder.getColor("text_light_2"));
        this.labelPrev.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));

        this.labelNext = new JLabel("test", SwingConstants.CENTER);
        this.labelNext.setForeground(holder.getColor("text_light_2"));
        this.labelNext.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));

        this.labelThemes = new JLabel("themes", SwingConstants.CENTER);
        this.labelThemes.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelThemes.setForeground(holder.getColor("text_light"));

        this.labelSettings = new JLabel("settings", SwingConstants.CENTER);
        this.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelSettings.setForeground(holder.getColor("text_light"));

        this.labelStart = new JLabel("start", SwingConstants.CENTER);
        this.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 60 : 40));
        this.labelStart.setForeground(holder.getColor("text_light"));

        this.labelEdit = new JLabel("edit mode", SwingConstants.CENTER);
        this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelEdit.setForeground(holder.getColor("text_light"));

        this.labelInfo = new JLabel("information", SwingConstants.CENTER);
        this.labelInfo.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelInfo.setForeground(holder.getColor("text_light"));

        this.buttonThemes = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));


        this.buttonSettings = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.buttonStart = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.buttonEdit = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.buttonInfo = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));




        add(this.labelTitle, "1, 0, 3, 1");
        add(this.labelPrev, "0, 0, 0, 1");
        add(this.labelNext, "4, 0, 4, 1");

        add(this.labelThemes, "0, 2");
        add(this.labelSettings, "1, 2");
        add(this.labelStart, "2, 2");
        add(this.labelEdit, "3, 2");
        add(this.labelInfo, "4, 2");


        add(this.buttonThemes, "0, 3");
        add(this.buttonSettings, "1, 3");
        add(this.buttonStart, "2, 3");
        add(this.buttonEdit, "3, 3");
        add(this.buttonInfo, "4, 3");

        this.labelNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if(labelNext.getText().length() > 0) {
                    currInd++;
                    exit();
                    enter();
                    refresh(panelTitle.parent.isFullScreen);
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
                    refresh(panelTitle.parent.isFullScreen);
                }
            }
        });



    }

    @Override
    public void enter() {
        DataLoader loader = DataLoader.getInstance();


        setVisible(false);
        int buttonX = (int) (this.panelTitle.getWidth() / (3));
        int buttonY = (int) (this.panelTitle.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.6);

        this.buttonThemes.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonSettings.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonStart.resizeImageForIcons(buttonSize * 5 / 4, buttonSize * 5 / 4);
        this.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonInfo.resizeImageForIcons(buttonSize, buttonSize);

        this.prevProblemSet = this.currInd > 0 ? loader.getProblemSets().get(this.currInd -1).getName() : "";
        this.currentProblemSet = loader.getProblemSets().get(this.currInd).getName();
        this.nextProblemSet = this.currInd + 1 < loader.getProblemSets().size() ? loader.getProblemSets().get(this.currInd + 1).getName() : "";


        this.labelPrev.setText(this.prevProblemSet);
        this.labelTitle.setText(this.currentProblemSet);
        this.labelNext.setText(this.nextProblemSet);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh(panelTitle.parent.isFullScreen);
            }
        });
        setVisible(true);
    }

    @Override
    public void exit() {

    }

    @Override
    public void refresh(boolean isFullScreen) {
        if (isFullScreen) {

            this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 150));
            this.labelPrev.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 60));
            this.labelNext.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 60));

            this.labelThemes.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 60));
            this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelInfo.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));


        } else {

            this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));
            this.labelPrev.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));
            this.labelNext.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));

            this.labelThemes.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 40));
            this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelInfo.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));

        }

        int buttonX = (int) (this.panelTitle.getWidth() / (3));
        int buttonY = (int) (this.panelTitle.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.6);

        this.buttonThemes.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonSettings.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonStart.resizeImageForIcons(buttonSize * 5 / 4, buttonSize * 5 / 4);
        this.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonInfo.resizeImageForIcons(buttonSize, buttonSize);

        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.labelNext);
            FontRef.scaleFont(this.labelPrev);
            FontRef.scaleFont(this.labelEdit);
            FontRef.scaleFont(this.labelStart);
            FontRef.scaleFont(this.labelInfo);
            FontRef.scaleFont(this.labelThemes);
            FontRef.scaleFont(this.labelSettings);
        }
    }

    @Override
    public void applyTexture(TextureHolder holder) {
        setVisible(false);
        this.setBackground(holder.getColor("interior"));

        this.labelThemes.setForeground(holder.getColor("text_light"));
        this.buttonThemes.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelSettings.setForeground(holder.getColor("text_light"));
        this.buttonSettings.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelStart.setForeground(holder.getColor("text_light"));
        this.buttonStart.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelEdit.setForeground(holder.getColor("text_light"));
        this.buttonEdit.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelInfo.setForeground(holder.getColor("text_light"));
        this.buttonInfo.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        //repaint();

        this.labelTitle.setForeground(holder.getColor("text"));
        this.labelPrev.setForeground(holder.getColor("text_light_2"));
        this.labelNext.setForeground(holder.getColor("text_light_2"));
        setVisible(true);
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelTitle;
    }
}
