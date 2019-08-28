package com.fieryslug.reinforcedcoral.core;

import com.fieryslug.reinforcedcoral.core.page.Page;
import com.fieryslug.reinforcedcoral.core.page.Widget;
import com.fieryslug.reinforcedcoral.core.problem.Problem;
import com.fieryslug.reinforcedcoral.core.problem.ProblemDisabled;
import com.fieryslug.reinforcedcoral.core.problem.ProblemDummy;
import com.fieryslug.reinforcedcoral.core.problem.ProblemMine;
import com.fieryslug.reinforcedcoral.core.problem.ProblemNull;
import com.fieryslug.reinforcedcoral.core.problem.ProblemTemp;
import com.fieryslug.reinforcedcoral.minigame.match.ProblemMatch;
import com.fieryslug.reinforcedcoral.minigame.minesweeper.ProblemMineSweeper;
import com.fieryslug.reinforcedcoral.minigame.snake.ProblemSnake;
import com.fieryslug.reinforcedcoral.util.DataLoader;
import com.fieryslug.reinforcedcoral.util.FuncBox;


import com.fieryslug.reinforcedcoral.util.Reference;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.crypto.Data;

public class ProblemSet {


    private String id;
    private String loadId;
    private String name;
    private ArrayList<Category> categories;
    private BiMap<String, Category> idCatMap;
    private BiMap<String, Problem> idProbMap;
    private Map<Problem, String> probShortIdMap;

    private Set<Problem> trash;

    private static Map<Class<? extends Problem>, Class<?>[]> argumentMap = new HashMap<>();


    private Map<String, URL> imageResources;
    private Map<String, URL> audioResources;

    private static final String MEDIA_DIR = "_media";
    private static final String TRASH_DIR = "_trash";

    static {
        argumentMap.put(ProblemMine.class, new Class<?>[]{String.class, Integer.class});
        argumentMap.put(ProblemMineSweeper.class, new Class<?>[]{String.class, Integer.class, Integer.class, Integer.class});
        argumentMap.put(ProblemNull.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemMatch.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemSnake.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemDisabled.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemDummy.class, new Class<?>[]{String.class});
        argumentMap.put(ProblemTemp.class, new Class<?>[]{});
    }

    public ProblemSet(String id) {
        this.id = id;
        this.categories = new ArrayList<>();
        this.idCatMap = HashBiMap.create();
        this.idProbMap = HashBiMap.create();
        this.probShortIdMap = new HashMap<>();
        trash = new HashSet<>();

        imageResources = new HashMap<>();
        audioResources = new HashMap<>();

    }

    public String getId() {
        return id;
    }

    public void acquireProblemSet() {
        /*
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
        */
        loadProblemSet("problemsets/" + this.id);

    }

