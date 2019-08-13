package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.minigame.pong.PanelPong;
import com.fieryslug.reinforcedcoral.panel.PanelGame;

public class ProblemPong extends Problem {

    private PanelPong panelPong;

    public ProblemPong(String name) {
        super(name, 0);
    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        panelGame.setPhase(GamePhase.SPECIAL);
        this.panelPong = new PanelPong(this);
        panelPong.bindPanelGame(panelGame);

        panelGame.parent.switchPanel(panelGame, panelGame);



        panelGame.add(this.panelPong, "0, 1, 5, 4");
        panelGame.currentMinigamePanel = this.panelPong;
        panelGame.validate();
        panelPong.start();

        return true;
    }
}