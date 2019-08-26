package com.fieryslug.reinforcedcoral.minigame.minesweeper;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;
import org.json.JSONArray;
import org.json.JSONObject;

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
        panelGame.add(this.panelMineSweeper, "0, 1, " + (panelGame.getPartitionNumber() - 1) + ", 1");
        panelGame.currentMinigamePanel = this.panelMineSweeper;

        this.panelMineSweeper.start();
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
        JSONObject jsonArg2 = new JSONObject();
        jsonArg2.put("arg", "name");
        jsonArg2.put("value", this.mineHeight);
        JSONObject jsonArg3 = new JSONObject();
        jsonArg3.put("arg", "name");
        jsonArg3.put("value", this.mineWidth);
        JSONObject jsonArg4 = new JSONObject();
        jsonArg4.put("arg", "name");
        jsonArg4.put("value", this.mineMines);
        arrayArgs.put(jsonArg1);
        arrayArgs.put(jsonArg2);
        arrayArgs.put(jsonArg3);
        arrayArgs.put(jsonArg4);
        jsonMatch.put("args", arrayArgs);
        return jsonMatch;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}

