package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Font;

import javax.swing.JTextArea;

import info.clearthought.layout.TableLayout;

public class PanelInformation extends PanelInterior {

    private ButtonCoral button1;

    private PanelTitleBeautified panelTitle;
    private JTextArea textArea;
    private String infoRest;

    PanelInformation(PanelTitleBeautified panelTitle) {
        this.panelTitle = panelTitle;
        TextureHolder holder = TextureHolder.getInstance();

        this.infoRest = "\n\nReinforced Coral\n\nversion:   " + Reference.VERSION + "   updated " + Reference.UPDATE_DATE + "\n\ncredits:\nfieryslug, evan, tsengbing, yushuanlee\nsarah, jammychiou1";


        double[][] size = {{1.0d/5, 1.0d/5, 1.0d/5, 1.0d/5, 1.0d/5}, {1.0d/6, 1.0d/6, 1.0d/3, 1.0d/6, 1.0d/6}};
        setLayout(new TableLayout(size));

        this.button1 = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.button1.addActionListener(actionEvent -> {
            panelTitle.setCurrentPanelInterior(panelTitle.panelInterior);
            panelTitle.parent.switchPanel(panelTitle, panelTitle);
        });

        this.textArea = new JTextArea();
        this.textArea.setText("a problem occurred");
        this.textArea.setLineWrap(true);
        this.textArea.setEditable(false);
        add(this.button1, "2, 4");
        add(this.textArea, "1, 1, 3, 3");

    }

    @Override
    public void enter() {

        int buttonX =  (this.panelTitle.getWidth() / (3));
        int buttonY =  (this.panelTitle.getHeight() / (10));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.6);

        this.button1.resizeImageForIcons(buttonSize, buttonSize);


        String ip = FuncBox.getIp();

        String info = "IP: " + ip + "\n" + this.infoRest;

        this.textArea.setText(info);

    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void applyTexture(TextureHolder holder) {

        setBackground(holder.getColor("interior"));
        this.textArea.setSelectedTextColor(holder.getColor("text"));
        this.textArea.setSelectionColor(Reference.TRANSPARENT);
        this.textArea.setForeground(holder.getColor("text"));
        this.textArea.setBackground(holder.getColor("interior"));
        this.button1.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));


    }

    @Override
    public void refresh(boolean isFullScreen) {
        int buttonX = this.panelTitle.getWidth() / 3;
        int buttonY = this.panelTitle.getHeight() / 10;

        this.button1.resizeIconToSquare(buttonX, buttonY, 0.6);

        if (isFullScreen) {

            this.textArea.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 39));

        } else {
            this.textArea.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 26));
        }
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelTitle;
    }
}
