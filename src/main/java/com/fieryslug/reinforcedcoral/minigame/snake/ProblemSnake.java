package com.fieryslug.reinforcedcoral.minigame.snake;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.minigame.PanelMiniGameIntro;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.util.FuncBox;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProblemSnake extends Problem {

    private PanelSnake panelSnake;
    private PanelGame panelGame;
    private PanelMiniGameIntro panelMiniGameIntro;

    public ProblemSnake(String name) {
        super(name, 0);
        this.panelSnake = new PanelSnake(this);
    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        //panelGame.setState(-1);
        this.panelGame = panelGame;
        panelGame.setPhase(GamePhase.SPECIAL);
        this.panelSnake = new PanelSnake(this);
        this.panelSnake.bindPanelGame(panelGame);

        if(this.panelMiniGameIntro == null)
            this.panelMiniGameIntro = new PanelMiniGameIntro(panelGame.parent);
        panelGame.parent.switchPanel(panelGame, panelGame);
        //panelGame.add(this.panelMiniGameIntro, "0, 1, 5, 3");
        panelGame.add(panelGame.panelCenter, "0, 1, " + (panelGame.getPartitionNumber() - 1) + ", 1");
        panelGame.panelCenter.add(this.panelMiniGameIntro, "0, 0, 4, 0");
        panelGame.panelCenter.add(panelMiniGameIntro.buttonBack, "0, 1");
        panelGame.panelCenter.add(panelMiniGameIntro.buttonNext, "4, 1");
        panelGame.panelCenter.add(panelGame.panelBanner, "0, 1, 4, 1");

        this.panelMiniGameIntro.inflate2(new Page(new JSONObject(FuncBox.readFile("/res/problems/special/snake.json"))));
        this.panelMiniGameIntro.refreshRendering(panelGame.parent.isFullScreen);
        this.panelMiniGameIntro.applyTexture();

        this.panelMiniGameIntro.buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelGame.setPhase(GamePhase.SPECIAL);
                panelGame.parent.switchPanel(panelGame, panelGame);

                panelGame.add(panelSnake, "0, 1, " + (panelGame.getPartitionNumber() - 1) + ", 1");
                panelGame.currentMinigamePanel = panelSnake;
                panelSnake.start();
            }
        });

        panelGame.currentMinigamePanel = this.panelMiniGameIntro;
        //panelGame.add(this.panelMiniGameIntro.buttonBack, "0, 4");
        //panelGame.add(this.panelMiniGameIntro.buttonNext, "5, 4");
        //panelGame.add(panelGame.panelBanner, "0, 4, 5, 4");


        /*
        panelGame.parent.switchPanel(panelGame, panelGame);

        panelGame.add(this.panelSnake, "0, 1, 5, 4");
        panelGame.currentMinigamePanel = this.panelSnake;
        panelSnake.start();
        */

        return true;
    }

    @Override
    public JSONObject exportAsJson() {
        JSONObject jsonMatch = new JSONObject();
        jsonMatch.put("special", true);
        jsonMatch.put("class", this.getClass().getName());
        JSONArray arrayArgs = new JSONArray();
        JSONObject jsonArg1 = new JSONObject();
        jsonArg1.put("arg", "name");
        jsonArg1.put("value", this.name);
        arrayArgs.put(jsonArg1);
        jsonMatch.put("args", arrayArgs);
        return jsonMatch;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
