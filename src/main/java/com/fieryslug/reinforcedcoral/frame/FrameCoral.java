package com.fieryslug.reinforcedcoral.frame;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.PanelSettings;
import com.fieryslug.reinforcedcoral.panel.PanelTitle;
import com.fieryslug.reinforcedcoral.util.ActionFullScreen;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class FrameCoral extends JFrame {

    public int maxHeight, maxWidth;

    public PanelPrime panelTitle;
    public PanelPrime panelSettings;
    public PanelPrime panelGame;

    public PanelPrime currentPanel;

    public boolean isFullScreen = false;

    public Game game;

    public FrameCoral(Game game) {



        this.game = game;
        this.game.teams.get(2).hasPrivilege = true;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.maxHeight = dimension.height;
        this.maxWidth = dimension.width;

        setSize(this.maxWidth *2/3, this.maxHeight *2/3);
        setLocationRelativeTo(null);
        setTitle(Reference.PROJECT_NAME);
        setIconImage(MediaRef.CORAL);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.BLACK);



        this.panelTitle = new PanelTitle(this);
        this.panelSettings = new PanelSettings(this);
        this.panelGame = new PanelGame(this);

        FuncBox.addKeyBinding(this.getRootPane(), "F11", new ActionFullScreen(this));

        getContentPane().add(panelTitle);
        this.currentPanel = panelTitle;

        setVisible(true);
    }

    public void switchPanel(PanelPrime panel1, PanelPrime panel2) {

        this.currentPanel = panel2;

        this.getContentPane().removeAll();

        this.getContentPane().add(panel2);

        panel1.revalidate();
        panel1.repaint();
        panel2.revalidate();
        panel2.repaint();

        panel1.exit();
        panel2.enter();


        this.invalidate();
        this.validate();

    }

    public void refresh() {

        this.currentPanel.refresh();

    }

    public void react(Map<String, String> queryMap) {

        String teamStr = queryMap.get("team");
        String buttonStr = queryMap.get("button");
        Team team = null;

        for (Team team1 : this.game.teams) {
            if (team1.getId() == Integer.valueOf(teamStr)) {
                team = team1;
            }
        }

        ControlKey controlKey = ControlKey.getKey(buttonStr);

        this.currentPanel.react(team, controlKey);

    }



}
