package com.fieryslug.reinforcedcoral.core;

public class Team {

    private int score;
    private int id;
    public boolean hasPrivilege;
    private String name;

    public Team(int id) {

        this.score = 0;
        this.id = id;
        this.hasPrivilege = false;
        this.name = "team " + String.valueOf(id) + "  ";

    }



    public void addPoints(int points) {

        addPoints(points, "");

    }

    public void addPoints(int points, String reason) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
