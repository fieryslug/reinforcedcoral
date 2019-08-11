package com.fieryslug.reinforcedcoral.core;

public enum GamePhase {
    //menu->in_problem->answering->intermediate->show_answer->solution->menu
    //menu->special->menu
    MENU(0), IN_PROBLEM(1), ANSWERING(2), SHOW_ANSWER(12), SOLUTION(3), POST_SOLUTION(13), SPECIAL(-1), INTERMEDIATE(11);

    int id;
    GamePhase(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

}
