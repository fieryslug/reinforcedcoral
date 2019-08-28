package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelAdd extends PanelConfirm {


    private JTextField field;

    PanelAdd(PanelEdit panelEdit) {
        super(panelEdit);

        field = new JTextField();
        field.setHorizontalAlignment(SwingConstants.CENTER);

        labels[0].setText("cancel");
        labels[1].setText("create");

        remove(labelBottom);
        add(field, "1, 1, 4, 1");

    }

    void prepare(String top, Runnable taskBack, Runnable taskConfirm) {

        super.prepare(top, "", taskBack, taskConfirm);
        field.setText("new problem set");


    }

    @Override
    public void applyTexture(TextureHolder holder) {
        super.applyTexture(holder);
        field.setBackground(holder.getColor("problem_preenabled"));
        field.setForeground(holder.getColor("problem_preenabled_text"));
    }

    @Override
    public void refresh(boolean isFullScreen) {
        super.refresh(isFullScreen);
        field.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 72 : 48));
    }

    public String getFieldText() {
        return field.getText();
    }
}
