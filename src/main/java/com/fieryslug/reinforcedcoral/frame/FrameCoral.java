package com.fieryslug.reinforcedcoral.frame;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.PanelSettings;
import com.fieryslug.reinforcedcoral.panel.PanelThemes;
import com.fieryslug.reinforcedcoral.panel.PanelTitle;
import com.fieryslug.reinforcedcoral.panel.edit.PanelEdit;
import com.fieryslug.reinforcedcoral.panel.title.PanelTitleBeautified;
import com.fieryslug.reinforcedcoral.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.util.Map;

public class FrameCoral extends JFrame {

    public int maxHeight, maxWidth;


    public PanelPrime panelTitleBeautified;
    public PanelPrime panelGame;
    public PanelPrime panelEdit;

    @Deprecated
    public PanelPrime panelSettings;
    @Deprecated
    public PanelPrime panelTitle;
    @Deprecated
    public PanelPrime panelThemes;


    public PanelPrime currentPanel;

    public boolean isFullScreen = false;

    public Game game;

    public static boolean locked = false;

    public TextureHolder textureHolder;

    public FrameCoral(Game game) {

        this.game = game;
        //this.game.teams.get(2).hasPrivilege = true;

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.maxHeight = dimension.height;
        this.maxWidth = dimension.width;



        setSize(this.maxWidth *2/3, this.maxHeight *2/3);

        setLocationRelativeTo(null);
        setTitle(Reference.PROJECT_NAME + "-" + Reference.VERSION);
        setIconImage(MediaRef.CORAL);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.BLACK);

        this.textureHolder = new TextureHolder();
        this.textureHolder.read(Preference.texture);

        this.panelTitle = new PanelTitle(this);
        this.panelTitleBeautified = new PanelTitleBeautified(this);
        this.panelSettings = new PanelSettings(this);
        this.panelGame = new PanelGame(this);
        this.panelThemes = new PanelThemes(this);
        this.panelEdit = new PanelEdit(this);

        FuncBox.addKeyBinding(this.getRootPane(), "F11", new ActionFullScreen(this));
        FuncBox.addKeyBinding(this.getRootPane(), KeyStroke.getKeyStroke('T', InputEvent.ALT_DOWN_MASK), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!locked) {
                    String texture = Preference.texture;
                    int index = 0;
                    for (int i = 0; i < Reference.TEXTURE_PACKS.length; ++i) {
                        if (texture.equals(Reference.TEXTURE_PACKS[i])) {
                            index = i;
                            break;
                        }
                    }
                    index = (index + 1) % Reference.TEXTURE_PACKS.length;
                    Preference.texture = Reference.TEXTURE_PACKS[index];
                    TextureHolder holder = TextureHolder.getInstance();
                    holder.read(Preference.texture);
                    //applyTexture(holder);
                    refresh(holder);
                }
            }
        });

        boolean notTesting = false;

        if(notTesting) getContentPane().add(panelTitle);
        else getContentPane().add(panelTitleBeautified);

        if(notTesting) this.currentPanel = panelTitle;
        else this.currentPanel = panelTitleBeautified;


        setVisible(true);
        System.out.println("uuuuuuuuuuuuuuuuu" + getInsets().top);
        setSize(this.maxWidth *2/3, this.maxHeight *2/3 + getInsets().top);
        if(notTesting) panelTitle.enter();
        else panelTitleBeautified.enter();




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

        panel2.refresh();

    }

    public void refresh(TextureHolder holder) {


        this.currentPanel.applyTexture(holder);

        this.currentPanel.refresh();

    }

    public void react(Map<String, String> queryMap) {

        String teamStr = queryMap.get("team");
        String buttonStr = queryMap.get("button");
        Team team = null;

        for (Team team1 : this.game.getTeams()) {
            if (team1.getId() == Integer.parseInt(teamStr)) {
                team = team1;
            }
        }

        ControlKey controlKey = ControlKey.getKey(buttonStr);

        this.currentPanel.react(team, controlKey);

    }

    public void refreshGame(Game game) {

        this.game = game;
        this.panelGame = new PanelGame(this);
        this.textureHolder.read(Preference.texture);

    }


    public void applyTexture(TextureHolder holder) {
        this.currentPanel.applyTexture(holder);
    }
}
