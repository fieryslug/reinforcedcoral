package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.core.page.Widget;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.Reference;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.apache.commons.io.FileUtils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import info.clearthought.layout.TableLayoutConstraints;


public class PanelEditPage extends PanelProblem {

    private PanelEditProblem panelEditProblem;

    private BiMap<Widget, JComponent> editMap;

    private Widget currWidget;

    private Border editBorder;
    private Border normalBorder;

    private String tempConstraints = null;

    private LinkedHashSet<Widget> abstractWidgetsSet;
    private ArrayList<Widget> abstractWidgets;
    private int scrollNum;

    public PanelEditPage(FrameCoral parent, PanelEditProblem panelEditProblem) {
        super(parent);
        this.panelEditProblem = panelEditProblem;
        abstractWidgetsSet = new LinkedHashSet<>();
        abstractWidgets = new ArrayList<>();
    }


    @Override
    public void inflate2(Page page) {
        removeAll();
        clearThings();
        this.height = (int)this.getHeight();
        this.width = (int)this.getWidth();

        this.page = page;

        this.widgetInstanceMap = new HashMap<>();
        this.editMap = HashBiMap.create();

        for (Widget widget : page.widgets) {

            addAndConfigWidget(widget);
        }
        updateAbstractWidgets();

        if (abstractWidgets.size() > 0) {
            panelEditProblem.labelAbstractName.setVisible(true);
            panelEditProblem.labelAbstract.setVisible(true);
            panelEditProblem.labelAbstract.setText("extra (" + 1 + "/" + abstractWidgets.size() + ")  ");
        }
        else {
            panelEditProblem.labelAbstractName.setVisible(false);
            panelEditProblem.labelAbstract.setVisible(false);
        }
    }

