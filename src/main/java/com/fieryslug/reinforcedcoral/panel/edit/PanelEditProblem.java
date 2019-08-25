package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.GamePhase;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.PlainDocument;

import info.clearthought.layout.TableLayout;

public class PanelEditProblem extends PanelInterior {


    private Problem problem;
    private int currPageNum;

    private static final GamePhase[] PHASES = new GamePhase[]{GamePhase.IN_PROBLEM, GamePhase.ANSWERING, GamePhase.SOLUTION, GamePhase.POST_SOLUTION};
    private GamePhase phase;


    private PanelEditPage panelInteriorPage;
    private JLabel labelBack;
    private ButtonCoral buttonBack;
    private ButtonCoral buttonPrev;
    private ButtonCoral buttonNext;

    private JLabel labelTitle;
    private JLabel labelProbName;

    private PanelEdit panelEdit;

    JLabel labelLayout;
    JLabel[] labels;
    JTextField[] fields;



    PanelEditProblem(PanelEdit panelEdit) {

        TextureHolder holder = TextureHolder.getInstance();

        double[][] size = new double[][]{{1.0d/6, 1.0d/6, 2.0d/6, 1.0d/6, 1.0d/6}, {5.0d/6, 1.0d/6}};
        setLayout(new TableLayout(size));

        this.panelEdit = panelEdit;

        this.panelInteriorPage = new PanelEditPage(panelEdit.parent, this);



        this.labelTitle = new JLabel("    editting:");
        this.labelProbName = new JLabel();

        this.labelBack = new JLabel("back", SwingConstants.LEFT);

        this.buttonBack = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonPrev = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonNext = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));


        this.labelLayout = new JLabel("    layout  (x,y<20)");
        this.labelLayout.setVisible(false);

        labels = new JLabel[4];
        String[] text = new String[]{"x1", "y1", "x2", "y2"};
        for (int i = 0; i < 4; ++i) {
            labels[i] = new JLabel("", SwingConstants.CENTER);
            labels[i].setText(text[i]);
            labels[i].setVisible(false);
        }

        fields = new JTextField[4];
        for (int i = 0; i < 4; ++i) {
            fields[i] = new JTextField();
            PlainDocument doc = (PlainDocument) fields[i].getDocument();

            doc.setDocumentFilter(new IntFilter());
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            fields[i].setVisible(false);
            fields[i].setOpaque(false);
        }

        this.buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                panelEdit.parent.switchPanel(panelEdit, panelEdit);

            }
        });

        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


                if (phase == GamePhase.IN_PROBLEM) {
                    if (currPageNum + 1 >= problem.pages.size()) {
                        currPageNum = 0;
                        setPhase(GamePhase.ANSWERING);
                    }
                    else {
                        currPageNum += 1;
                        if(problem.pages.get(currPageNum).isFinal())
                            setPhase(GamePhase.ANSWERING);

                        panelInteriorPage.setCurrWidget(null);
                        panelEdit.parent.switchPanel(panelEdit, panelEdit);
                    }
                } else if (phase == GamePhase.ANSWERING) {

                } else if (phase == GamePhase.SOLUTION) {

                } else if (phase == GamePhase.POST_SOLUTION) {

                }

            }
        });

        buttonPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (phase == GamePhase.IN_PROBLEM) {
                    if (currPageNum == 0) {

                    }
                    else {
                        currPageNum -= 1;
                        panelEdit.setCurrentPanelInterior(panelEdit.panelEditProblem);
                        panelInteriorPage.setCurrWidget(null);
                        panelEdit.parent.switchPanel(panelEdit, panelEdit);

                    }
                } else if (phase == GamePhase.ANSWERING) {

                        if (currPageNum == 0) {

                        }
                        else {
                            currPageNum -= 1;
                            setPhase(GamePhase.IN_PROBLEM);
                            panelEdit.setCurrentPanelInterior(panelEdit.panelEditProblem);
                            panelInteriorPage.setCurrWidget(null);
                            panelEdit.parent.switchPanel(panelEdit, panelEdit);

                        }

                } else if (phase == GamePhase.SOLUTION) {

                } else if (phase == GamePhase.POST_SOLUTION) {

                }
            }
        });

    }


    @Override
    public void enter() {

        panelEdit.panels[0].add(labelTitle, "0, 0, 0, 0");
        panelEdit.panels[0].add(labelProbName, "1, 0, 3, 0");
        panelEdit.panels[0].add(buttonBack, "0, 2");
        panelEdit.panels[0].add(labelBack, "1, 2");
        panelEdit.panels[2].add(labelLayout, "0, 0, 1, 0");

        for (int i = 0; i < 4; ++i) {
            panelEdit.panels[2].add(labels[i], i + ", 1");
            panelEdit.panels[2].add(fields[i], i + ", 2");
        }


        labelProbName.setText(problem.name);

        if (this.phase == PHASES[0]) {

            panelInteriorPage.inflate2(problem.pages.get(currPageNum));

            add(panelInteriorPage, "0, 0, 4, 0");

            if(currPageNum > 0)
                add(buttonPrev, "0, 1");
            add(buttonNext, "4, 1");
        }
        if (this.phase == PHASES[1]) {


            panelInteriorPage.inflate2(problem.pages.get(currPageNum));

            add(panelInteriorPage, "0, 0, 4, 0");

            if(currPageNum > 0)
                add(buttonPrev, "0, 1");
            add(buttonNext, "4, 1");

        }
        if (this.phase == PHASES[2]) {

        }
        if (this.phase == PHASES[3]) {

        }


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
        panelInteriorPage.removeAll();
        panelEdit.panels[0].removeAll();
        panelEdit.panels[1].removeAll();
        panelEdit.panels[2].removeAll();
    }

    @Override
    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("interior"));
        panelInteriorPage.setBackground(holder.getColor("interior"));

        this.labelTitle.setForeground(holder.getColor("teamu_text"));
        this.labelProbName.setForeground(holder.getColor("teamu_text"));

        labelBack.setForeground(holder.getColor("teamu_score"));
        labelLayout.setForeground(holder.getColor("teamd_text"));

        for (int i = 0; i < 4; ++i) {
            labels[i].setForeground(holder.getColor("teamd_score"));
            fields[i].setForeground(holder.getColor("teamd_text"));
            fields[i].setBackground(holder.getColor("teamd"));
            fields[i].setCaretColor(holder.getColor("teamd_text"));
            fields[i].setBorder(null);
        }


        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};


        buttonBack.setImages(images[0], images[1], images[2]);
        buttonPrev.setImages(images[0], images[1], images[2]);
        buttonNext.setImages(images[0], images[1], images[2]);
        panelInteriorPage.applyTexture();

    }

    @Override
    public void refresh(boolean isFullScreen) {

        this.labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        this.labelProbName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));

        this.labelBack.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));

        labelLayout.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));

        for (int i = 0; i < 4; ++i) {
            labels[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 36 : 24));
            fields[i].setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 36 : 24));
        }

        int buttonX = panelEdit.parent.getContentPane().getWidth() / 2 / 8;
        int buttonY = panelEdit.parent.getContentPane().getHeight() / 2 / 8;

        int buttonXBox = panelEdit.panels[0].getWidth() / 4;
        int buttonYsBox = panelEdit.panels[0].getHeight() / 10;

        buttonBack.resizeIconToSquare(buttonXBox, buttonYsBox * 3, 0.85);
        buttonPrev.resizeIconToSquare(buttonX, buttonY, 1);
        buttonNext.resizeIconToSquare(buttonX, buttonY, 1);

        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.labelTitle);
            FontRef.scaleFont(this.labelProbName);
            FontRef.scaleFont(this.labelBack);
        }
        panelInteriorPage.refreshRendering(isFullScreen);

    }

    void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    void setCurrPageNum(int pageNum) {
        this.currPageNum = pageNum;
    }

    void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelEdit;
    }
}
