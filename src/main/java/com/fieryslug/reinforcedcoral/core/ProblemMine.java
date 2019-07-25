package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelTeam;
import com.fieryslug.reinforcedcoral.widget.ButtonProblem;

import java.awt.Component;
import java.net.URI;
import java.rmi.server.ExportException;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.SpringLayout;

public class ProblemMine extends Problem {

    private Timer timer;

    public ProblemMine(String name) {

        super(name, 0);
        this.timer = new Timer();

    }

    @Override
    public boolean onClick(PanelGame panelGame) {
        Game game = panelGame.parent.game;
        Team team = game.getPrivelgeTeam();

        PanelTeam panelTeam = panelGame.teamPanelMap.get(team);
        String s = panelTeam.labelScore.getText();
        panelTeam.labelScore.setText("<html>" + s + "<font color=red> -50</font></html>");
        team.addPoints(-50);
        ButtonProblem buttonProblem = panelGame.problemButtonMap.get(this);
        buttonProblem.state = 1;
        panelGame.refresh();

        Player player = null;

        try {
            //player = Manager.createRealizedPlayer(ProblemMine.class.getResource("/res/videos/explosion.mp4"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Component video = player.getVisualComponent();
        //panelTeam.add(video);


        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panelTeam.labelScore.setText(String.valueOf(team.getScore()));
            }
        }, 2000);

        System.out.println("hi");

        return true;
    }

}
