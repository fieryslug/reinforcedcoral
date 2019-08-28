package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.SwingUtilities;

public class ProblemNull extends Problem {

    public ProblemNull(String name) {

        super(name, 0);

    }

    @Override
    public boolean onClick(PanelGame panelGame) {

        ButtonProblem buttonProblem = panelGame.problemButtonMap.get(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                buttonProblem.setState(1);
            }
        });

        return true;

    }

    @Override
    public JSONObject exportAsJson() {
        JSONObject jsonNull = new JSONObject();
        jsonNull.put("special", true);
        jsonNull.put("class", this.getClass().getName());
        JSONArray arrayArgs = new JSONArray();
        JSONObject jsonArg1 = new JSONObject();
        jsonArg1.put("arg", "name");
        jsonArg1.put("value", this.name);
        arrayArgs.put(jsonArg1);
        jsonNull.put("args", arrayArgs);
        return jsonNull;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}
