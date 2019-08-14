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

public class PanelMatch extends JPanel implements PanelMiniGame {
    private PanelGame panelGame;
    private Problem parentProblem;
    private JLayeredPane layeredPane;

    private JButton buttonBack;
    private JLabel[][] grid = new JLabel[3][8];
    private int[][] card = new int[3][8];
    private Team[] teamOrder = new Team[4];
    private int playing = 0;
    private int nowX = 0, nowY = 0;
    private int pickNum = 0;
    private boolean[][] picked = new boolean[3][8];
    private boolean[][] confirmed = new boolean[3][8];
    private boolean correct = false;
    private int tot = 0;
    private boolean ready = false;
    private static final String FACEDOWN = new String(Character.toChars(0x1F0A0));
    //private static final String FACEDOWN = "#";

    private String idToString(int id) {
        int suit = id / 6;
        int rank = id % 6;
        //return ""+rank;
        return new String(Character.toChars(0x1F000 | ((10 + suit) << 4) | (rank >= 11 ? 2 + rank : 1 + rank)));
    }

    public PanelMatch(Problem problem, PanelGame panelGame) {

        this.parentProblem = problem;
        this.panelGame = panelGame;

        List<Integer> pool = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            pool.add(i);
        }
        Collections.shuffle(pool);
        for (int i = 0; i < 24; i++) {
            card[i/8][i%8] = pool.get(i);
        }

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        double[][] size = {new double[8], new double[3]};
        for (int i = 0; i < 8; ++i) {
            size[0][i] = 1.0/8;
        }
        for (int i = 0; i < 3; ++i) {
            size[1][i] = 1.0/3;
        }

        TableLayout layout = new TableLayout(size);
        layout.setHGap(5);
        layout.setVGap(5);
        setLayout(layout);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                //grid[i][j] = new JLabel(idToString(card[i][j]), SwingConstants.CENTER);
                grid[i][j] = new JLabel(FACEDOWN, SwingConstants.CENTER);
                grid[i][j].setOpaque(true);
                grid[i][j].setBackground(new Color(0, 0, 0, 0));
                grid[i][j].setForeground(new Color(31, 31, 31, 255));
                grid[i][j].setFont(new Font("DejaVu Sans", Font.PLAIN, panelGame.parent.isFullScreen ? 135 : 90));
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
        this.buttonBack.setVisible(false);

        add(buttonBack, "3, 2, 4, 2");

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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if (picked[i][j]) {
                    if (pickNum == 2) {
                        if (correct) grid[i][j].setBackground(new Color(0, 152, 16, 255));
                        else grid[i][j].setBackground(new Color(211, 116, 56, 255));
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

        /*for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j].setText(idToString(card[i][j]));
            }
        }*/
        ready = true;
        recolor();
        repaint();
    }

    @Override
    public void react(Team team, ControlKey key) {
        System.out.println(team.getId());
        System.out.println(teamOrder[playing].getId());
        //System.out.println(ControlKey.KEY_CHARACTER_MAP.get(key));

        if (ready && team.getId() == teamOrder[playing].getId()) {
            if (key == ControlKey.UP) {
                if (nowX-1 >= 0) nowX--;
            }
            else if (key == ControlKey.DOWN) {
                if (nowX+1 < 3) nowX++;
            }
            else if (key == ControlKey.LEFT) {
                if (nowY-1 >= 0) nowY--;
            }
            else if (key == ControlKey.RIGHT) {
                if (nowY+1 < 8) nowY++;
            }
            else if (key == ControlKey.ENTER) {
                if  (!picked[nowX][nowY] && !confirmed[nowX][nowY] && pickNum < 2) {
                    picked[nowX][nowY] = true;
                    pickNum++;
                    grid[nowX][nowY].setText(idToString(card[nowX][nowY]));
                    if (card[nowX][nowY] / 6 == 1 || card[nowX][nowY] / 6 == 2) {
                        grid[nowX][nowY].setForeground(new Color(255, 0, 0, 255));
                    }
                    if (pickNum == 2) {
                        boolean flag = false;
                        int first = -1;
                        int fx = 0, fy = 0;
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 8; j++) {
                                if (picked[i][j]) {
                                    if (flag) {
                                        if (first % 6 == card[i][j] % 6 && first/6 + card[i][j]/6 == 3) {
                                            correct = true;
                                            confirmed[i][j] = confirmed[fx][fy] = true;
                                            PanelTeam panelTeam = panelGame.teamPanelMap.get(teamOrder[playing]);
                                            panelTeam.labelScore.setText(String.valueOf(teamOrder[playing].getScore()) + " + 50");
                                            teamOrder[playing].addPoints(50);
                                            //panelGame.panelBoxes.get(playing).labelScore.repaint();
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
                                for (int i = 0; i < 3; i++) {
                                    for (int j = 0; j < 8; j++) {
                                        picked[i][j] = false;
                                        if (!confirmed[i][j]) {
                                            grid[i][j].setText(FACEDOWN);
                                            grid[i][j].setForeground(new Color(31, 31, 31, 255));
                                        }
                                    }
                                }
                                PanelTeam panelTeam = panelGame.teamPanelMap.get(teamOrder[playing]);
                                panelTeam.labelScore.setText(String.valueOf(teamOrder[playing].getScore()));
                                if (!correct) playing = (playing + 1) % 4;
                                correct = false;
                                if (tot >= 24) {
                                    //System.out.println("WTF???");
                                    buttonBack.setVisible(true);
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
        /*
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j].setFont(new Font("DejaVu Sans", Font.PLAIN, panelGame.parent.isFullScreen ? 135 : 90));
            }
        }
        */
    }

    @Override
    public void refreshRendering(boolean isFullScreen) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j].setFont(FontRef.getFont("DejaVu Sans", Font.PLAIN, panelGame.parent.isFullScreen ? 135 : 90));
            }
        }

    }
}
