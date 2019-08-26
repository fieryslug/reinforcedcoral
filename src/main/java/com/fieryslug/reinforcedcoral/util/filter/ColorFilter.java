package com.fieryslug.reinforcedcoral.util.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ColorFilter extends FilterCoral{


    private static final Set<Character> allowedChars = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    )));

    @Override
    protected boolean test(String text) {

        if("-1".contains(text)) return true;

        if (text.length() > 6) {
            return false;
        }
        boolean flag = true;
        for (int i = 0; i < text.length(); ++i) {
            if (!allowedChars.contains(text.charAt(i))) {
                flag = false;
                break;
            }
        }
        return flag;

    }
}
