package com.fieryslug.reinforcedcoral.util.layout;

import java.awt.Component;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;

public class ModifiedTableLayout extends TableLayout {

    private double[][] size;

    public ModifiedTableLayout(double[][] size) {

        super(modifySize(size));
        this.size = size;

    }

    @Override
    public void addLayoutComponent(Component component, Object constraint) {


        if (size[0].length > 1 && size[1].length > 1) {
            super.addLayoutComponent(component, constraint);
            return;
        }
        else {
            String constr = constraint.toString();
            TableLayoutConstraints layoutConstraints = new TableLayoutConstraints(constr);

            if (size[0].length == 1) {

                layoutConstraints.col2 = 1;

            }
            if (size[1].length == 1) {
                layoutConstraints.row2 = 1;
            }

            super.addLayoutComponent(component, layoutConstraints.toString());


        }


    }

    private static double[][] modifySize(double[][] size) {

        double[] hor = size[0];
        double[] ver = size[1];

        if (size[0].length == 1) {
            hor = new double[]{0.5d, 0.5d};
        }
        if (size[1].length == 1) {
            ver = new double[]{0.5d, 0.5d};
        }
        return new double[][]{hor, ver};

    }


}
