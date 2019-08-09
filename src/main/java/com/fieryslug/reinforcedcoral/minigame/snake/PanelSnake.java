package com.fieryslug.reinforcedcoral.minigame.snake;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.Problem;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.minigame.PanelMiniGame;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.widget.Direction;
import com.sun.jna.platform.win32.COM.COMUtils;

import java.awt.Color;
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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import info.clearthought.layout.TableLayout;

public class PanelSnake extends PanelMiniGame {

    private static final int COUNT_DOWN = 4;

    public JButton tempButton;
    public JButton tempButton2;
    private PanelGame panelGame;
    private Random random;
    private Timer timerCountDown;
    private int countDown = COUNT_DOWN;
    private JLabel labelCountDown;
    private JLabel labelGameOver;
    private Timer timerUpdate;

    private int fruitCountDown = 0;
    private ArrayList<FruitGenerator> fruitGenerators;
    private Map<Integer, FruitGenerator> idGeneratorMap;
    private int snakesAlive = 4;

    private LabelSnake[][] pixels;
    private Set<Point> freeSlots;
    private int[][] snakeOccupation;
    private Map<Team, Snake> teamSnakeMap;
    private Map<Snake, Team> snakeTeamMap;
    private Snake[] snakes;
    private Map<Point, Color> colorPlan;
    private Map<Point, String> textPlan;

    private Problem parentProblem;

    private JButton buttonBack;

    private static final Color[] SNAKE_COLORS = {Reference.MAGENTA, Reference.AQUA, Reference.YELLOW, Reference.GREEN};

    public PanelSnake(Problem problem) {

        this.parentProblem = problem;
        this.random = new Random();
        this.pixels = new LabelSnake[50][20];
        this.snakeOccupation = new int[50][20];
        this.teamSnakeMap = new HashMap<>();
        this.snakeTeamMap = new HashMap<>();
        this.timerCountDown = new Timer();//
        this.timerUpdate = new Timer();//
        this.colorPlan = new HashMap<>();
        this.textPlan = new HashMap<>();
        this.fruitGenerators = new ArrayList<>();
        this.idGeneratorMap = new HashMap<>();
        this.freeSlots = new HashSet<>();

        addFruitGenerator(new FruitGenerator(7, 0, 51, -6, Reference.WHITE));
        addFruitGenerator(new FruitGenerator(50, 1, 21, -1, Reference.LIME));
        addFruitGenerator(new FruitGenerator(50, 1, 49, -2, Reference.LIME));
        addFruitGenerator(new FruitGenerator(50, 1, 37, -3, Reference.LIME));
        addFruitGenerator(new FruitGenerator(150, 3, 59, -4, Reference.BROWN));
        addFruitGenerator(new FruitGenerator(500, 10, 119, -5, Reference.RED));

        this.snakes = new Snake[4];

        this.setBackground(Reference.DARKDARKBLUE);

        this.labelCountDown = new JLabel(String.valueOf(countDown), SwingConstants.CENTER);
        this.labelCountDown.setFont(FontRef.MONOSPACED60BOLD);
        this.labelCountDown.setForeground(Reference.WHITE);
        this.labelCountDown.setOpaque(true);
        this.labelCountDown.setBackground(Reference.DARKDARKBLUE);

        this.labelGameOver = new JLabel("", SwingConstants.CENTER);
        this.labelGameOver.setFont(FontRef.MONOSPACED60BOLD);
        this.labelGameOver.setForeground(Reference.WHITE);
        this.labelGameOver.setOpaque(true);
        this.labelGameOver.setBackground(new Color(170, 97, 62, 0));
        double[][] size = {new double[50], new double[20]};
        for (int i = 0; i < 50; ++i) {
            size[0][i] = 0.02;
        }
        for (int i = 0; i < 20; ++i) {
            size[1][i] = 0.05;
        }
        setLayout(new TableLayout(size));

        for (int i = 0; i < 50; ++i) {
            for (int j = 0; j < 20; ++j) {
                this.pixels[i][j] = new LabelSnake();
                add(this.pixels[i][j], i + ", " + j);
                this.freeSlots.add(new Point(i, j));
            }
        }

        this.buttonBack = new JButton();
        this.buttonBack.setText("main menu");
        this.buttonBack.setFont(FontRef.JHENGHEI30);
        this.buttonBack.setForeground(Reference.WHITE);
        this.buttonBack.setBackground(new Color(72, 91, 146, 64));
        this.buttonBack.setFocusPainted(false);
        this.buttonBack.setFocusable(false);
        linkButtons();
        JLabel dummy = new JLabel();
        dummy.setBackground(Reference.BLACK);
        dummy.setOpaque(true);
        add(this.labelCountDown, "0, 0, 49, 19");
        add(dummy, "0, 0");
        //add(tempButton, "0, 0");
        //add(tempButton2, "1, 1");
    }

