package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.widget.ButtonCoral;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

//import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class PanelTitle extends PanelPrime {

    public JButton buttonStart;
    public JButton buttonSettings;
    public JLabel labelTitle;

    public JButton buttontest;


    public PanelTitle(FrameCoral parent) {

        super(parent);
        initialize();
        linkButtons();

    }

    public void initialize() {

        labelTitle = new JLabel();
        labelTitle.setText("Zhī Shì Wáng II");
        labelTitle.setFont(Reference.MONOSPACED60BOLD);
        labelTitle.setForeground(Reference.WHITE);

        buttonStart = new JButton();
        buttonStart.setText("Reionize Mitochondrea");
        buttonStart.setFont(Reference.MONOSPACED45);
        buttonStart.setForeground(Reference.AQUA);
        buttonStart.setBackground(Reference.DARKRED);
        buttonStart.setPreferredSize(new Dimension(650, 50));
        buttonStart.setFocusPainted(false);

        buttonSettings = new JButton();
        buttonSettings.setText("Settings");
        buttonSettings.setFont(Reference.MONOSPACED45);
        buttonSettings.setForeground(Reference.BLACK);
        buttonSettings.setBackground(Reference.GRAY);
        buttonSettings.setPreferredSize(new Dimension(350, 50));
        buttonSettings.setFocusPainted(false);

        buttontest = new ButtonCoral(MediaRef.ADD, MediaRef.ADD_HOVER, MediaRef.ADD_PRESS);


        add(FuncBox.blankLabel(2000, 200));
        add(labelTitle);
        add(FuncBox.blankLabel(2000, 50));
        add(buttonStart);
        add(FuncBox.blankLabel(2000, 30));
        add(buttonSettings);
        //add(buttontest);

    }

    private void linkButtons() {

        this.buttonStart.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        parent.switchPanel(PanelTitle.this, parent.panelGame);
                    }
                }
        );
        this.buttonSettings.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        parent.switchPanel(PanelTitle.this, parent.panelSettings);
                    }
                }
        );
    }

}
