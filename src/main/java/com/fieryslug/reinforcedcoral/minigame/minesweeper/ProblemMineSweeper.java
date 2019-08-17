package com.fieryslug.reinforcedcoral.minigame.minesweeper;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;

public class ProblemMineSweeper extends Problem {

    private PanelMineSweeper panelMineSweeper;

    private int mineHeight, mineWidth, mineMines;

    public ProblemMineSweeper(String name) {
        this(name, 7, 24, 34);
    }

    public ProblemMineSweeper(String name, Integer height, Integer width, Integer mines) {
        super(name, 0);
        this.mineHeight = height;
        this.mineWidth = width;
        this.mineMines = mines;
    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        PanelProblem panelProblem = new PanelProblem (panelGame.parent);
        panelGame.setPhase(GamePhase.SPECIAL);
        this.panelMineSweeper = new PanelMineSweeper(this, this.mineHeight, this.mineWidth, this.mineMines);
        this.panelMineSweeper.bindPanelGame(panelGame);

        panelGame.parent.switchPanel(panelGame, panelGame);
        panelGame.add(this.panelMineSweeper, "0, 1, 5, 4");
        panelGame.currentMinigamePanel = this.panelMineSweeper;

        this.panelMineSweeper.start();
        return true;
    }
}