    public void bindPanelGame(PanelGame panelGame) {
        this.panelGame = panelGame;
    }

    public void linkButtons() {

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                panelGame.setState(0);
                panelGame.setPhase(GamePhase.MENU);
                panelGame.parent.switchPanel(panelGame, panelGame);
                panelGame.problemButtonMap.get(parentProblem).setState(1);

                for (Team team : panelGame.parent.game.teams) {
                    PanelTeam panelTeam = panelGame.teamPanelMap.get(team);
                    panelTeam.labelName.setForeground(Reference.BLAZE);
                    panelTeam.labelScore.setForeground(Reference.BLAZE);
                }

            }
        });

    }

    public void start() {

        //from constructor
        this.timerUpdate = new Timer();
        this.timerCountDown = new Timer();
        remove(this.labelCountDown);
        remove(this.buttonBack);
        remove(this.labelGameOver);

        add(this.labelCountDown, "0, 0, 49, 19");

        this.countDown = COUNT_DOWN;

        for (int i = 0; i < 49; ++i) {
            for (int j = 0; j < 19; ++j) {
                this.pixels[i][j].state = 0;
                this.pixels[i][j].setBackground(Reference.DARKDARKBLUE);
            }
        }




        int tempInt = 0;
        for (Team team : this.panelGame.parent.game.teams) {

            PanelTeam panelTeam = panelGame.teamPanelMap.get(team);
            panelTeam.labelName.setForeground(SNAKE_COLORS[tempInt]);
            panelTeam.labelScore.setForeground(SNAKE_COLORS[tempInt]);

            Direction direction = null;
            if (tempInt % 2 == 0) {
                direction = Direction.RIGHT;
            } else {
                direction = Direction.LEFT;
            }
            Snake snake = new Snake(direction, SNAKE_COLORS[tempInt]);
            this.snakes[tempInt] = snake;
            this.teamSnakeMap.put(team, snake);
            this.snakeTeamMap.put(snake, team);
            Point point = null;
            switch (tempInt) {
                case 0:
                    snake.occupiedSlots.offer(new Point(0, 0));
                    point = new Point(1, 0);
                    snake.occupiedSlots.offer(point);
                    this.pixels[0][0].occupied = true;
                    this.pixels[1][0].occupied = true;
                    snake.head = point;
                    break;
                case 1:
                    snake.occupiedSlots.offer(new Point(49, 0));
                    point = new Point(48, 0);
                    snake.occupiedSlots.offer(point);
                    this.pixels[49][0].occupied = true;
                    this.pixels[48][0].occupied = true;
                    snake.head = point;
                    break;
                case 2:
                    snake.occupiedSlots.offer(new Point(0, 19));
                    point = new Point(1, 19);
                    snake.occupiedSlots.offer(point);
                    this.pixels[0][19].occupied = true;
                    this.pixels[1][19].occupied = true;
                    snake.head = point;
                    break;
                case 3:
                    snake.occupiedSlots.offer(new Point(49, 19));
                    point = new Point(48, 19);
                    snake.occupiedSlots.offer(point);
                    this.pixels[49][19].occupied = true;
                    this.pixels[48][19].occupied = true;
                    snake.head = point;
                    break;
            }

            tempInt++;
        }

        this.timerCountDown.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                countDown--;
                if (countDown == -1) {
                    timerCountDown.cancel();
                    remove(labelCountDown);
                    repaint();
                    startTimerUpdate();

                }
                labelCountDown.setText(String.valueOf(countDown));
            }
        }, 1000, 1000);

    }

    private void startTimerUpdate() {
        this.timerUpdate.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                update();
            }
        }, 500, 500);

    }

    private void update() {

        if(this.snakesAlive > 1) {

            for (int i = 0; i < 4; ++i) {
                Snake snake = this.snakes[i];
                Team team = this.snakeTeamMap.get(snake);
                if (snake.isAlive) {

                    if (snake.secondaryTurn != null) {
                        snake.direction = snake.primaryTurn;
                        snake.primaryTurn = snake.secondaryTurn;
                        snake.secondaryTurn = null;
                    } else if (snake.primaryTurn != null) {
                        snake.direction = snake.primaryTurn;
                        snake.primaryTurn = null;
                    }


                    Point point = snake.head.getNeighbor(snake.direction, 50, 20);

                    if (this.pixels[point.x][point.y].state == 1) {
                        snake.isAlive = false;
                        this.snakesAlive--;
                        this.textPlan.put(snake.head, "X");
                        continue;
                    }
                    if (this.pixels[point.x][point.y].state < 0) {
                        int id = this.pixels[point.x][point.y].state;
                        FruitGenerator generator = this.idGeneratorMap.get(id);
                        snake.fruitEaten += generator.fruitWorth;
                        team.addPoints(generator.points);
                        this.panelGame.teamPanelMap.get(team).labelScore.setText(String.valueOf(team.getScore()));
                        this.pixels[point.x][point.y].state = 0;
                        this.freeSlots.add(point);
                        this.textPlan.put(point, "");
                        generator.active = true;
                    }

                    snake.occupiedSlots.offer(point);
                    snake.head = point;

                    Point pointTail = snake.occupiedSlots.peek();

                    if (snake.fruitEaten == 0) {
                        this.colorPlan.put(pointTail, Reference.DARKDARKBLUE);
                        this.pixels[pointTail.x][pointTail.y].occupied = false;
                        this.pixels[pointTail.x][pointTail.y].state = 0;
                        this.freeSlots.add(pointTail);

                        snake.occupiedSlots.poll();
                    }
                    if (snake.fruitEaten != 0) {
                        snake.fruitEaten--;
                    }
                }
                //System.out.println(snake.getLength());


            }
            for (int i = 0; i < 4; ++i) {
                this.colorPlan.put(this.snakes[i].head, this.snakes[i].color);
                this.pixels[this.snakes[i].head.x][this.snakes[i].head.y].occupied = true;
                this.pixels[this.snakes[i].head.x][this.snakes[i].head.y].state = 1;
                this.freeSlots.remove(this.snakes[i].head);
            }
            this.finishPlans();

            /*
            if (this.fruitCountDown == 0) {
                this.fruitCountDown = 15;

                int x, y;
                do {
                    x = this.random.nextInt(50);
                    y = this.random.nextInt(20);
                } while (this.pixels[x][y].state != 0);

                Point point = new Point(x, y);
                this.colorPlan.put(point, Reference.LIME);
                this.textPlan.put(point, "50");
                this.pixels[x][y].state = -1;


            }
            */
            for (FruitGenerator generator : this.fruitGenerators) {
                if(generator.countDown <= 0) {

                    int x, y;
                    /*
                    do {
                        x = this.random.nextInt(50);
                        y = this.random.nextInt(20);
                    } while (this.pixels[x][y].state != 0);


                    Point point = new Point(x, y);
                    */
                    Point point = FuncBox.<Point>ramdomChoice(this.freeSlots, this.random);
                    this.pixels[point.x][point.y].state = generator.id;
                    this.freeSlots.remove(point);

                    this.colorPlan.put(point, generator.color);
                    this.textPlan.put(point, String.valueOf(generator.points));

                    generator.active = false;
                    generator.countDown = generator.coolDown;
                }
                else if(generator.active)
                    generator.countDown--;


            }

            this.finishPlans();
        }
        else {
            this.timerUpdate.cancel();
            add(this.labelGameOver, "15, 5, 34, 13");
            this.labelGameOver.setText("Game Over");
            add(this.buttonBack, "15, 14, 34, 16");
            this.buttonBack.setText("");
            this.buttonBack.setText("main menu");
        }

    }

    @Override
    public void react(Team team, ControlKey key) {

        Snake snake = this.teamSnakeMap.get(team);
        if (snake != null && snake.isAlive) {
            Direction direction = key.toDirection();
            if (direction != null) {

                if (snake.primaryTurn == null ) {
                    if(!snake.direction.isSimilarTo(direction))
                        snake.primaryTurn = direction;
                }
                else if (snake.secondaryTurn == null && !snake.primaryTurn.isSimilarTo(direction)) {
                    snake.secondaryTurn = direction;
                }
            }
        }
        System.out.println(team);
    }

    private void addFruitGenerator(FruitGenerator generator) {

        if (this.idGeneratorMap.get(generator.id) != null || generator.id >= 0) {
            System.out.println("invalid fruit generator!");
            return;
        }

        this.idGeneratorMap.put(generator.id, generator);
        this.fruitGenerators.add(generator);

    }

    private void finishPlans() {
        for (Point point : this.colorPlan.keySet()) {
            this.pixels[point.x][point.y].setBackground(this.colorPlan.get(point));
        }
        this.colorPlan.clear();
        for (Point point : this.textPlan.keySet()) {
            this.pixels[point.x][point.y].setText(this.textPlan.get(point));
            repaint();
        }
        this.textPlan.clear();
    }
}
