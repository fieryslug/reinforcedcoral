package com.fieryslug.reinforcedcoral.panel.title;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.PanelInterior;
import com.fieryslug.reinforcedcoral.panel.PanelPrime;
import com.fieryslug.reinforcedcoral.panel.PanelThemes;
import com.fieryslug.reinforcedcoral.panel.PanelTitle;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.fieryslug.reinforcedcoral.widget.button.ButtonCoral;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import info.clearthought.layout.TableLayout;

public class PanelThemesNew extends PanelInterior {

    private PanelTitleBeautified panelTitle;

    private JScrollPane scrollPane;
    private JLabel labelImage;
    private JPanel scrollView;
    private JButton buttonConfirm;
    private JComboBox<String> comboBox;

    private JLabel label1;
    private JLabel label2;
    private ButtonCoral button1;
    private ButtonCoral button2;

    private String selectedTexture;

    public PanelThemesNew(PanelTitleBeautified panelTitle) {

        TextureHolder holder = TextureHolder.getInstance();

        this.panelTitle = panelTitle;

        this.selectedTexture = Preference.texture;

        //double[][] size = {{0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}, {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}};
        double[][] size = {FuncBox.createDivisionArray(12), FuncBox.createDivisionArray(12)};
        setLayout(new TableLayout(size));

        this.scrollView = new JPanel();
        this.scrollView.setBackground(Reference.DARKGRAY);
        this.scrollView.setLayout(new BoxLayout(this.scrollView, BoxLayout.Y_AXIS));

        this.scrollPane = new JScrollPane(this.scrollView);
        this.scrollPane.getViewport().setOpaque(true);
        this.scrollPane.getViewport().setBorder(null);
        this.scrollPane.setHorizontalScrollBar(null);
        this.scrollPane.setBorder(null);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.comboBox = new JComboBox<String>(Reference.TEXTURE_PACKS);
        this.comboBox.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 20));
        this.comboBox.setBackground(Reference.DARKDARKBLUE);
        this.comboBox.setForeground(Reference.WHITE);
        this.comboBox.getEditor().getEditorComponent().setBackground(Reference.DARKDARKBLUE);
        this.comboBox.setSelectedItem(Preference.texture);

        this.buttonConfirm = new JButton("ok");
        this.buttonConfirm.setBackground(Reference.BLAZE);
        this.buttonConfirm.setForeground(Reference.WHITE);
        this.buttonConfirm.setAlignmentX(CENTER_ALIGNMENT);
        this.buttonConfirm.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 40));
        this.buttonConfirm.setFocusPainted(false);

        this.labelImage = new JLabel();
        this.labelImage.setOpaque(false);
        this.labelImage.setBackground(Reference.TRANSPARENT);

        this.label1 = new JLabel("ok", SwingConstants.CENTER);
        this.label1.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.label1.setForeground(holder.getColor("text_light"));

        this.label2 = new JLabel("apply", SwingConstants.CENTER);
        this.label2.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, panelTitle.parent.isFullScreen ? 42 : 28));
        this.label2.setForeground(holder.getColor("text_light"));

        this.button1 = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.button2 = new ButtonCoral(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));

        this.comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                selectedTexture = itemEvent.getItem().toString();

                int width = getWidth() * 5/12;
                int height = getHeight() * 11/12;
                width = (int) (width * 0.9);
                height = (int) (height * 0.9);

                Image image = FuncBox.resizeImagePreservingRatio(MediaRef.TEXTURE_ICON_MAP.get(selectedTexture), width, height);

                labelImage.setIcon(new ImageIcon(image));
            }
        });

        addAndLinkButtons();

        //add(this.comboBox, "2, 2");
        //add(this.labelImage, "4, 2, 8, 7");
        //add(this.buttonConfirm, "4, 9, 5, 9");
        add(this.comboBox, "2, 3, 3, 3");
        add(this.labelImage, "6, 1, 10, 10");

        add(this.label1, "1, 8, 1, 9");
        add(this.label2, "4, 8, 4, 9");

        add(this.button1, "1, 10, 1, 11");
        add(this.button2, "4, 10, 4, 11");
    }

    private void addAndLinkButtons() {

        for (String texture : Reference.TEXTURE_PACKS) {

            JButton button = new JButton();
            button.setText(texture);
            button.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 20));
            button.setForeground(Reference.WHITE);
            button.setOpaque(false);
            button.setIcon(null);
            button.setBackground(Reference.TRANSPARENT);
            button.setBorder(null);
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(0, 70));
            button.setAlignmentX(CENTER_ALIGNMENT);
            this.scrollView.add(button);
        }


        this.button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Preference.texture = selectedTexture;
                TextureHolder.getInstance().read(Preference.texture);

                panelTitle.setCurrentPanelInterior(panelTitle.panelInterior);
                panelTitle.parent.switchPanel(panelTitle, panelTitle);
            }
        });

        this.button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Preference.texture = selectedTexture;
                TextureHolder holder = TextureHolder.getInstance();
                holder.read(Preference.texture);

                panelTitle.parent.switchPanel(panelTitle, panelTitle);
            }
        });


    }


    @Override
    public void refresh(boolean isFullScreen) {

        int width = getWidth() * 5/12;
        int height = getHeight() * 11/12;
        width = (int) (width * 0.9);
        height = (int) (height * 0.9);

        Image image = FuncBox.resizeImagePreservingRatio(MediaRef.TEXTURE_ICON_MAP.get(selectedTexture), width, height);
        labelImage.setIcon(new ImageIcon(image));

        if (isFullScreen) {

            this.comboBox.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 24));
            this.label1.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));
            this.label2.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 42));

        } else {
            this.comboBox.setFont(FontRef.getFont(FontRef.TAIPEI, Font.BOLD, 16));
            this.label1.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
            this.label2.setFont(FontRef.getFont(FontRef.NEMESIS, Font.PLAIN, 28));
        }

        int buttonX = (int) (this.panelTitle.getWidth() / (4));
        int buttonY = (int) (this.panelTitle.getHeight() / (6));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.4);

        this.button1.resizeImageForIcons(buttonSize, buttonSize);
        this.button2.resizeImageForIcons(buttonSize, buttonSize);

    }

    @Override
    public void enter() {

        int buttonX = (int) (this.panelTitle.getWidth() / (4));
        int buttonY = (int) (this.panelTitle.getHeight() / (6));
        int buttonSize = Math.min(buttonX, buttonY);
        buttonSize = (int) (buttonSize * 0.4);

        this.button1.resizeImageForIcons(buttonSize, buttonSize);
        this.button2.resizeImageForIcons(buttonSize, buttonSize);

    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void applyTexture(TextureHolder holder) {
        this.comboBox.setBackground(holder.getColor("teamu"));
        this.comboBox.setForeground(holder.getColor("teamu_text"));
        this.comboBox.setFocusable(false);
        setBackground(holder.getColor("interior"));

        this.label1.setForeground(holder.getColor("text_light"));
        this.label2.setForeground(holder.getColor("text_light"));

        this.button1.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
        this.button2.setImages(holder.getImage("button/button"), holder.getImage("button/button_hover"), holder.getImage("button/button_press"));
    }

    @Override
    public PanelPrime getPanelParent() {
        return this.panelTitle;
    }
}

