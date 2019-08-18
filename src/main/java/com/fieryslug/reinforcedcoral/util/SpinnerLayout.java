package com.fieryslug.reinforcedcoral.util;

import java.awt.BorderLayout;
import java.awt.Component;

public class SpinnerLayout extends BorderLayout {
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if("Editor".equals(constraints)) {
            constraints = "Center";
        } else if("Next".equals(constraints)) {
            constraints = "East";
        } else if("Previous".equals(constraints)) {
            constraints = "West";
        }
        super.addLayoutComponent(comp, constraints);
    }
}