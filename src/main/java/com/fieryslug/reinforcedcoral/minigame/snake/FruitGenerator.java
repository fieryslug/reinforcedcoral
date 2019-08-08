package com.fieryslug.reinforcedcoral.minigame.snake;

public class FruitGenerator {

    public int points;
    public int fruitWorth;
    public int coolDown;
    public int id;

    public boolean active = true;
    public int countDown;

    private int boundX = 50;
    private int boundY = 20;

    public FruitGenerator(int points, int fruitWorth, int coolDown, int id) {
        this.points = points;
        this.fruitWorth = fruitWorth;
        this.coolDown = coolDown;
        this.id = id;

        this.countDown = coolDown;
    }

}
