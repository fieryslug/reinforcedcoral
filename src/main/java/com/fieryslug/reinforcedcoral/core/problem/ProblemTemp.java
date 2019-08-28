package com.fieryslug.reinforcedcoral.core.problem;

import org.json.JSONArray;
import org.json.JSONObject;

public class ProblemTemp extends ProblemNull {

    public ProblemTemp() {
        super("void");
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public JSONObject exportAsJson() {

        JSONObject jsonNull = new JSONObject();
        jsonNull.put("special", true);
        jsonNull.put("class", this.getClass().getName());
        JSONArray arrayArgs = new JSONArray();

        jsonNull.put("args", arrayArgs);
        return jsonNull;

    }
}
