package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.Category;
import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonColorized;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.*;

public class PanelEditGame extends PanelInterior {

    private PanelEdit panelEdit;
    private BiMap<ButtonProblem, Problem> buttonProblemMap;
    private BiMap<JLabel, Category> labelCategoryMap;

    private JLabel labelTitle;


    private ButtonCoral buttonBack;
    private JLabel labelBack;
    private ButtonCoral buttonSave;
    private JLabel labelSave;

    private ProblemSet currProblemSet;

    public PanelEditGame(PanelEdit panelEdit) {
        TextureHolder holder = TextureHolder.getInstance();
        this.buttonProblemMap = HashBiMap.create();
        this.labelCategoryMap = HashBiMap.create();

        this.panelEdit = panelEdit;
        this.labelTitle = new JLabel("  edit mode", SwingConstants.LEFT);
        this.buttonBack = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.labelBack = new JLabel("back", SwingConstants.LEFT);
        this.buttonSave = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.labelSave = new JLabel("save", SwingConstants.LEFT);
    }

    @Override
    public void enter() {
        TextureHolder holder = TextureHolder.getInstance();

        this.buttonProblemMap.clear();
        this.labelCategoryMap.clear();

        Game game = panelEdit.parent.game;
        ProblemSet set = game.getProblemSet();
        this.currProblemSet = set.copy();

        int cat = set.getCategoriesCount(), probsPerCat = set.getProblemsPerCategory();
        double[][] size = {FuncBox.createDivisionArray(cat), FuncBox.createDivisionArray(probsPerCat + 1)};
        setLayout(new TableLayout(size));

        int i=0, j=1;

        for (Category category : game.getCategories()) {

            JLabel labelCat = new JLabel("", SwingConstants.CENTER);
            labelCat.setText(category.name);
            labelCat.setOpaque(true);
            this.labelCategoryMap.put(labelCat, category);
            add(labelCat, i + ", " + 0);

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



                j++;
            }
            i++;
            j=1;
        }



        panelEdit.panels[0].add(this.labelTitle, "0, 0, 1, 0");
        panelEdit.panels[0].add(this.buttonBack, "0, 2");
        panelEdit.panels[0].add(this.labelBack, "1, 2");
        panelEdit.panels[0].add(this.buttonSave, "2, 2");
        panelEdit.panels[0].add(this.labelSave, "3, 2");

        applyTexture(holder);
        refresh(panelEdit.parent.isFullScreen);



    }

    @Override
    public void exit() {

    }

    @Override
    public void applyTexture(TextureHolder holder) {

        this.labelTitle.setForeground(holder.getColor("teamu_text"));
        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.setIcon(null);
            button.setBackground(holder.getColor("problem"));
            button.setBorder(BorderFactory.createLineBorder(holder.getColor("problem_border"), 3));
            button.label.setForeground(holder.getColor("problem_text"));
            button.label.setOpaque(false);

        }
        for (JLabel label : this.labelCategoryMap.keySet()) {
            label.setBackground(holder.getColor("title"));
            label.setBorder(BorderFactory.createLineBorder(holder.getColor("title_border"), 3));
            label.setForeground(holder.getColor("title_text"));
        }

        this.labelBack.setForeground(holder.getColor("teamu_score"));
        this.labelSave.setForeground(holder.getColor("teamu_score"));
        this.buttonBack.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonSave.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

    }

    @Override
    public void refresh(boolean isFullScreen) {
        this.labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        for (JLabel label : this.labelCategoryMap.keySet()) {
            label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));
        }
        for (ButtonProblem button : this.buttonProblemMap.keySet()) {
            button.label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 39 : 26));
        }

        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        this.labelSave.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));

        int buttonX = panelEdit.panels[0].getWidth() / 4;
        int buttonYs = panelEdit.panels[0].getHeight() / 10;
        this.buttonBack.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        this.buttonSave.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);

    }
}
