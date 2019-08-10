package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;

public class PanelTeam extends JPanel {

    public JLabel labelName;
    public JLabel labelScore;
    public JLabel labelState;

    public Team team;

    public PanelTeam(Team team) {

        this.team = team;
        initialize();

    }

    public void initialize() {

        this.setBorder(Reference.BEVEL1);
        this.setBackground(Reference.BLACK);

        this.labelName = new JLabel();
        this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 30));
        this.labelName.setForeground(Reference.BLAZE);
        this.labelName.setText("第" + this.team.getId() + "小隊    ");

        this.labelScore = new JLabel();
        this.labelScore.setFont(FontRef.MONOSPACED30BOLD);
        this.labelScore.setForeground(Reference.BLAZE);
        this.labelScore.setText(String.valueOf(this.team.getScore()));

        this.labelState = new JLabel();
        this.labelState.setFont(FontRef.TAIPEI40BOLD);
        this.labelState.setForeground(Reference.WHITE);
        this.labelState.setText("");

        this.labelScore.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

                boolean isUp = mouseWheelEvent.getPreciseWheelRotation() < 0;

                if (mouseWheelEvent.isControlDown()) {
                    if(isUp)
                        team.addPoints(100);
                    else
                        team.addPoints(-100);
                }
                if (mouseWheelEvent.isShiftDown()) {
                    if (isUp)
                        team.addPoints(5);
                    else
                        team.addPoints(-5);

                }
                if (mouseWheelEvent.isAltDown()) {
                    if(isUp)
                        team.addPoints(1);
                    else
                        team.addPoints(-1);
                }
                labelScore.setText(String.valueOf(team.getScore()));
            }
        });

        add(this.labelName);
        add(this.labelScore);
        add(FuncBox.blankLabel(2000, 10));
        add(this.labelState);

    }

    @Deprecated
    public void enter(boolean isFullScreen) {

        if(isFullScreen) {
            this.labelState.setFont(Reference.JHENGHEI60BOLD);
        }
        else {
            this.labelState.setFont(Reference.JHENGHEI40BOLD);
        }

    }

    public void refreshFontSize(boolean isFullScreen) {
        if (isFullScreen) {
            this.labelState.setFont(FontRef.TAIPEI60BOLD);
            this.labelScore.setFont(FontRef.MONOSPACED45BOLD);
            this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 51));
        } else {
            this.labelState.setFont(FontRef.TAIPEI40BOLD);
            this.labelScore.setFont(FontRef.MONOSPACED30BOLD);
            this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 34));
        }
    }

    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("team"));
        setBorder(BorderFactory.createLineBorder(holder.getColor("team_border"), 3));
        this.labelName.setForeground(holder.getColor("team_text"));

    }

}
