package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import info.clearthought.layout.TableLayout;

public class PanelTitleBeautified extends PanelPrime {

    PanelTitleInterior panelInterior;
    PanelInformation panelInformation;
    private PanelTeam[] panelTeams;
    private PanelInterior currentPanelInterior;
    PanelThemesNew panelThemes;

    public PanelTitleBeautified(FrameCoral parent) {

        super(parent);
        initialize();
        linkButtons();

    }

    @Override
    public void initialize() {

        TextureHolder holder = TextureHolder.getInstance();

        this.panelInterior = new PanelTitleInterior(this);
        this.panelInterior.setBackground(holder.getColor("interior"));
        this.panelInformation = new PanelInformation(this);
        this.panelThemes = new PanelThemesNew(this);

        this.currentPanelInterior = this.panelInterior;



    }

    private void linkButtons() {


        this.panelInterior.buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                parent.switchPanel(PanelTitleBeautified.this, parent.panelGame);
            }
        });

        this.panelInterior.buttonThemes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                currentPanelInterior = panelThemes;
                parent.switchPanel(PanelTitleBeautified.this, PanelTitleBeautified.this);
            }
        });

        this.panelInterior.buttonInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                currentPanelInterior = panelInformation;
                parent.switchPanel(PanelTitleBeautified.this, PanelTitleBeautified.this);

            }
        });


    }



    @Override
    public void enter() {

        int a = (Preference.teams + 1) / 2;
        System.out.println(a);

        this.panelInterior.labelTitle.setText("eee");

        double[][] size = {new double[a], {0.2d, 0.2d, 0.2d, 0.1d, 0.1d, 0.2d}};

        for (int i = 0; i < a; ++i) {
            size[0][i] = 1.0d / a;
        }

        setLayout(new TableLayout(size));

        this.panelTeams = new PanelTeam[Preference.teams];

        for (int t = 0; t < a; ++t) {
            this.panelTeams[t] = new PanelTeam(this.parent.game.getTeams().get(t), t+1);
            add(this.panelTeams[t], t + ", 0," + t + ", 0");
        }

        for (int u = 0; u < a && a + u < Preference.teams; ++u) {
            this.panelTeams[a + u] = new PanelTeam(this.parent.game.getTeams().get(a+u), a+u+1);
            add(this.panelTeams[a + u], u + ", 5," + u + ", 5");
        }


        add(this.currentPanelInterior, "0, 1, " + (a-1) + ", 4");

        applyTexture(TextureHolder.getInstance());

        /*
        int buttonX = (int) (this.getWidth() / (3));
        int buttonY = (int) (this.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.8);


        this.panelInterior.buttonThemes.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonSettings.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonStart.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.button5.resizeImageForIcons(buttonSize, buttonSize);
        */
        //this.panelInterior.enter();
        this.currentPanelInterior.enter();
    }

    @Override
    public void exit() {
        this.currentPanelInterior.exit();
    }

    @Override
    public void refresh() {

        if (this.parent.isFullScreen) {

            /*
            this.panelInterior.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 150));

            this.panelInterior.label1.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.panelInterior.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.panelInterior.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 60));
            this.panelInterior.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.panelInterior.label5.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            */
            for (PanelTeam panelTeam : this.panelTeams) {
                panelTeam.refreshFontSize(true);
            }

        } else {

            /*
            this.panelInterior.labelTitle.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 100));

            this.panelInterior.label1.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.panelInterior.labelSettings.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.panelInterior.labelStart.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 40));
            this.panelInterior.labelEdit.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.panelInterior.label5.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            */
            for (PanelTeam panelTeam : this.panelTeams) {
                panelTeam.refreshFontSize(false);
            }
        }

        /*
        int buttonX = (int) (this.getWidth() / (3));
        int buttonY = (int) (this.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.8);

        this.panelInterior.buttonThemes.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonSettings.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonStart.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.buttonEdit.resizeImageForIcons(buttonSize, buttonSize);
        this.panelInterior.button5.resizeImageForIcons(buttonSize, buttonSize);
        */
        //this.panelInterior.refresh();
        this.currentPanelInterior.refresh(this.parent.isFullScreen);
    }

    @Override
    public void applyTexture(TextureHolder holder) {
        /*

        this.panelInterior.setBackground(holder.getColor("interior"));

        this.panelInterior.label1.setForeground(holder.getColor("text_light"));
        this.panelInterior.buttonThemes.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));


        this.panelInterior.labelSettings.setForeground(holder.getColor("text_light"));
        this.panelInterior.buttonSettings.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.panelInterior.labelStart.setForeground(holder.getColor("text_light"));
        this.panelInterior.buttonStart.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.panelInterior.labelEdit.setForeground(holder.getColor("text_light"));
        this.panelInterior.buttonEdit.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.panelInterior.label5.setForeground(holder.getColor("text_light"));
        this.panelInterior.button5.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        */

        int a = (Preference.teams + 1) / 2;

        for (PanelTeam panelTeam : this.panelTeams) {
            panelTeam.applyTexture(holder);
        }



        //this.panelInterior.labelTitle.setForeground(holder.getColor("text"));

        //this.panelInterior.applyTexture(holder);
        this.currentPanelInterior.applyTexture(holder);
    }

    void setCurrentPanelInterior(PanelInterior panelInterior) {
        this.currentPanelInterior = panelInterior;
    }
}
