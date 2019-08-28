package com.fieryslug.reinforcedcoral.util;

import com.fieryslug.reinforcedcoral.core.ProblemSet;
import com.google.common.base.Charsets;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class DataLoader {

    private static DataLoader dataLoader = new DataLoader();
    private ArrayList<ProblemSet> problemSets;
    public static final String EXTERNAL_FOLDER = "coral";

    public static DataLoader getInstance() {
        return dataLoader;
    }

    public DataLoader() {
        this.problemSets = new ArrayList<>();
    }

    public void checkFiles() {

        System.out.println("checking files ...");
        checkFile(EXTERNAL_FOLDER, true);
        checkFile(EXTERNAL_FOLDER + "/texturepacks", true);
        checkFile(EXTERNAL_FOLDER + "/problemsets", true);
        checkFile(EXTERNAL_FOLDER + "/problemsets/index.json", false);
        checkFile(EXTERNAL_FOLDER + "/logs", true);
        checkFile(EXTERNAL_FOLDER + "/.tmp", true);

        checkIndex();

    }

    public void checkFile(String path, boolean isDir) {
        File file = new File(path);
        if (file.exists()) {
            System.out.println(path + " found");
        } else {
            System.out.println(path + " does not exist");

            boolean b;
            if (isDir)
                b = file.mkdir();
            else {
                try {
                    b = file.createNewFile();
                } catch (Exception e) {
                    e.printStackTrace();
                    b = false;
                }
            }

            if (b) {
                System.out.println("generated " + (isDir ? "directory " : "file ") + path);
            } else {
                System.out.println("failed to generate " + (isDir ? "directory " : "file ") + path);

            }
        }
    }

    public void writeToFile(String path, String data, boolean override) {
        File file = new File(path);
        if (file.exists() && !override) return;
        checkFile(path, false);
        try {
            FileUtils.writeStringToFile(file, data, Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readCustomTextures() {

        String[] packs = FuncBox.listDir(EXTERNAL_FOLDER + "/texturepacks");
        for (String pack : packs) {
            System.out.println(pack);
            //FuncBox.readExternalFile(EXTERNAL_FOLDER + "/texturepacks/" + pack);
        }

    }

    public void loadAllProblemSets() {
        this.problemSets.clear();
        System.out.println("\n[loader] loading all available problem sets");
        System.out.println("==================================");
        File rootPath = new File(EXTERNAL_FOLDER + "/problemsets");

        JSONObject jsonIndex = new JSONObject(FuncBox.readExternalFile(rootPath + "/index.json"));
        JSONArray arrayIndex = jsonIndex.getJSONArray("index");

        String[] setIds = new String[arrayIndex.length()];
        for (int i = 0; i < arrayIndex.length(); ++i) {
            setIds[i] = arrayIndex.getString(i);
        }

        for (String setPath : setIds) {
            File sub = new File(rootPath, setPath);

            System.out.println("\nloading problem set " + setPath);
            ProblemSet set = new ProblemSet(setPath);
            set.acquireProblemSet();
            set.loadResources();
            this.problemSets.add(set);

        }
    }

    public void updateProblemSetIndex() {

        File file = new File(EXTERNAL_FOLDER + "/problemsets");
        String[] setIds = file.list();

        JSONObject jsonIndex = new JSONObject();
        JSONArray arrayIndex = new JSONArray();

        for (String setId : setIds) {

            File fileSet = new File(file, setId);
            if (fileSet.isDirectory()) {

                ProblemSet set = new ProblemSet(setId);
                boolean b = true;
                try {
                    set.acquireProblemSet();
                    set.loadResources();
                } catch (Exception e) {
                    System.out.println("an error occurred while loading " + setId + ": ");
                    e.printStackTrace();
                    b = false;
                }
                if (b) {
                    arrayIndex.put(setId);
                }

            }

        }

        jsonIndex.put("index", arrayIndex);

        writeToFile(EXTERNAL_FOLDER + "/problemsets/index.json", jsonIndex.toString(2), true);
    }

    public ArrayList<ProblemSet> getProblemSets() {
        return this.problemSets;
    }

    public boolean deleteDirectory(File file) {
        if (file.exists()) {
            File[] allContents = file.listFiles();
            if (allContents != null) {
                for (File sub : allContents) {
                    deleteDirectory(sub);
                }
            }
        }
        return file.delete();
    }

    public void checkIndex() {


        String res = FuncBox.readExternalFile(EXTERNAL_FOLDER + "/problemsets/index.json");
        if (res.length() == 0) {
            JSONObject jsonIndex = new JSONObject();
            jsonIndex.put("index", new JSONArray());
            writeToFile(EXTERNAL_FOLDER + "/problemsets/index.json", jsonIndex.toString(2), true);
        }


    }

    public void deleteTempFiles() {

        File file = new File(EXTERNAL_FOLDER + "/.tmp");
        if (file.exists()) {
            deleteDirectory(file);
        }
        checkFile(EXTERNAL_FOLDER + "/.tmp", true);

    }
}