    @Override
    protected void addAndConfigWidget(Widget widget) {
        super.addAndConfigWidget(widget);


        if (!widget.isAbstract()) {
            JComponent component = widgetInstanceMap.get(widget);

            if (widget.widgetType == Widget.EnumWidget.JLABEL) {
                JTextField field = new JTextField();
                if (widget.getCenter())
                    field.setHorizontalAlignment(SwingConstants.CENTER);
                field.setText(widget.content);

                editMap.put(widget, field);

            }
            if (widget.widgetType == Widget.EnumWidget.JTEXTAREA) {
                JTextArea area = new JTextArea();
                area.setEditable(true);
                area.setText(widget.content);
                area.setLineWrap(true);
                editMap.put(widget, area);
            }
            if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                JLabel label = (JLabel) component;
                JLabel labelNew = new JLabel();
                labelNew.setBorder(editBorder);
                labelNew.setIcon(label.getIcon());
                editMap.put(widget, labelNew);
            }

            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    //System.out.println("clicked!");
                    setCurrWidget(widget);

                }
            });
        }

        /*
        for (Widget widget1 : widgetInstanceMap.keySet()) {

            if (!widget1.isAbstract()) {
                JComponent component = widgetInstanceMap.get(widget1);

                if (widget1.widgetType == Widget.EnumWidget.JLABEL) {
                    JTextField field = new JTextField();
                    field.setHorizontalAlignment(SwingConstants.CENTER);
                    field.setText(widget1.content);

                    editMap.put(widget1, field);

                }

                component.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent mouseEvent) {
                        System.out.println("clicked!");
                        setCurrWidget(widget1);

                    }
                });
            }

        }
        */

    }

    @Override
    public void refreshRendering(boolean isFullScreen) {
        setPreferredSize(new Dimension(getWidth(), getHeight()));

        super.refreshRendering(isFullScreen);

        for (Widget widget : editMap.keySet()) {


            if (!widget.isAbstract()) {
                JComponent component = this.editMap.get(widget);
                if (widget.widgetType == Widget.EnumWidget.JLABEL) {
                    JTextField field = (JTextField) component;
                    field.setFont(FontRef.getFont(widget.getFontName(), widget.getBold() ? Font.BOLD : Font.PLAIN, isFullScreen ? widget.getTextSizeFull() : widget.getTextSize()));
                    if (widget.getCenter()) {
                        ((JLabel) widgetInstanceMap.get(widget)).setHorizontalAlignment(SwingConstants.CENTER);
                        field.setHorizontalAlignment(SwingConstants.CENTER);
                    } else {
                        System.out.println("not center!");
                        ((JLabel) widgetInstanceMap.get(widget)).setHorizontalAlignment(SwingConstants.LEFT);
                        field.setHorizontalAlignment(SwingConstants.LEFT);
                    }
                }
                if (widget.widgetType == Widget.EnumWidget.JTEXTAREA) {
                    JTextArea area = (JTextArea) component;
                    area.setFont(FontRef.getFont(widget.getFontName(), widget.getBold() ? Font.BOLD : Font.PLAIN, isFullScreen ? widget.getTextSizeFull() : widget.getTextSize()));
                }
                if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                    JLabel label = (JLabel) component;
                    label.setIcon(((JLabel)widgetInstanceMap.get(widget)).getIcon());
                    if (tempConstraints != null) {
                        TableLayoutConstraints constraints = new TableLayoutConstraints(tempConstraints);
                        int boxesX = constraints.col2 - constraints.col1 + 1;
                        int boxesY = constraints.row2 - constraints.row1 + 1;
                        int x = getWidth() * boxesX / 20;
                        int y = getHeight() * boxesY / 20;
                        Image image = getImageForWidget(widget);
                        label.setIcon(new ImageIcon(FuncBox.resizeImagePreservingRatio(image, x, y)));
                    }
                }

            }
        }


    }

    @Override
    public void applyTexture() {
        super.applyTexture();

        TextureHolder holder = TextureHolder.getInstance();
        editBorder = FuncBox.getLineBorder(holder.getColor("edit_border"), 3);
        normalBorder = FuncBox.getLineBorder(holder.getColor("text_light_2"), 3);

        for (Widget widget : editMap.keySet()) {


            if (!widget.isAbstract()) {
                JComponent component = this.editMap.get(widget);
                component.setBackground(holder.getColor("interior"));
                component.setBorder(FuncBox.getCompoundLineBorder(holder.getColor("edit_border"), 3));
                if(!widget.properties.containsKey("textcolor"))
                    component.setForeground(holder.getColor("text"));
                else
                    component.setForeground(widget.getTextColor());
            }

        }

    }

    void setCurrWidget(Widget widget) {

        if(widget == null) {
            if (widgetInstanceMap.keySet().contains(currWidget)) {
                JComponent component = widgetInstanceMap.get(currWidget);

                if(tempConstraints != null) {
                    currWidget.constraints = tempConstraints;
                    tempConstraints = null;
                }

                remove(editMap.get(currWidget));
                if(component != null)
                    add(component, currWidget.constraints);


                if (currWidget.widgetType == Widget.EnumWidget.JLABEL) {
                    JLabel label = (JLabel) component;
                    JTextField field0 = (JTextField) (editMap.get(currWidget));
                    currWidget.content = field0.getText();
                    label.setText(currWidget.content);
                }
                if (currWidget.widgetType == Widget.EnumWidget.JTEXTAREA) {
                    JTextArea area = (JTextArea) component;
                    JTextArea area0 = (JTextArea) (editMap.get(currWidget));
                    currWidget.content = area0.getText();
                    area.setText(currWidget.content);
                }
                if (currWidget.widgetType == Widget.EnumWidget.IMAGE) {
                    JLabel label = (JLabel) widgetInstanceMap.get(currWidget);
                    label.setBorder(null);
                }
                if (currWidget.widgetType == Widget.EnumWidget.AUDIO) {
                    panelEditProblem.labelAbstractName.setBorder(normalBorder);
                }

                System.out.println("widget is null");
                System.out.println(currWidget.widgetType);


            }
            currWidget = null;
            panelEditProblem.setAllUnvisible();

            repaint();
            panelEditProblem.refresh(panelEditProblem.isFullScreen());
            return;
        }

        if(!widget.isAbstract() || true) {
            if (widgetInstanceMap.keySet().contains(currWidget)) {
                JComponent component = widgetInstanceMap.get(currWidget);

                if(tempConstraints != null) {
                    currWidget.constraints = tempConstraints;
                    tempConstraints = null;
                }

               panelEditProblem.setAllUnvisible();

                if(editMap.get(currWidget) != null)
                    remove(editMap.get(currWidget));

                if(component != null)
                    add(component, currWidget.constraints);
                System.out.println(currWidget.widgetType);
                if (currWidget.widgetType == Widget.EnumWidget.JLABEL) {
                    JLabel label = (JLabel) component;
                    JTextField field0 = (JTextField) (editMap.get(currWidget));
                    currWidget.content = field0.getText();

                    label.setText(currWidget.content);
                }
                if (currWidget.widgetType == Widget.EnumWidget.JTEXTAREA) {
                    JTextArea area = (JTextArea) component;
                    JTextArea area0 = (JTextArea) (editMap.get(currWidget));
                    currWidget.content = area0.getText();
                    area.setText(currWidget.content);
                }
                if (currWidget.widgetType == Widget.EnumWidget.IMAGE) {
                    JLabel label = (JLabel) widgetInstanceMap.get(currWidget);
                    label.setBorder(null);
                }
                if (currWidget.widgetType == Widget.EnumWidget.AUDIO) {
                    panelEditProblem.labelAbstractName.setBorder(normalBorder);
                    System.out.println("hola!");
                }

            }
            currWidget = null;
            boolean flag = false;
            boolean flag2 = false;
            if (widget.widgetType == Widget.EnumWidget.JLABEL) {

                JTextField edit = (JTextField) (editMap.get(widget));
                edit.setText(widget.content);

                //System.out.println(edit);
                remove(widgetInstanceMap.get(widget));
                add(edit, widget.constraints);
                edit.requestFocus();
                //System.out.println("added");
                currWidget = widget;
                flag = true;
                flag2 = true;
                panelEditProblem.toggleButtons[0].setEnabled(true);

            }
            if (widget.widgetType == Widget.EnumWidget.JTEXTAREA) {

                JTextArea area = (JTextArea) (editMap.get(widget));
                area.setText(widget.content);
                remove(widgetInstanceMap.get(widget));
                add(area, widget.constraints);
                area.requestFocus();
                currWidget = widget;
                flag = true;
                flag2 = true;
                panelEditProblem.toggleButtons[0].setEnabled(false);
            }
            if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                JLabel label = (JLabel) editMap.get(widget);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setIcon(((JLabel)widgetInstanceMap.get(widget)).getIcon());
                remove(widgetInstanceMap.get(widget));
                add(label, widget.constraints);
                currWidget = widget;
                flag = true;
                String imageName = FuncBox.getRawFileName(widget.content);

                if(imageName.startsWith(Reference.EXTERNAL_PREFIX))
                    imageName = imageName.substring(Reference.EXTERNAL_PREFIX.length());
                else
                    imageName = FuncBox.getRawFileName(imageName);

                panelEditProblem.labelImage.setText(imageName);
                panelEditProblem.labelImage.setVisible(true);
                panelEditProblem.buttonImage.setVisible(true);
                panelEditProblem.labelChooseFile.setVisible(true);
            }
            if (widget.widgetType == Widget.EnumWidget.AUDIO) {

                panelEditProblem.labelAbstractName.setBorder(editBorder);
                currWidget = widget;


            }
            //----------
            if (flag) {
                panelEditProblem.labelLayout.setVisible(true);
                panelEditProblem.buttonApplyLayout.setVisible(true);
                panelEditProblem.labelApplyLayout.setVisible(true);
                for (int i = 0; i < 4; ++i) {
                    panelEditProblem.fields[i].setVisible(true);
                    panelEditProblem.labels[i].setVisible(true);
                    panelEditProblem.fields[i].selectAll();
                }
                TableLayoutConstraints constraints = widget.getConstraints();
                panelEditProblem.fields[0].setText("" + constraints.col1);
                panelEditProblem.fields[1].setText("" + constraints.row1);
                panelEditProblem.fields[2].setText("" + constraints.col2);
                panelEditProblem.fields[3].setText("" + constraints.row2);
            }

            if (flag2) {
                for (int i = 0; i < 4; ++i) {
                    panelEditProblem.toggleButtons[i].setVisible(true);
                    panelEditProblem.labelsAttr[i].setVisible(true);
                    panelEditProblem.fieldsAttr[i].setVisible(true);
                }
                panelEditProblem.toggleButtons[0].setSelected(widget.getCenter());
                panelEditProblem.toggleButtons[1].setSelected(widget.getBold());
                panelEditProblem.fieldsAttr[0].setText("" + widget.getTextSize());
                if(widget.properties.keySet().contains("textcolor")) {
                    Color color = widget.getTextColor();
                    panelEditProblem.fieldsAttr[1].setText(String.format("%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
                }
                else {
                    panelEditProblem.fieldsAttr[1].setText("-1");
                }
            }
            panelEditProblem.labelDelWidget.setVisible(true);
            panelEditProblem.buttonDelWidget.setVisible(true);
            panelEditProblem.labelNewWidget.setVisible(false);
            panelEditProblem.buttonNewWidget.setVisible(false);
            panelEditProblem.comboBox.setVisible(false);
        }

        else {

        }

        repaint();
        panelEditProblem.refresh(panelEditProblem.isFullScreen());


    }

    private void recolorWidget(Widget widget) {

        TextureHolder holder = TextureHolder.getInstance();

        if (widget.widgetType == Widget.EnumWidget.JLABEL) {
            JLabel label = (JLabel) widgetInstanceMap.get(widget);
            JTextField edit = (JTextField) editMap.get(widget);

            if(widget.properties.keySet().contains("textcolor")) {
                label.setForeground(widget.getTextColor());
                edit.setForeground(widget.getTextColor());
            }
            else {
                label.setForeground(holder.getColor("text"));
                edit.setForeground(holder.getColor("text"));
            }

        }
        if(widget.widgetType == Widget.EnumWidget.JTEXTAREA) {
            JTextArea area = (JTextArea) widgetInstanceMap.get(widget);
            JTextArea edit = (JTextArea) editMap.get(widget);
            if(widget.properties.keySet().contains("textcolor")) {
                area.setForeground(widget.getTextColor());
                edit.setForeground(widget.getTextColor());
            }
            else {
                area.setForeground(holder.getColor("text"));
                edit.setForeground(holder.getColor("text"));
            }
        }

    }

    void relocateWidget() {


        if (currWidget != null) {

            int x1 = Integer.parseInt(panelEditProblem.fields[0].getText());
            int y1 = Integer.parseInt(panelEditProblem.fields[1].getText());
            int x2 = Integer.parseInt(panelEditProblem.fields[2].getText());
            int y2 = Integer.parseInt(panelEditProblem.fields[3].getText());

            String constraints = x1 + ", " + y1 + ", " + x2 + ", " + y2;
            tempConstraints = constraints;
            JComponent component = editMap.get(currWidget);
            remove(component);
            add(component, constraints);
            repaint();
            revalidate();
            refreshRendering(panelEditProblem.isFullScreen());

        }

    }

    void reformatWidget() {

        if(currWidget != null && (currWidget.widgetType == Widget.EnumWidget.JLABEL || currWidget.widgetType == Widget.EnumWidget.JTEXTAREA)) {

            JComponent component = editMap.get(currWidget);
            boolean bold = panelEditProblem.toggleButtons[1].isSelected();
            boolean center = panelEditProblem.toggleButtons[0].isSelected();

            int textSize = Integer.parseInt(currWidget.properties.get("textsize"));
            try {
                textSize = Integer.parseInt(panelEditProblem.fieldsAttr[0].getText());
            } catch (NumberFormatException e) {
                //e.printStackTrace();
            }

            String color = currWidget.properties.get("textcolor");
            color = panelEditProblem.fieldsAttr[1].getText();

            if(color.equals("-1")) {
                currWidget.properties.remove("textcolor");
            }
            else {
                currWidget.properties.put("textcolor", color);
            }

            System.out.println(panelEditProblem.fieldsAttr[0].getText());

            currWidget.properties.put("textbold", String.valueOf(bold));
            currWidget.properties.put("center", String.valueOf(center));
            currWidget.properties.put("textsize", String.valueOf(textSize));
            currWidget.properties.put("textsizefull", String.valueOf(textSize * 3 / 2));


            repaint();
            revalidate();
            refreshRendering(panelEditProblem.isFullScreen());
            recolorWidget(currWidget);
        }
    }

    void setWidgetImage(File file) {

        if (currWidget.widgetType == Widget.EnumWidget.IMAGE) {

            String relId = Reference.EXTERNAL_PREFIX + FuncBox.getRawFileName(file.getPath());

            URL url = null;

            ProblemSet set = page.getParentProb().getParentCat().getParentSet();

            try {
                File file1 = new File(DataLoader.EXTERNAL_FOLDER + "/.tmp/" + file.getName());

                FileUtils.copyFile(file, file1);
                url = file1.toURL();
            } catch (Exception e) {
                e.printStackTrace();
            }

            set.getImageResources().put(relId, url);

            if (url != null) {
                System.out.println(relId + " added to resource set");
            }

            currWidget.content = Reference.EXTERNAL_PREFIX + file.getName();

            panelEditProblem.labelImage.setText(file.getName());

            refreshRendering(panelEditProblem.isFullScreen());

        }

    }

    private void updateAbstractWidgets() {

        abstractWidgetsSet.clear();
        abstractWidgets.clear();
        scrollNum = 0;
        if (page.type == Reference.MAGIC_PRIME) {

            for (Widget widget : page.widgets) {
                if (widget.isAbstract()) {
                    abstractWidgetsSet.add(widget);
                }
            }
        }
        abstractWidgets.addAll(abstractWidgetsSet);
        if(abstractWidgets.size() > 0)
            panelEditProblem.labelAbstractName.setText(abstractWidgets.get(scrollNum).content);

    }

    void scrollAbstractWidget(MouseWheelEvent event) {

        if(abstractWidgetsSet.size() > 0) {
            int size = abstractWidgets.size();
            boolean b = event.getPreciseWheelRotation() > 0;
            if (b) {
                scrollNum += 1;
                scrollNum %= size;
            } else {
                scrollNum -= 1;
                scrollNum %= size;
            }

            panelEditProblem.labelAbstractName.setText(abstractWidgets.get(scrollNum).content);
        }

    }

    Widget getCurrAbstractWidget() {
        if (abstractWidgets.size() > 0) {
            return abstractWidgets.get(scrollNum);
        }
        return null;
    }

    Widget getCurrWidget() {
        return currWidget;
    }
}
