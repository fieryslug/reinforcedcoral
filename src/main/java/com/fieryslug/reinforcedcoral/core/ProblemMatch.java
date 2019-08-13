package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.minigame.match.PanelMatch;
import com.fieryslug.reinforcedcoral.minigame.pong.PanelPong;
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
