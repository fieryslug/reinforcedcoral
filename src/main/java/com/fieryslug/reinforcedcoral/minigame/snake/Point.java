package com.fieryslug.reinforcedcoral.minigame.snake;

import com.fieryslug.reinforcedcoral.widget.Direction;

public class Point {

    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point getNeighbor(Direction direction, int boundX, int boundY) {

        switch (direction) {
            case UP:
                return new Point(x, Math.floorMod(y-1, boundY));
            case DOWN:
                return new Point(x, Math.floorMod(y+1, boundY));
            case LEFT:
                return new Point(Math.floorMod(x-1, boundX), y);
            case RIGHT:
                return new Point(Math.floorMod(x+1, boundX), y);
        }
        return this;

    }

    @Override
    public int hashCode() {
        return Integer.valueOf(this.x).hashCode() + Integer.valueOf(this.y).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || ! (o instanceof Point)) return false;
        Point other = (Point) o;
        return this.x == other.x && this.y == other.y;
    }
}
