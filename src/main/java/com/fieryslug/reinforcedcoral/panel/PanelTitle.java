package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

public class PanelTitle extends PanelPrime {

    public JButton buttonStart;
    public JButton buttonSettings;
    public JButton buttonThemes;
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
        labelTitle.setFont(FontRef.getFont(FontRef.MONOSPACE, Font.BOLD, 60));
        labelTitle.setForeground(Reference.WHITE);

        buttonStart = new JButton();
        buttonStart.setText("Reionize Mitochondrea");
        buttonStart.setFont(FontRef.getFont(FontRef.MONOSPACE, Font.PLAIN, 45));
        buttonStart.setForeground(Reference.AQUA);
        buttonStart.setBackground(Reference.DARKRED);
        buttonStart.setPreferredSize(new Dimension(650, 50));
        buttonStart.setFocusPainted(false);

        buttonSettings = new JButton();
        buttonSettings.setText("Settings");
        buttonSettings.setFont(FontRef.getFont(FontRef.MONOSPACE, Font.PLAIN, 45));
        buttonSettings.setForeground(Reference.BLACK);
        buttonSettings.setBackground(Reference.GRAY);
        buttonSettings.setPreferredSize(new Dimension(350, 50));
        buttonSettings.setFocusPainted(false);

        buttonThemes = new JButton();
        buttonThemes.setText("Themes");
        buttonThemes.setFont(FontRef.getFont(FontRef.MONOSPACE, Font.PLAIN, 45));
        buttonThemes.setForeground(Reference.WHITE);
        buttonThemes.setBackground(Reference.BLAZE);
        buttonThemes.setPreferredSize(new Dimension(250, 50));
        buttonThemes.setFocusPainted(false);
        buttontest = new ButtonCoral(MediaRef.ADD, MediaRef.ADD_HOVER, MediaRef.ADD_PRESS);


        add(FuncBox.blankLabel(2000, 200));
        add(labelTitle);
        add(FuncBox.blankLabel(2000, 50));
        add(buttonStart);
        add(FuncBox.blankLabel(2000, 30));
        add(buttonSettings);
        add(FuncBox.blankLabel(2000, 30));
        add(buttonThemes);
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
        this.buttonThemes.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        parent.switchPanel(PanelTitle.this, parent.panelThemes);
                    }
            }
        );
    }

}
