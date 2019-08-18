package com.fieryslug.reinforcedcoral.minigame.pong;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.minigame.PanelMiniGame;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Reference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;

public class PanelPong extends JPanel implements PanelMiniGame {
    private PanelGame panelGame;
    private Problem parentProblem;
    private JButton buttonBack;
    private long t;
    private int W, H;
    private int[] bPos = new int[2];
    private int[] bVel = new int[2];
    private int[][] pPos = new int[4][2];
    private int[][] pVel = new int[4][2];

    public void bindPanelGame(PanelGame panelGame) {
        this.panelGame = panelGame;
    }

    public PanelPong(Problem problem) {
        this.parentProblem = problem;
        setLayout(new BorderLayout());

        this.buttonBack = new JButton();
        this.buttonBack.setText("main menu");
        this.buttonBack.setFont(FontRef.getFont(FontRef.TAIPEI, Font.PLAIN, 30));
        this.buttonBack.setForeground(Reference.WHITE);
        this.buttonBack.setBackground(new Color(72, 91, 146, 64));
        this.buttonBack.setFocusPainted(false);
        this.buttonBack.setFocusable(false);

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                panelGame.setState(0);
                panelGame.setPhase(GamePhase.MENU);
                panelGame.parent.switchPanel(panelGame, panelGame);
                panelGame.problemButtonMap.get(parentProblem).setState(1);


                /*
                for (Team team : panelGame.parent.game.teams) {
                    PanelTeam panelTeam = panelGame.teamPanelMap.get(team);
                    panelTeam.labelName.setForeground(Reference.BLAZE);
                    panelTeam.labelScore.setForeground(Reference.BLAZE);
                }
                */

            }
        });


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Reference.DARKDARKBLUE);
        g.drawString(String.valueOf(t), 20, 20);
        g.fillRect(0, 0, W, H);
        g.setColor(Reference.WHITE);
        g.fillOval(bPos[0] - 20, bPos[1] - 20, 40, 40);
    }

    @Override
    public void react(Team team, ControlKey key) {
        System.out.println(team.getId());
        System.out.println(ControlKey.KEY_CHARACTER_MAP.get(key));
        bPos[0] = W - bPos[0];
    }

    public void start() {

        W = getWidth();
        H = getHeight();

        bPos[0] = W/2 - 30;
        bPos[1] = H/2;

        Thread loop = new Thread() {
            @Override
            public void run(){
                Instant stt = Instant.now();
                Instant before = Instant.now();
                Instant after = Instant.now();

                while (true) {
                    after = Instant.now();
                    long delta = Duration.between(before, after).toMillis();
                    long frstt = Duration.between(stt, after).toMillis();
                    if (delta > 1000 / 60) {
                        t = frstt;
                        if (frstt >= 300000) {
                            break;
                        }
                        repaint();
                        //System.out.println("V"+isVisible());
                        //System.out.println("W"+getWidth());
                        //System.out.println("H"+getHeight());
                    }
                }
                add(buttonBack, BorderLayout.SOUTH);
                buttonBack.setText("");
                buttonBack.setText("main menu");
            }
        };
        repaint();
        loop.start();
    }

    @Override
    public void applyTexture() {

    }

    @Override
    public void refreshRendering(boolean isFullScreen) {

    }
}