package com.fieryslug.reinforcedcoral.core;

import java.util.ArrayList;

public class Game {

    public ArrayList<Team> teams;
    public ArrayList<Category> categories;

    public Game(int t1, int t2, int t3, int t4) {

        this.teams = new ArrayList<>();

        this.teams.add(new Team(t1));
        this.teams.add(new Team(t2));
        this.teams.add(new Team(t3));
        this.teams.add(new Team(t4));

        this.categories = new ArrayList<>();

    }

    public void addCategory(Category category) {

        this.categories.add(category);

    }

}
