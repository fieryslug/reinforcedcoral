package com.fieryslug.reinforcedcoral.minigame.minesweeper;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.minigame.PanelMiniGame;
import com.fieryslug.reinforcedcoral.minigame.snake.Point;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.Direction;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.Border;

import info.clearthought.layout.TableLayout;

public class PanelMineSweeper extends JPanel implements PanelMiniGame {

    private ProblemMineSweeper problemMineSweeper;
    private PanelGame panelGame;

    private int rows, columns;
    private MineSlot slots[][];
    private Random random;
    private int currentTeamNum = 0;
    private Map<Team, Point> cursorMap;
    private ArrayList<Team> teams;
    private Timer timer;
    private Timer timer2;




    private boolean ready = false;
    private boolean locked = false;

    private JButton buttonBack;

    private int mines;
    int slotCount;

    private Border[] borders;

    private static final Color[] FLAG_COLORS = {Reference.MAGENTA, Reference.AQUA, Reference.YELLOW, Reference.GREEN};
    private static final Color CURRENT_TEAM_COLOR = Reference.DARKRED;
    private static final int COUNTDOWN = 10;
    private int countDown;


    PanelMineSweeper(ProblemMineSweeper problem, int rows, int columns, int mines) {
        this.random = new Random();
        this.cursorMap = new HashMap<>();

        this.problemMineSweeper = problem;

        this.rows = rows;
        this.columns = columns;
        this.mines = mines;

        int height = this.rows + 1, width = this.columns + 1;
        this.setBackground(Reference.DARKERGREEN);

        double[][] size = {new double[width + 1], new double[height + 1]};
        double border_y = 0.5d / height;
        double border_x = 0.5d / width;

        size[0][0] = size[0][width] = border_x;
        size[1][0] = size[1][height] = border_y;

        for (int i = 1; i < width; ++i) {
            size[0][i] = 1.0d / width;
        }
        for (int i = 1; i < height; ++i) {
            size[1][i] = 1.0d / height;
        }
        setLayout(new TableLayout(size));

        this.slots = new MineSlot[width][height];
        this.slotCount = width * height;

        for (int i = 0; i < this.columns; ++i) {
            for (int j = 0; j < this.rows; ++j) {
                this.slots[i][j] = new MineSlot(i, j, this);
                add(this.slots[i][j], (i + 1) + ", " + (j + 1));
            }
        }

        for (int i = 0; i < this.columns; ++i) {
            for (int j = 0; j < this.rows; ++j) {

                if (i > 0)
                    this.slots[i][j].neighbors.put(Direction.LEFT, this.slots[i - 1][j]);
                if (i < this.columns - 1)
                    this.slots[i][j].neighbors.put(Direction.RIGHT, this.slots[i + 1][j]);
                if (j > 0)
                    this.slots[i][j].neighbors.put(Direction.UP, this.slots[i][j - 1]);
                if (j < this.rows - 1)
                    this.slots[i][j].neighbors.put(Direction.DOWN, this.slots[i][j + 1]);

            }
        }

        this.borders = new Border[4];
        for (int i = 0; i < 4; ++i) {
            this.borders[i] = BorderFactory.createLineBorder(FLAG_COLORS[i], 3);
        }
        this.buttonBack = new JButton();
        this.buttonBack.setBackground(Reference.BLACK);
        add(this.buttonBack, "0, 0");

        linkButtons();


    }

