package com.fieryslug.reinforcedcoral.widget;

public enum Direction {

    UP(0), DOWN(1), LEFT(2), RIGHT(3);

    int id;

    Direction(int id) {

        this.id = id;

    }

    public boolean isSimilarTo(Direction other) {

        if(this.id == 0 || this.id == 1) return other.id == 0 || other.id == 1;
        if(this.id == 2 || this.id == 3) return other.id == 2 || other.id == 3;
        return false;
    }

}
