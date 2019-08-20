package com.fieryslug.reinforcedcoral.panel.subpanel;

import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import info.clearthought.layout.TableLayout;

import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.*;

public class PanelTeam extends JPanel {

    public JLabel labelName;
    public JLabel labelScore;
    public JLabel labelState;

    public Team team;

    private int where;
    private boolean isUp;

    public PanelTeam(Team team, int i) {

        this.team = team;
        this.where = i;
        this.isUp = i - 1 < ((Preference.teams + 1) / 2);
        initialize();

    }

    public PanelTeam(Team team, boolean isUp) {

        this.team = team;
        this.isUp = isUp;
        initialize();

    }

    public void initialize() {

        double[][] size = new double[][]{{0.15, 0.7, 0.15}, {0.33, 0.25, 0.42}};
        setLayout(new TableLayout(size));

        this.setBorder(Reference.BEVEL1);
        this.setBackground(Reference.BLACK);

        this.labelName = new JLabel("", SwingConstants.CENTER);
        this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 34));
        this.labelName.setForeground(Reference.BLAZE);
        this.labelName.setText(this.team.getName());

        this.labelScore = new JLabel("", SwingConstants.CENTER);
        this.labelScore.setFont(FontRef.getFont(FontRef.MONOSPACED, Font.BOLD, 30));
        this.labelScore.setForeground(Reference.BLAZE);
        this.labelScore.setText(String.valueOf(this.team.getScore()));

        this.labelState = new JLabel();
        this.labelState.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));
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

        add(this.labelName, "1, 0");
        add(this.labelScore, "1, 1");
        //add(FuncBox.blankLabel(2000, 10));
        add(this.labelState, "0, 2, 2, 2");
        //this.labelScore.setBorder(Reference.BEVEL1);
        //this.labelName.setBorder(Reference.COMPOUND1);
    }

    @Deprecated
    public void enter(boolean isFullScreen) {

        if(isFullScreen) {
            this.labelState.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 60));
        }
        else {
            this.labelState.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));
        }

    }

    public void refreshFontSize(boolean isFullScreen) {
        if (isFullScreen) {
            this.labelState.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 60));
            this.labelScore.setFont(FontRef.getFont(FontRef.MONOSPACED, Font.BOLD, 45));
            this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 51));
        } else {
            this.labelState.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));
            this.labelScore.setFont(FontRef.getFont(FontRef.MONOSPACED, Font.BOLD, 30));
            this.labelName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 34));
            new Color(110, 170, 131);
        }
        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelName);
            FontRef.scaleFont(this.labelScore);
            FontRef.scaleFont(this.labelState);
        }
    }

    public void applyTexture(TextureHolder holder) {

        //System.out.println("hi");

        String side = this.isUp ? "u" : "d";

        setBackground(holder.getColor("team" + side));
        //System.out.println(holder.getColor("team" + this.where));
        //System.out.println();
        setBorder(FuncBox.getLineBorder(holder.getColor("team" + side + "_border"), 3));



        this.labelName.setForeground(holder.getColor("team" + side + "_text"));
        this.labelScore.setForeground(holder.getColor("team" + side + "_score"));

        if (this.team.hasPrivilege) {
            setBackground(holder.getColor("team_privilege"));
            setBorder(FuncBox.getLineBorder(holder.getColor("team_privilege_border"), 3));
            this.labelName.setForeground(holder.getColor("team_privilege_text"));
            this.labelScore.setForeground(holder.getColor("team_privilege_score"));
        }

    }


    public void applyPrimitiveTexture(TextureHolder holder) {
        String side = this.isUp ? "u" : "d";

        setBackground(holder.getColor("team" + side));

        setBorder(BorderFactory.createLineBorder(holder.getColor("team" + side + "_border"), 3));

        this.labelName.setForeground(holder.getColor("team" + side + "_text"));
        this.labelScore.setForeground(holder.getColor("team" + side + "_score"));
    }

    public boolean isUp() {
        return this.isUp;
    }

}
