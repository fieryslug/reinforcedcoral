package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.core.ControlKey;
import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.panel.PanelGame;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.fieryslug.reinforcedcoral.widget.button.ButtonProblem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class Problem {

    private int points;
    private int duration;
    private boolean fuzzy = true;
    public String name;
    private ArrayList<ControlKey> answer;
    public ArrayList<Page> pages;
    public Page pageSolution;
    public ArrayList<Page> pagesExplanation;

    public Map<ArrayList<ControlKey>, Integer> keysPointsMap;
    public ArrayList<Problem> dependences;

    protected ButtonProblem buttonProblem;

    @Deprecated
    public Problem(String name, int points) {

        this.name = name;
        this.points = points;
        this.pages = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();
        this.dependences = new ArrayList<>();
        this.pagesExplanation = new ArrayList<>();

    }

    public Problem(JSONObject jsonObject) {
        readFromJson(jsonObject);
    }

    public Problem(String path, boolean isExternal) {

        JSONObject jsonObject;

        if (isExternal) {
            final String PATH = DataLoader.EXTERNAL_FOLDER + "/problemsets/";
            jsonObject = new JSONObject(FuncBox.readExternalFile(PATH + path));
        }
        else {
            final  String PATH = "/res/problems/";
            jsonObject = new JSONObject(FuncBox.readFile(PATH + path));
        }

        readFromJson(jsonObject);

        /*
        try {
            this.name = jsonObject.getString("name");
            this.fuzzy = jsonObject.getBoolean("fuzzy");
            String answerString = jsonObject.getString("answer");
            this.answer = ControlKey.stringToArray(answerString);

            this.duration = jsonObject.optInt("duration", 15);
            //System.out.println(this.name + ":" + this.duration);

            JSONObject pointsObj = jsonObject.getJSONObject("points");
            Set<String> pointsKeys = pointsObj.keySet();
            for (String s : pointsKeys) {
                int points = pointsObj.getInt(s);
                ArrayList<ControlKey> tempKeys = ControlKey.stringToArray(s);
                this.keysPointsMap.put(tempKeys, points);
            }

            JSONArray arrayPages = jsonObject.getJSONArray("pages");
            for (int i = 0; i < arrayPages.length(); ++i) {
                JSONObject objectPage = arrayPages.getJSONObject(i);
                this.pages.add(new Page(objectPage));
            }

            Page page = new Page(jsonObject.getJSONObject("solution"));
            this.pageSolution = page;


            if (jsonObject.has("post_solution")) {
                JSONArray arraySolutions = jsonObject.getJSONArray("post_solution");
                for (int i = 0; i < arraySolutions.length(); ++i) {
                    JSONObject objectSolution = arraySolutions.getJSONObject(i);
                    this.pagesExplanation.add(new Page(objectSolution));
                }
            }
        } catch (Exception e) {
            System.out.println("Error occurred while loading " + path + ":");
            e.printStackTrace();
        }
        */
    }

    public Problem(String path) {
        this(path, false);

        /*
        this.pages = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();
        this.dependences = new ArrayList<>();
        this.pagesExplanation = new ArrayList<>();
        try {
            //this.answer = new ArrayList<>();


            final String PATH = "/res/problems/";
            JSONObject jsonObject = new JSONObject(FuncBox.readFile(PATH + path));

            this.name = jsonObject.getString("name");
            this.fuzzy = jsonObject.getBoolean("fuzzy");
            String answerString = jsonObject.getString("answer");
            this.answer = ControlKey.stringToArray(answerString);

            this.duration = jsonObject.optInt("duration", 15);
            //System.out.println(this.name + ":" + this.duration);

            JSONObject pointsObj = jsonObject.getJSONObject("points");
            Set<String> pointsKeys = pointsObj.keySet();
            for (String s : pointsKeys) {
                int points = pointsObj.getInt(s);
                ArrayList<ControlKey> tempKeys = ControlKey.stringToArray(s);
                this.keysPointsMap.put(tempKeys, points);
            }

            JSONArray arrayPages = jsonObject.getJSONArray("pages");
            for (int i = 0; i < arrayPages.length(); ++i) {
                JSONObject objectPage = arrayPages.getJSONObject(i);
                this.pages.add(new Page(objectPage));
            }

            Page page = new Page(jsonObject.getJSONObject("solution"));
            this.pageSolution = page;


            if (jsonObject.has("post_solution")) {
                JSONArray arraySolutions = jsonObject.getJSONArray("post_solution");
                for (int i = 0; i < arraySolutions.length(); ++i) {
                    JSONObject objectSolution = arraySolutions.getJSONObject(i);
                    this.pagesExplanation.add(new Page(objectSolution));
                }
            }


        } catch (Exception e) {
            System.out.println("Error occurred while loading " + path + ":");
            e.printStackTrace();
        }
        */
    }

    public void bindButton(ButtonProblem button) {
        this.buttonProblem = button;
        onButtonBound();
    }

    protected void onButtonBound() {

    }

    public boolean onClick(PanelGame panelGame) {
        return false;
    }

    @Deprecated
    public boolean checkAnswer(ArrayList<ControlKey> answer) {

        if (this.fuzzy) {
            Collections.sort(answer);
            Collections.sort(this.answer);

            return answer.equals(this.answer);

        } else {

            if (answer.size() != this.answer.size()) return false;
            for (int i = 0; i < answer.size(); ++i) {
                if (!answer.get(i).equals(this.answer.get(i))) return false;
            }
            return true;

        }
    }

    public int getPoints(ArrayList<ControlKey> teamAnswer) {

        int points1 = 0;
        if (this.fuzzy) {
            ArrayList<ControlKey> list1 = (ArrayList<ControlKey>) teamAnswer.clone();
            Collections.sort(list1);
            for (ArrayList<ControlKey> candidate : this.keysPointsMap.keySet()) {

                ArrayList<ControlKey> list0 = (ArrayList<ControlKey>) candidate.clone();
                Collections.sort(list0);
                if (list0.equals(list1)) {
                    points1 = Integer.max(points1, this.keysPointsMap.get(candidate));
                }
            }
        } else {
            for (ArrayList<ControlKey> candidate : this.keysPointsMap.keySet()) {

                if (teamAnswer.equals(candidate)) {
                    points1 = Integer.max(points1, this.keysPointsMap.get(candidate));
                }
            }
        }
        //System.out.println("POINTS: " + points1);
        return points1;
    }

    public JSONObject exportAsJson() {

        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("fuzzy", this.fuzzy);
        json.put("answer", this.answer);
        json.put("duration", this.duration);

        JSONObject jsonPoints = new JSONObject();
        for (ArrayList<ControlKey> keys : this.keysPointsMap.keySet()) {
            jsonPoints.put(ControlKey.stringRepresentation(keys), this.keysPointsMap.get(keys));
        }

        json.put("points", jsonPoints);

        JSONArray arrayPages = new JSONArray();

        for (Page page : this.pages) {
            arrayPages.put(page.exportAsJson());
        }

        json.put("pages", arrayPages);

        json.put("solution", this.pageSolution.exportAsJson());

        if (this.pagesExplanation.size() > 0) {
            JSONArray arrayExplanation = new JSONArray();
            for (Page page : this.pagesExplanation) {
                arrayExplanation.put(page.exportAsJson());
            }
            json.put("post_solution", arrayExplanation);
        }

        return json;

    }


    public void addDependence(Problem... problems) {
        this.dependences.addAll(Arrays.asList(problems));
    }


    public int getDuration() {
        return this.duration;
    }

    private void readFromJson(JSONObject jsonObject) {
        this.pages = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();
        this.dependences = new ArrayList<>();
        this.pagesExplanation = new ArrayList<>();
        try {
            //this.answer = new ArrayList<>();

            this.name = jsonObject.getString("name");
            this.fuzzy = jsonObject.getBoolean("fuzzy");
            String answerString = jsonObject.getString("answer");
            this.answer = ControlKey.stringToArray(answerString);

            this.duration = jsonObject.optInt("duration", 15);
            //System.out.println(this.name + ":" + this.duration);

            JSONObject pointsObj = jsonObject.getJSONObject("points");
            Set<String> pointsKeys = pointsObj.keySet();
            for (String s : pointsKeys) {
                int points = pointsObj.getInt(s);
                ArrayList<ControlKey> tempKeys = ControlKey.stringToArray(s);
                this.keysPointsMap.put(tempKeys, points);
            }

            JSONArray arrayPages = jsonObject.getJSONArray("pages");
            for (int i = 0; i < arrayPages.length(); ++i) {
                JSONObject objectPage = arrayPages.getJSONObject(i);
                this.pages.add(new Page(objectPage));
            }

            Page page = new Page(jsonObject.getJSONObject("solution"));
            this.pageSolution = page;


            if (jsonObject.has("post_solution")) {
                JSONArray arraySolutions = jsonObject.getJSONArray("post_solution");
                for (int i = 0; i < arraySolutions.length(); ++i) {
                    JSONObject objectSolution = arraySolutions.getJSONObject(i);
                    this.pagesExplanation.add(new Page(objectSolution));
                }
            }


        } catch (Exception e) {
            System.out.println("Error occurred while parsing json file");
            e.printStackTrace();
        }
    }
}
