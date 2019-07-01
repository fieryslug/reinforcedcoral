package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.widget.Direction;

import java.util.HashMap;
import java.util.Map;

public enum ControlKey {

    UP(0), DOWN(1), LEFT(2), RIGHT(3), A(4), B(5), C(6), D(7), E(8), ENTER(9);


    int id;

    public static final Map<String, ControlKey> KEY_MAP = new HashMap<>();
    static {

        KEY_MAP.put("bt_up", UP);
        KEY_MAP.put("bt_down", DOWN);
        KEY_MAP.put("bt_left", LEFT);
        KEY_MAP.put("bt_right", RIGHT);
        KEY_MAP.put("bt_a", A);
        KEY_MAP.put("bt_b", B);
        KEY_MAP.put("bt_c", C);
        KEY_MAP.put("bt_d", D);
        KEY_MAP.put("bt_enter", ENTER);

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





}
