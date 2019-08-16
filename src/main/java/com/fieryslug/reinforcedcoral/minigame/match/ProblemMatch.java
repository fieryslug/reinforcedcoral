package com.fieryslug.reinforcedcoral.minigame.match;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelGame;

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



        panelGame.add(this.panelMatch, "0, 1, 5, 4");
        panelGame.currentMinigamePanel = this.panelMatch;
        panelGame.validate();
        panelMatch.start();

        return true;
    }
}
