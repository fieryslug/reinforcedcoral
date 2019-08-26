package com.fieryslug.reinforcedcoral.core;

import java.util.ArrayList;

public class Game {

    private ArrayList<Team> teams;
    //public ArrayList<Category> categories;
    private ProblemSet problemSet;

    @Deprecated
    public Game(int t1, int t2, int t3, int t4) {

        this.setTeams(new ArrayList<>());

        this.getTeams().add(new Team(t1));
        this.getTeams().add(new Team(t2));
        this.getTeams().add(new Team(t3));
        this.getTeams().add(new Team(t4));

        //this.categories = new ArrayList<>();
        this.problemSet = new ProblemSet("");

    }

    public Game(ProblemSet problemSet, ArrayList<Team> teams) {

        this.problemSet = problemSet;
        this.setTeams(teams);

    }

    public void addCategory(Category category) {

        this.problemSet.addCategory(category);

    }

    public ArrayList<Category> getCategories() {
        return this.problemSet.getCategories();
    }

    public void setPrivilegeTeam(Team team) {

        if (this.getTeams().contains(team)) {

            for (Team team1 : this.getTeams())
                team1.hasPrivilege = false;


            team.hasPrivilege = true;
        }

    }

    public ProblemSet getProblemSet() {
        return this.problemSet;
    }

    public Team getPrivelgeTeam() {
        for (Team team1 : this.getTeams()) {
            if (team1.hasPrivilege) return team1;
        }
        return null;
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }


    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
}