    private void linkButtons() {

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                panelGame.problemButtonMap.get(problemMineSweeper).setState(1);
                panelGame.setPhase(GamePhase.MENU);
                panelGame.parent.switchPanel(panelGame, panelGame);
                timer2.cancel();
                timer.cancel();
            }
        });

    }

    void start() {

        applyTexture();
        generateMines(-1, -1, this.mines);


        this.timer = new Timer();
        this.timer2 = new Timer();

        for (int i = 0; i < this.columns; ++i) {
            for (int j = 0; j < this.rows; ++j) {
                this.slots[i][j].calculateAdjacentSlots();
                this.slots[i][j].calculateNeighborMines();
                //this.slots[i][j].setText("" + this.slots[i][j].getNeighborMines());
            }
        }

        this.currentTeamNum = 0;
        this.panelGame.teamPanelMap.get(this.teams.get(this.currentTeamNum)).setBackground(CURRENT_TEAM_COLOR);
        this.cursorMap.put(this.teams.get(0), new Point(0, 0));
        this.cursorMap.put(this.teams.get(1), new Point(this.columns - 1, 0));
        this.cursorMap.put(this.teams.get(2), new Point(0, this.rows - 1));
        this.cursorMap.put(this.teams.get(3), new Point(this.columns - 1, this.rows - 1));

        this.ready = true;
        this.locked = false;
        updateDisplay();
        updateSlotDisplayEntirely();

    }

    @Override
    public void react(Team team, ControlKey key) {


        if (this.ready && !this.locked) {

            Team currentTeam = this.teams.get(this.currentTeamNum);
            if (currentTeam.equals(team)) {

                Direction direction = key.toDirection();
                if (direction != null) {

                    Point point = this.cursorMap.get(team);
                    MineSlot nextSlot = this.slots[point.x][point.y].neighbors.get(direction);
                    if(nextSlot != null) {
                        Point newPoint = nextSlot.getPoint();
                        this.cursorMap.put(team, newPoint);
                        updateDisplay();
                        changeSelectedSlotDisplay(this.currentTeamNum, point, newPoint);
                    }

                }
                else if (key == ControlKey.DEL) {
                    Point point = this.cursorMap.get(team);
                    MineSlot s = this.slots[point.x][point.y];
                    if(s.getState() == 0) {
                        if (s.type == 1) {
                            s.setState(-1);
                            //s.setFont(FontRef.getFont("Arial Unicode MS", Font.PLAIN, 10));
                            s.setText("");
                            s.setIconAndResize();
                            s.setBackground(FLAG_COLORS[currentTeamNum]);

                            this.locked = true;
                            addPointsToTeam(50, team);
                            this.timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            toNextTeam();
                                        }
                                    });
                                    locked = false;
                                }
                            }, 1500);
                        }
                        else if (s.type == 0) {
                            popMine(s.x, s.y);
                            this.locked = true;
                            addPointsToTeam(-150, team);
                            this.timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            toNextTeam();
                                        }
                                    });
                                    locked = false;
                                }
                            }, 1500);
                        }
                    }
                }
                else if (key == ControlKey.ENTER) {
                    Point point = this.cursorMap.get(team);
                    MineSlot s = this.slots[point.x][point.y];
                    if (s.getState() == 0) {
                        int points = popMine(point.x, point.y);
                        if (points != 0) {

                            this.locked = true;
                            addPointsToTeam(points, team);

                            this.timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            toNextTeam();
                                        }
                                    });
                                    locked = false;
                                }
                            }, 1500);
                        } else {
                            this.timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            toNextTeam();
                                        }
                                    });
                                    locked = false;
                                }
                            }, 500);
                        }
                    }
                    else {
                        addPointsToTeam(-50, team);
                        this.timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        toNextTeam();
                                    }
                                });
                                locked = false;
                            }
                        }, 1500);
                    }
                }
                System.out.println("slots left: " + this.slotCount);
            }
        }

    }

    private void addPointsToTeam(int points, Team team) {
        PanelTeam panelTeam = this.panelGame.teamPanelMap.get(team);
        if(points >= 0)
            panelTeam.labelScore.setText(panelTeam.labelScore.getText() + " +" + points);
        if(points < 0)
            panelTeam.labelScore.setText(panelTeam.labelScore.getText() + " " + points);
        team.addPoints(points);
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        panelTeam.labelScore.setText("" + team.getScore());
                    }
                });
            }
        }, 1000);
    }

    private void toNextTeam() {
        timer2.cancel();
        timer2 = new Timer();
        Team team = this.teams.get(this.currentTeamNum);
        PanelTeam panelTeam = this.panelGame.teamPanelMap.get(team);
        panelTeam.labelState.setText("");
        panelTeam.setBackground(TextureHolder.getInstance().getColor("team" + (currentTeamNum + 1)));
        currentTeamNum++;
        currentTeamNum %= 4;
        Team nextTeam = teams.get(currentTeamNum);
        PanelTeam panelNextTeam = this.panelGame.teamPanelMap.get(nextTeam);
        panelGame.teamPanelMap.get(nextTeam).setBackground(CURRENT_TEAM_COLOR);

        this.countDown = COUNTDOWN;

        this.timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                countDown--;
                panelNextTeam.labelState.setText(countDown + "");
                if (countDown <= 0) {
                    countDown = COUNTDOWN;
                    react(nextTeam, ControlKey.ENTER);
                    timer2.cancel();
                    timer2 = new Timer();
                }
            }
        }, 1000, 1000);
    }

    private void generateMines(int safeX, int safeY, int mines) {

        if (safeX == -1 && safeY == -1) {
            Set<Point> setPoints = new HashSet<>();
            for (int i = 0; i < this.columns; ++i) {
                for (int j = 0; j < this.rows; ++j) {
                    setPoints.add(new Point(i, j));
                    this.slots[i][j].type = 0; //non-mine
                    this.slots[i][j].setState(0); //default, not popped
                    //this.slots[i][j].setBorder(Reference.BEVEL1);

                }
            }
            Set<Point> points = FuncBox.randomChoice(setPoints, this.random, mines);
            for (Point point : points) {

                this.slots[point.x][point.y].type = 1;
                //this.slots[point.x][point.y].setBackground(Reference.BLAZE);
                //this.slots[point.x][point.y].setBorder(Reference.BEVEL1);
                //System.out.println(point.x + ", " + point.y);

            }


        } else {

        }

    }

    private int popMine(int x, int y) {

        MineSlot s = this.slots[x][y];
        int points = 0;
        if (s.getState() == 0) {
            if (s.unpoppedAndZero()) {

                s.popSelf();
                for (MineSlot slot : s.getAdjacentSlots()) {
                    if (slot.getState() == 0) popMine(slot.x, slot.y);
                }
                points = 10;

            } else if (s.unpoppedAndSafe()) {
                s.popSelf();
                points = 10;
            } else if (s.type == 1) {

                points = -100;
                s.popSelf();
                MediaRef.playSound(MediaRef.EXPLOSION);
            }
        }

        return points;
    }


    @Override
    public void applyTexture() {
        int index = 0;
        for (Team team : this.panelGame.parent.game.teams) {
            PanelTeam panelTeam = this.panelGame.teamPanelMap.get(team);
            panelTeam.labelName.setForeground(FLAG_COLORS[index]);
            panelTeam.labelScore.setForeground(FLAG_COLORS[index]);
            index++;
        }
        updateDisplay();
        System.out.println("updated");


    }

    void bindPanelGame(PanelGame panelGame) {
        this.panelGame = panelGame;
        this.teams = this.panelGame.parent.game.teams;

    }

    private void updateDisplay() {
        if (this.ready) {
            Team team = this.teams.get(this.currentTeamNum);
            this.panelGame.teamPanelMap.get(team).setBackground(CURRENT_TEAM_COLOR);
            Point point = this.cursorMap.get(team);
            System.out.println(point);
            //this.slots[point.x][point.y].setBorder(this.borders[this.currentTeamNum]);
            //changeSelectedSlotDisplay(this.currentTeamNum, );
        }

        for (int i = 0; i < this.columns; ++i) {
            for (int j = 0; j < this.rows; ++j) {
                MineSlot s = this.slots[i][j];

                if (this.panelGame.parent.isFullScreen) {
                    s.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 39));
                } else {
                    s.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 26));
                }

                if (s.hasIcon) {
                    s.setIconAndResize();
                }

            }
        }
    }

    private void changeSelectedSlotDisplay(int teamNum, Point oldPoint, Point newPoint) {
        if (this.ready) {


            this.slots[newPoint.x][newPoint.y].setTopBorder(this.borders[teamNum]);
            this.slots[oldPoint.x][oldPoint.y].removeBorder(this.borders[teamNum]);

        }
    }

    private void updateSlotDisplayEntirely() {
        if(this.ready) {

            for (int i = 0; i < this.columns; ++i) {
                for (int j = 0; j < this.rows; ++j) {
                    this.slots[i][j].removeAllBorders();
                    this.slots[i][j].setBorder(Reference.PLAIN1);
                }
            }

            for (int i = 0; i < 4; ++i) {
                Team team = this.teams.get(i);
                Point point = this.cursorMap.get(team);
                this.slots[point.x][point.y].setTopBorder(this.borders[i]);

            }
        }

    }
}
