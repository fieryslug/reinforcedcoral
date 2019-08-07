package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.util.FontRef;
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
//import layout.TableLayout;
import info.clearthought.layout.TableLayout;
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
    public int prevState;
    public Problem currentProblem;
    public int currentPageNumber = 0;

    ArrayList<PanelTeam> panelBoxes;
    public JPanel panelInteriorMenu;
    public PanelProblem panelInteriorPage;
    public ButtonCoral buttonNext;
    public ButtonCoral buttonPrev;
    public ButtonCoral buttonConfirm;

    public ArrayList<JLabel> categoryLabels;

    public Map<Problem, ButtonProblem> problemButtonMap;
    public Map<ButtonProblem, Problem> buttonProblemMap;

    public ButtonProblem[][] positionButtonMap;
    public ButtonProblem buttonSelected = null;
    public Map<Team, Integer> teamIndexMap;
    public Map<Team, PanelTeam> teamPanelMap;

    public Map<Team, ArrayList<ControlKey>> teamKeys;
    public Map<Team, Boolean> teamLockedMap;
    public Map<Team, Integer> teamTempScoreMap;

    public static final int MAX_KEYS = 1;

    private double[][] layoutSize;

    public PanelGame(FrameCoral frame) {

        super(frame);
        this.state = 0; //0: menu, 1: problem, 2: answer, 3: post-answer
        this.prevState = 0;
        this.layoutSize = new double[][]{{0.166, 0.166, 0.166, 0.166, 0.166, 0.166}, {0.2, 0.2, 0.2, 0.1, 0.1, 0.2}};

        setLayout(new FlowLayout(FlowLayout.CENTER));

        initialize();
        linkButtons();

    }

    @Override
    public void initialize() {
        setLayout(new TableLayout(this.layoutSize));

        this.panelBoxes = new ArrayList<>();
        this.buttonProblemMap = new HashMap<>();
        this.problemButtonMap = new HashMap<>();
        this.positionButtonMap = new ButtonProblem[4][6];
        this.categoryLabels = new ArrayList<>();
        this.teamIndexMap = new HashMap<>();
        this.teamKeys = new HashMap<>();
        this.teamPanelMap = new HashMap<>();
        this.teamLockedMap = new HashMap<>();
        this.teamTempScoreMap = new HashMap<>();

        double size[][] = {{0.25, 0.25, 0.25, 0.25}, {0.1, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15}};
        this.panelInteriorMenu = new JPanel();

        this.panelInteriorMenu.setLayout(new TableLayout(size));
        this.panelInteriorMenu.setBackground(Reference.DARKGRAY);
        this.panelInteriorMenu.setBorder(Reference.BEVEL2);

        this.panelInteriorPage = new PanelProblem(this.parent);

        this.buttonNext = new ButtonCoral(MediaRef.ADD, MediaRef.ADD_HOVER, MediaRef.ADD_PRESS);
        this.buttonPrev = new ButtonCoral(MediaRef.ADD, MediaRef.ADD_HOVER, MediaRef.ADD_PRESS);
        this.buttonConfirm = new ButtonCoral(MediaRef.ADD, MediaRef.ADD_HOVER, MediaRef.ADD_PRESS);

        for (int i = 0; i < 4; ++i) {
            Team team = this.parent.game.teams.get(i);
            teamIndexMap.put(team, i);
            PanelTeam panel = new PanelTeam(team);
            //panel.setPreferredSize(new Dimension(this.boxWidth, this.boxHeight));
            this.panelBoxes.add(panel);
            this.teamPanelMap.put(team, panel);
        }

        int i = 0, j = 0;
        for (Category category : this.parent.game.categories) {
            JLabel label = new JLabel("", SwingConstants.CENTER);

            label.setText(category.name);
            label.setFont(FontRef.JHENGHEI30);
            label.setForeground(Reference.AQUA);
            label.setBorder(Reference.BEVEL2);
            this.categoryLabels.add(label);


            for (Problem problem : category.problems) {
                ButtonProblem button = new ButtonProblem(MediaRef.PROBLEM, MediaRef.PROBLEM_HOVER, MediaRef.PROBLEM_PRESS);
                button.setImageDisabled(MediaRef.PROBLEM_DISABLED);
                button.setImageSelected(MediaRef.PROBLEM_SELECTED);
                button.setImageDisabledSelected(MediaRef.PROBLEM_DISABLED_SELECTED);
                button.setImagePreenabled(MediaRef.PROBLEM_PREENABLED);
                button.setImagePreenabledSelected(MediaRef.PROBLEM_PREENABLED_SELECTED);
                button.setLayout(new BorderLayout(5, 5));

                JLabel label2 = new JLabel("", SwingConstants.CENTER);
                label2.setFont(FontRef.JHENGHEI30);
                label2.setForeground(Reference.WHITE);
                label2.setText("<html><div style='text-align: center;'>" + problem.name + "</div></html>");
                button.add(label2);
                button.label = label2;
                this.buttonProblemMap.put(button, problem);
                this.problemButtonMap.put(problem, button);

                this.positionButtonMap[i][j] = button;
                //if(!this.dependencesSatisfied(button)) button.setState(-1);
                j += 1;

            }
            i += 1;
            j = 0;
        }
        for (Category category : this.parent.game.categories) {
            for (Problem problem : category.problems) {
                ButtonProblem button = this.problemButtonMap.get(problem);
                if(!this.dependencesSatisfied(button)) button.setState(-1);
            }
        }

        for (int i1 = 0; i1 < 4; ++i1) {
            for (int j1 = 0; j1 < 6; ++j1) {

                if (j1 != 0)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.UP, this.positionButtonMap[i1][j1 - 1]);
                if (j1 != 5)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.DOWN, this.positionButtonMap[i1][j1 + 1]);
                if (i1 != 0)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.LEFT, this.positionButtonMap[i1 - 1][j1]);
                if (i1 != 3)
                    this.positionButtonMap[i1][j1].setNeighbor(Direction.RIGHT, this.positionButtonMap[i1 + 1][j1]);

            }

        }

    }

    private void linkButtons() {

        this.buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (PanelGame.this.state == 1) {
                    if (currentPageNumber + 1 < currentProblem.pages.size()) {
                        int nextpage = PanelGame.this.currentPageNumber + 1;
                        if (PanelGame.this.currentProblem.pages.get(nextpage).isFinal()) {
                            PanelGame.this.setState(2);
                        }
                        PanelGame.this.currentPageNumber = nextpage;
                        PanelGame.this.parent.switchPanel(PanelGame.this, PanelGame.this);

                    }
                }
            }
        });

        this.buttonPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (PanelGame.this.state == 1) {

                    if (currentPageNumber == 0) {
                        System.out.println("switched");
                        PanelGame.this.setState(0);
                        PanelGame.this.currentProblem = null;
                        PanelGame.this.currentPageNumber = 0;
                        PanelGame.this.parent.switchPanel(PanelGame.this, PanelGame.this);
                    } else {
                        PanelGame.this.currentPageNumber--;
                        PanelGame.this.parent.switchPanel(PanelGame.this, PanelGame.this);
                    }

                }

            }
        });

        this.buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(state == 2 ) {
                    for (Team team : PanelGame.this.parent.game.teams) {
                        System.out.println(teamKeys.get(team));
                        int points = currentProblem.getPoints(teamKeys.get(team));
                        System.out.println(team.getId() + ": " + points);
                        teamTempScoreMap.put(team, points);
                    }
                    PanelGame.this.setState(3);
                }
                else if(state == 3) {
                    ButtonProblem buttonProblem = problemButtonMap.get(currentProblem);
                    buttonProblem.setState(1);
                    setState(0);
                }
                parent.switchPanel(PanelGame.this, PanelGame.this);
            }
        });

        for (Category category : this.parent.game.categories) {

            for (Problem problem : category.problems) {

                ButtonProblem button = this.problemButtonMap.get(problem);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //button.setState(1);
                        if(!problem.onClick(PanelGame.this)) {
                            PanelGame.this.currentProblem = problem;
                            PanelGame.this.setState(1);
                            PanelGame.this.parent.switchPanel(PanelGame.this, PanelGame.this);
                        }

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

        if (this.state != this.prevState) reset();

        this.teamKeys.clear();
        for (int i = 0; i < 4; ++i) {
            Team team = this.parent.game.teams.get(i);
            PanelTeam panelTeam = this.panelBoxes.get(i);
            this.teamKeys.put(team, new ArrayList<>());
            this.teamLockedMap.put(team, false);
            panelTeam.setForeground(Reference.WHITE);
            panelTeam.labelName.setText("第" + team.getId() + "小隊  ");

            /*
            if(this.parent.isFullScreen) {
                panelTeam.labelName.setFont(FontRef.TAIPEI40);
            }
            else {
                panelTeam.labelName.setFont(FontRef.TAIPEI30);
            }
            */

            panelTeam.labelScore.setText(String.valueOf(team.getScore()));
            //panelTeam.enter(this.parent.isFullScreen);

            //System.out.println(team.getId() + ": " + team.getScore());

        }

        this.boxWidth = this.frameWidth / 2 - 10;
        this.boxHeight = this.frameHeight / 5 - 5;
        this.paneWidth = this.frameWidth - 10;
        this.paneHeight = this.frameHeight * 3 / 5 - 5;

        for (int i = 0; i < 4; ++i) {
            PanelTeam panel = this.panelBoxes.get(i);
            Team team = this.parent.game.teams.get(i);
            if (team.hasPrivilege) {
                panel.setBackground(Reference.DARKBLUE);
            } else {
                panel.setBackground(Reference.BLACK);
            }
            //panel.setPreferredSize(new Dimension(this.boxWidth, this.boxHeight));
        }

        if (this.state == 0) {

            for (Team team : this.parent.game.teams) {
                this.teamPanelMap.get(team).labelState.setText("");
            }

            this.currentPageNumber = 0;
            double size[][] = {{0.25, 0.25, 0.25, 0.25}, {0.1, 0.15, 0.15, 0.15, 0.15, 0.15, 0.15}};
            this.panelInteriorMenu.removeAll();
            this.panelInteriorMenu.setLayout(new TableLayout(size));
            //this.panelInteriorMenu.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));

            int i = 0, j = 1;

            for (Category category : this.parent.game.categories) {

                String pos = String.valueOf(i) + ", " + String.valueOf(0);
                JLabel labelCategory = this.categoryLabels.get(i);
                /*
                if(this.parent.isFullScreen)
                    labelCategory.setFont(FontRef.TAIPEI45);
                else
                    labelCategory.setFont(FontRef.TAIPEI35);
                */
                this.panelInteriorMenu.add(labelCategory, pos);

                for (Problem problem : category.problems) {
                    ButtonProblem button = this.problemButtonMap.get(problem);

                    this.panelInteriorMenu.add(button, String.valueOf(i) + ", " + String.valueOf(j));
                    //button.resizeImageForIcons((int) (this.paneWidth * 0.25), (int) (this.paneHeight * 0.15));

                    if (button.state == -1) {
                        if(dependencesSatisfied(button)) {
                            button.setState(0);
                            button.playAnimation();
                        }
                    }

                    /*
                    if(this.parent.isFullScreen)
                        button.label.setFont(FontRef.TAIPEI40);
                    else
                        button.label.setFont(FontRef.TAIPEI30);
                    */

                    j += 1;
                }
                i += 1;
                j = 1;
            }


            add(this.panelBoxes.get(0), "0, 0, 2, 0");
            add(this.panelBoxes.get(1), "3, 0, 5, 0");
            add(this.panelInteriorMenu, "0, 1, 5, 4");
            add(this.panelBoxes.get(2), "0, 5, 2, 5");
            add(this.panelBoxes.get(3), "3, 5, 5, 5");


        }
        int buttonX = this.paneHeight / 8, buttonY = this.paneHeight / 8;
        if (this.state == 1) {

            //this.panelInteriorPage.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight * 5 / 6));
            //this.buttonNext.setPreferredSize(new Dimension(buttonX, buttonY));
            //this.buttonNext.resizeImageForIcons(buttonX, buttonY);
            //this.buttonPrev.setPreferredSize(new Dimension(buttonX, buttonY));
            //this.buttonPrev.resizeImageForIcons(buttonX, buttonY);

            this.panelInteriorPage.inflate2(this.currentProblem.pages.get(this.currentPageNumber));
            //this.panelInteriorPage.changeFonts(this.parent.isFullScreen);

            add(this.panelBoxes.get(0), "0, 0, 2, 0");
            add(this.panelBoxes.get(1), "3, 0, 5, 0");
            //add(FuncBox.blankLabel(2000, 2), "0, 1, 1, 3");
            add(this.panelInteriorPage, "0, 1, 5, 3");
            add(this.buttonPrev, "0, 4");
            add(this.buttonNext, "5, 4");
            //add(FuncBox.blankLabel(2000, 2));
            add(this.panelBoxes.get(2), "0, 5, 2, 5");
            add(this.panelBoxes.get(3), "3, 5, 5, 5");

        }

        if (this.state == 2) {

            for (Team team : this.parent.game.teams) {
                this.teamLockedMap.put(team, false);
            }
            this.buttonConfirm.resizeImageForIcons(buttonX, buttonY);
            this.panelInteriorPage.inflate2(this.currentProblem.pages.get(this.currentPageNumber));
            this.panelInteriorPage.changeFonts(this.parent.isFullScreen);
            add(this.panelBoxes.get(0), "0, 0, 2, 0");
            add(this.panelBoxes.get(1), "3, 0, 5, 0");
            //add(FuncBox.blankLabel(2000, 2));
            add(this.panelInteriorPage, "0, 1, 5, 3");
            add(this.buttonConfirm, "2, 4, 3, 4");
            //add(FuncBox.blankLabel(2000, 2));
            add(this.panelBoxes.get(2), "0, 5, 2, 5");
            add(this.panelBoxes.get(3), "3, 5, 5, 5");
        }
        if (this.state == 3) {

            for (Team team : this.parent.game.teams) {
                this.teamLockedMap.put(team, false);
                PanelTeam panel = this.teamPanelMap.get(team);
                int tempScore = this.teamTempScoreMap.get(team);
                if(tempScore > 0) {
                    panel.labelScore.setText("<html>" + team.getScore() + "<font color=lime> +" + tempScore + "</font>" + "</html>");
                    panel.labelState.setForeground(Reference.LIME);
                    this.parent.game.setPrivilegeTeam(team);
                }
                else {
                    panel.labelState.setForeground(Reference.RED);
                }
                team.addPoints(tempScore);
                this.teamTempScoreMap.put(team, 0);
            }

            this.panelInteriorPage.inflate2(this.currentProblem.pageSolution);
            //this.panelInteriorPage.changeFonts(this.parent.isFullScreen);
            add(this.panelBoxes.get(0), "0, 0, 2, 0");
            add(this.panelBoxes.get(1), "3, 0, 5, 0");
            //add(FuncBox.blankLabel(2000, 2));
            add(this.panelInteriorPage, "0, 1, 5, 3");
            add(this.buttonConfirm, "2, 4, 3, 4");
            //add(FuncBox.blankLabel(2000, 2));
            add(this.panelBoxes.get(2), "0, 5, 2, 5");
            add(this.panelBoxes.get(3), "3, 5, 5, 5");
        }
        this.refresh();
    }

    @Override
    public void refresh() {
        this.frameWidth = this.parent.getContentPane().getWidth();
        this.frameHeight = this.parent.getContentPane().getHeight();
        this.boxWidth = this.frameWidth / 2 - 10;
        this.boxHeight = this.frameHeight / 5 - 5;
        this.paneWidth = this.frameWidth - 10;
        if(this.state == 0)
            this.paneHeight = this.frameHeight * 3 / 5 - 5;
        else
            this.paneHeight = this.frameHeight * 3 / 6 - 5;
        int buttonX = this.paneHeight / 8, buttonY = this.paneHeight / 8;
        for (Team team : this.parent.game.teams) {
            PanelTeam panelTeam = this.teamPanelMap.get(team);
            panelTeam.refreshFontSize(this.parent.isFullScreen);
        }
        if (this.state == 0) {
            int i = 0, j = 1;
            for (Category category : this.parent.game.categories) {
                JLabel labelCategory = this.categoryLabels.get(i);
                if (this.parent.isFullScreen) {
                    labelCategory.setFont(FontRef.TAIPEI45);
                } else {
                    labelCategory.setFont(FontRef.TAIPEI35);
                }
                for (Problem problem : category.problems) {
                    ButtonProblem button = this.problemButtonMap.get(problem);
                    if (this.parent.isFullScreen) {
                        button.label.setFont(FontRef.TAIPEI40);
                    } else {
                        button.label.setFont(FontRef.TAIPEI30);
                    }
                    button.resizeImageForIcons((int)(this.paneWidth * 0.25), (int)(this.paneHeight * 0.15));
                    j += 1;
                }
                i += 1;
                j = 0;
            }
        }
        if (this.state == 1) {
            this.panelInteriorPage.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));
            this.buttonNext.resizeImageForIcons(buttonX, buttonY);
            this.buttonPrev.resizeImageForIcons(buttonX, buttonY);
            this.panelInteriorPage.changeFonts(this.parent.isFullScreen);
        }
        if (this.state == 2) {
            this.panelInteriorPage.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));
            this.buttonConfirm.resizeImageForIcons(buttonX, buttonY);
            this.panelInteriorPage.changeFonts(this.parent.isFullScreen);
        }
        if (this.state == 3) {
            this.panelInteriorPage.setPreferredSize(new Dimension(this.paneWidth, this.paneHeight));
            this.buttonConfirm.resizeImageForIcons(buttonX, buttonY);
            this.panelInteriorPage.changeFonts(this.parent.isFullScreen);
        }
    }

    private void reset() {

        for (int i = 0; i < 4; ++i) {
            Team team = this.parent.game.teams.get(i);
            PanelTeam panelTeam = this.panelBoxes.get(i);
            this.teamKeys.put(team, new ArrayList<>());
            this.teamLockedMap.put(team, false);
            //this.teamTempScoreMap.put(team, 0);

            panelTeam.labelState.setForeground(Reference.WHITE);

            panelTeam.labelScore.setText(String.valueOf(team.getScore()));
            panelTeam.enter(this.parent.isFullScreen);


            //System.out.println(team.getId() + ": " + team.getScore());

        }

    }


    @Override
    public void react(Team team, ControlKey key) {

        if (this.state == 0) {

            System.out.println("team: " + team.getId());
            System.out.println("button: " + key);
            Direction direction = key.toDirection();

            if (team.hasPrivilege) {

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

                if (key == ControlKey.ENTER) {

                    this.buttonSelected.doClick();

                }
            }
        }

        if (this.state == 2) {
            if (!(this.teamLockedMap.get(team))) {
                ArrayList<ControlKey> keys = this.teamKeys.get(team);
                if (key.equals(ControlKey.ENTER)) {
                    boolean flag = true;
                    if(keys.isEmpty()) flag = false;
                    for (int i = 0; i < 4; ++i) {
                        Team team1 = this.parent.game.teams.get(i);
                        if (this.teamLockedMap.get(team1) && !team1.equals(team)) {
                            if (this.teamKeys.get(team1).equals(this.teamKeys.get(team))) {
                                flag = false;
                            }
                        }
                    }
                    if (flag) {
                        MediaRef.playWav(MediaRef.SUPERCELL);
                        this.teamLockedMap.put(team, true);
                        this.teamPanelMap.get(team).labelState.setForeground(Reference.AQUA);
                    }
                }
                else if (key.equals(ControlKey.DEL)) {
                    keys.remove(keys.size() - 1);
                }
                else if (this.teamKeys.get(team).size() < MAX_KEYS) {
                        keys.add(key);
                }
                    String s = ControlKey.stringRepresentation(keys);
                    this.teamPanelMap.get(team).labelState.setText(s);
            }
        }
    }

    private void setState(int newState) {

        this.prevState = this.state;
        this.state = newState;

    }

    private boolean dependencesSatisfied(ButtonProblem button) {

        Problem problem = this.buttonProblemMap.get(button);
        boolean flag = true;
        for (Problem problem1 : problem.dependences) {

            if (this.problemButtonMap.get(problem1).state != 1) {
                flag = false;
            }

        }
        return flag;
    }
}
