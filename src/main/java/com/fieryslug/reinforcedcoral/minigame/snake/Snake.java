package com.fieryslug.reinforcedcoral.minigame.snake;

import com.fieryslug.reinforcedcoral.widget.Direction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Snake {

    public Queue<Point> occupiedSlots;
    public Point head;
    public Point tail;
    public boolean isAlive = true;
    public Direction direction;
    public Direction primaryTurn = null;
    public Direction secondaryTurn = null;
    public Color color;

    public int fruitEaten = 0;

    public Snake(Direction direction, Color color) {
        this.occupiedSlots = new LinkedList<>();
        this.direction = direction;
        this.color = color;
    }

    public int getLength() {
        return occupiedSlots.size();
    }



}
