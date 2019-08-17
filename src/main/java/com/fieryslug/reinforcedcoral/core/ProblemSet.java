package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.problem.ProblemMine;
import com.fieryslug.reinforcedcoral.core.problem.ProblemNull;
import com.fieryslug.reinforcedcoral.minigame.match.ProblemMatch;
import com.fieryslug.reinforcedcoral.minigame.minesweeper.ProblemMineSweeper;
import com.fieryslug.reinforcedcoral.minigame.snake.ProblemSnake;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import jdk.nashorn.api.scripting.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProblemSet {

    private String name;
    public ArrayList<Category> categories;
    private BiMap<String, Category> idCatMap;
    private BiMap<String, Problem> idProbMap;

    public static Map<Class<? extends Problem>, Class<?>[]> argumentMap = new HashMap<>();

    static {
        argumentMap.put(ProblemMine.class, new Class<?>[]{String.class, Integer.class});
        argumentMap.put(ProblemMineSweeper.class, new Class<?>[]{String.class, Integer.class, Integer.class, Integer.class});
        argumentMap.put(ProblemNull.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemMatch.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemSnake.class, new Class<?>[]{String.class});
    }

    public ProblemSet(String name) {
        this.name = name;
        this.categories = new ArrayList<>();
        this.idCatMap = HashBiMap.create();
        this.idProbMap = HashBiMap.create();
    }

    public void loadProblemSet() {
        this.categories.clear();
        this.idCatMap.clear();
        this.idProbMap.clear();

        String path = DataLoader.EXTERNAL_FOLDER + "/problemsets/" + this.name;

        String metaRes = FuncBox.readExternalFile(path + "/meta.json");
        JSONObject metaJson = new JSONObject(metaRes);
        //this.name = metaJson.getString("name");

        JSONArray arrayCats = metaJson.getJSONArray("categories");
        JSONObject jsonProbs = metaJson.getJSONObject("problems");
        for (int i = 0; i < arrayCats.length(); ++i) {
            JSONObject jsonCat = arrayCats.getJSONObject(i);
            String catName = jsonCat.getString("name");
            String catId = jsonCat.getString("id");
            Category category = new Category(catName);

            this.idCatMap.put(catId, category);

            JSONArray arrayProbs = jsonProbs.getJSONArray(catId);
            for (int j = 0; j < arrayProbs.length(); ++j) {
                String probId = catId + "/" + arrayProbs.getString(j);

                String probPath = path + "/" + probId + ".json";
                Problem problem = new Problem("null", 0);

                JSONObject jsonProb = new JSONObject(FuncBox.readExternalFile(probPath));
                boolean flag = false;
                if (jsonProb.has("special")) flag = jsonProb.getBoolean("special");
                if (flag) {

                    System.out.println("---detected special problem, trying to create using reflection---" + probId);
                    try {

                        Class<?> clazz = Class.forName(jsonProb.getString("class"));
                        Class<?>[] argumentClasses = argumentMap.get(clazz);
                        Constructor<?> cons = clazz.getConstructor(argumentClasses);
                        JSONArray arrayArgs = jsonProb.getJSONArray("args");
                        Object[] arguments = new Object[arrayArgs.length()];

                        for (int k = 0; k < arrayArgs.length(); ++k) {
                            JSONObject jsonArg = arrayArgs.getJSONObject(k);
                            arguments[k] = argumentClasses[k].cast(jsonArg.get("value"));
                        }

                        System.out.println("required argument types: ");
                        for(Class clazz1 : argumentClasses) System.out.print(clazz1.getName() + " ");

                        System.out.println("arguments provided: ");
                        for(Object obj : arguments) System.out.print(obj.getClass() + ": " + obj + " ");
                        System.out.println("\n");

                        problem = (Problem)(cons.newInstance(arguments));

                    } catch (Exception e) {
                        System.out.println("failed to generate problem with " + probId);
                        e.printStackTrace();
                    }



                    /*
                    String specialName = jsonProb.getString("class");
                    if (specialName.equals(ProblemMine.class.getName())) {
                        System.out.println("creating mine!");
                        JSONArray arrayArgs = jsonProb.getJSONArray("args");
                        String name = arrayArgs.getJSONObject(0).getString("value");
                        int points = arrayArgs.getJSONObject(1).getInt("value");
                        problem = new ProblemMine(name, points);
                    }
                    */

                }
                else {
                    //System.out.println("probpath2: " + probPath);
                    System.out.println("creating problem " + probId);
                    problem = new Problem(this.name + "/" + probId + ".json", true);
                }
                category.addProblem(problem);
                this.idProbMap.put(probId, problem);
            }
            this.categories.add(category);
        }

        JSONObject jsonDepend = metaJson.getJSONObject("dependencies");

        for (Category category : this.categories) {
            for (Problem problem : category.problems) {

                String probId = this.idProbMap.inverse().get(problem);


                if (jsonDepend.has(probId)) {
                    JSONArray arrayDependencies = jsonDepend.getJSONArray(probId);
                    for (int p = 0; p < arrayDependencies.length(); ++p) {
                        String otherId = arrayDependencies.getString(p);
                        Problem problemOther = this.idProbMap.get(otherId);
                        problem.addDependence(problemOther);
                    }
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}
