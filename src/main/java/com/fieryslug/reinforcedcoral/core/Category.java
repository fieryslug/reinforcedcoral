package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.problem.Problem;

import java.util.ArrayList;
import java.util.Arrays;

public class Category {

    public ArrayList<Problem> problems;
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
        }
        this.problems.addAll(Arrays.asList(problems));

    }

}
