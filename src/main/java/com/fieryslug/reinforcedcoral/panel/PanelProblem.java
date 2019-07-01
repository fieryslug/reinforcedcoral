package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.Page;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.Reference;

import javax.swing.*;
import java.util.ArrayList;

public class PanelProblem extends JPanel {

    public JTextArea areaDescription;

    public PanelProblem(FrameCoral parent) {

        this.areaDescription = new JTextArea();
        this.areaDescription.setFont(Reference.JHENGHEI30);
        this.areaDescription.setBackground(Reference.BLACK);
        this.areaDescription.setForeground(Reference.WHITE);
        this.add(this.areaDescription);

    }

    public void inflate(Page page) {

        this.areaDescription.setText(page.htmlText);
        System.out.println("inflated");

    }

    public void enter() {


    }
}
