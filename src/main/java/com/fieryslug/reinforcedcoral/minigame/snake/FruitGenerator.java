package com.fieryslug.reinforcedcoral.minigame.snake;

import java.awt.Color;

public class FruitGenerator {

    public int points;
    public int fruitWorth;
    public int coolDown;
    public int firstCoolDown;
    public int id;

    public boolean active = true;
    public int countDown;

    private int boundX = 50;
    private int boundY = 20;

    public Color color;

    public FruitGenerator(int points, int fruitWorth, int coolDown, int firstCoolDown, int id, Color color) {
        this.points = points;
        this.fruitWorth = fruitWorth;
        this.coolDown = coolDown;
        this.firstCoolDown = firstCoolDown;
        this.id = id;
        this.color = color;

        this.countDown = firstCoolDown;
    }

}
