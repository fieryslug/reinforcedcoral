package com.fieryslug.reinforcedcoral.minigame.match;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProblemMatch extends Problem {

    private PanelMatch panelMatch;
    public ProblemMatch(String name) {
        super(name, 0);
    }
    @Override
    public boolean onClick(PanelGame panelGame) {

        panelGame.setPhase(GamePhase.SPECIAL);
        this.panelMatch = new PanelMatch(this, panelGame);

        panelGame.parent.switchPanel(panelGame, panelGame);

        int a = panelGame.getPartitionNumber();

        panelGame.add(this.panelMatch, "0, 1, " + (a - 1) + ", 1");
        panelGame.currentMinigamePanel = this.panelMatch;
        panelGame.validate();
        panelMatch.start();

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
