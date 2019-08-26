package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.panel.PanelGame;

public class ProblemDisabled extends Problem {

    public ProblemDisabled(String name) {
        super(name, 0);

    }

    @Override
    protected void onButtonBound() {
        this.buttonProblem.setState(1);
    }

    @Override
    public boolean onClick(PanelGame panelGame) {
        return true;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
