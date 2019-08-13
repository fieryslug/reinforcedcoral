package com.fieryslug.reinforcedcoral.minigame;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelProblem;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.ButtonCoral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelMiniGameIntro extends PanelProblem implements PanelMiniGame {


    public ButtonCoral buttonBack;
    public ButtonCoral buttonNext;
    private PanelGame panelGame;

    public PanelMiniGameIntro(FrameCoral parent) {
        super(parent);
        this.panelGame = (PanelGame)parent.panelGame;

        TextureHolder holder = TextureHolder.getInstance();
        this.buttonBack = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelGame.setPhase(GamePhase.MENU);
                parent.switchPanel(panelGame, panelGame);
            }
        });
    }

    @Override
    public void react(Team team, ControlKey key) {
    }

    @Override
    public void applyTexture() {
        super.applyTexture();
        TextureHolder holder = TextureHolder.getInstance();
        int width = this.buttonBack.getWidth();
        int height = this.buttonBack.getHeight();
        int buttonX = this.panelGame.paneHeight / 8, buttonY = this.panelGame.paneHeight / 8;
        this.buttonBack.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        if (width > 0 && height > 0) {
            this.buttonBack.resizeImageForIcons(buttonX, buttonY);
        }

    }
}
