package com.fieryslug.reinforcedcoral.widget;

import javax.swing.*;
import java.awt.*;

public class ButtonProblem extends ButtonCoral {

    private boolean enabled;
    private boolean selected;
    public Image imageDisabled;
    public Image imageSelected;
    public Image imageDisabledSelected;
    private ImageIcon iconDisabled;
    private ImageIcon iconSelected;
    private ImageIcon iconDisabledSelected;

    public ButtonProblem(Image imageDefault, Image imageHover, Image imagePress) {

        super(imageDefault, imageHover, imagePress);
        this.enabled = true;

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

    public void setButtonEnabled(boolean b) {

        this.enabled = b;
        refreshState(true);

    }
    public void setButtonSelected(boolean b) {

        this.selected = b;
        refreshState(false);
    }

    private void refreshState(boolean alterMouseListener) {

        if(this.enabled) {
            if(alterMouseListener)
                addMouseListener(this.mouseListener);
            if(this.selected) {
                setIcon(this.iconSelected);
            }
            else {
                setIcon(this.iconDefault);
            }

        }
        else {
            if(alterMouseListener)
                removeMouseListener(this.mouseListener);
            if(this.selected) {
                setIcon(this.iconDisabledSelected);
            }
            else {
                setIcon(this.iconDisabled);
            }

        }
    }


    @Override
    public void resizeImageForIcons(int x, int y) {

        super.resizeImageForIcons(x, y);
        this.iconDisabled = ButtonCoral.resizeImage(this.imageDisabled, x, y);
        this.iconSelected = ButtonCoral.resizeImage(this.imageSelected, x, y);
        this.iconDisabledSelected = ButtonCoral.resizeImage(this.imageDisabledSelected, x, y);


        refreshState(false);
    }
}
