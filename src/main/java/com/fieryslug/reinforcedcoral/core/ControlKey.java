package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.widget.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ComboBoxEditor;

public enum ControlKey {

    UP(0), DOWN(1), LEFT(2), RIGHT(3), A(4), B(5), C(6), D(7), E(8), ENTER(9), DEL(10);


    int id;

    public static final Map<String, ControlKey> KEY_MAP = new HashMap<>();
    public static final Map<ControlKey, Character> KEY_CHARACTER_MAP = new HashMap<>();
    public static final Map<Character, ControlKey> CHARACTER_KEY_MAP = new HashMap<>();
    public static final ControlKey[] NORMAL_KEYS = new ControlKey[]{A, B, C, D, UP, DOWN, LEFT, RIGHT};

    static {

        KEY_MAP.put("bt_up", UP);
        KEY_MAP.put("bt_down", DOWN);
        KEY_MAP.put("bt_left", LEFT);
        KEY_MAP.put("bt_right", RIGHT);
        KEY_MAP.put("bt_a", A);
        KEY_MAP.put("bt_b", B);
        KEY_MAP.put("bt_c", C);
        KEY_MAP.put("bt_d", D);
        KEY_MAP.put("bt_e", E);
        KEY_MAP.put("bt_enter", ENTER);
        KEY_MAP.put("bt_delete", DEL);

        KEY_CHARACTER_MAP.put(UP, '↑');
        KEY_CHARACTER_MAP.put(DOWN, '↓');
        KEY_CHARACTER_MAP.put(LEFT, '←');
        KEY_CHARACTER_MAP.put(RIGHT, '→');
        KEY_CHARACTER_MAP.put(A, 'A');
        KEY_CHARACTER_MAP.put(B, 'B');
        KEY_CHARACTER_MAP.put(C, 'C');
        KEY_CHARACTER_MAP.put(D, 'D');
        KEY_CHARACTER_MAP.put(E, 'E');

        CHARACTER_KEY_MAP.put('↑', UP);
        CHARACTER_KEY_MAP.put('↓', DOWN);
        CHARACTER_KEY_MAP.put('←', LEFT);
        CHARACTER_KEY_MAP.put('→', RIGHT);
        CHARACTER_KEY_MAP.put('A', A);
        CHARACTER_KEY_MAP.put('B', B);
        CHARACTER_KEY_MAP.put('C', C);
        CHARACTER_KEY_MAP.put('D', D);
        CHARACTER_KEY_MAP.put('E', E);


    }

    ControlKey(int id) {

        this.id = id;

    }


    public static ControlKey getKey(String str) {

        ControlKey controlKey = KEY_MAP.get(str);
        return controlKey;

    }

    public Direction toDirection() {

        switch (this) {

            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
            case LEFT:
                return Direction.LEFT;
            case RIGHT:
                return Direction.RIGHT;
            default:
                return null;
        }

    }



    public static String stringRepresentation(ArrayList<ControlKey> controlKeys) {

        //if(controlKeys == null) return "";

        String s = "";
        for (ControlKey key : controlKeys) {
            s = s + KEY_CHARACTER_MAP.get(key);
        }

        return s;
    }

    public static ArrayList<ControlKey> stringToArray(String s) {

        ArrayList<ControlKey> tempRes = new ArrayList<>();
        for (char c : s.toCharArray()) {
            tempRes.add(CHARACTER_KEY_MAP.get(c));
        }

        return tempRes;

    }


    @Override
    public String toString() {
        return KEY_CHARACTER_MAP.get(this).toString();
    }
}
