package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.widget.ButtonProblem;

public class ProblemSlipper extends Problem {

    public ProblemSlipper(String name) {

        super(name, 0);

    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        ButtonProblem buttonProblem = panelGame.problemButtonMap.get(this);
        buttonProblem.setState(1);
        return true;

    }
}
