package com.fieryslug.reinforcedcoral.core.problem;

import com.fieryslug.reinforcedcoral.core.Category;
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
    public String shortId;
    public String id;


    private ArrayList<ControlKey> answer;

    private ArrayList<Page> pages;
    private Page pageSolution;
    private ArrayList<Page> pagesExplanation;

    public Map<ArrayList<ControlKey>, Integer> keysPointsMap;
    private ArrayList<Problem> dependencies;
    private Set<Problem> dependents;

    protected ButtonProblem buttonProblem;

    private Category parentCat;

    @Deprecated
    public Problem(String name, int points) {

        this.name = name;
        this.points = points;

        this.pages = new ArrayList<>();
        this.pagesExplanation = new ArrayList<>();
        this.answer = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();
        this.dependencies = new ArrayList<>();
        dependents = new HashSet<>();


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

    public Problem(String path, String shortId) {
        this(path, false);
        this.shortId = shortId;

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
        json.put("answer", ControlKey.stringRepresentation(this.answer));
        json.put("duration", this.duration);

        JSONObject jsonPoints = new JSONObject();
        for (ArrayList<ControlKey> keys : this.keysPointsMap.keySet()) {
            jsonPoints.put(ControlKey.stringRepresentation(keys), this.keysPointsMap.get(keys));
        }

        json.put("points", jsonPoints);

        JSONArray arrayPages = new JSONArray();

        for (Page page : this.getPages()) {
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
        this.dependencies.addAll(Arrays.asList(problems));
        for (Problem problem : problems) {
            problem.dependents.add(this);
        }
    }




    private void readFromJson(JSONObject jsonObject) {
        this.pages = new ArrayList<>();
        this.keysPointsMap = new HashMap<>();
        this.dependencies = new ArrayList<>();
        dependents = new HashSet<>();
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
                Page page = new Page(objectPage);
                this.getPages().add(page);
                page.setParentProb(this);
            }

            Page page = new Page(jsonObject.getJSONObject("solution"));
            this.pageSolution = page;
            this.pageSolution.setParentProb(this);


            if (jsonObject.has("post_solution")) {
                JSONArray arraySolutions = jsonObject.getJSONArray("post_solution");
                for (int i = 0; i < arraySolutions.length(); ++i) {
                    JSONObject objectSolution = arraySolutions.getJSONObject(i);
                    Page page1 = new Page(objectSolution);
                    this.pagesExplanation.add(page1);
                    page1.setParentProb(this);
                }
            }


        } catch (Exception e) {
            System.out.println("Error occurred while parsing json file");
            e.printStackTrace();
        }
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public Category getParentCat() {
        return parentCat;
    }

    public void setParentCat(Category parentCat) {
        this.parentCat = parentCat;
    }

    public void normalizePages() {

        if(!isSpecial()) {
            Page page;

            for (int i = 0; i < getPages().size(); ++i) {
                page = pages.get(i).toNormalForm();
                pages.set(i, page);
                page.setParentProb(this);
            }
            page = pageSolution.toNormalForm();
            pageSolution = page;
            page.setParentProb(this);

            for (int i = 0; i < pagesExplanation.size(); ++i) {
                page = pagesExplanation.get(i).toNormalForm();
                pagesExplanation.set(i, page);
                page.setParentProb(this);
            }
        }

    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public Page getPageSolution() {
        return pageSolution;
    }

    public ArrayList<Page> getPagesExplanation() {
        return pagesExplanation;
    }

    public boolean isSpecial() {
        return false;
    }

    public Set<Problem> getDependents() {
        return dependents;
    }

    public ArrayList<Problem> getDependencies() {
        return dependencies;
    }

    public static Problem createEmptyProblem(ArrayList<ControlKey> controlKeys, int points, String shortId) {

        Problem problem = new Problem("", 0);
        problem.name = "new problem";
        problem.duration = 15;
        problem.shortId = shortId;
        problem.fuzzy = false;
        problem.pages.add(Page.createEmptyPage(true));
        problem.pageSolution = Page.createEmptyPage(false);
        problem.answer = controlKeys;
        problem.keysPointsMap.put(controlKeys, points);
        return problem;
    }

    public ArrayList<ControlKey> getTrueAnswer() {

        int points = -1;
        ArrayList<ControlKey> res = null;
        for (ArrayList<ControlKey> controlKeys : keysPointsMap.keySet()) {

            int points1 = keysPointsMap.get(controlKeys);

            if (points1 > points) {
                points = points1;
                res = controlKeys;
            }

        }
        return res;

    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setMonoAnswer(ControlKey key, int points) {

        keysPointsMap.clear();
        ArrayList<ControlKey> keys = new ArrayList<>();
        keys.add(key);
        keysPointsMap.put(keys, points);

    }

}
