package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.minigame.snake.PanelSnake;
import com.fieryslug.reinforcedcoral.panel.PanelGame;

public class ProblemSnake extends Problem {

    private PanelSnake panelSnake;

    public ProblemSnake(String name) {
        super(name, 0);
        this.panelSnake = new PanelSnake(this);

    }


    @Override
    public boolean onClick(PanelGame panelGame) {

        panelGame.setState(-1);
        this.panelSnake = new PanelSnake(this);
        panelSnake.bindPanelGame(panelGame);

        panelGame.parent.switchPanel(panelGame, panelGame);



        panelGame.add(this.panelSnake, "0, 1, 5, 4");
        panelGame.currenMinigamePanel = this.panelSnake;
        panelSnake.start();

        return true;
    }
}
