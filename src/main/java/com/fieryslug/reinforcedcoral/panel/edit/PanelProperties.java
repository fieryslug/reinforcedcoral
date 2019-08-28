package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.util.filter.IntFilter;
import com.fieryslug.reinforcedcoral.widget.PaddedComponent;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.PlainDocument;

import info.clearthought.layout.TableLayout;

public class PanelProperties extends PanelInterior {

    private PanelEdit panelEdit;
    private Problem problem;

    private JLabel[] labels;
    private JComboBox<ControlKey> comboBox;
    private JTextField fieldPoints;
    private JTextField fieldDuration;

    //panel0
    private JLabel labelTitle;
    private JLabel labelProbName;
    private ButtonCoral buttonSave;
    private JLabel labelSave;








    PanelProperties(PanelEdit panelEdit) {
        TextureHolder holder = TextureHolder.getInstance();
        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        double[][] size = new double[][]{FuncBox.createDivisionArray(6), FuncBox.createDivisionArray(6)};
        setLayout(new TableLayout(size));

        this.panelEdit = panelEdit;

        labels = new JLabel[6];
        for (int i = 0; i < 6; ++i) {
            labels[i] = new JLabel("", SwingConstants.RIGHT);
        }
        labels[0].setText("answer");
        comboBox = new JComboBox<>(ControlKey.NORMAL_KEYS);
        comboBox.setFocusable(false);
        ((JLabel)comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        labels[1].setText("points");
        fieldPoints = new JTextField();
        fieldPoints.setHorizontalAlignment(SwingConstants.CENTER);
        ((PlainDocument) fieldPoints.getDocument()).setDocumentFilter(new IntFilter(-10000, 10001, 6));

        labels[2].setText("duration");
        fieldDuration = new JTextField();
        fieldDuration.setHorizontalAlignment(SwingConstants.CENTER);
        ((PlainDocument) fieldDuration.getDocument()).setDocumentFilter(new IntFilter(1, 121, 3));

        //panel0
        labelTitle = new JLabel("    editting:");
        labelProbName = new JLabel();
        labelSave = new JLabel("save", SwingConstants.LEFT);
        buttonSave = new ButtonCoral(images[0], images[1], images[2]);


        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                problem.setDuration(Integer.parseInt(fieldDuration.getText()));
                problem.setMonoAnswer((ControlKey)comboBox.getSelectedItem(), Integer.parseInt(fieldPoints.getText()));

                panelEdit.setCurrentPanelInterior(panelEdit.panelEditGame);
                panelEdit.switchSelf();
                panelEdit.panelEditGame.setCurrProblem(problem);
                panelEdit.panelEditGame.inflateEditSlotPanel();
            }
        });

        add(labels[0], "1, 0, 1, 1");
        add(new PaddedComponent(0.35, 0.35, comboBox), "2, 0, 2, 1");
        add(labels[1], "1, 2, 1, 3");
        add(new PaddedComponent(0.3, 0.35, fieldPoints), "2, 2, 2, 3");
        add(labels[2], "1, 4, 1, 5");
        add(new PaddedComponent(0.3, 0.35, fieldDuration), "2, 4, 2, 5");

    }

    @Override
    public void enter() {


        panelEdit.panels[0].add(labelTitle, "0, 0, 0, 0");
        panelEdit.panels[0].add(labelProbName, "1, 0, 3, 0");
        panelEdit.panels[0].add(buttonSave, "0, 2");
        panelEdit.panels[0].add(labelSave, "1, 2");

        labelProbName.setText(problem.name);

        ArrayList<ControlKey> answer = problem.getTrueAnswer();
        ControlKey controlKey = answer.get(0);
        comboBox.setSelectedItem(controlKey);
        fieldPoints.setText("" + problem.keysPointsMap.get(answer));
        fieldDuration.setText("" + problem.getDuration());
        System.out.println(problem.getDuration());


    }

    @Override
    public void exit() {

        for (int i = 0; i < 4; ++i) {
            panelEdit.panels[i].removeAll();
        }

    }

    @Override
    public void applyTexture(TextureHolder holder) {

        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        setBackground(holder.getColor("interior"));

        for (int i = 0; i < 6; ++i) {
            labels[i].setForeground(holder.getColor("text_light"));
        }
        comboBox.setBackground(holder.getColor("interior"));
        comboBox.setForeground(holder.getColor("text"));
        fieldPoints.setBackground(holder.getColor("problem_preenabled"));
        fieldPoints.setForeground(holder.getColor("text"));
        fieldPoints.setCaretColor(holder.getColor("text"));
        fieldPoints.setBorder(null);
        fieldDuration.setBackground(holder.getColor("problem_preenabled"));
        fieldDuration.setForeground(holder.getColor("text"));
        fieldDuration.setCaretColor(holder.getColor("text"));
        fieldDuration.setBorder(null);
        //fieldPoints.setBorder(FuncBox.getLineBorder(holder.getColor("text_light_2"), 3));

        //panel0
        labelTitle.setForeground(holder.getColor("teamu_text"));
        labelProbName.setForeground(holder.getColor("teamu_text"));
        labelSave.setForeground(holder.getColor("teamu_score"));
        buttonSave.setImages(images[0], images[1], images[2]);

    }

    @Override
    public void refresh(boolean isFullScreen) {


        int buttonX = panelEdit.parent.getContentPane().getWidth() / 2 / 8;
        int buttonY = panelEdit.parent.getContentPane().getHeight() / 2 / 8;

        int boxX = panelEdit.panels[0].getWidth();
        int boxY = panelEdit.panels[0].getHeight();

        int buttonXBox = boxX/ 4;
        int buttonYsBox = boxY / 10;

        for (int i = 0; i < 6; ++i) {
            labels[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        }
        comboBox.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 36 : 24));
        fieldPoints.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 36 : 24));
        fieldDuration.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 36 : 24));

        //panel0
        labelTitle.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 45 : 30));
        labelProbName.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 45 : 30));
        labelSave.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 39 : 26));
        buttonSave.resizeIconToSquare(buttonXBox, buttonYsBox * 3, 0.85);


        if (Preference.autoScaleFontSize) {

            for (int i = 0; i < 6; ++i) {
                FontRef.scaleFont(labels[i]);
            }
            FontRef.scaleFont((JLabel) comboBox.getRenderer());
            FontRef.scaleFont(fieldPoints);
            FontRef.scaleFont(fieldDuration);

            FontRef.scaleFont(labelTitle);
            FontRef.scaleFont(labelProbName);
            FontRef.scaleFont(labelSave);
            FontRef.scaleFont(buttonSave);
        }


    }

    @Override
    public PanelPrime getPanelParent() {
        return panelEdit;
    }

    @Override
    public boolean isFullScreen() {
        return panelEdit.parent.isFullScreen;
    }

    void setProblem(Problem problem) {
        this.problem = problem;
    }
}
