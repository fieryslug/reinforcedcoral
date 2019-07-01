package com.fieryslug.reinforcedcoral.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Problem {

    private int points;
    private boolean fuzzy = true;
    public String name;
    private ArrayList<ControlKey> answer;
    public ArrayList<Page> pages;

    public Problem(String name, int points) {

        this.name = name;
        this.points = points;
        this.pages = new ArrayList<>();
        this.answer = new ArrayList<>();

    }

    public int getPoints() {
        return this.points;
    }

    public void addPages(Page... pages) {

        this.pages.addAll(Arrays.asList(pages));

    }

    public boolean checkAnswer(ArrayList<ControlKey> answer) {

        if (this.fuzzy) {
            Collections.sort(answer);
            Collections.sort(this.answer);

            return answer.equals(this.answer);

        } else {

            if (answer.size() != this.answer.size()) return false;
            for (int i = 0; i < answer.size(); ++i) {
                if (!answer.get(i).equals(this.answer.get(i))) return false;
            }
            return true;

        }
    }


}
