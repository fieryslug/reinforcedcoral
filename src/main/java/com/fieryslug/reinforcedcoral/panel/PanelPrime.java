package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.Team;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;

import javax.swing.*;
import java.awt.*;

public abstract class PanelPrime extends JPanel {

    public FrameCoral parent;

    public PanelPrime(FrameCoral parent) {

        this.parent = parent;

        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER));

    }

    public void initialize() {

    }

    public void enter() {

    }

    public void exit() {

    }

    public void react(Team team, ControlKey key) {

    }

}
