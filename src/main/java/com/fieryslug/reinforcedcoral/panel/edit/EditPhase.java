package com.fieryslug.reinforcedcoral.panel.edit;

public enum EditPhase {

    MENU(0), DEPENDENCIES(1);

    int state;
    EditPhase(int state) {
        this.state = state;
    }

    int getState() {
        return this.state;
    }

}
