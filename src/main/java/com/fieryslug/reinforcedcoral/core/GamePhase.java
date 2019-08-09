package com.fieryslug.reinforcedcoral.core;

public enum GamePhase {
    MENU(0), IN_PROBLEM(1), ANSWERING(2), SHOW_ANSWER(12), SOLUTION(3), SPECIAL(-1);

    int id;
    GamePhase(int id) {
        this.id = id;
    }

}
