package com.fieryslug.reinforcedcoral.util;

import org.json.JSONObject;

public interface JsonContainer {


    void readJson(JSONObject object);

    JSONObject exportAsJson();

}
