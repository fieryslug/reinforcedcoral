package com.fieryslug.reinforcedcoral.core;

public class Team {

    private int score;
    private int id;
    public boolean hasPrivilege;

    public Team(int id) {

        this.score = 0;
        this.id = id;
        this.hasPrivilege = false;

    }

    public void addPoints(int points) {

        this.score += points;

    }

    public int getScore() {

        return this.score;

    }

    public int getId() {

        return this.id;

    }

}
