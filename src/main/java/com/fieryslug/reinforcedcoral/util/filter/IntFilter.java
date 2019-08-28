package com.fieryslug.reinforcedcoral.util.filter;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class IntFilter extends FilterCoral {

    private int min, max, chars;

    public IntFilter(int min, int max, int chars) {
        this.min = min;
        this.max = max;
        this.chars = chars;
    }

    @Override
    protected boolean test(String text) {

        if (min < 0 && text.equals("-")) return true;

        if (text.length() == 0) {
            return  true;
        }

        if(text.length() > this.chars)
            return false;

        try {
            int i = Integer.parseInt(text);
            return i < this.max && i >= this.min;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
