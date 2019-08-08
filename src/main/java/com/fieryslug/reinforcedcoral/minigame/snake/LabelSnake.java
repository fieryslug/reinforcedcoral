package com.fieryslug.reinforcedcoral.minigame.snake;

import com.fieryslug.reinforcedcoral.util.Reference;

import javax.swing.JLabel;

public class LabelSnake extends JLabel {

    public boolean occupied = false;
    public int state = 0;

    public LabelSnake() {
        super("", CENTER);
        setOpaque(true);
        setBackground(Reference.TRANSPARENT);


    }

}
