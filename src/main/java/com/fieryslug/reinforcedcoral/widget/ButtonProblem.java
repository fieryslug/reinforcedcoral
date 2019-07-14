package com.fieryslug.reinforcedcoral.widget;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ButtonProblem extends ButtonCoral {

    private boolean enabled;
    public int state; //0:normal, 1:deactivated, -1:to be activated
    private boolean selected;
    public Image imageDisabled;
    public Image imageSelected;
    public Image imageDisabledSelected;
    public Image imagePreenabled;
    public Image imagePreenabledSelected;
    public ImageIcon iconDisabled;
    private ImageIcon iconSelected;
    private ImageIcon iconDisabledSelected;
    private ImageIcon iconPreeanbled;
    private ImageIcon iconPreenabledSelected;

    public JLabel label;

    public ButtonProblem(Image imageDefault, Image imageHover, Image imagePress) {

        super(imageDefault, imageHover, imagePress);
        this.enabled = true;
        this.state = 0;

    }

    public void setImageDisabled(Image image) {

        this.imageDisabled = image;
        this.iconDisabled = new ImageIcon(this.imageDisabled);

    }

    public void setImageSelected(Image image) {

        this.imageSelected = image;
        this.iconSelected = new ImageIcon(this.imageSelected);

    }

    public void setImageDisabledSelected(Image image) {
        this.imageDisabledSelected = image;
        this.iconDisabledSelected = new ImageIcon(this.imageDisabledSelected);
    }

    public void setImagePreenabled(Image image) {
        this.imagePreenabled = image;
        this.iconPreeanbled = new ImageIcon(this.imagePreenabled);
    }

    public void setImagePreenabledSelected(Image image) {
        this.imagePreenabledSelected = image;
        this.iconPreenabledSelected = new ImageIcon(this.imagePreenabledSelected);
    }

    public void setState(int state) {

        this.state = state;
        refreshState();

    }
    public void setButtonSelected(boolean b) {

        this.selected = b;
        refreshState();
    }

    private void refreshState() {

        if(this.state == 0) {
            setEnabled(true);
            if(!Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                addMouseListener(this.mouseListener);
            if(this.selected) {
                setIcon(this.iconSelected);
            }
            else {
                setIcon(this.iconDefault);
            }

        }
        if(this.state == 1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setDisabledIcon(this.iconDisabledSelected);
            }
            else {
                setDisabledIcon(this.iconDisabled);
            }
        }
        if(this.state == -1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setDisabledIcon(this.iconPreenabledSelected);
            }
            else {
                setDisabledIcon(this.iconPreeanbled);
            }

        }
    }


    @Override
    public void resizeImageForIcons(int x, int y) {

        super.resizeImageForIcons(x, y);
        this.iconDisabled = ButtonCoral.resizeImage(this.imageDisabled, x, y);
        this.iconSelected = ButtonCoral.resizeImage(this.imageSelected, x, y);
        this.iconDisabledSelected = ButtonCoral.resizeImage(this.imageDisabledSelected, x, y);
        this.iconPreeanbled = ButtonCoral.resizeImage(this.imagePreenabled, x, y);
        this.iconPreenabledSelected = ButtonCoral.resizeImage(this.imagePreenabledSelected, x, y);

        refreshState();
    }
}
