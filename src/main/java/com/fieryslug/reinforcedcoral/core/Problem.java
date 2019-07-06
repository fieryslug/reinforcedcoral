package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

public class Problem {

    private int points;
    private boolean fuzzy = true;
    public String name;
    private ArrayList<ControlKey> answer;
    public ArrayList<Page> pages;

    public Map<ArrayList<ControlKey>, Integer> keysPointsMap;
    public ArrayList<Problem> dependences;

    @Deprecated
    public Problem(String name, int points) {

        this.name = name;
        this.points = points;
        this.pages = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();

    }

    public Problem(String path) {

        try {
            //this.answer = new ArrayList<>();
            this.pages = new ArrayList<>();
            this.keysPointsMap = new HashMap<>();
            this.dependences = new ArrayList<>();

            final String PATH = "/res/problems/";
            JSONObject jsonObject = new JSONObject(FuncBox.readFile(PATH + path));

            this.name = jsonObject.getString("name");
            this.fuzzy = jsonObject.getBoolean("fuzzy");
            String answerString = jsonObject.getString("answer");
            this.answer = ControlKey.stringToArray(answerString);

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
        }
        catch (Exception e) {
            System.out.println("Error occurred while loading " + path + ":");
            e.printStackTrace();
        }
    }

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
        if(this.fuzzy) {
            ArrayList<ControlKey> list1 = (ArrayList<ControlKey>)teamAnswer.clone();
            Collections.sort(list1);
            for(ArrayList<ControlKey> candidate : this.keysPointsMap.keySet()) {

                ArrayList<ControlKey> list0 = (ArrayList<ControlKey>)candidate.clone();
                Collections.sort(list0);
                if(list0.equals(list1)) {
                    points1 = Integer.max(points1, this.keysPointsMap.get(candidate));
                }
            }
        }
        else {
            for(ArrayList<ControlKey> candidate : this.keysPointsMap.keySet()) {

                if(teamAnswer.equals(candidate)) {
                    points1 = Integer.max(points1, this.keysPointsMap.get(candidate));
                }
            }
        }
        //System.out.println("POINTS: " + points1);
        return points1;
    }

    public void addDependence(Problem... problems) {
        this.dependences.addAll(Arrays.asList(problems));
    }



}
