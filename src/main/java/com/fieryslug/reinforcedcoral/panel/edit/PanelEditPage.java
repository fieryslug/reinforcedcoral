package com.fieryslug.reinforcedcoral.panel.edit;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.core.page.Widget;
import com.fieryslug.reinforcedcoral.frame.FrameCoral;
import com.fieryslug.reinforcedcoral.panel.subpanel.PanelProblem;
import com.fieryslug.reinforcedcoral.util.FontRef;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.TextureHolder;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    public PanelEditPage(FrameCoral parent, PanelEditProblem panelEditProblem) {
        super(parent);
        this.panelEditProblem = panelEditProblem;
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
            }

            component.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    System.out.println("clicked!");
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
        super.refreshRendering(isFullScreen);

        for (Widget widget : editMap.keySet()) {


            if (!widget.isAbstract()) {
                JComponent component = this.editMap.get(widget);
                if (widget.widgetType == Widget.EnumWidget.JLABEL) {
                    JTextField field = (JTextField) component;
                    field.setFont(FontRef.getFont(widget.getFontName(), widget.getBold() ? Font.BOLD : Font.PLAIN, isFullScreen ? widget.getTextSizeFull() : widget.getTextSize()));
                }
                if (widget.widgetType == Widget.EnumWidget.JTEXTAREA) {
                    JTextArea area = (JTextArea) component;
                    area.setFont(FontRef.getFont(widget.getFontName(), widget.getBold() ? Font.BOLD : Font.PLAIN, isFullScreen ? widget.getTextSizeFull() : widget.getTextSize()));
                }


            }

        }
    }

    @Override
    public void applyTexture() {
        super.applyTexture();

        TextureHolder holder = TextureHolder.getInstance();
        editBorder = FuncBox.getLineBorder(holder.getColor("edit_border"), 3);

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

                remove(editMap.get(currWidget));
                add(component, currWidget.constraints);


                if (currWidget.widgetType == Widget.EnumWidget.JLABEL) {
                    JLabel label = (JLabel) component;
                    JTextField field0 = (JTextField) (editMap.get(currWidget));
                    currWidget.content = field0.getText();
                    label.setText(currWidget.content);
                }


            }
            currWidget = null;
            panelEditProblem.labelLayout.setVisible(false);
            for (int i = 0; i < 4; ++i) {
                panelEditProblem.fields[i].setVisible(false);
                panelEditProblem.labels[i].setVisible(false);
            }
            return;
        }

        if(!widget.isAbstract()) {
            if (widgetInstanceMap.keySet().contains(currWidget)) {
                JComponent component = widgetInstanceMap.get(currWidget);

                if(editMap.get(currWidget) != null)
                    remove(editMap.get(currWidget));

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

            }
            currWidget = null;
            boolean flag = false;
            if (widget.widgetType == Widget.EnumWidget.JLABEL) {

                JTextField edit = (JTextField)(editMap.get(widget));
                edit.setText(widget.content);
                System.out.println(edit);
                remove(widgetInstanceMap.get(widget));
                add(edit, widget.constraints);
                System.out.println("added");
                currWidget = widget;
                flag = true;
            }
            if (widget.widgetType == Widget.EnumWidget.JTEXTAREA) {

                JTextArea area = (JTextArea) (editMap.get(widget));
                area.setText(widget.content);
                remove(widgetInstanceMap.get(widget));
                add(area, widget.constraints);
                currWidget = widget;
                flag = true;
            }
            if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                JLabel label = (JLabel) widgetInstanceMap.get(widget);
                label.setBorder(editBorder);
                currWidget = widget;
                flag = true;
            }

            if (flag) {
                panelEditProblem.labelLayout.setVisible(true);
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
        }

        repaint();
        panelEditProblem.refresh(panelEditProblem.isFullScreen());


    }
}
