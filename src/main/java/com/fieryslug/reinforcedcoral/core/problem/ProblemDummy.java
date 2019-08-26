package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.panel.PanelGame;

public class ProblemDummy extends Problem {

    public ProblemDummy(String name) {
        super(name, 0);
        addDependence(this);
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
