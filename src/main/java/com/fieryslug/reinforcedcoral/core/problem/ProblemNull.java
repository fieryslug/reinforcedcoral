package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;

import javax.swing.SwingUtilities;

public class ProblemNull extends Problem {

    public ProblemNull(String name) {

        super(name, 0);

    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        ButtonProblem buttonProblem = panelGame.problemButtonMap.get(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                buttonProblem.setState(1);
            }
        });

        return true;

    }
}
