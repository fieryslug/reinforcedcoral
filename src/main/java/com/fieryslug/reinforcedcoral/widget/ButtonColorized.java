package com.fieryslug.reinforcedcoral.widget;

import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.util.TextureHolder;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ButtonColorized extends ButtonProblem {



    public ButtonColorized() {
        super(null, null, null);


    }

    @Override
    public void onPressed() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem_hover"));
        setBorder(BorderFactory.createLineBorder(holder.getColor("problem_hover_border"), 3));
    }

    @Override
    public void toDefault() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem"));
        try {
            this.label.setForeground(holder.getColor("problem_text"));
        } catch (Exception e) {

        }
        setBorder(BorderFactory.createLineBorder(holder.getColor("problem_border"), 3));
    }

    @Override
    public void onHover() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem_hover"));
        setBorder(BorderFactory.createLineBorder(holder.getColor("problem_hover_border"), 3));
    }

    @Override
    public void onEntered() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem_hover"));
        setBorder(BorderFactory.createLineBorder(holder.getColor("problem_hover_border"), 3));
        this.label.setForeground(holder.getColor("problem_hover_text"));
        this.isMouseInside = true;
    }

    @Override
    public void onExited() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem"));
        setBorder(BorderFactory.createLineBorder(holder.getColor("problem_border"), 3));
        this.label.setForeground(holder.getColor("problem_text"));
        this.isMouseInside = false;
    }

    @Override
    public void toPreenabled() {
        TextureHolder holder = TextureHolder.getInstance();
        setBackground(holder.getColor("problem_preenabled"));
        this.label.setForeground(holder.getColor("problem_preenabled_text"));
        setBorder(FuncBox.getLineBorder(holder.getColor("problem_preenabled_border"), 3));
    }

    @Override
    protected void refreshState() {
        TextureHolder holder = TextureHolder.getInstance();
        if(this.state == 0) {
            setEnabled(true);
            if(!Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                addMouseListener(this.mouseListener);
            if(this.selected) {
                setBackground(holder.getColor("problem_selected"));
                this.label.setForeground(holder.getColor("problem_selected_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_selected_border"), 6));
            }
            else {
                setBackground(holder.getColor("problem"));
                this.label.setForeground(holder.getColor("problem_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_border"), 3));
            }

        }
        if(this.state == 1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setBackground(holder.getColor("problem_selected_disabled"));
                this.label.setForeground(holder.getColor("problem_selected_disabled_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_selected_disabled_border"), 6));
            }
            else {
                setBackground(holder.getColor("problem_disabled"));
                this.label.setForeground(holder.getColor("problem_disabled_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_disabled_border"), 3));
            }
        }
        if(this.state == -1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setBackground(holder.getColor("problem_selected_preenabled"));
                this.label.setForeground(holder.getColor("problem_selected_preenabled_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_selected_preenabled_border"), 6));
            }
            else {
                setBackground(holder.getColor("problem_preenabled"));
                this.label.setForeground(holder.getColor("problem_preenabled_text"));
                setBorder(FuncBox.getLineBorder(holder.getColor("problem_preenabled_border"), 3));
            }

        }
    }

    @Override
    public void resizeImageForIcons(int x, int y) {
        refreshState();
    }
}
