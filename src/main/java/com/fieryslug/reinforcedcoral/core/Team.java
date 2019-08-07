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

    public void setScore(int score) {
        this.score = score;
    }

    public int getId() {

        return this.id;

    }

    public void setId(int id) {
        this.id = id;
    }

}
