package com.fieryslug.reinforcedcoral.core;

import java.util.ArrayList;

public class Game {

    public ArrayList<Team> teams;
    public ArrayList<Category> categories;
    private ProblemSet problemSet;

    @Deprecated
    public Game(int t1, int t2, int t3, int t4) {

        this.teams = new ArrayList<>();

        this.teams.add(new Team(t1));
        this.teams.add(new Team(t2));
        this.teams.add(new Team(t3));
        this.teams.add(new Team(t4));

        this.categories = new ArrayList<>();
        this.problemSet = new ProblemSet("test");
        this.problemSet.categories = this.categories;

    }

    public Game(ProblemSet problemSet, ArrayList<Team> teams) {

        this.problemSet = problemSet;
        this.teams = teams;

    }

    public void addCategory(Category category) {

        this.categories.add(category);

    }

    public ArrayList<Category> getCategories() {
        return this.problemSet.categories;
    }

    public void setPrivilegeTeam(Team team) {

        if (this.teams.contains(team)) {

            for (Team team1 : this.teams)
                team1.hasPrivilege = false;


            team.hasPrivilege = true;
        }

    }

    public Team getPrivelgeTeam() {
        for (Team team1 : this.teams) {
            if (team1.hasPrivilege) return team1;
        }
        return null;
    }


}
