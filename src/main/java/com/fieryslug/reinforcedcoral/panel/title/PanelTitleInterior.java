package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import info.clearthought.layout.TableLayout;

public class PanelTitleInterior extends PanelInterior {

    JLabel labelTitle;

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


    public PanelTitleInterior(PanelTitleBeautified panelTitle) {

        this.panelTitle = panelTitle;

        TextureHolder holder = TextureHolder.getInstance();

        double sixth = 1.0d/6;

        double[][] size = {{1.0d/5, 1.0d/5, 1.0d/5, 1.0d/5, 1.0d/5}, {1.0d/3, 1.0d/3, 1.0d/6, 1.0d/6}};
        setLayout(new TableLayout(size));



        this.labelTitle = new JLabel("test", SwingConstants.CENTER);
        this.labelTitle.setForeground(holder.getColor("text"));
        this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));

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



        add(this.labelTitle, "0, 0, 4, 1");

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



    }

    @Override
    public void enter() {

        int buttonX = (int) (this.panelTitle.getWidth() / (3));
        int buttonY = (int) (this.panelTitle.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.6);

        this.buttonThemes.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonSettings.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonStart.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonInfo.resizeImageForIcons(buttonSize, buttonSize);
    }

    @Override
    public void exit() {

    }

    @Override
    public void refresh(boolean isFullScreen) {
        if (isFullScreen) {

            this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 150));

            this.labelThemes.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 60));
            this.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelInfo.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));


        } else {

            this.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));

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
        this.buttonStart.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonInfo.resizeImageForIcons(buttonSize, buttonSize);
    }

    @Override
    public void applyTexture(TextureHolder holder) {
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




        this.labelTitle.setForeground(holder.getColor("text"));
    }

}
