package com.fieryslug.reinforcedcoral.panel;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.core.page.Widget;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.MediaRef;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.widget.FontChangerLabel;
import com.fieryslug.reinforcedcoral.widget.FontChangerTextArea;

//import layout.TableLayout;
//import layout.TableLayoutConstraints;
import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstraints;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PanelProblem extends JPanel {

    public JLabel labelTitle;
    public JTextArea areaDescription;
    public JLabel labelImage;

    private int height;
    private int width;
    private Page page;
    private TableLayout layout;
    private double[][] layoutSize;

    public Map<Widget, JComponent> widgetInstanceMap;

    private Map<String, AudioStream> playingAudios;

    public PanelProblem(FrameCoral parent) {

        this.layoutSize = new double[2][20];
        for(int i=0; i<20; ++i) this.layoutSize[0][i] = this.layoutSize[1][i] = 0.05;

        /*for(int i=0; i<20; ++i) {
            layoutSize[i][0] = layoutSize[i][1] = 0.05;
        }
        */

        this.layout = new TableLayout(layoutSize);
        setLayout(this.layout);

        this.areaDescription = new FontChangerTextArea();
        this.areaDescription.setFont(FontRef.TAIPEI30);
        this.areaDescription.setBackground(Reference.BLACK);
        this.areaDescription.setForeground(Reference.WHITE);
        this.areaDescription.setLineWrap(true);
        this.areaDescription.setEditable(false);
        this.setBackground(Reference.BLACK);

        this.labelTitle = new FontChangerLabel();
        this.labelTitle.setFont(FontRef.TAIPEI40BOLD);
        this.labelTitle.setBackground(Reference.BLACK);
        this.labelTitle.setForeground(Reference.WHITE);

        this.labelImage = new JLabel();
        this.labelImage.setBackground(Reference.BLACK);

        this.playingAudios = new HashMap<>();

        //setBorder(FontRef.BEVELGREEN);



    }
    /*
    public void inflate(Page page) {

        this.areaDescription.setText("");
        HTMLDocument document = (HTMLDocument)(this.areaDescription.getDocument());
        HTMLEditorKit editorKit = (HTMLEditorKit)(this.areaDescription.getEditorKit());

        try {
            editorKit.insertHTML(document, document.getLength(), page.htmlText, 0, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("inflated:\n" + page.htmlText);

    }
    */

    public void inflate2(Page page) {

        removeAll();
        clearThings();
        setLayout(new TableLayout(this.layoutSize));

        this.height = (int)this.getPreferredSize().getHeight();
        this.width = (int)this.getPreferredSize().getWidth();

        this.page = page;
        this.widgetInstanceMap = new HashMap<>();
        //answer
        if(page.type == -1) {

            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 19");

        }
        //title+description
        if(page.type == 0) {
            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));


            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 19");

        }
        //title
        if(page.type == 1) {
            this.labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
            this.labelTitle.setText(page.res.get(0));
            //this.areaDescription.setAlignmentX(CENTER_ALIGNMENT);
            //this.areaDescription.setAlignmentY(CENTER_ALIGNMENT);
            //this.areaDescription.setText(page.res.get(0));

            add(this.labelTitle, "0, 0, 19, 19");
        }
        //title+description+image
        if(page.type == 2) {

            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            Image image = MediaRef.getImage(page.res.get(2));
            image = FuncBox.resizeImagePreservingRatio(image, this.width * 8/20, this.height * 15/20);
            this.labelImage.setIcon(new ImageIcon(image));

            add(this.labelTitle, "0, 0, 19, 4");
            add(FuncBox.blankLabel(0, 0), "0, 5, 12, 7");
            add(this.areaDescription, "1, 7, 11, 19");
            add(this.labelImage, "12, 5, 19, 19");

        }
        //image
        if(page.type == 3) {
            Image image = MediaRef.getImage(page.res.get(0));
            image = FuncBox.resizeImagePreservingRatio(image, this.width, this.height);
            this.labelImage.setIcon(new ImageIcon(image));

        }
        //title + top text bottom image
        if (page.type == 4) {
            this.labelTitle.setText(page.res.get(0));
            this.areaDescription.setText(page.res.get(1));
            Image image = MediaRef.getImage(page.res.get(2));
            this.labelImage.setHorizontalAlignment(SwingConstants.CENTER);
            image = FuncBox.resizeImagePreservingRatio(image, this.width, this.height * 5 / 20);
            this.labelImage.setIcon(new ImageIcon(image));

            add(this.labelTitle, "0, 0, 19, 4");
            add(this.areaDescription, "1, 5, 18, 9");
            add(this.labelImage, "0, 10, 19, 19");
        }

        if(page.type == Reference.MAGIC_PRIME) {
            for (Widget widget : page.widgets) {
                addAndConfigWidget(widget);
            }
        }
    }

    private void clearThings() {

        this.labelTitle.setText("");
        this.labelTitle.setIcon(null);

        this.areaDescription.setText("");

        this.labelImage.setIcon(null);


    }

    public void enter() {
    }

    private void addAndConfigWidget(Widget widget) {

        if(widget.widgetType == Widget.EnumWidget.JLABEL) {
            JLabel label = new FontChangerLabel();
            label.setText(widget.content);
            label.setForeground(widget.getTextColor());
            label.setBackground(Reference.BLACK);
            label.setBackground(Reference.TRANSPARENT);
            if(widget.getCenter())
                label.setHorizontalAlignment(SwingConstants.CENTER);

            this.widgetInstanceMap.put(widget, label);
            add(label, widget.constraints);

        }
        if(widget.widgetType == Widget.EnumWidget.JTEXTAREA) {
            JTextArea area = new FontChangerTextArea();
            area.setText(widget.content);
            area.setForeground(widget.getTextColor());
            area.setBackground(Reference.BLACK);
            area.setLineWrap(true);
            area.setEditable(false);
            area.setSelectedTextColor(widget.getTextColor());
            area.setSelectionColor(Reference.TRANSPARENT);
            if (widget.getCenter()) {
                area.setAlignmentX(CENTER_ALIGNMENT);
            }
            this.widgetInstanceMap.put(widget, area);
            add(area, widget.constraints);
        }
        if(widget.widgetType == Widget.EnumWidget.IMAGE) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            Image image = MediaRef.getImage(widget.content);
            TableLayoutConstraints constraints = widget.getConstraints();
            int widthBoxes = constraints.col2 - constraints.col1 + 1;
            int heightBoxes = constraints.row2 - constraints.row1 + 1;
            image = FuncBox.resizeImagePreservingRatio(image, this.width * widthBoxes / 20, this.height * heightBoxes / 20);

            label.setIcon(new ImageIcon(image));
            this.widgetInstanceMap.put(widget, label);
            add(label, widget.constraints);
        }
        if (widget.widgetType == Widget.EnumWidget.AUDIO) {
            AudioStream audioStreamOld = this.playingAudios.get(widget.content);
            if (audioStreamOld != null) {
                AudioPlayer.player.stop(audioStreamOld);
                this.playingAudios.remove(widget.content);
            }
            AudioStream audioStream = MediaRef.playWav(widget.content);
            this.playingAudios.put(widget.content, audioStream);
        }
        if (widget.widgetType == Widget.EnumWidget.AUDIOSTOP) {
            AudioStream audioStream = this.playingAudios.get(widget.content);
            AudioPlayer.player.stop(audioStream);
        }
    }

    public void changeFonts(boolean isFullScreen) {
        this.height = (int)this.getPreferredSize().getHeight();
        this.width = (int)this.getPreferredSize().getWidth();
        System.out.println("height: " + this.height + ", width: " + this.width);
        if (isFullScreen) {
            if(this.page.type == 0) {
                this.labelTitle.setFont(FontRef.TAIPEI60BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI45);
            }
            if(this.page.type == 1) {
                this.labelTitle.setFont(FontRef.TAIPEI120);
            }
            if(this.page.type == 2) {
                this.labelTitle.setFont(FontRef.TAIPEI60BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI45);
                Image image = MediaRef.getImage(page.res.get(2));
                image = FuncBox.resizeImagePreservingRatio(image, this.width * 8/20, this.height * 15/20);
                this.labelImage.setIcon(new ImageIcon(image));
            }
            if(this.page.type == -1) {
                this.labelTitle.setFont(FontRef.TAIPEI60BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI45);
            }
            if(this.page.type == 4) {
                this.labelTitle.setFont(FontRef.TAIPEI60BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI45);
            }
            if(this.page.type == Reference.MAGIC_PRIME) {

                for(Widget widget : this.page.widgets) {
                    /*
                    if(!widget.isAbstract()) {
                        JComponent component = this.widgetInstanceMap.get(widget);
                        String fontName = widget.getFontName();
                        if (widget.getBold())
                            component.setFont(new Font("Taipei Sans TC Beta Bold", Font.PLAIN, widget.getTextSizeFull()));
                        else
                            component.setFont(new Font("Taipei Sans TC Beta Regular", Font.PLAIN, widget.getTextSizeFull()));
                    }
                    */
                    if(!widget.isAbstract()) {
                        JComponent component = this.widgetInstanceMap.get(widget);
                        component.setFont(widget.getFont(true));
                    }

                }

            }
        }
        else {
            if(this.page.type == 0) {
                this.labelTitle.setFont(FontRef.TAIPEI40BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI30);
            }
            if(this.page.type == 1) {
                this.labelTitle.setFont(FontRef.TAIPEI80);
            }
            if(this.page.type == 2) {
                this.labelTitle.setFont(FontRef.TAIPEI40BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI30);
                Image image = MediaRef.getImage(page.res.get(2));
                image = FuncBox.resizeImagePreservingRatio(image, this.width * 8/20, this.height * 15/20);
                this.labelImage.setIcon(new ImageIcon(image));
            }
            if(this.page.type == -1) {
                this.labelTitle.setFont(FontRef.TAIPEI40BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI30);
            }
            if (this.page.type == 4) {
                this.labelTitle.setFont(FontRef.TAIPEI40BOLD);
                this.areaDescription.setFont(FontRef.TAIPEI30);
            }
            if(this.page.type == Reference.MAGIC_PRIME) {

                for(Widget widget : this.page.widgets) {
                    /*
                    if(!widget.isAbstract()) {
                        JComponent component = this.widgetInstanceMap.get(widget);
                        if (widget.getBold())
                            component.setFont(new Font("Taipei Sans TC Beta Bold", Font.PLAIN, widget.getTextSize()));
                        else
                            component.setFont(new Font("Taipei Sans TC Beta Regular", Font.PLAIN, widget.getTextSize()));
                    }
                    */
                    if(!widget.isAbstract()) {
                        JComponent component = this.widgetInstanceMap.get(widget);
                        component.setFont(widget.getFont(false));
                    }
                }
            }
        }

        if(this.page.type == Reference.MAGIC_PRIME) {
            for (Widget widget : this.page.widgets) {

                if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                    JLabel label = (JLabel) this.widgetInstanceMap.get(widget);
                    Image image = MediaRef.getImage(widget.content);
                    TableLayoutConstraints constraints = widget.getConstraints();
                    int widthBoxes = constraints.col2 - constraints.col1 + 1;
                    int heightBoxes = constraints.row2 - constraints.row1 + 1;
                    Image image1 = FuncBox.resizeImagePreservingRatio(image, this.width * widthBoxes / 20, this.height * heightBoxes / 20);
                    System.out.println();
                    label.setIcon(new ImageIcon(image1));

                }
            }
        }
    }

    //called when switched back to menu
    public void clearSounds() {
        for (String audioName : this.playingAudios.keySet()) {
            AudioStream audioStream = this.playingAudios.get(audioName);
            AudioPlayer.player.stop(audioStream);
        }
        this.playingAudios.clear();
    }
}
