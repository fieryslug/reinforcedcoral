package com.fieryslug.reinforcedcoral.minigame.match;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.Problem;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.minigame.PanelMiniGame;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.panel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PanelMatch extends PanelMiniGame {
    private PanelGame panelGame;
    private Problem parentProblem;
    private JLayeredPane layeredPane;

    private JButton buttonBack;
    private JLabel[][] grid = new JLabel[4][13];
    private int[][] card = new int[4][13];
    private Team[] teamOrder = new Team[4];
    private int playing = 0;
    private int nowX = 0, nowY = 0;
    private int pickNum = 0;
    private boolean[][] picked = new boolean[4][13];
    private boolean[][] confirmed = new boolean[4][13];
    private boolean correct = false;
    private int tot = 0;
    private static final String FACEDOWN = new String(Character.toChars(0x1F0A0));

    public void bindPanelGame(PanelGame panelGame) {
        this.panelGame = panelGame;
    }

    private String idToString(int id) {
        int suit = id / 13;
        int rank = id % 13;
        return new String(Character.toChars(0x1F000 | ((10 + suit) << 4) | (rank >= 11 ? 2 + rank : 1 + rank)));
    }

    public PanelMatch(Problem problem) {

        this.parentProblem = problem;

        List<Integer> pool = new ArrayList<>();
        for (int i = 0; i < 52; i++) {
            pool.add(i);
        }
        Collections.shuffle(pool);
        for (int i = 0; i < 52; i++) {
            card[i/13][i%13] = pool.get(i);
        }

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        double[][] size = {new double[13], new double[4]};
        for (int i = 0; i < 13; ++i) {
            size[0][i] = 1.0/13;
        }
        for (int i = 0; i < 4; ++i) {
            size[1][i] = 1.0/4;
        }

        TableLayout layout = new TableLayout(size);
        layout.setHGap(5);
        layout.setVGap(5);
        setLayout(layout);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                //grid[i][j] = new JLabel(idToString(card[i][j]), SwingConstants.CENTER);
                grid[i][j] = new JLabel(FACEDOWN, SwingConstants.CENTER);
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(new Color(0, 0, 0, 0));
                grid[i][j].setFont(new Font("Microsoft JhengHei", Font.PLAIN, 70));
                //grid[i][j].setText(new String(Character.toChars(0x1F0A0)));
                add(grid[i][j], j + ", " + i);
                //layeredPane.setLayer(grid[i][j], JLayeredPane.DEFAULT_LAYER);
            }
        }

        this.buttonBack = new JButton();
        this.buttonBack.setText("main menu");
        this.buttonBack.setFont(FontRef.JHENGHEI30);
        this.buttonBack.setForeground(Reference.WHITE);
        this.buttonBack.setBackground(new Color(72, 91, 146, 167));
        this.buttonBack.setFocusPainted(false);
        this.buttonBack.setFocusable(false);

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelGame.setState(0);
                panelGame.setPhase(GamePhase.MENU);
                panelGame.parent.switchPanel(panelGame, panelGame);
                panelGame.problemButtonMap.get(parentProblem).setState(1);
            }
        });
    }

    private void recolor() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                if (picked[i][j]) {
                    if (pickNum == 2) {
                        if (correct) grid[i][j].setBackground(new Color(0, 152, 16, 255));
                        else grid[i][j].setBackground(new Color(211, 58, 33, 255));
                    }
                    else grid[i][j].setBackground(new Color(112, 89, 180, 255));
                }
                else {
                    grid[i][j].setBackground(new Color(0, 0, 0, 0));
                }
            }
        }
        grid[nowX][nowY].setBackground(new Color(189, 170, 47, 255));
        for (int i = 0; i < 4; i++) {
            if (i == playing) {
                panelGame.teamPanelMap.get(teamOrder[i]).setBackground(TextureHolder.getInstance().getColor("team_privilege"));
            }
            else {
                panelGame.teamPanelMap.get(teamOrder[i]).setBackground(TextureHolder.getInstance().getColor("team"+(i+1)));
            }
            //panelGame.teamPanelMap.get(team).setBackground(TextureHolder.getInstance().getColor("team"+(tmp+1)));
        }
    }

    public void start() {
        int tmp = 0;
        for (Team team : this.panelGame.parent.game.teams) {
            teamOrder[tmp] = team;
            if (team.hasPrivilege) {
                playing = tmp;
            }
            tmp++;
            //panelGame.teamPanelMap.get(team).setBackground(TextureHolder.getInstance().getColor("team"+(tmp+1)));
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                grid[i][j].setText(idToString(card[i][j]));
            }
        }

        recolor();
        repaint();
    }

    @Override
    public void react(Team team, ControlKey key) {
        System.out.println(team.getId());
        System.out.println(teamOrder[playing].getId());
        //System.out.println(ControlKey.KEY_CHARACTER_MAP.get(key));

        if (team.getId() == teamOrder[playing].getId() || true) {
            if (key == ControlKey.UP) {
                if (nowX-1 >= 0) nowX--;
            }
            else if (key == ControlKey.DOWN) {
                if (nowX+1 < 4) nowX++;
            }
            else if (key == ControlKey.LEFT) {
                if (nowY-1 >= 0) nowY--;
            }
            else if (key == ControlKey.RIGHT) {
                if (nowY+1 < 13) nowY++;
            }
            else if (key == ControlKey.ENTER) {
                if  (!picked[nowX][nowY] && !confirmed[nowX][nowY] && pickNum < 2) {
                    picked[nowX][nowY] = true;
                    pickNum++;
                    grid[nowX][nowY].setText(idToString(card[nowX][nowY]));
                    if (pickNum == 2) {
                        boolean flag = false;
                        int first = -1;
                        int fx = 0, fy = 0;
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 13; j++) {
                                if (picked[i][j]) {
                                    if (flag) {
                                        if (first % 13 == card[i][j] % 13 && first/13 + card[i][j]/13 == 3) {
                                            correct = true;
                                            confirmed[i][j] = confirmed[fx][fy] = true;
                                            teamOrder[playing].addPoints(20);
                                            //panelGame.panelBoxes.get(playing).labelScore.repaint();
                                            PanelTeam panelTeam = panelGame.teamPanelMap.get(teamOrder[playing]);
                                            panelTeam.labelScore.setText(String.valueOf(teamOrder[playing].getScore()));
                                            //panelTeam.repaint();
                                            tot += 2;
                                        }
                                    }
                                    else {
                                        first = card[i][j];
                                        fx = i;
                                        fy = j;
                                        flag = true;
                                    }
                                }
                            }
                        }
                        recolor();
                        repaint();
                        Timer timer = new Timer(correct ? 3000 : 5000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent arg0) {
                                pickNum = 0;
                                for (int i = 0; i < 4; i++) {
                                    for (int j = 0; j < 13; j++) {
                                        picked[i][j] = false;
                                        if (!confirmed[i][j]) {
                                            grid[i][j].setText(FACEDOWN);
                                        }
                                    }
                                }
                                if (!correct) playing = (playing + 1) % 4;
                                correct = false;
                                if (tot >= 2) {
                                    System.out.println("WTF???");
                                    add(buttonBack, "5, 3, 7, 3");
                                    //layeredPane.setLayer(buttonBack, JLayeredPane.PALETTE_LAYER);
                                    //setComponentZOrder(buttonBack, 52);
                                }
                                recolor();
                                repaint();
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                        return;
                    }
                }
            }
            recolor();
            repaint();
        }
    }

    @Override
    public void applyTexture() {
        recolor();
    }
}
