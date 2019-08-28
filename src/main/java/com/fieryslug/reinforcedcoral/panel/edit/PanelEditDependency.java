package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.Category;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.util.layout.ModifiedTableLayout;
import com.fieryslug.reinforcedcoral.widget.button.ButtonColorized;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PanelEditDependency extends PanelInterior {

    private PanelEdit panelEdit;
    private ProblemSet targetSet;
    private Problem currProblem;
    private BiMap<ButtonProblem, Problem> buttonProblemMap;
    private BiMap<JLabel, Category> labelCategoryMap;

    private EditPhase phase;

    private JLabel labelTitle;
    private JLabel labelProbName;
    private JLabel labelDependency;
    private ButtonCoral buttonSave;
    private JLabel labelSave;
    private Map<Problem, Set<Problem>> dependTemp;
    private int dependsCount;

    PanelEditDependency(PanelEdit panelEdit, ProblemSet set, Problem problem) {
        TextureHolder holder = TextureHolder.getInstance();
        this.targetSet = set;
        this.panelEdit = panelEdit;
        this.currProblem = problem;
        this.buttonProblemMap = HashBiMap.create();
        this.labelCategoryMap = HashBiMap.create();
        this.dependTemp = new HashMap<>();

        Image[] images = new Image[]{holder.getImage("button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        this.labelTitle = new JLabel();
        this.labelProbName = new JLabel();

        this.labelDependency = new JLabel("    dependencies", SwingConstants.LEFT);
        this.labelDependency.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelEdit.parent.isFullScreen ? 45 : 30));

        this.buttonSave = new ButtonCoral(images[0], images[1], images[2]);
        this.labelSave = new JLabel("save", SwingConstants.LEFT);

        this.buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                panelEdit.switchSelf();
                panelEdit.panelEditGame.setCurrProblem(problem);
                panelEdit.panelEditGame.inflateEditSlotPanel();
            }
        });
    }

    @Override
    public void enter() {

        double[][] size = new double[][]{FuncBox.createDivisionArray(targetSet.getCategoriesCount()), FuncBox.createDivisionArray(targetSet.getProblemsPerCategory() + 1)};
        setLayout(new ModifiedTableLayout(size));

        labelDependency.setText("   dependencies  (" + currProblem.getDependencies().size() + ")");
        labelTitle.setText("    editting:");
        labelProbName.setText(currProblem.name);

        int i = 0, j = 1;
        for (Category category : this.targetSet.getCategories()) {

            JLabel labelCat = new JLabel("", SwingConstants.CENTER);
            labelCat.setText(category.name);
            labelCat.setOpaque(true);
            this.labelCategoryMap.put(labelCat, category);
            add(labelCat, i + ", " + 0);

            for (Problem problem : category.getProblems()) {

                ButtonProblem button = new ButtonColorized();
                String constraints = i + ", " + j;
                button.setLayout(new BorderLayout(5, 5));
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.setFocusable(true);

                add(button, constraints);
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent actionEvent) {
                        if(problem != currProblem) {
                            if (currProblem.getDependencies().contains(problem)) {
                                currProblem.getDependencies().remove(problem);
                                button.setState(0);
                            } else {
                                currProblem.getDependencies().add(problem);
                                button.setState(-1);
                            }
                            labelDependency.setText("   dependencies  (" + currProblem.getDependencies().size() + ")");
                        }
                    }
                });

                this.buttonProblemMap.put(button, problem);

                JLabel label = new JLabel(problem.name, SwingConstants.CENTER);
                label.setText("<html><div style='text-align: center;'>" + problem.name + "</div></html>");
                label.setText(problem.name);
                label.setOpaque(false);
                button.add(label);
                button.label = label;
                if (this.currProblem == problem) {
                    button.setState(1);
                }
                if (this.currProblem.getDependencies().contains(problem)) {
                    button.setState(-1);
                }

                j++;
            }
            i++;
            j = 1;
        }
        panelEdit.panels[1].add(this.labelDependency, "0, 0, 1, 0");
        panelEdit.panels[0].add(this.buttonSave, "0, 2");
        panelEdit.panels[0].add(this.labelSave, "1, 2");
        panelEdit.panels[0].add(this.labelTitle, "0, 0, 0, 0");
        panelEdit.panels[0].add(this.labelProbName, "1, 0, 3, 0");

        applyTexture(TextureHolder.getInstance());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh(panelEdit.parent.isFullScreen);
            }
        });
    }

    @Override
    public void exit() {
        removeAll();
        panelEdit.panels[0].removeAll();
        panelEdit.panels[1].removeAll();
    }

    @Override
    public void applyTexture(TextureHolder holder) {
        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};
        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.setIcon(null);
            Problem problem = this.buttonProblemMap.get(button);
            if (problem == this.currProblem) {
                button.setBackground(holder.getColor("problem_disabled"));
                button.setBorder(FuncBox.getLineBorder(holder.getColor("problem_disabled_border"), 3));
                button.label.setForeground(holder.getColor("problem_disabled_text"));
            } else if (this.currProblem.getDependencies().contains(problem)) {
                System.out.println(problem.name);
                button.setBackground(holder.getColor("problem_preenabled"));
                button.setBorder(FuncBox.getLineBorder(holder.getColor("problem_preenabled_border"), 3));
                button.label.setForeground(holder.getColor("problem_preenabled_text"));
            } else {
                button.setBackground(holder.getColor("problem"));
                button.setBorder(FuncBox.getLineBorder(holder.getColor("problem_border"), 3));
                button.label.setForeground(holder.getColor("problem_text"));
            }

            //button.refreshRendering();
        }
        for (JLabel label : this.labelCategoryMap.keySet()) {
            label.setBackground(holder.getColor("title"));
            label.setBorder(BorderFactory.createLineBorder(holder.getColor("title_border"), 3));
            label.setForeground(holder.getColor("title_text"));
        }
        this.labelTitle.setForeground(holder.getColor("teamu_text"));
        this.labelProbName.setForeground(holder.getColor("teamu_text"));

        this.labelDependency.setForeground(holder.getColor("teamu_text"));
        this.labelSave.setForeground(holder.getColor("teamu_score"));
        this.buttonSave.setImages(images[0], images[1], images[2]);
    }

    @Override
    public void refresh(boolean isFullScreen) {
        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 39 : 26));
        }
        for (JLabel label : this.labelCategoryMap.keySet()) {
            label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));
        }

        this.labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        this.labelProbName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));

        this.labelDependency.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        this.labelSave.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));

        int buttonX = panelEdit.panels[0].getWidth() / 4;
        int buttonYs = panelEdit.panels[0].getHeight() / 10;

        this.buttonSave.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);

        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.labelProbName);
            FontRef.scaleFont(this.labelDependency);
            FontRef.scaleFont(this.labelSave);
        }
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelEdit;
    }
}
