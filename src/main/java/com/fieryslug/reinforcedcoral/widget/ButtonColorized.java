package com.fieryslug.reinforcedcoral.widget;

import java.util.Arrays;

public class ButtonColorized extends ButtonProblem {



    public ButtonColorized() {
        super(null, null, null);


    }

    @Override
    public void onHover() {

    }

    @Override
    public void toDefault() {

    }

    @Override
    public void onEntered() {

    }

    @Override
    public void onExited() {

    }

    @Override
    protected void refreshState() {
        if(this.state == 0) {
            setEnabled(true);
            if(!Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                addMouseListener(this.mouseListener);
            if(this.selected) {

            }
            else {

            }

        }
        if(this.state == 1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {

            }
            else {

            }
        }
        if(this.state == -1) {
            setEnabled(false);
            if(Arrays.asList(getMouseListeners()).contains(this.mouseListener))
                removeMouseListener(this.mouseListener);
            if(this.selected) {

            }
            else {

            }

        }
    }

    @Override
    public void resizeImageForIcons(int x, int y) {
        refreshState();
    }
}