    public void loadProblemSet(String path1) {

        String path = DataLoader.EXTERNAL_FOLDER + "/" + path1;
        loadId = path1;
        this.categories.clear();
        this.idCatMap.clear();
        this.idProbMap.clear();
        this.probShortIdMap.clear();
        trash.clear();

        imageResources.clear();
        audioResources.clear();
        System.out.println("loading set");

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
                        for (Class clazz1 : argumentClasses)
                            System.out.print(clazz1.getName() + " ");

                        System.out.println("arguments provided: ");
                        for (Object obj : arguments)
                            System.out.print(obj.getClass() + ": " + obj + " ");
                        System.out.println("\n");

                        problem = (Problem) (cons.newInstance(arguments));

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

                } else {
                    //System.out.println("probpath2: " + probPath);
                    System.out.println("creating problem " + probId);
                    System.out.println(path1);
                    //problem = new Problem(path1 + "/" + probId + ".json", true);
                    problem = new Problem(jsonProb);
                }
                problem.setShortId(probShortId);
                category.addProblems(problem);
                this.idProbMap.put(probId, problem);
                this.probShortIdMap.put(problem, probShortId);
            }
            this.addCategory(category);

        }

        JSONObject jsonDepend = metaJson.getJSONObject("dependencies");

        for (Category category : this.categories) {
            for (Problem problem : category.getProblems()) {

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

        //media
        //trash
        File trashDir = new File(path + "/" + TRASH_DIR);

        if (trashDir.exists()) {

            String[] trashPaths = trashDir.list();
            for (String trashPath : trashPaths) {
                String res = FuncBox.readExternalFile(path + "/" + TRASH_DIR + "/" + trashPath);
                JSONObject json = new JSONObject(res);
                Problem problem = generateProblem(json);


                problem.setShortId(trashPath);

                if(problem != null)
                    trash.add(problem);
            }
        }
    }


    public boolean saveProblemSet(String name1, boolean override) {
        String path = "problemsets/" + name1;
        return dumpProblemSet(path, override);
        /*
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
        */

    }

    public boolean dumpProblemSet(String path, boolean override) {
        path = DataLoader.EXTERNAL_FOLDER + "/" + path;
        DataLoader loader = DataLoader.getInstance();
        File file = new File(path);
        if (file.exists() && !override) {
            System.out.println(path + " already exists, unable to dump data");
            return false;
        }

        loader.deleteDirectory(file);

        String data = exportMeta().toString(2);

        loader.checkFile(path, true);
        loader.writeToFile(path + "/meta.json", data, override);




        for (Category category : this.categories) {
            //loader.checkFile(path + "/" + this.idCatMap.inverse().get(category), true);
            loader.checkFile(path + "/" + category.id, true);
            for (Problem problem : category.getProblems()) {
                //String probPath = path + "/" + this.idProbMap.inverse().get(problem) + ".json";
                //System.out.println(problem.name + ": " + problem.id);
                String probPath = path + "/" + problem.id + ".json";
                String dataProb = problem.exportAsJson().toString(2);
                loader.writeToFile(probPath, dataProb, override);
            }
        }

        //media--------------------------------------------------------------------------------------
        loader.checkFile(path + "/" + MEDIA_DIR, true);
        loader.checkFile(path + "/" + MEDIA_DIR + " /image", true);
        loader.checkFile(path + "/" + MEDIA_DIR + "/audio", true);



        File fileMedia = new File(path + "/" + MEDIA_DIR);
        File fileImage = new File(fileMedia, "image");
        File fileAudio = new File(fileMedia, "audio");

        System.out.println("[DEBUG] " + imageResources.size());
        for (String imageId : imageResources.keySet()) {

            if (imageId.startsWith(Reference.EXTERNAL_PREFIX)) {

                URL url = imageResources.get(imageId);
                File dest = new File(fileImage, imageId.substring(Reference.EXTERNAL_PREFIX.length()));
                try {
                    FileUtils.copyInputStreamToFile(url.openStream(), dest);
                } catch (Exception e) {
                    System.out.println("Error occurred while writing file" + dest.getPath());
                    e.printStackTrace();
                }

            }

        }
        System.out.println("[DEBUG] " + audioResources.size());
        for (String audioId : audioResources.keySet()) {
            if (audioId.startsWith(Reference.EXTERNAL_PREFIX)) {
                URL url = audioResources.get(audioId);
                File dest = new File(fileAudio, audioId.substring(Reference.EXTERNAL_PREFIX.length()));
                try {
                    FileUtils.copyInputStreamToFile(url.openStream(), dest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        //trash---------------------------------------------------------------------------------
        loader.checkFile(path + "/" + TRASH_DIR, true);

        int i=0;

        for (Problem trashProb : trash) {
            System.out.println(trashProb + ": " + trashProb.name + ": " + trashProb.getClass());
            loader.writeToFile(path + "/" + TRASH_DIR + "/" + i + ".json", trashProb.exportAsJson().toString(2), override);
            i++;
        }

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
            for (Problem problem : category.getProblems()) {

                String probShortId = this.probShortIdMap.get(problem);
                probShortId = problem.shortId;
                System.out.println("[DEBUG] " + problem.name + ": " + problem.shortId);
                arrayProbs.put(probShortId);
            }
            //jsonProbs.put(this.idCatMap.inverse().get(category), arrayProbs);
            jsonProbs.put(category.id, arrayProbs);
        }
        jsonMeta.put("problems", jsonProbs);

        JSONObject jsonDepend = new JSONObject();

        for (Category category : this.categories) {
            for (Problem problem : category.getProblems()) {
                if (problem.getDependencies().size() > 0) {
                    JSONArray arrayDependencies = new JSONArray();
                    for (Problem other : problem.getDependencies()) {
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
        category.setParentSet(this);
        this.categories.add(category);
        this.idCatMap.put(category.id, category);
        for (Problem problem : category.getProblems()) {

            this.idProbMap.put(problem.id, problem);
            this.probShortIdMap.put(problem, problem.shortId);

        }
    }

    public ArrayList<Category> getCategories() {
        return this.categories;
    }

    public int getCategoriesCount() {
        return this.categories.size();
    }

    public int getProblemsPerCategory() {

        int r = this.categories.get(0).getProblems().size();

        for (Category category : this.categories) {
            r = Math.max(r, category.getProblems().size());
        }

        return r;
    }

    public ProblemSet copy() {

        dumpProblemSet(".tmp/" + this.id + "_clone", true);

        ProblemSet set = new ProblemSet(this.id + "_clone");


        set.loadProblemSet(".tmp/" + this.id + "_clone");

        DataLoader.getInstance().deleteDirectory(new File(DataLoader.EXTERNAL_FOLDER + "/.tmp/" + this.id + "_clone"));


        set.imageResources = new HashMap<>(imageResources);
        set.audioResources = new HashMap<>(audioResources);


        return set;

    }

    public void loadResources() {

        for (Category category : categories) {
            for (Problem problem : category.getProblems()) {

                if(!problem.isSpecial()) {
                    for (Page page : problem.getPages()) {
                        loadResourcesForPage(page);
                    }
                    System.out.println(problem.name);
                    loadResourcesForPage(problem.getPageSolution());
                    for (Page page : problem.getPagesExplanation()) {
                        loadResourcesForPage(page);
                    }
                }
            }
        }

        for (Problem problem : trash) {
            if(!problem.isSpecial()) {
                for (Page page : problem.getPages()) {
                    loadResourcesForPage(page);
                }
                System.out.println(problem.name);
                loadResourcesForPage(problem.getPageSolution());
                for (Page page : problem.getPagesExplanation()) {
                    loadResourcesForPage(page);
                }
            }
        }
        imageResources.put("/res/images/tzuyu.jpg", ProblemSet.class.getResource("/res/images/tzuyu.jpg"));
        System.out.println("[problem set image resources loaded] " + imageResources);
        System.out.println("[problem set audio resources loaded] " + audioResources);
    }

    private void loadResourcesForPage(Page page) {

        DataLoader loader = DataLoader.getInstance();
        String commonPath = DataLoader.EXTERNAL_FOLDER + "/" + loadId + "/" + MEDIA_DIR;
        loader.checkFile(commonPath + "/image", true);
        loader.checkFile(commonPath + "/audio", true);
        if (page.type == Reference.MAGIC_PRIME) {
            for (Widget widget : page.widgets) {
                if (widget.widgetType == Widget.EnumWidget.IMAGE) {

                    String imageId = widget.content;
                    URL url = null;
                    if (imageId.startsWith(Reference.EXTERNAL_PREFIX)) {
                        String imageName = imageId.substring(Reference.EXTERNAL_PREFIX.length());
                        try {
                            url = new File(commonPath + "/image/" + imageName).toURL();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        url = ProblemSet.class.getResource(imageId);
                    }

                    imageResources.put(imageId, url);
                }
                if (widget.widgetType == Widget.EnumWidget.AUDIO) {
                    String audioId = widget.content;
                    URL url = null;
                    if (audioId.startsWith(Reference.EXTERNAL_PREFIX)) {
                        String audioName = audioId.substring(Reference.EXTERNAL_PREFIX.length());
                        try {
                            url = new File(commonPath + "/audio/" + audioName).toURL();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        url = ProblemSet.class.getResource(audioId);
                    }
                    audioResources.put(audioId, url);
                }
            }
        }
    }

    public void normalize() {
        for (Category category : categories) {
            for (Problem problem : category.getProblems()) {
                problem.normalizePages();
                if(!problem.isSpecial()) {

                    for (Page page : problem.getPages()) {
                        normalizePage(page);
                    }
                    normalizePage(problem.getPageSolution());
                    for (Page page : problem.getPagesExplanation()) {
                        normalizePage(page);
                    }
                }
            }
        }

        int ideal = getProblemsPerCategory();


        for (Category category : categories) {

            ArrayList<Problem> problems = category.getProblems();
            int lack = ideal - problems.size();
            for (int i = 0; i < lack; ++i) {
                Problem problem = new ProblemTemp();
                problem.setShortId(shortIdForProblem(category, "0"));
                category.addProblems(problem);
            }
        }
    }

    private void normalizePage(Page page) {

        if (page.type == Reference.MAGIC_PRIME) {
            for (Widget widget : page.widgets) {


                if (widget.widgetType == Widget.EnumWidget.IMAGE) {
                    if (!widget.content.startsWith(Reference.EXTERNAL_PREFIX)) {
                        String imageName = FuncBox.getRawFileName(widget.content);
                        String imageId = Reference.EXTERNAL_PREFIX + imageName;
                        URL url = FuncBox.class.getResource(widget.content);
                        widget.content = imageId;
                        System.out.println(imageId);
                        imageResources.put(imageId, url);
                    }
                }
                if (widget.widgetType == Widget.EnumWidget.AUDIO) {
                    if (!widget.content.startsWith(Reference.EXTERNAL_PREFIX)) {
                        String audioName = FuncBox.getRawFileName(widget.content);
                        String audioId = Reference.EXTERNAL_PREFIX + audioName;
                        URL url = FuncBox.class.getResource(widget.content);
                        widget.content = audioId;
                        audioResources.put(audioId, url);
                    }
                }
            }
        }
    }


    public Map<String, URL> getImageResources() {
        return imageResources;
    }

    public Map<String, URL> getAudioResources() {
        return audioResources;
    }

    public String idForCategory() {

        Set<String> occupied = new HashSet<>();
        for (Category category : categories) {
            occupied.add(category.id);
        }

        int i = 0;
        String id;
        while (true) {
            id = "cat" + i;
            if (!occupied.contains(id)) {
                break;
            }
            i += 1;
        }
        return id;

    }

    public static String shortIdForProblem(Category category, String preferred) {

        Set<String> occupied = new HashSet<>();
        for (Problem problem : category.getProblems()) {
            occupied.add(problem.shortId);
        }

        if (!occupied.contains(preferred)) {
            return preferred;
        }

        int i = 0;
        String id;
        while (true) {
            id = "" + i;
            if (!occupied.contains(id)) {
                break;
            }
            i += 1;
        }
        return id;

    }

    public void deleteProblem(Problem problem) {

        System.out.println("[DEBUG] deleting " + problem.name + ": " + problem.getClass());

        Category category = problem.getParentCat();
        if (category != null && categories.contains(category)) {

            int ind = category.getProblems().lastIndexOf(problem);

            if(ind >= 0) {
                if(! (problem instanceof ProblemTemp))
                    trash.add(problem);

                Problem problemTemp = new ProblemTemp();
                problemTemp.setShortId(shortIdForProblem(category, problem.shortId));
                problemTemp.setParentCat(category);
                category.set(ind, problemTemp);

                for (Problem dependent : problem.getDependents()) {
                    dependent.getDependencies().remove(problem);
                }
            }

        }

    }

    public void deleteCategory(Category category) {

        if (categories.contains(category)) {

            for (Problem problem : category.getProblems()) {

                deleteProblem(problem);

            }
            categories.remove(category);

        }

    }

    private Problem generateProblem(JSONObject jsonProb) {
        Problem problem = null;
        boolean flag = false;
        if (jsonProb.has("special")) flag = jsonProb.getBoolean("special");
        if (flag) {

            System.out.println("---detected special problem, trying to create using reflection---");
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
                for (Class clazz1 : argumentClasses)
                    System.out.print(clazz1.getName() + " ");

                System.out.println("arguments provided: ");
                for (Object obj : arguments)
                    System.out.print(obj.getClass() + ": " + obj + " ");
                System.out.println("\n");

                problem = (Problem) (cons.newInstance(arguments));

            } catch (Exception e) {
                System.out.println("failed to generate problem");
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

        } else {
            //System.out.println("probpath2: " + probPath);
            System.out.println("creating problem");
            //problem = new Problem(path1 + "/" + probId + ".json", true);
            problem = new Problem(jsonProb);
        }

        return problem;
    }

    public static ProblemSet generateProblemSet(String name, int cats, int probsPerCat) {

        String id = FuncBox.toValidFileName(name);

        ProblemSet set = new ProblemSet(idForSet(id));
        set.loadId = set.id;

        set.name = name;

        for (int i = 0; i < cats; ++i) {

            Category category = new Category("void", "" + i);
            set.idCatMap.put("" + i, category);
            set.categories.add(category);
            for (int j = 0; j < probsPerCat; ++j) {
                Problem problem = new ProblemTemp();
                problem.setShortId("" + j);
                category.addProblems(problem);
                set.idProbMap.put(problem.id, problem);
                set.probShortIdMap.put(problem, problem.shortId);
            }

        }
        return set;

    }

    public static String idForSet(String preferred) {

        ArrayList<ProblemSet> sets = DataLoader.getInstance().getProblemSets();
        Set<String> occuppied = new HashSet<>();

        for (ProblemSet set : sets) {
            occuppied.add(set.id);
        }

        if(occuppied.contains(preferred)) {

            int i=0;
            while (true) {
                if (!occuppied.contains("" + i)) {
                    return ""+i;
                }
                i += 1;
            }
        }

        return preferred;

    }

}
