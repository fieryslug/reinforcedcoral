package com.fieryslug.reinforcedcoral.minigame.minesweeper;

import com.fieryslug.reinforcedcoral.minigame.snake.Point;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.widget.Direction;

import java.awt.Font;
import java.awt.Image;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import javax.print.attribute.standard.Media;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MineSlot extends JLabel {

    int type = 0; //0: plain, 1: mine
    private int state = 0; //0: unpopped, 1: popped, -1: flagged mine
    int neighborMines = 0;

    private Point point;
    private Stack<Border> borderStack;


    Map<Direction, MineSlot> neighbors;
    private Set<MineSlot> adjacentSlots;
    private PanelMineSweeper mineParent;

    int x, y;
    boolean hasIcon = false;

    MineSlot(int x, int y, PanelMineSweeper parent) {
        super("", CENTER);
        this.x = x;
        this.y = y;
        this.mineParent = parent;
        this.neighbors = new HashMap<>();
        this.point = new Point(x, y);
        this.borderStack = new Stack<>();
        this.setOpaque(true);
        this.setBackground(Reference.DARKERGREEN);
        this.setBorder(Reference.PLAIN1);
        this.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 20));
        this.setForeground(Reference.BLAZE);
    }

    Point getPoint() {
        return this.point;
    }

    public void setTopBorder(Border border) {
        this.borderStack.push(border);
        setBorder(border);
    }

    public void removeBorder(Border border) {
        this.borderStack.remove(border);

        if (this.borderStack.empty())
            setBorder(Reference.PLAIN1);
        else
            setBorder(this.borderStack.peek());
    }

    public void removeAllBorders() {
        this.borderStack.clear();
    }

    public boolean unpoppedAndZero() {
        return this.type == 0 && this.state == 0 && this.neighborMines == 0;
    }

    boolean unpoppedAndSafe() {
        return this.type == 0 && this.state == 0;
    }

    public void popSelf() {
        if (this.type == 0) {
            setState(1);
            this.setBackground(Reference.DARKGRAY);
            if (this.neighborMines > 0)
                this.setText(this.neighborMines + "");
        }
        if (this.type == 1) {
            setState(1);
            this.setBackground(Reference.RED);
            this.setForeground(Reference.GRAY);
            this.setText("X");
        }
    }

    void setState(int state) {
        if (state == 1 && this.state == 0) {
            this.state = 1;
            this.mineParent.slotCount--;
        }
        else if (state == -1 && this.state == 0) {
            this.state = -1;
            this.mineParent.slotCount--;
        }
        if(state == 0)
            this.state = 0;

        if (this.mineParent.slotCount == 0) {
            this.mineParent.endGame();
        }
    }



    int getState() {
        return this.state;
    }

    private int verticalNeighborMines() {
        int mines = 0;
        MineSlot u = this.neighbors.get(Direction.UP);
        if (u != null) {
            if(u.type == 1) mines += 1;
        }
        MineSlot d = this.neighbors.get(Direction.DOWN);
        if (d != null) {
            if(d.type == 1) mines += 1;
        }
        return mines;
    }
    private int horizontalNeighborMines() {
        int mines = 0;
        MineSlot l = this.neighbors.get(Direction.LEFT);
        if (l != null) {
            if(l.type == 1) mines += 1;
        }
        MineSlot r = this.neighbors.get(Direction.RIGHT);
        if (r != null) {
            if(r.type == 1) mines += 1;
        }
        return mines;
    }

    void calculateNeighborMines() {

        int mines = 0;
        mines += verticalNeighborMines();
        mines += horizontalNeighborMines();
        MineSlot l = this.neighbors.get(Direction.LEFT);
        if (l != null) {
            mines += l.verticalNeighborMines();
        }
        MineSlot r = this.neighbors.get(Direction.RIGHT);
        if (r != null) {
            mines += r.verticalNeighborMines();
        }
        this.neighborMines = mines;

    }

    void calculateAdjacentSlots() {

        Set<MineSlot> set = new HashSet<>();
        for (Direction direction : this.neighbors.keySet()) {
            MineSlot neighbor = this.neighbors.get(direction);
            set.add(neighbor);
            for (Direction direction1 : neighbor.neighbors.keySet()) {
                if (!direction.isSimilarTo(direction1)) {
                    MineSlot neighbor1 = neighbor.neighbors.get(direction1);
                    set.add(neighbor1);
                }
            }
        }
        this.adjacentSlots = set;

    }

    Set<MineSlot> getAdjacentSlots() {
        return this.adjacentSlots;
    }

    int getNeighborMines() {
        return this.neighborMines;
    }

    void setIconAndResize() {
        int width = getWidth() * 3 / 4;
        int height = getHeight() * 3 / 4;

        Image image = MediaRef.getImage("/res/images/flag_mine.png");
        image = FuncBox.resizeImagePreservingRatio(image, width, height);

        setIcon(new ImageIcon(image));
        this.hasIcon = true;
    }
}
