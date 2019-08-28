package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelTeam;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.layout.SpinnerLayout;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

import info.clearthought.layout.TableLayout;

public class PanelOptions extends PanelInterior {

    private PanelTitleBeautified panelTitle;

    JLabel labelOk;
    JLabel labelApply;
    ButtonCoral buttonOk;
    ButtonCoral buttonApply;


    JLabel labelScale;
    JLabel labelMultiplier;
    JLabel labelTeams;
    JLabel label4;
    JLabel label5;
    JLabel label6;

    JSpinner spinnerMultiplier;
    JSpinner spinnerTeams;
    JTextField fieldMultiplier;
    JTextField fieldTeams;
    BasicArrowButton[] buttons;

    JToggleButton toggleScale;

    private Map<PanelTeam, JTextField> panelFieldMap;

    public PanelOptions(PanelTitleBeautified panelTitle) {
        TextureHolder holder = TextureHolder.getInstance();
        this.panelFieldMap = new HashMap<>();
        this.panelTitle = panelTitle;

        //setBackground(holder.getColor("interior"));

        double[][] size = {FuncBox.createDivisionArray(24), FuncBox.createDivisionArray(24)};
        setLayout(new TableLayout(size));



        this.labelOk = new JLabel("ok", SwingConstants.CENTER);
        this.labelOk.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelOk.setForeground(holder.getColor("text_light"));

        this.labelApply = new JLabel("apply", SwingConstants.CENTER);
        this.labelApply.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelApply.setForeground(holder.getColor("text_light"));

        this.buttonOk = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonApply = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelScale = new JLabel("auto scale text", SwingConstants.RIGHT);
        this.labelScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelScale.setForeground(holder.getColor("text_light_2"));

        this.labelMultiplier = new JLabel("text size multiplier", SwingConstants.RIGHT);
        this.labelMultiplier.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelMultiplier.setForeground(holder.getColor("text_light_2"));

        this.spinnerMultiplier = new JSpinner(new SpinnerNumberModel(1.0d, 0.5d, 2.0d, 0.1d)) {
            @Override
            public void setLayout(LayoutManager layoutManager) {
                super.setLayout(new SpinnerLayout());
            }
        };
        this.spinnerMultiplier.setBorder(null);
        this.fieldMultiplier = (JTextField)(this.spinnerMultiplier.getEditor().getComponent(0));
        this.fieldMultiplier.setBackground(holder.getColor("interior"));
        this.fieldMultiplier.setForeground(holder.getColor("text"));
        this.fieldMultiplier.setFont(FontRef.getFont(FontRef.TAIPEI, Font.PLAIN, 28));
        this.fieldMultiplier.setHorizontalAlignment(SwingConstants.CENTER);
        this.fieldMultiplier.setEditable(false);

        BasicArrowButton button = (BasicArrowButton)(this.spinnerMultiplier.getComponent(0));
        button.setBackground(holder.getColor("teamu"));
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setForeground(holder.getColor("teamu_text"));
        button.setDirection(SwingConstants.EAST);
        button.setBorderPainted(false);
        BasicArrowButton button1 = (BasicArrowButton)(this.spinnerMultiplier.getComponent(1));
        button1.setBackground(holder.getColor("teamu"));
        button1.setFocusPainted(false);
        button1.setFocusable(false);
        button1.setForeground(holder.getColor("text"));
        button1.setDirection(SwingConstants.WEST);
        button1.setBorderPainted(false);

        this.spinnerTeams = new JSpinner(new SpinnerNumberModel(4, 1, 12, 1)) {
            @Override
            public void setLayout(LayoutManager layoutManager) {
                super.setLayout(new SpinnerLayout());
            }
        };
        this.spinnerTeams.setBorder(null);

        this.fieldTeams = (JTextField)(this.spinnerTeams.getEditor().getComponent(0));
        this.fieldTeams.setBackground(holder.getColor("interior"));
        this.fieldTeams.setFont(FontRef.getFont(FontRef.TAIPEI, Font.PLAIN, 28));
        this.fieldTeams.setHorizontalAlignment(SwingConstants.CENTER);
        this.fieldTeams.setForeground(holder.getColor("text"));
        this.fieldTeams.setEditable(false);

        BasicArrowButton button11 = (BasicArrowButton)(this.spinnerTeams.getComponent(0));
        button11.setBackground(holder.getColor("teamu"));
        button11.setFocusPainted(false);
        button11.setFocusable(false);
        button11.setForeground(holder.getColor("text"));
        button11.setDirection(SwingConstants.EAST);
        button11.setBorderPainted(false);
        BasicArrowButton button12 = (BasicArrowButton)(this.spinnerTeams.getComponent(1));
        button12.setBackground(holder.getColor("teamu"));
        button12.setFocusPainted(false);
        button12.setFocusable(false);
        button12.setForeground(holder.getColor("text"));
        button12.setDirection(SwingConstants.WEST);
        button12.setBorderPainted(false);

        this.buttons = new BasicArrowButton[]{button, button1, button11, button12};




        this.labelTeams = new JLabel("teams", SwingConstants.RIGHT);
        this.labelTeams.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.labelTeams.setForeground(holder.getColor("text_light_2"));

        this.toggleScale = new JToggleButton("off");
        this.toggleScale.setBackground(holder.getColor("problem_preenabled"));
        this.toggleScale.setBorder(FuncBox.getLineBorder(holder.getColor("problem_border"), 3));
        this.toggleScale.setFocusPainted(false);
        this.toggleScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 22));
        this.toggleScale.setForeground(holder.getColor("problem_text"));

        UIManager.put("ToggleButton.select", holder.getColor("problem"));
        SwingUtilities.updateComponentTreeUI(this.toggleScale);
        this.toggleScale.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    toggleScale.setForeground(holder.getColor("problem_text"));
                    toggleScale.setText("on");
                }
                else {
                    toggleScale.setForeground(holder.getColor("problem_preenabled_text"));
                    toggleScale.setText("off");
                }
            }
        });

        this.buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                Preference.teams = Integer.parseInt(fieldTeams.getText());
                Preference.autoScaleFontSize = toggleScale.isSelected();
                Preference.fontSizeMultiplier = Double.parseDouble(fieldMultiplier.getText());

                for (PanelTeam panelTeam : panelTitle.panelTeams) {
                    JTextField field = panelFieldMap.get(panelTeam);
                    panelTeam.team.setName(field.getText());
                }

                int originalTeams = panelTitle.parent.game.getTeams().size();

                if (Preference.teams > originalTeams) {

                    for (int t = originalTeams; t < Preference.teams; ++t) {
                        panelTitle.parent.game.getTeams().add(new Team(t+1));
                    }

                } else if (Preference.teams < originalTeams) {

                }

                panelTitle.setCurrentPanelInterior(panelTitle.panelInterior);
                panelTitle.parent.switchPanel(panelTitle, panelTitle);
            }

        });

        add(this.labelOk, "8, 18, 15, 19");
        //add(this.labelApply, "3, 4, 4, 4");
        add(this.buttonOk, "8, 20, 15, 23");
        //add(this.buttonApply, "3, 5, 4, 5");

        add(this.labelScale, "0, 4, 7, 7");
        add(this.toggleScale, "9, 5, 10, 6");

        add(this.labelMultiplier, "0, 8, 7, 11");
        add(this.spinnerMultiplier, "9, 9, 10, 10");
        add(this.labelTeams, "0, 12, 7, 15");
        add(this.spinnerTeams, "9, 13, 10, 14");



    }

    @Override
    public void enter() {
        this.panelFieldMap.clear();

        int buttonX = (int) (this.panelTitle.getWidth() / (3));
        int buttonY = (int) (this.panelTitle.getHeight() / (6));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.4);

        this.buttonOk.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonApply.resizeImageForIcons(buttonSize, buttonSize);

        this.toggleScale.setSelected(Preference.autoScaleFontSize);
        this.fieldTeams.setText(String.valueOf(Preference.teams));
        this.fieldMultiplier.setText(String.valueOf(Preference.fontSizeMultiplier));

        for (PanelTeam panelTeam : this.panelTitle.panelTeams) {
            JTextField field = new JTextField();
            field.setText(panelTeam.labelName.getText());
            field.setFont(panelTeam.labelName.getFont());
            field.setAlignmentX(CENTER_ALIGNMENT);
            //field.setBorder(null);
            field.setBorder(FuncBox.getLineBorder(TextureHolder.getInstance().getColor("edit_border"), 3));
            field.setOpaque(false);
            field.setForeground(panelTeam.labelName.getForeground());
            this.panelFieldMap.put(panelTeam, field);
            panelTeam.removeAll();
            panelTeam.add(field, "1, 0");
            panelTeam.add(panelTeam.labelScore, "1, 1");
            //panelTeam.add(FuncBox.blankLabel(2000, 10));
            panelTeam.add(panelTeam.labelState, "0, 2, 2, 2");
            field.setHorizontalAlignment(SwingConstants.CENTER);
        }



    }

    @Override
    public void exit() {


    }

    @Override
    public void applyTexture(TextureHolder holder) {
        setBackground(holder.getColor("interior"));

        this.labelOk.setForeground(holder.getColor("text_light"));
        this.labelApply.setForeground(holder.getColor("text_light"));
        this.buttonOk.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.buttonApply.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.labelScale.setForeground(holder.getColor("text_light_2"));
        this.labelMultiplier.setForeground(holder.getColor("text_light_2"));
        this.labelTeams.setForeground(holder.getColor("text_light_2"));

        this.fieldMultiplier.setForeground(holder.getColor("text"));
        this.fieldMultiplier.setBackground(holder.getColor("interior"));
        this.fieldMultiplier.setSelectedTextColor(holder.getColor("text"));
        this.fieldMultiplier.setSelectionColor(holder.getColor("interior"));
        this.fieldTeams.setForeground(holder.getColor("text"));
        this.fieldTeams.setBackground(holder.getColor("interior"));
        this.fieldTeams.setSelectedTextColor(holder.getColor("text"));
        this.fieldTeams.setSelectionColor(holder.getColor("interior"));


        this.toggleScale.setBackground(holder.getColor("problem_preenabled"));
        this.toggleScale.setBorder(FuncBox.getLineBorder(holder.getColor("teamu_border"), 3));
        UIManager.put("ToggleButton.select", holder.getColor("problem"));
        SwingUtilities.updateComponentTreeUI(this.toggleScale);
        if (this.toggleScale.isSelected()) {
            this.toggleScale.setForeground(holder.getColor("problem_text"));
        }
        else {
            this.toggleScale.setForeground(holder.getColor("problem_preenabled_text"));
        }

        try {
            for (PanelTeam panelTeam : this.panelTitle.panelTeams) {

                JTextField field = this.panelFieldMap.get(panelTeam);
                if (panelTeam.isUp())
                    field.setForeground(holder.getColor("teamu_text"));
                else
                    field.setForeground(holder.getColor("teamd_text"));
                field.setBorder(FuncBox.getLineBorder(holder.getColor("edit_border"), 3));

            }
        } catch (Exception e) {

        }
        for (BasicArrowButton button : this.buttons) {
            button.setBackground(holder.getColor("teamu"));
        }
    }

    @Override
    public void refresh(boolean isFullScreen) {

        if (isFullScreen) {

            this.labelOk.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelApply.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelMultiplier.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.labelTeams.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.fieldTeams.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.fieldMultiplier.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.toggleScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 33));


        } else {

            this.labelOk.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelApply.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelMultiplier.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.labelTeams.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.fieldTeams.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.fieldMultiplier.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.toggleScale.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 22));
        }

        try {
            for (PanelTeam panelTeam : this.panelTitle.panelTeams) {

                JTextField field = this.panelFieldMap.get(panelTeam);
                if (isFullScreen)
                    field.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 51));
                else
                    field.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 34));

            }
        } catch (Exception e) {

        }



        int buttonX = (int) (this.panelTitle.getWidth() / (3));
        int buttonY = (int) (this.panelTitle.getHeight() / (6));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.4);

        this.buttonOk.resizeImageForIcons(buttonSize, buttonSize);
        this.buttonApply.resizeImageForIcons(buttonSize, buttonSize);

        if (Preference.autoScaleFontSize) {
            FontRef.scaleFont(this.toggleScale);
            FontRef.scaleFont(this.labelOk);
            FontRef.scaleFont(this.labelScale);
            FontRef.scaleFont(this.labelMultiplier);
            FontRef.scaleFont(this.labelTeams);
            FontRef.scaleFont(this.fieldTeams);
            FontRef.scaleFont(this.fieldMultiplier);
            for (PanelTeam panelTeam : this.panelTitle.panelTeams) {

                JTextField field = this.panelFieldMap.get(panelTeam);
                FontRef.scaleFont(field);
            }
        }
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelTitle;
    }
}
