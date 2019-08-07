package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.Game;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.core.WorkTable;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import info.clearthought.layout.TableLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanelSettings extends PanelPrime {

    public JTextField fieldTeam1;
    public JTextField fieldTeam2;
    public JTextField fieldTeam3;
    public JTextField fieldTeam4;
    public JLabel labelTeam1;
    public JLabel labelTeam2;
    public JLabel labelTeam3;
    public JLabel labelTeam4;

    public JButton buttonConfirm;

    public JButton buttonSet1;
    public JButton buttonSet2;

    private int problemSetNumber = 1;

    public PanelSettings(FrameCoral parent) {
        super(parent);
        initialize();
        linkButtons();
    }

    public void initialize() {
        double[][] size = {{0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}, {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}};
        TableLayout tableLayout = new TableLayout(size);
        this.setLayout(tableLayout);
        this.fieldTeam1 = getTextField();
        this.fieldTeam2 = getTextField();
        this.fieldTeam3 = getTextField();
        this.fieldTeam4 = getTextField();
        this.labelTeam1 = getLabel();
        this.labelTeam2 = getLabel();
        this.labelTeam3 = getLabel();
        this.labelTeam4 = getLabel();

        this.labelTeam1.setText("team 1 ");
        this.labelTeam2.setText("team 2 ");
        this.labelTeam3.setText("team 3 ");
        this.labelTeam4.setText("team 4 ");


        this.buttonConfirm = new JButton();
        this.buttonConfirm.setForeground(Reference.AQUA);
        this.buttonConfirm.setBackground(Reference.DARKGREEN);
        this.buttonConfirm.setText("confirm");
        this.buttonConfirm.setFocusPainted(false);

        this.buttonSet1 = new JButton();
        this.buttonSet1.setForeground(Reference.WHITE);
        this.buttonSet1.setBackground(Reference.AQUA);
        this.buttonSet1.setText("1");
        this.buttonSet1.setFont(FontRef.TAIPEI60);
        this.buttonSet1.setFocusPainted(false);
        this.buttonSet1.setBorderPainted(false);

        this.buttonSet2 = new JButton();
        this.buttonSet2.setForeground(Reference.WHITE);
        this.buttonSet2.setBackground(Reference.DARKBLUE);
        this.buttonSet2.setText("2");
        this.buttonSet2.setFont(FontRef.TAIPEI60);
        this.buttonSet2.setFocusPainted(false);
        this.buttonSet2.setBorderPainted(false);

        add(this.fieldTeam1, "5, 1, 5, 1");
        add(this.fieldTeam2, "5, 3, 5, 3");
        add(this.fieldTeam3, "5, 5, 5, 5");
        add(this.fieldTeam4, "5, 7, 5, 7");
        add(this.labelTeam1, "3, 1, 4, 1");
        add(this.labelTeam2, "3, 3, 4, 3");
        add(this.labelTeam3, "3, 5, 4, 5");
        add(this.labelTeam4, "3, 7, 4, 7");
        add(this.buttonConfirm, "4, 9, 5, 9");

        add(this.buttonSet1, "7, 5");
        add(this.buttonSet2, "8, 5");

        refresh();
    }

    public void linkButtons() {
        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(problemSetNumber == 1) {
                    Game game = WorkTable.getGame();
                    parent.refreshGame(game);
                }
                if (problemSetNumber == 2) {
                    Game game = WorkTable.getGame1();
                    parent.refreshGame(game);
                }

                parent.game.teams.get(0).setId(Integer.parseInt(fieldTeam1.getText()));
                parent.game.teams.get(1).setId(Integer.parseInt(fieldTeam2.getText()));
                parent.game.teams.get(2).setId(Integer.parseInt(fieldTeam3.getText()));
                parent.game.teams.get(3).setId(Integer.parseInt(fieldTeam4.getText()));
                parent.switchPanel(PanelSettings.this, parent.panelTitle);
            }
        });
        buttonSet1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                problemSetNumber = 1;
                buttonSet1.setBackground(Reference.AQUA);
                buttonSet2.setBackground(Reference.DARKBLUE);
            }
        });
        buttonSet2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                problemSetNumber = 2;
                buttonSet2.setBackground(Reference.AQUA);
                buttonSet1.setBackground(Reference.DARKBLUE);
            }
        });
    }

    @Override
    public void refresh() {
        if (parent.isFullScreen) {
            this.fieldTeam1.setFont(FontRef.TAIPEI60);
            this.fieldTeam2.setFont(FontRef.TAIPEI60);
            this.fieldTeam3.setFont(FontRef.TAIPEI60);
            this.fieldTeam4.setFont(FontRef.TAIPEI60);
            this.labelTeam1.setFont(FontRef.TAIPEI60);
            this.labelTeam2.setFont(FontRef.TAIPEI60);
            this.labelTeam3.setFont(FontRef.TAIPEI60);
            this.labelTeam3.setFont(FontRef.TAIPEI60);
            this.labelTeam4.setFont(FontRef.TAIPEI60);
            this.buttonConfirm.setFont(FontRef.TAIPEI60);
            this.buttonSet1.setFont(FontRef.TAIPEI90);
            this.buttonSet2.setFont(FontRef.TAIPEI90);
        } else {
            this.fieldTeam1.setFont(FontRef.TAIPEI40);
            this.fieldTeam2.setFont(FontRef.TAIPEI40);
            this.fieldTeam3.setFont(FontRef.TAIPEI40);
            this.fieldTeam4.setFont(FontRef.TAIPEI40);
            this.labelTeam1.setFont(FontRef.TAIPEI40);
            this.labelTeam2.setFont(FontRef.TAIPEI40);
            this.labelTeam3.setFont(FontRef.TAIPEI40);
            this.labelTeam4.setFont(FontRef.TAIPEI40);
            this.buttonConfirm.setFont(FontRef.TAIPEI40);

            this.buttonSet1.setFont(FontRef.TAIPEI60);
            this.buttonSet2.setFont(FontRef.TAIPEI60);

        }
    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void enter() {
        super.enter();
    }

    private JTextField getTextField() {

        JTextField textField = new JTextField();
        textField.setBorder(Reference.BEVELGREEN);
        textField.setBackground(Reference.DARKGRAY);
        textField.setForeground(Reference.AQUA);

        return textField;
    }

    private JLabel getLabel() {
        JLabel label = new JLabel();
        label.setForeground(Reference.BLAZE);
        label.setBackground(Reference.TRANSPARENT);

        return label;
    }


}
