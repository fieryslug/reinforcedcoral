package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.LambdaMetafactory;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import info.clearthought.layout.TableLayout;

public class PanelConfirm extends PanelInterior {


    private PanelEdit panelEdit;

    private JLabel labelTop;
    protected JLabel labelBottom;

    protected JLabel[] labels;
    private ButtonCoral[] buttons;

    private Runnable taskConfirm;
    private Runnable taskBack;

    public PanelConfirm(PanelEdit panelEdit) {
        this.panelEdit = panelEdit;

        double[][] size = new double[][]{{0.1, 0.15, 0.25, 0.25, 0.15, 0.1}, {1.0d/3, 1.0d/3, 1.0d/6, 1.0d/6}};
        setLayout(new TableLayout(size));

        TextureHolder holder = TextureHolder.getInstance();
        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        labelTop = new JLabel("", SwingConstants.CENTER);
        labelBottom = new JLabel("", SwingConstants.CENTER);

        labels = new JLabel[2];

        for (int i = 0; i < 2; ++i) {
            labels[i] = new JLabel("", SwingConstants.CENTER);
        }
        labels[0].setText("back");
        labels[1].setText("confirm");

        buttons = new ButtonCoral[2];

        for (int i = 0; i < 2; ++i) {
            buttons[i] = new ButtonCoral(images[0], images[1], images[2]);
        }

        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (taskBack != null) {
                    taskBack.run();
                }
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (taskConfirm != null) {
                    taskConfirm.run();
                }
            }
        });


        add(labelTop, "1, 0, 4, 0");
        add(labelBottom, "1, 1, 4, 1");
        add(labels[0], "2, 2");
        add(buttons[0], "2, 3");
        add(labels[1], "3, 2");
        add(buttons[1], "3, 3");
    }


    @Override
    public void enter() {

        setVisible(false);
        applyTexture(TextureHolder.getInstance());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                refresh(isFullScreen());
            }
        });
        setVisible(true);
    }

    @Override
    public void exit() {

        buttons[1].setEnabled(true);

    }

    @Override
    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("interior"));

        for (int i = 0; i < 2; ++i) {
            labels[i].setForeground(holder.getColor("text_light"));
        }
        labelTop.setForeground(holder.getColor("text"));
        labelBottom.setForeground(holder.getColor("text"));


        Image[] images = new Image[]{holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press")};

        for (int i = 0; i < 2; ++i) {
            buttons[i].setImages(images[0], images[1], images[2]);
        }


    }

    @Override
    public void refresh(boolean isFullScreen) {


        for (int i = 0; i < 2; ++i) {
            labels[i].setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, isFullScreen ? 42 : 28));
        }
        labelTop.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 48 : 32));
        labelBottom.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, isFullScreen ? 48 : 32));

        int panelX = panelEdit.getWidth();
        int panelY = panelEdit.getHeight() * 3/5;

        for (int i = 0; i < 2; ++i) {
            buttons[i].resizeIconToSquare(panelX / 4, panelY / 6, 0.65);
        }

        if (Preference.autoScaleFontSize) {

            FontRef.scaleFont(labelTop);
            FontRef.scaleFont(labelBottom);
            for (int i = 0; i < 2; ++i) {
                FontRef.scaleFont(labels[i]);
            }
        }

    }

    @Override
    public PanelPrime getPanelParent() {
        return panelEdit;
    }

    @Override
    public boolean isFullScreen() {
        return panelEdit.parent.isFullScreen;
    }

    void prepare(String top, String bottom, Runnable taskBack, Runnable taskConfirm) {


        labelTop.setText(top);
        labelBottom.setText(bottom);
        this.taskBack = taskBack;
        this.taskConfirm = taskConfirm;

    }

    public ButtonCoral getButtonConfirm() {
        return buttons[1];
    }


}
