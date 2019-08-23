package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.Category;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.util.*;
import com.fieryslug.reinforcedcoral.widget.button.ButtonColorized;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeoutException;

public class PanelEditGame extends PanelInterior {

    private PanelEdit panelEdit;
    private BiMap<ButtonProblem, Problem> buttonProblemMap;
    private BiMap<JLabel, Category> labelCategoryMap;

    private JLabel labelTitle;
    private JTextField fieldSetName;


    private ButtonCoral buttonBack;
    private JLabel labelBack;
    private ButtonCoral buttonSave;
    private JLabel labelSave;

    private JLabel labelSlot;
    private JTextField fieldSlot;
    private JLabel[] labelsProb;
    private ButtonCoral[] buttonsProb;


    private ProblemSet targetSet;

    private Problem currProblem = null;
    private Category currCat = null;
    private int currType = 0;
    private EditPhase phase;
    boolean needsNewSet = false;

    public PanelEditGame(PanelEdit panelEdit) {
        TextureHolder holder = TextureHolder.getInstance();

        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        this.buttonProblemMap = HashBiMap.create();
        this.labelCategoryMap = HashBiMap.create();

        this.panelEdit = panelEdit;
        this.labelTitle = new JLabel("  edit mode", SwingConstants.LEFT);
        this.fieldSetName = new JTextField();
        this.fieldSetName.setHorizontalAlignment(SwingConstants.CENTER);

        this.buttonBack = new ButtonCoral(images[0], images[1], images[2]);
        this.labelBack = new JLabel("back", SwingConstants.LEFT);

        this.buttonSave = new ButtonCoral(images[0], images[1], images[2]);
        this.labelSave = new JLabel("save", SwingConstants.LEFT);

        this.labelSlot = new JLabel("  problem:", SwingConstants.LEFT);
        this.fieldSlot = new JTextField();
        this.fieldSlot.setHorizontalAlignment(SwingConstants.CENTER);

        this.labelsProb = new JLabel[4];
        this.buttonsProb = new ButtonCoral[4];
        for (int i = 0; i < 4; ++i) {
            this.labelsProb[i] = new JLabel("", SwingConstants.CENTER);
            this.buttonsProb[i] = new ButtonCoral(images[0], images[1], images[2]);
        }
        this.labelsProb[0].setText(" dependencies");
        this.labelsProb[1].setText("content");

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelEdit.exit();
                panelEdit.currentPanelInterior = panelEdit.panelEditTitle;
                panelEdit.enter();
                panelEdit.refresh();
                panelEdit.repaint();
            }
        });

        this.buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                updateChanges();

                targetSet.saveProblemSet(targetSet.getId(), true);

                DataLoader.getInstance().loadAllProblemSets();
                DataLoader.getInstance().updateProblemSetIndex();

                panelEdit.exit();
                panelEdit.currentPanelInterior = panelEdit.panelEditTitle;
                panelEdit.enter();
                panelEdit.refresh();
                panelEdit.repaint();
            }
        });

        this.buttonsProb[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                PanelEditDependency panel = new PanelEditDependency(panelEdit, targetSet, currProblem);
                panelEdit.exit();
                panelEdit.currentPanelInterior = panel;
                panelEdit.enter();
                panelEdit.refresh();
                panelEdit.repaint();

                /*
                setPhase(EditPhase.DEPENDENCIES);
                removeAll();
                for (int i = 0; i < 4; ++i) {
                    panelEdit.panels[i].removeAll();
                }
                exit();
                enter();
                revalidate();
                repaint();
                panelEdit.revalidate();
                panelEdit.repaint();
                */
            }
        });

        this.fieldSlot.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent keyEvent) {
                String text = fieldSlot.getText();
                System.out.println("action");
                if (currProblem != null) {
                    ButtonProblem button = buttonProblemMap.inverse().get(currProblem);
                    currProblem.name = text;
                    button.label.setText(currProblem.name);
                    if(Preference.autoScaleFontSize) {
                        button.label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.PLAIN, panelEdit.parent.isFullScreen ? 39 : 26));
                        FontRef.scaleFont(button.label);
                    }
                } else if (currCat != null) {
                    JLabel label = labelCategoryMap.inverse().get(currCat);
                    currCat.name = text;
                    label.setText(currCat.name);
                    if (Preference.autoScaleFontSize) {
                        label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, panelEdit.parent.isFullScreen ? 45 : 30));
                        FontRef.scaleFont(label);
                    }
                }
            }
        });
    }

    @Override
    public void enter() {
        TextureHolder holder = TextureHolder.getInstance();

        if (this.phase == EditPhase.MENU) {

            this.buttonProblemMap.clear();
            this.labelCategoryMap.clear();
            setCurrCat(null);
            setCurrProblem(null);

            //Game game = panelEdit.parent.game;
            int ind = panelEdit.currInd;
            if(targetSet == null) {
                ProblemSet set = DataLoader.getInstance().getProblemSets().get(ind);
                this.targetSet = set.copy();
                this.targetSet.setId(set.getId());
            }
            this.needsNewSet = false;

            int cat = targetSet.getCategoriesCount(), probsPerCat = targetSet.getProblemsPerCategory();
            double[][] size = {FuncBox.createDivisionArray(cat), FuncBox.createDivisionArray(probsPerCat + 1)};
            setLayout(new TableLayout(size));

            int i = 0, j = 1;

            for (Category category : this.targetSet.getCategories()) {

                JLabel labelCat = new JLabel("", SwingConstants.CENTER);
                labelCat.setText(category.name);
                labelCat.setOpaque(true);
                this.labelCategoryMap.put(labelCat, category);
                add(labelCat, i + ", " + 0);
                labelCat.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        setCurrCat(category);
                        inflateEditSlotPanel();
                    }
                });

                for (Problem problem : category.problems) {

                    ButtonProblem button = new ButtonColorized();
                    String constraints = i + ", " + j;
                    button.setLayout(new BorderLayout(5, 5));
                    button.setOpaque(true);
                    button.setBorderPainted(true);
                    button.setFocusable(true);

                    add(button, constraints);

                    this.buttonProblemMap.put(button, problem);

                    JLabel label = new JLabel(problem.name, SwingConstants.CENTER);
                    label.setText("<html><div style='text-align: center;'>" + problem.name + "</div></html>");
                    label.setText(problem.name);
                    label.setOpaque(false);
                    button.add(label);
                    button.label = label;

                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            setCurrProblem(problem);
                            inflateEditSlotPanel();
                        }
                    });

                    j++;
                }
                i++;
                j = 1;
            }


            this.fieldSetName.setText(this.targetSet.getName());
            //setCurrProblem(this.targetSet.getCategories().get(0).problems.get(0));

            this.labelSlot.setVisible(false);
            this.fieldSlot.setVisible(false);
            for (int k = 0; k < 4; ++k) {
                this.labelsProb[k].setVisible(false);
                this.buttonsProb[k].setVisible(false);
            }

            panelEdit.panels[0].add(this.labelTitle, "0, 0, 0, 0");
            panelEdit.panels[0].add(this.buttonBack, "0, 2");
            panelEdit.panels[0].add(this.labelBack, "1, 2");
            panelEdit.panels[0].add(this.buttonSave, "2, 2");
            panelEdit.panels[0].add(this.labelSave, "3, 2");
            panelEdit.panels[0].add(this.fieldSetName, "1, 0, 3, 1");

            panelEdit.panels[1].add(labelSlot, "0, 0");
            labelSlot.setText("  problem: ");
            panelEdit.panels[1].add(fieldSlot, "1, 0, 2, 0");

            for (int k = 0; k < 4; ++k) {
                panelEdit.panels[1].add(labelsProb[k], k + ", 1");
                panelEdit.panels[1].add(buttonsProb[k], k + ", 2");
            }

        }
        if (this.phase == EditPhase.DEPENDENCIES) {

            System.out.println(this.currProblem.id);
            int i = 0, j = 1;
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    buttonProblemMap.inverse().get(currProblem).setState(0);
                }
            });

            for (Category cat : this.targetSet.getCategories()) {
                JLabel label = this.labelCategoryMap.inverse().get(cat);
                add(label, i + ", 0");
                for (Problem prob : cat.problems) {
                    ButtonProblem button = this.buttonProblemMap.inverse().get(prob);
                    if (this.currProblem.dependences.contains(prob)) {
                        System.out.println(prob.name);
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                button.setState(-1);
                            }
                        });

                    }
                    add(button, i + ", " + j);
                    j += 1;
                }
                i += 1;
                j = 1;
            }

        }
        System.out.println("in edit menu enter:  width " + panelEdit.getWidth());


        applyTexture(holder);
        refresh(panelEdit.parent.isFullScreen);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh(panelEdit.parent.isFullScreen);
            }
        });

    }

    @Override
    public void exit() {

        for (int i = 0; i < 4; ++i) {
            panelEdit.panels[i].removeAll();
        }
        //this.currProblem = null;
        //this.currCat = null;

    }

    @Override
    public void applyTexture(TextureHolder holder) {

        this.labelTitle.setForeground(holder.getColor("teamu_text"));
        this.fieldSetName.setForeground(holder.getColor("teamu_text"));
        this.fieldSetName.setCaretColor(holder.getColor("teamu_text"));
        this.fieldSetName.setBackground(holder.getColor("teamu"));
        this.fieldSetName.setBorder(FuncBox.getLineBorder(holder.getColor("title"), panelEdit.parent.isFullScreen ? 6 : 4 ));

        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.setIcon(null);
            if(this.buttonProblemMap.get(button) != this.currProblem) {
                button.setBackground(holder.getColor("problem"));
                button.setBorder(FuncBox.getLineBorder(holder.getColor("problem_border"), 3));
                button.label.setForeground(holder.getColor("problem_text"));

            }
            else {
                button.setBackground(holder.getColor("problem_preenabled"));
                button.setBorder(FuncBox.getLineBorder(holder.getColor("problem_preenabled_border"), 3));
                button.label.setForeground(holder.getColor("problem_preenabled_text"));

            }
        }
        for (JLabel label : this.labelCategoryMap.keySet()) {
            if(this.labelCategoryMap.get(label) != this.currCat) {
                label.setBackground(holder.getColor("title"));
                label.setBorder(BorderFactory.createLineBorder(holder.getColor("title_border"), 3));
                label.setForeground(holder.getColor("title_text"));
            }
            else {
                label.setBackground(holder.getColor("title_dark"));
                label.setBorder(BorderFactory.createLineBorder(holder.getColor("title_dark_border"), 3));
                label.setForeground(holder.getColor("title_dark_text"));
            }
        }

        this.labelBack.setForeground(holder.getColor("teamu_score"));
        this.labelSave.setForeground(holder.getColor("teamu_score"));

        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};
        this.buttonBack.setImages(images[0], images[1], images[2]);
        this.buttonSave.setImages(images[0], images[1], images[2]);

        this.labelSlot.setForeground(holder.getColor("teamu_text"));
        this.fieldSlot.setBorder(FuncBox.getLineBorder(holder.getColor("problem"), 3));
        this.fieldSlot.setForeground(holder.getColor("problem_preenabled_text"));
        this.fieldSlot.setBackground(holder.getColor("teamu"));
        this.fieldSlot.setCaretColor(holder.getColor("teamu_score"));
        this.fieldSlot.setBackground(holder.getColor("problem_preenabled"));

        for (int i = 0; i < 4; ++i) {
            this.buttonsProb[i].setImages(images[0], images[1], images[2]);
            this.labelsProb[i].setForeground(holder.getColor("teamu_text"));
        }
    }

    @Override
    public void refresh(boolean isFullScreen) {

        System.out.println("in edit menu refresh: width " + getWidth());
        this.labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        this.fieldSetName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 57 : 38));

        for (JLabel label : this.labelCategoryMap.keySet()) {
            label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));
        }
        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 39 : 26));
        }

        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        this.labelSave.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));

        this.labelSlot.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        this.fieldSlot.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 42 : 28));

        int buttonX = panelEdit.panels[0].getWidth() / 4;
        int buttonYs = panelEdit.panels[0].getHeight() / 10;
        this.buttonBack.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        this.buttonSave.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        for (int i = 0; i < 4; ++i) {
            this.labelsProb[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 33 : 22));
            this.buttonsProb[i].resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        }

        if (Preference.autoScaleFontSize) {
            for (JLabel label : this.labelCategoryMap.keySet()) {
                FontRef.scaleFont(label);
            }
            for (ButtonProblem button : this.buttonProblemMap.keySet()) {
                FontRef.scaleFont(button.label);
            }
            FontRef.scaleFont(this.labelBack);
            FontRef.scaleFont(this.labelSave);
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.fieldSetName);
            FontRef.scaleFont(this.labelSlot);
            FontRef.scaleFont(this.fieldSlot);
            for (int i = 0; i < 4; ++i) {
                FontRef.scaleFont(this.labelsProb[i]);
            }
        }

    }

    private void updateChanges() {

        this.targetSet.setName(this.fieldSetName.getText());

    }

    private void setCurrProblem(Problem problem) {
        this.currType = 0;
        if (this.buttonProblemMap.inverse().keySet().contains(problem)) {

            if(this.currProblem != null)
                this.buttonProblemMap.inverse().get(this.currProblem).setState(0);

            if (this.currCat != null) {
                JLabel label = this.labelCategoryMap.inverse().get(this.currCat);
                TextureHolder holder = TextureHolder.getInstance();
                label.setBackground(holder.getColor("title"));
                label.setBorder(FuncBox.getLineBorder(holder.getColor("title_border"), 3));
                label.setForeground(holder.getColor("title_text"));
                this.currCat = null;
            }
            this.currProblem = problem;
            ButtonProblem button = this.buttonProblemMap.inverse().get(problem);
            button.setState(-1);

        }
    }

    private void setCurrCat(Category cat) {
        this.currType = 1;

        if (this.labelCategoryMap.inverse().keySet().contains(cat)) {

            TextureHolder holder = TextureHolder.getInstance();
            if (this.currCat != null) {
                JLabel label = this.labelCategoryMap.inverse().get(this.currCat);
                label.setBackground(holder.getColor("title"));
                label.setBorder(FuncBox.getLineBorder(holder.getColor("title_border"), 3));
                label.setForeground(holder.getColor("title_text"));
            }
            if (this.currProblem != null) {
                ButtonProblem button = this.buttonProblemMap.inverse().get(this.currProblem);
                this.currProblem = null;
                button.setState(0);
            }

            this.currCat = cat;
            JLabel label1 = this.labelCategoryMap.inverse().get(cat);
            label1.setBackground(holder.getColor("title_dark"));
            label1.setBorder(FuncBox.getLineBorder(holder.getColor("title_dark_border"), 3));
            label1.setForeground(holder.getColor("title_dark_text"));
        }
    }

    private void inflateEditSlotPanel() {

        if (this.currProblem != null) {

            labelSlot.setVisible(true);
            fieldSlot.setVisible(true);
            labelSlot.setText("  problem: ");
            fieldSlot.setText(this.currProblem.name);

            for (int i = 0; i < 4; ++i) {
                this.labelsProb[i].setVisible(true);
                this.buttonsProb[i].setVisible(true);
            }

            if(Preference.autoScaleFontSize) {
                FontRef.scaleFont(this.labelSlot);
                FontRef.scaleFont(this.fieldSlot);
            }

        } else if (this.currCat != null) {
            labelSlot.setVisible(true);
            fieldSlot.setVisible(true);
            labelSlot.setText("  category: ");
            fieldSlot.setText(this.currCat.name);
            for (int i = 0; i < 4; ++i) {
                this.labelsProb[i].setVisible(false);
                this.buttonsProb[i].setVisible(false);
            }
        }
        else {
            labelSlot.setVisible(false);
            fieldSlot.setVisible(false);
            for (int i = 0; i < 4; ++i) {
                this.labelsProb[i].setVisible(false);
                this.buttonsProb[i].setVisible(false);
            }
        }

    }

    void setPhase(EditPhase phase) {
        this.phase = phase;
    }
}
