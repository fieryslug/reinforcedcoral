package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.problem.ProblemDisabled;
import com.fieryslug.reinforcedcoral.core.problem.ProblemDummy;
import com.fieryslug.reinforcedcoral.core.problem.ProblemMine;
import com.fieryslug.reinforcedcoral.core.problem.ProblemNull;
import com.fieryslug.reinforcedcoral.minigame.match.ProblemMatch;
import com.fieryslug.reinforcedcoral.minigame.minesweeper.ProblemMineSweeper;
import com.fieryslug.reinforcedcoral.minigame.snake.ProblemSnake;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProblemSet {

    private String id;
    private String name;
    private ArrayList<Category> categories;
    private BiMap<String, Category> idCatMap;
    private BiMap<String, Problem> idProbMap;
    private Map<Problem, String> probShortIdMap;

    public static Map<Class<? extends Problem>, Class<?>[]> argumentMap = new HashMap<>();

    static {
        argumentMap.put(ProblemMine.class, new Class<?>[]{String.class, Integer.class});
        argumentMap.put(ProblemMineSweeper.class, new Class<?>[]{String.class, Integer.class, Integer.class, Integer.class});
        argumentMap.put(ProblemNull.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemMatch.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemSnake.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemDisabled.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemDummy.class, new Class<?>[]{String.class});
    }

    public ProblemSet(String id) {
        this.id = id;
        this.categories = new ArrayList<>();
        this.idCatMap = HashBiMap.create();
        this.idProbMap = HashBiMap.create();
        this.probShortIdMap = new HashMap<>();
    }

    public void loadProblemSet() {
        this.categories.clear();
        this.idCatMap.clear();
        this.idProbMap.clear();
        this.probShortIdMap.clear();

        String path = DataLoader.EXTERNAL_FOLDER + "/problemsets/" + this.id;

        String metaRes = FuncBox.readExternalFile(path + "/meta.json");
        JSONObject metaJson = new JSONObject(metaRes);

        this.name = metaJson.getString("name");
        //this.name = metaJson.getString("name");

        JSONArray arrayCats = metaJson.getJSONArray("categories");
        JSONObject jsonProbs = metaJson.getJSONObject("problems");
        for (int i = 0; i < arrayCats.length(); ++i) {
            JSONObject jsonCat = arrayCats.getJSONObject(i);
            String catName = jsonCat.getString("name");
            String catId = jsonCat.getString("id");
            Category category = new Category(catName, catId);

            this.idCatMap.put(catId, category);

            JSONArray arrayProbs = jsonProbs.getJSONArray(catId);
            for (int j = 0; j < arrayProbs.length(); ++j) {
                String probId = catId + "/" + arrayProbs.getString(j);
                String probShortId = arrayProbs.getString(j);

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
                    problem = new Problem(this.id + "/" + probId + ".json", true);
                }
                problem.setShortId(probShortId);
                category.addProblems(problem);
                this.idProbMap.put(probId, problem);
                this.probShortIdMap.put(problem, probShortId);
            }
            this.categories.add(category);
        }

        JSONObject jsonDepend = metaJson.getJSONObject("dependencies");

        for (Category category : this.categories) {
            for (Problem problem : category.problems) {

                //String probId = this.idProbMap.inverse().get(problem);
                String probId = problem.id;


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

    public String getId() {
        return id;
    }

    public boolean dumpProblemSet(String name1, boolean override) {

        DataLoader loader = DataLoader.getInstance();
        String path = DataLoader.EXTERNAL_FOLDER + "/problemsets/" + name1;
        File file = new File(path);
        if (file.exists() && !override) {
            System.out.println(path + " already exists, unable to dump data");
            return false;
        }

        loader.deleteDirectory(file);

        String data = exportMeta().toString(2);

        loader.writeToFile(path + "/meta.json", data, override);

        for (Category category : this.categories) {
            //loader.checkFile(path + "/" + this.idCatMap.inverse().get(category), true);
            loader.checkFile(path + "/" + category.id, true);
            for (Problem problem : category.problems) {
                //String probPath = path + "/" + this.idProbMap.inverse().get(problem) + ".json";
                System.out.println(problem.name + ": " + problem.id);
                String probPath = path + "/" + problem.id + ".json";
                String dataProb = problem.exportAsJson().toString(2);
                loader.writeToFile(probPath, dataProb, override);
            }
        }

        JSONObject jsonIndex = new JSONObject(FuncBox.readExternalFile(DataLoader.EXTERNAL_FOLDER + "/problemsets/index.json"));
        JSONArray arrayIndex = jsonIndex.getJSONArray("index");
        arrayIndex.put(name1);
        loader.writeToFile(DataLoader.EXTERNAL_FOLDER + "/problemsets/index.json", jsonIndex.toString(2), true);
        return true;
    }

    public JSONObject exportMeta() {
        JSONObject jsonMeta = new JSONObject();
        JSONArray arrayCats = new JSONArray();
        jsonMeta.put("name", this.name);
        for (Category category : this.categories) {
            JSONObject jsonCat = new JSONObject();
            jsonCat.put("name", category.name);
            //jsonCat.put("id", this.idCatMap.inverse().get(category));
            jsonCat.put("id", category.id);
            arrayCats.put(jsonCat);
        }
        jsonMeta.put("categories", arrayCats);

        JSONObject jsonProbs = new JSONObject();

        for (Category category : this.categories) {
            JSONArray arrayProbs = new JSONArray();
            for (Problem problem : category.problems) {

                String probShortId = this.probShortIdMap.get(problem);
                arrayProbs.put(probShortId);
            }
            //jsonProbs.put(this.idCatMap.inverse().get(category), arrayProbs);
            jsonProbs.put(category.id, arrayProbs);
        }
        jsonMeta.put("problems", jsonProbs);

        JSONObject jsonDepend = new JSONObject();

        for (Category category : this.categories) {
            for (Problem problem : category.problems) {
                if (problem.dependences.size() > 0) {
                    JSONArray arrayDependencies = new JSONArray();
                    for (Problem other : problem.dependences) {
                        //arrayDependencies.put(this.idProbMap.inverse().get(other));
                        arrayDependencies.put(other.id);
                    }
                    //jsonDepend.put(this.idProbMap.inverse().get(problem), arrayDependencies);
                    jsonDepend.put(problem.id, arrayDependencies);
                }
            }
        }

        jsonMeta.put("dependencies", jsonDepend);
        return jsonMeta;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {

        this.name = name;

    }

    public String getName() {
        return this.name;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        this.idCatMap.put(category.id, category);
        for (Problem problem : category.problems) {

            this.idProbMap.put(problem.id, problem);
            this.probShortIdMap.put(problem, problem.shortId);

        }
    }

    ArrayList<Category> getCategories() {
        return this.categories;
    }

    public int getCategoriesCount() {
        return this.categories.size();
    }

    public int getProblemsPerCategory() {

        int r = this.categories.get(0).problems.size();

        for (Category category : this.categories) {
            r = Math.max(r, category.problems.size());
        }

        return r;
    }



}
