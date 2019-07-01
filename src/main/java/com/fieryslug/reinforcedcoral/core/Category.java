package com.fieryslug.reinforcedcoral.core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {

    public ArrayList<Problem> problems;
    public String name;

    public Category(String name) {

        this.name = name;
        this.problems = new ArrayList<>();

    }

    public void addProblem(Problem ... problems) {

        this.problems.addAll(Arrays.asList(problems));

    }

}
