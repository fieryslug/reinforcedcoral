package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.problem.Problem;

import java.util.ArrayList;
import java.util.Arrays;

public class Category {

    private ArrayList<Problem> problems;
    private ProblemSet parentSet;
    public String name;
    public String id;

    public Category(String name, String id) {

        this.name = name;
        this.problems = new ArrayList<>();
        this.id = id;

    }

    public void addProblems(Problem ... problems) {

        for (Problem problem : problems) {
            problem.id = this.id + "/" + problem.shortId;
            problem.setParentCat(this);
        }
        this.getProblems().addAll(Arrays.asList(problems));

    }

    public void set(int i, Problem problem) {

        problem.setShortId(ProblemSet.shortIdForProblem(this, problem.shortId));

        problem.id = this.id + "/" + problem.shortId;
        problem.setParentCat(this);
        problems.set(i, problem);

    }

    public ArrayList<Problem> getProblems() {
        return problems;
    }

    public void setParentSet(ProblemSet set) {
        this.parentSet = set;
    }

    public ProblemSet getParentSet() {
        return parentSet;
    }
}
