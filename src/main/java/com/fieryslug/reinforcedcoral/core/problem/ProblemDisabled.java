package com.fieryslug.reinforcedcoral.core.problem;

public class ProblemDisabled extends Problem {

    public ProblemDisabled() {
        super("", 0);

    }

    @Override
    protected void onButtonBound() {
        this.buttonProblem.setState(1);
    }
}
