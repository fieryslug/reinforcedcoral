package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.panel.PanelGame;

public class ProblemSnake extends Problem {


    public ProblemSnake(String name) {
        super(name, 0);

    }


    @Override
    public boolean onClick(PanelGame panelGame) {
        return true;
    }
}
