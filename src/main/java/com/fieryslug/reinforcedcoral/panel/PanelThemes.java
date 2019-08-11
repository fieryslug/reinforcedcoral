package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Preference;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Ref;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import info.clearthought.layout.TableLayout;

public class PanelThemes extends PanelPrime {

    private JScrollPane scrollPane;
    private JLabel labelImage;
    private JPanel scrollView;
    private JButton buttonConfirm;
    private JComboBox<String> comboBox;

    private String selectedTexture;

    public PanelThemes(FrameCoral frameCoral) {
        super(frameCoral);

        this.selectedTexture = Preference.texture;

        double[][] size = {{0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}, {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1}};
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
        this.buttonConfirm.setFont(FontRef.TAIPEI40BOLD);
        this.buttonConfirm.setFocusPainted(false);

        this.labelImage = new JLabel();
        this.labelImage.setOpaque(false);
        this.labelImage.setBackground(Reference.TRANSPARENT);

        this.comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                selectedTexture = itemEvent.getItem().toString();
                Image image = FuncBox.resizeImagePreservingRatio(MediaRef.TEXTURE_ICON_MAP.get(selectedTexture), labelImage.getWidth(), labelImage.getHeight());

                labelImage.setIcon(new ImageIcon(image));
            }
        });

        addAndLinkButtons();

        add(this.comboBox, "2, 2");
        add(this.labelImage, "4, 2, 8, 7");
        add(this.buttonConfirm, "4, 9, 5, 9");
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

        this.buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Preference.texture = selectedTexture;
                TextureHolder.getInstance().read(Preference.texture);
                parent.switchPanel(PanelThemes.this, parent.panelTitle);
            }
        });

    }

    @Override
    protected void paintComponent(Graphics graphics) {

        super.paintComponent(graphics);

    }


    @Override
    public void refresh() {

        Image image = FuncBox.resizeImagePreservingRatio(MediaRef.TEXTURE_ICON_MAP.get(selectedTexture), labelImage.getWidth(), labelImage.getHeight());
        labelImage.setIcon(new ImageIcon(image));

    }
}
