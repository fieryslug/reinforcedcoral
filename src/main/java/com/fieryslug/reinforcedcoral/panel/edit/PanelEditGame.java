package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.Category;
import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.problem.ProblemNull;
import com.fieryslug.reinforcedcoral.core.problem.ProblemTemp;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.*;
import com.fieryslug.reinforcedcoral.util.layout.ModifiedTableLayout;
import com.fieryslug.reinforcedcoral.widget.button.ButtonColorized;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.json.JSONObject;

import info.clearthought.layout.TableLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class PanelEditGame extends PanelInterior {

    private PanelEdit panelEdit;
    private BiMap<ButtonProblem, Problem> buttonProblemMap;
    private BiMap<JLabel, Category> labelCategoryMap;

    private JLabel labelTitle;
    private JTextField fieldSetName;


    //panel 0
    private ButtonCoral buttonBack;
    private JLabel labelBack;
    private ButtonCoral buttonSave;
    private JLabel labelSave;

    //panel 1
    private JLabel labelSlot;
    private JTextField fieldSlot;
    private JLabel[] labelsProb;
    private ButtonCoral[] buttonsProb;

    //panel 2
    private ButtonCoral[] buttonsMove;
    private JLabel labelMove;
    private JPanel panelMove;

    //panel 3
    private JLabel[] labelsBR;
    private ButtonCoral[] buttonsBR;



    ProblemSet targetSet;

    private Problem currProblem = null;
    private Category currCat = null;
    private int currType = 0;
    private EditPhase phase;
    boolean needsNewSet = false;

    private MouseListener mouseClickListener;

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
        labelsProb[2].setText("properties");
        labelsProb[3].setText("delete");


        labelMove = new JLabel("move", SwingConstants.CENTER);
        buttonsMove = new ButtonCoral[4];
        for (int i = 0; i < 4; ++i) {
            buttonsMove[i] = new ButtonCoral(images[0], images[1], images[2]);
        }

        panelMove = new JPanel();
        panelMove.setOpaque(false);
        panelMove.setLayout(new TableLayout(new double[][]{FuncBox.createDivisionArray(3), FuncBox.createDivisionArray(3)}));
        panelMove.add(buttonsMove[0], "1, 0");
        panelMove.add(buttonsMove[1], "1, 2");
        panelMove.add(buttonsMove[2], "0, 1");
        panelMove.add(buttonsMove[3], "2, 1");

        labelsBR = new JLabel[4];
        buttonsBR = new ButtonCoral[4];

        for (int i = 0; i < 4; ++i) {
            labelsBR[i] = new JLabel("", SwingConstants.CENTER);
            buttonsBR[i] = new ButtonCoral(images[0], images[1], images[2]);
        }
        labelsBR[0].setText(" new category");
        labelsBR[1].setText(" new row");
        buttonsBR[2].setVisible(false);
        labelsBR[3].setText(" delete bottom row");

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //panelEdit.exit();
                panelEdit.setCurrentPanelInterior(panelEdit.panelEditTitle);
                //panelEdit.enter();
                //panelEdit.refresh();
                //panelEdit.repaint();
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        this.buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                updateChanges();


                targetSet.dumpProblemSet(".tmp/" + targetSet.getId(), true);
                targetSet.loadProblemSet(".tmp/" + targetSet.getId());
                targetSet.loadResources();

                targetSet.saveProblemSet(targetSet.getId(), true);


                DataLoader.getInstance().loadAllProblemSets();
                DataLoader.getInstance().updateProblemSetIndex();

                //panelEdit.exit();
                panelEdit.setCurrentPanelInterior(panelEdit.panelEditTitle);
                //panelEdit.enter();
                //panelEdit.refresh();
                //panelEdit.repaint();
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        this.buttonsProb[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(currProblem != null) {
                    if(currProblem instanceof ProblemTemp) {

                        Category category = currProblem.getParentCat();
                        ArrayList<Problem> problems = category.getProblems();
                        int ind = problems.lastIndexOf(currProblem);

                        if (ind >= 0) {
                            JSONObject json = new JSONObject();


                            ArrayList<ControlKey> controlKeys = new ArrayList<ControlKey>();
                            controlKeys.add(ControlKey.A);

                            Problem problem = Problem.createEmptyProblem(controlKeys, 100, currProblem.shortId);
                            problem.setShortId(ProblemSet.shortIdForProblem(category, currProblem.shortId));
                            category.set(ind, problem);
                            panelEdit.switchSelf();
                            setCurrProblem(category.getProblems().get(ind));
                            inflateEditSlotPanel();
                        }

                    }
                    else {
                        PanelEditDependency panel = new PanelEditDependency(panelEdit, targetSet, currProblem);

                        panelEdit.setCurrentPanelInterior(panel);
                        panelEdit.parent.switchPanel(panelEdit, panelEdit);
                    }
                }

            }
        });

        this.buttonsProb[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //panelEdit.exit();

                if(!currProblem.isSpecial()) {
                    currProblem.normalizePages();


                    panelEdit.setCurrentPanelInterior(panelEdit.panelEditProblem);
                    panelEdit.panelEditProblem.setPhase(GamePhase.IN_PROBLEM);
                    panelEdit.panelEditProblem.setCurrPageNum(0);
                    panelEdit.panelEditProblem.setProblem(currProblem);
                    panelEdit.parent.switchPanel(panelEdit, panelEdit);
                }
                //panelEdit.enter();
                //panelEdit.refresh();
                //panelEdit.repaint();

            }
        });

        buttonsProb[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(currProblem != null) {
                    if(!currProblem.isSpecial()) {
                        panelEdit.panelProperties.setProblem(currProblem);
                        panelEdit.setCurrentPanelInterior(panelEdit.panelProperties);
                        panelEdit.switchSelf();
                    }
                }
            }
        });

        buttonsProb[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if (currProblem != null) {
                    Problem problem = currProblem;
                    String top = "<html><strong>Do you really want to delete " + FuncBox.removeHtmlTag(currProblem.name) + " ?</strong></html>";
                    String bottom = "<html><strong>" + FuncBox.removeHtmlTag(currProblem.name) + " will be lost forever! (a long time!)" + "</strong></html>";

                    panelEdit.setCurrentPanelInterior(panelEdit.panelConfirm);
                    panelEdit.panelConfirm.prepare(top, bottom, new Runnable() {
                        @Override
                        public void run() {
                            panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                            panelEdit.parent.switchPanel(panelEdit, panelEdit);
                            setCurrProblem(problem);
                            inflateEditSlotPanel();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {

                            targetSet.deleteProblem(currProblem);

                            panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                            panelEdit.parent.switchPanel(panelEdit, panelEdit);
                        }
                    });

                    panelEdit.parent.switchPanel(panelEdit, panelEdit);
                } else if (currCat != null) {

                    Category category = currCat;
                    String top = "<html><strong>Do you really want to delete " + FuncBox.removeHtmlTag(currCat.name) + " and its " + category.getProblems().size() + " problems ?</strong></html>";
                    String bottom = "<html><strong>" + FuncBox.removeHtmlTag(currCat.name) + " will be lost forever! (a long time!)" + "</strong></html>";

                    if (targetSet.getCategories().size() < 2) {
                        top = "<html><strong>Couldn't delete " + FuncBox.removeHtmlTag(currCat.name) + "!</strong></html>";
                        bottom = "";
                        panelEdit.panelConfirm.getButtonConfirm().setEnabled(false);
                    }

                    panelEdit.setCurrentPanelInterior(panelEdit.panelConfirm);
                    panelEdit.panelConfirm.prepare(top, bottom, new Runnable() {
                        @Override
                        public void run() {
                            panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                            panelEdit.parent.switchPanel(panelEdit, panelEdit);
                            setCurrCat(category);
                            inflateEditSlotPanel();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {

                            targetSet.deleteCategory(currCat);

                            panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                            panelEdit.parent.switchPanel(panelEdit, panelEdit);
                        }
                    });
                    panelEdit.parent.switchPanel(panelEdit, panelEdit);

                }

            }
        });

        this.fieldSlot.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                update();
            }

            private void update() {
                String text = fieldSlot.getText();
                System.out.println("keyReleased");
                if (currProblem != null) {
                    ButtonProblem button = buttonProblemMap.inverse().get(currProblem);
                    currProblem.name = text;
                    System.out.println("name set" + text);
                    button.label.setText(currProblem.name);
                    if(Preference.autoScaleFontSize) {
                        button.label.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, panelEdit.parent.isFullScreen ? 39 : 26));
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

        buttonsMove[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                System.out.println(currProblem);

                if(currProblem != null) {

                    ArrayList<Category> categories = targetSet.getCategories();
                    int catInd = categories.lastIndexOf(currProblem.getParentCat());
                    int probInd = currProblem.getParentCat().getProblems().lastIndexOf(currProblem);

                    if (catInd > 0 && probInd >= 0) {
                        Category cat = categories.get(catInd - 1);
                        Category cat1 = currProblem.getParentCat();
                        Problem prob = cat.getProblems().get(probInd);



                        cat.set(probInd, currProblem);
                        cat1.set(probInd, prob);
                        currProblem.setParentCat(cat);
                        prob.setParentCat(cat1);
                        rearrange();
                    }


                }
                else if (currCat != null) {

                    int ind = targetSet.getCategories().lastIndexOf(currCat);

                    if (ind > 0) {
                        Category cat = targetSet.getCategories().get(ind - 1);

                        targetSet.getCategories().set(ind - 1, currCat);
                        targetSet.getCategories().set(ind, cat);
                        rearrange();
                    }
                }


            }
        });
        buttonsMove[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currProblem != null) {
                    ArrayList<Category> categories = targetSet.getCategories();
                    int catInd = categories.lastIndexOf(currProblem.getParentCat());
                    int probInd = currProblem.getParentCat().getProblems().indexOf(currProblem);

                    if (catInd >= 0 && catInd < categories.size() - 1 && probInd >= 0) {
                        Category cat = categories.get(catInd + 1);
                        Category cat1 = currProblem.getParentCat();
                        Problem prob = cat.getProblems().get(probInd);
                        //cat.getProblems().set(probInd, currProblem);
                        //cat1.getProblems().set(probInd, prob);
                        cat.set(probInd, currProblem);
                        cat1.set(probInd, prob);
                        currProblem.setParentCat(cat);
                        prob.setParentCat(cat1);
                        rearrange();
                    }
                }
                else if (currCat != null) {
                    int ind = targetSet.getCategories().lastIndexOf(currCat);

                    if (ind >= 0 && ind < targetSet.getCategories().size()-1) {
                        Category cat = targetSet.getCategories().get(ind + 1);
                        targetSet.getCategories().set(ind + 1, currCat);
                        targetSet.getCategories().set(ind, cat);
                        rearrange();
                    }
                }
            }
        });
        buttonsMove[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currProblem != null) {
                    Category cat = currProblem.getParentCat();
                    ArrayList<Problem> problems = cat.getProblems();
                    int ind = problems.lastIndexOf(currProblem);
                    if (ind > 0) {
                        Problem prob = problems.get(ind - 1);

                        cat.set(ind - 1, currProblem);
                        cat.set(ind, prob);
                        rearrange();
                    }
                }
            }
        });
        buttonsMove[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (currProblem != null) {
                    Category cat = currProblem.getParentCat();
                    ArrayList<Problem> problems = cat.getProblems();
                    int ind = problems.lastIndexOf(currProblem);
                    if (ind >= 0 && ind < problems.size() - 1) {
                        Problem prob = problems.get(ind + 1);

                        cat.set(ind + 1, currProblem);
                        cat.set(ind, prob);
                        rearrange();
                    }
                }
            }
        });

        buttonsBR[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Category category = new Category("void", targetSet.idForCategory());
                int probs = targetSet.getProblemsPerCategory();

                for (int i = 0; i < probs; ++i) {
                    Problem problem = new ProblemTemp();
                    problem.shortId = ProblemSet.shortIdForProblem(category, "0");
                    category.addProblems(problem);
                }
                targetSet.addCategory(category);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        buttonsBR[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                for (Category category : targetSet.getCategories()) {

                    Problem problem = new ProblemTemp();
                    problem.setShortId(targetSet.shortIdForProblem(category, "0"));
                    category.addProblems(problem);
                }
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        buttonsBR[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int ind = targetSet.getProblemsPerCategory() - 1;

                String probs = "";
                for (Category category : targetSet.getCategories()) {
                    probs += category.getProblems().get(ind).name + ", ";
                }
                probs = probs.substring(0, probs.length() - 2);
                String top = "Do you really want to delete " + probs + "?";
                String bottom = "they will be lost forever! (a long time!)";


                if (targetSet.getProblemsPerCategory() == 1) {
                    top = "Couldn't delete " + targetSet.getCategories().size() + " problems!";
                    bottom = "";
                    panelEdit.panelConfirm.getButtonConfirm().setEnabled(false);
                }

                Problem problem = currProblem;
                Category category = currCat;

                panelEdit.panelConfirm.prepare(top, bottom, new Runnable() {
                    @Override
                    public void run() {

                        panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                        panelEdit.parent.switchPanel(panelEdit, panelEdit);
                        if (problem != null) {
                            setCurrProblem(problem);
                        } else if (category != null) {
                            setCurrCat(category);
                        }
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        for (Category cat : targetSet.getCategories()) {

                            Problem problem = cat.getProblems().get(ind);
                            targetSet.deleteProblem(problem);
                            cat.getProblems().remove(ind);

                        }
                        panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                        panelEdit.parent.switchPanel(panelEdit, panelEdit);

                    }
                });

                panelEdit.setCurrentPanelInterior(panelEdit.panelConfirm);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);
            }
        });

        mouseClickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                setCurrCat(null);
                setCurrProblem(null);
                inflateEditSlotPanel();
            }
        };
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
            setLayout(new ModifiedTableLayout(size));

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

                for (Problem problem : category.getProblems()) {

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

            for (int k = 0; k < 4; ++k) {
                buttonsMove[k].setVisible(false);
            }
            labelMove.setVisible(false);

            panelEdit.panels[0].add(this.labelTitle, "0, 0, 0, 0");
            panelEdit.panels[0].add(this.buttonBack, "0, 2");
            panelEdit.panels[0].add(this.labelBack, "1, 2");
            panelEdit.panels[0].add(this.buttonSave, "2, 2");
            panelEdit.panels[0].add(this.labelSave, "3, 2");
            panelEdit.panels[0].add(this.fieldSetName, "1, 0, 3, 1");

            panelEdit.panels[1].add(labelSlot, "0, 0");
            labelSlot.setText("  problem: ");
            panelEdit.panels[1].add(fieldSlot, "1, 0, 2, 0");

            panelEdit.panels[2].add(panelMove, "3, 0, 3, 2");
            panelEdit.panels[2].add(labelMove, "2, 1");



            for (int k = 0; k < 4; ++k) {
                panelEdit.panels[1].add(labelsProb[k], k + ", 1");
                panelEdit.panels[1].add(buttonsProb[k], k + ", 2");
                panelEdit.panels[3].add(labelsBR[k], k + ", 1");
                panelEdit.panels[3].add(buttonsBR[k], k + ", 2");
            }

            panelEdit.addMouseListener(mouseClickListener);

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
        panelEdit.removeMouseListener(mouseClickListener);
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

        labelMove.setForeground(holder.getColor("teamd_score"));

        for (int i = 0; i < 4; ++i) {
            this.buttonsProb[i].setImages(images[0], images[1], images[2]);
            this.labelsProb[i].setForeground(holder.getColor("teamu_text"));
        }

        for (int i = 0; i < 4; ++i) {
            buttonsMove[i].setImages(images[0], images[1], images[2]);
        }

        for (int i = 0; i < 4; ++i) {
            labelsBR[i].setForeground(holder.getColor("teamd_score"));
            buttonsBR[i].setImages(images[0], images[1], images[2]);
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

        labelMove.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));


        int buttonX = panelEdit.panels[0].getWidth() / 4;
        int buttonYs = panelEdit.panels[0].getHeight() / 10;
        this.buttonBack.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        this.buttonSave.resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        for (int i = 0; i < 4; ++i) {
            this.labelsProb[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 33 : 22));
            this.buttonsProb[i].resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
        }

        int boxX = panelEdit.panels[0].getWidth();
        int boxY = panelEdit.panels[0].getHeight();

        for (int i = 0; i < 4; ++i) {
            buttonsMove[i].resizeIconToSquare(boxX / 12, boxY / 3, 0.8d);
        }

        for (int i = 0; i < 4; ++i) {
            labelsBR[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 33 : 22));
            buttonsBR[i].resizeIconToSquare(buttonX, buttonYs * 3, 0.85);
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
            FontRef.scaleFont(labelMove);
            for (int i = 0; i < 4; ++i) {
                FontRef.scaleFont(this.labelsProb[i]);
                FontRef.scaleFont(labelsBR[i]);
            }
        }

    }

    private void updateChanges() {

        this.targetSet.setName(this.fieldSetName.getText());

    }

    void setCurrProblem(Problem problem) {
        this.currType = 0;
        if (this.buttonProblemMap.inverse().keySet().contains(problem) || problem == null) {

            if(this.currProblem != null) {
                this.buttonProblemMap.inverse().get(this.currProblem).setState(0);
                currProblem = null;
            }

            if (this.currCat != null) {
                JLabel label = this.labelCategoryMap.inverse().get(this.currCat);
                TextureHolder holder = TextureHolder.getInstance();
                label.setBackground(holder.getColor("title"));
                label.setBorder(FuncBox.getLineBorder(holder.getColor("title_border"), 3));
                label.setForeground(holder.getColor("title_text"));
                this.currCat = null;
            }
            currProblem = problem;
            if(problem != null) {
                ButtonProblem button = this.buttonProblemMap.inverse().get(problem);
                button.setState(-1);
            }

        }

    }

    void setCurrCat(Category cat) {
        this.currType = 1;
        TextureHolder holder = TextureHolder.getInstance();
        if (this.labelCategoryMap.inverse().keySet().contains(cat) || cat == null) {


            if (this.currCat != null) {
                JLabel label = this.labelCategoryMap.inverse().get(this.currCat);
                if(label != null) {
                    label.setBackground(holder.getColor("title"));
                    label.setBorder(FuncBox.getLineBorder(holder.getColor("title_border"), 3));
                    label.setForeground(holder.getColor("title_text"));
                }
                currCat = null;
            }
            if (this.currProblem != null) {
                ButtonProblem button = this.buttonProblemMap.inverse().get(this.currProblem);
                this.currProblem = null;
                if(button != null)
                    button.setState(0);
            }

            this.currCat = cat;
            if(cat != null) {
                JLabel label1 = this.labelCategoryMap.inverse().get(cat);
                if(label1 != null) {
                    label1.setBackground(holder.getColor("title_dark"));
                    label1.setBorder(FuncBox.getLineBorder(holder.getColor("title_dark_border"), 3));
                    label1.setForeground(holder.getColor("title_dark_text"));
                }
            }
        }
    }

    void inflateEditSlotPanel() {

        if (this.currProblem != null) {

            if (currProblem instanceof ProblemTemp) {

                for (int i = 0; i < 4; ++i) {
                    labelsProb[i].setVisible(false);
                    buttonsProb[i].setVisible(false);
                }

                labelSlot.setVisible(true);
                labelSlot.setText("  new problem: ");
                fieldSlot.setVisible(true);
                fieldSlot.setText(currProblem.name);
                labelsProb[0].setVisible(true);
                buttonsProb[0].setVisible(true);
                labelsProb[0].setText("create");

            }
            else {
                labelSlot.setVisible(true);
                fieldSlot.setVisible(true);
                labelSlot.setText("  problem: ");
                fieldSlot.setText(this.currProblem.name);

                for (int i = 0; i < 4; ++i) {
                    this.labelsProb[i].setVisible(true);
                    this.buttonsProb[i].setVisible(true);
                }
                labelsProb[0].setText("  dependencies");



                if (currProblem.isSpecial()) {
                    buttonsProb[1].setEnabled(false);
                    buttonsProb[2].setEnabled(false);
                } else {
                    buttonsProb[1].setEnabled(true);
                    buttonsProb[2].setEnabled(true);
                }
            }
            for (int i = 0; i < 4; ++i) {
                buttonsMove[i].setVisible(true);
            }
            labelsProb[3].setText("delete");
            buttonsMove[0].setEnabled(true);
            buttonsMove[1].setEnabled(true);
            labelMove.setVisible(true);

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
            for (int i = 0; i < 4; ++i) {
                buttonsMove[i].setVisible(true);
            }
            labelsProb[3].setText("delete all");
            labelsProb[3].setVisible(true);
            buttonsProb[3].setVisible(true);
            buttonsMove[0].setEnabled(false);
            buttonsMove[1].setEnabled(false);
            labelMove.setVisible(true);
        }
        else {
            labelSlot.setVisible(false);
            fieldSlot.setVisible(false);
            for (int i = 0; i < 4; ++i) {
                this.labelsProb[i].setVisible(false);
                this.buttonsProb[i].setVisible(false);
                buttonsMove[i].setVisible(false);

            }
            labelMove.setVisible(false);
        }

    }

    void setPhase(EditPhase phase) {
        this.phase = phase;
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelEdit;
    }

    private void rearrange() {

        int i=0, j=1;
        for (Category category : targetSet.getCategories()) {
            add(labelCategoryMap.inverse().get(category), i + ", " + 0);
            for (Problem problem : category.getProblems()) {
                add(buttonProblemMap.inverse().get(problem), i + ", " + j);
                j += 1;
            }
            i += 1;
            j = 1;
        }
        repaint();
        revalidate();

    }
}
