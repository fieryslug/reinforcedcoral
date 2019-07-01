package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.widget.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.ButtonProblem;
import com.fieryslug.reinforcedcoral.widget.Direction;
import com.fieryslug.reinforcedcoral.core.Category;
import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Problem;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PanelGame extends PanelPrime {

    int maxHeight;
    int maxWidth;
    int frameHeight;
    int frameWidth;

    int boxWidth;
    int boxHeight;
    int paneWidth;
    int paneHeight;

    public int state;
    public Problem currentProblem;
    public int currentPageNumber = 0;

    ArrayList<PanelTeam> panelBoxes;
    public JPanel panelInteriorMenu;
    public PanelProblem panelInteriorPage;

    public ArrayList<JLabel> categoryLabels;

    public Map<Problem, ButtonProblem> problemButtonMap;
    public Map<ButtonProblem, Problem> buttonProblemMap;
    public ButtonProblem[][] positionButtonMap;
    public ButtonProblem buttonSelected = null;


    public PanelGame(FrameCoral frame) {

        super(frame);
        this.state = 0;

        setLayout(new FlowLayout(FlowLayout.CENTER));

        initialize();
        linkButtons();

    }

    @Override
    public void initialize() {

        this.panelBoxes = new ArrayList<>();
        this.buttonProblemMap = new HashMap<>();
        this.problemButtonMap = new HashMap<>();
        this.positionButtonMap = new ButtonProblem[4][6];
        this.categoryLabels = new ArrayList<>();

        double size[][] = {{0.25, 0.25, 0.25, 0.25}, {0.1, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15}};
        this.panelInteriorMenu = new JPanel();

        this.panelInteriorMenu.setLayout(new TableLayout(size));
        this.panelInteriorMenu.setBackground(Reference.DARKGRAY);
        this.panelInteriorMenu.setBorder(Reference.BEVEL2);

        this.panelInteriorPage = new PanelProblem(this.parent);

        for(int i=0; i<4; ++i) {
            Team team = this.parent.game.teams.get(i);
            PanelTeam panel = new PanelTeam(team);
            panel.setPreferredSize(new Dimension(this.boxWidth, this.boxHeight));
            this.panelBoxes.add(panel);

        }

        int i=0, j=0;
        for(Category category : this.parent.game.categories) {
            JLabel label = new JLabel("", SwingConstants.CENTER);

            label.setText(category.name);
            label.setFont(Reference.JHENGHEI30);
            label.setForeground(Reference.AQUA);
            label.setBorder(Reference.BEVEL2);
            this.categoryLabels.add(label);


            for(Problem problem : category.problems) {
                ButtonProblem button = new ButtonProblem(MediaRef.PROBLEM, MediaRef.PROBLEM_HOVER, MediaRef.PROBLEM_PRESS);
                button.setImageDisabled(MediaRef.PROBLEM_DISABLED);
                button.setImageSelected(MediaRef.PROBLEM_SELECTED);
                button.setImageDisabledSelected(MediaRef.PROBLEM_DISABLED_SELECTED);
                button.setLayout(new BorderLayout(5, 5));
                JLabel label2 = new JLabel("", SwingConstants.CENTER);
                label2.setFont(Reference.JHENGHEI30);
                label2.setForeground(Reference.WHITE);
                label2.setText("<html><div style='text-align: center;'>" + problem.name + "</div></html>");
                button.add(label2);
                this.buttonProblemMap.put(button, problem);
                this.problemButtonMap.put(problem, button);

                this.positionButtonMap[i][j] = button;
                j += 1;

            }
            i += 1;
            j = 0;
        }

        for(int i1=0; i1<4; ++i1) {
            for(int j1=0; j1<6; ++j1) {

                if(j1 != 0)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.UP, this.positionButtonMap[i1][j1-1]);
                if(j1 != 5)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.DOWN, this.positionButtonMap[i1][j1+1]);
                if(i1 != 0)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.LEFT, this.positionButtonMap[i1-1][j1]);
                if(i1 != 3)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.RIGHT, this.positionButtonMap[i1+1][j1]);

            }

        }

    }

    private void linkButtons() {

        for(Category category : this.parent.game.categories) {

            for (Problem problem : category.problems) {

                ButtonProblem button = this.problemButtonMap.get(problem);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button.setButtonEnabled(false);
                        button.setEnabled(false);

                        PanelGame.this.currentProblem = problem;
                        PanelGame.this.state = 1;
                        PanelGame.this.parent.switchPanel(PanelGame.this, PanelGame.this);

                    }
                });



            }
        }
    }

    @Override
    public void enter() {
        removeAll();
        this.frameWidth = this.parent.getContentPane().getWidth();
        this.frameHeight = this.parent.getContentPane().getHeight();

        this.boxWidth = this.frameWidth / 2 - 10;
        this.boxHeight = this.frameHeight / 5 - 5;
        this.paneWidth = this.frameWidth - 10;
        this.paneHeight = this.frameHeight * 3 / 5 - 5;
        for (int i = 0; i < 4; ++i) {
            PanelTeam panel = this.panelBoxes.get(i);
            Team team = this.parent.game.teams.get(i);
            if(team.hasPrivilege) {
                panel.setBackground(Reference.DARKBLUE);
            }
            else {
                panel.setBackground(Reference.BLACK);
            }
            panel.setPreferredSize(new Dimension(this.boxWidth, this.boxHeight));
        }

        if(this.state == 0) {
            this.currentPageNumber = 0;
            double size[][] = {{0.25, 0.25, 0.25, 0.25}, {0.1, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15}};
            this.panelInteriorMenu.removeAll();
            this.panelInteriorMenu.setLayout(new TableLayout(size));
            this.panelInteriorMenu.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));

            int i=0, j=1;

            for(Category category : this.parent.game.categories) {

                String pos = String.valueOf(i) + ", " + String.valueOf(0);
                this.panelInteriorMenu.add(this.categoryLabels.get(i), pos);

                for(Problem problem : category.problems) {
                    ButtonCoral button = this.problemButtonMap.get(problem);
                    this.panelInteriorMenu.add(button, String.valueOf(i) + ", " + String.valueOf(j));
                    button.resizeImageForIcons((int)(this.paneWidth*0.25), (int)(this.paneHeight*0.15));

                    j += 1;
                }
                i += 1;
                j = 1;

            }

            add(this.panelBoxes.get(0));
            add(this.panelBoxes.get(1));
            add(this.panelInteriorMenu);
            add(this.panelBoxes.get(2));
            add(this.panelBoxes.get(3));
        }

        if(this.state == 1) {
            this.panelInteriorPage.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));

            this.panelInteriorPage.inflate(this.currentProblem.pages.get(this.currentPageNumber));

            add(this.panelBoxes.get(0));
            add(this.panelBoxes.get(1));
            add(this.panelInteriorPage);
            add(this.panelBoxes.get(2));
            add(this.panelBoxes.get(3));
        }
    }

    @Override
    public void react(Team team, ControlKey key) {

        if(this.state == 0) {

            System.out.println("team: " + team.getId());
            System.out.println("button: " + key);
            Direction direction = key.toDirection();

            if(team.hasPrivilege) {

                if (direction != null) {

                    if (this.buttonSelected == null) {
                        this.buttonSelected = this.positionButtonMap[0][0];
                    } else {
                        ButtonCoral button1 = this.buttonSelected.getNeighbor(direction);
                        if (button1 != null) {
                            this.buttonSelected.setButtonSelected(false);
                            this.buttonSelected = (ButtonProblem) button1;
                            System.out.println("playsound");
                            MediaRef.playWav(MediaRef.APPLE1);
                        }
                    }
                    this.buttonSelected.setButtonSelected(true);

                }

                if(key == ControlKey.ENTER) {

                    this.buttonSelected.doClick();

                }
            }
        }
    }
}
