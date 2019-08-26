package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import javax.media.Player;

//import sun.audio.AudioPlayer;

public class ProblemMine extends Problem {

    private Timer timer;
    private int points;

    public ProblemMine(String name) {

        this(name, -300);

    }

    public ProblemMine(String name, Integer points) {
        super(name, 0);
        this.points = points;
    }

    @Override
    public boolean onClick(PanelGame panelGame) {
        Game game = panelGame.parent.game;
        Team team = game.getPrivelgeTeam();
        System.out.println(team);

        if(team != null) {
            this.timer = new Timer();

            PanelTeam panelTeam = panelGame.teamPanelMap.get(team);
            String s = panelTeam.labelScore.getText();
            //MediaRef.playWav(MediaRef.EXPLOSION);
            MediaRef.playSound(MediaRef.EXPLOSION);
            panelTeam.labelScore.setText("<html>" + s + "<font color=red> "+ this.points +"</font></html>");
            team.addPoints(this.points);
            ButtonProblem buttonProblem = panelGame.problemButtonMap.get(this);
            buttonProblem.state = 1;
            panelGame.refresh();


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

            //System.out.println("hi");
        }

        return true;
    }

    @Override
    public JSONObject exportAsJson() {
        JSONObject jsonMine = new JSONObject();
        jsonMine.put("special", true);
        jsonMine.put("class", this.getClass().getName());

        JSONArray arrayArgs = new JSONArray();

        JSONObject jsonArg0 = new JSONObject();
        jsonArg0.put("arg", "name");
        jsonArg0.put("value", this.name);

        JSONObject jsonArg1 = new JSONObject();
        jsonArg1.put("arg", "points");
        jsonArg1.put("value", this.points);

        arrayArgs.put(jsonArg0);
        arrayArgs.put(jsonArg1);


        jsonMine.put("args", arrayArgs);

        return jsonMine;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
