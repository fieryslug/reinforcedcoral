package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.layout.ModifiedTableLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class PaddedComponent extends JLabel {

    public PaddedComponent(double borderX, double borderY, JComponent component) {

        double[][] size = new double[][]{{borderX, 1 - 2 * borderX, borderX}, {borderY, 1 - 2 * borderY, borderY}};
        setLayout(new ModifiedTableLayout(size));

        add(component, "1, 1");

    }

}
