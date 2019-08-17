package com.fieryslug.reinforcedcoral.util;

import com.google.common.base.Charsets;
import org.apache.commons.io.FileUtils;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

    private static DataLoader dataLoader = new DataLoader();
    public static final String EXTERNAL_FOLDER = "coral";

    public static DataLoader getInstance() {
        return dataLoader;
    }

    public void checkFiles() {

        System.out.println("checking files ...");
        checkFile(EXTERNAL_FOLDER, true);
        checkFile(EXTERNAL_FOLDER + "/texturepacks", true);
        checkFile(EXTERNAL_FOLDER + "/problemsets", true);
        checkFile(EXTERNAL_FOLDER + "/problemsets/sets.json", false);
        checkFile(EXTERNAL_FOLDER + "/logs", true);
        checkFile(EXTERNAL_FOLDER + "/.tmp", true);

    }

    public void checkFile(String path, boolean isDir) {
        File file = new File(path);
        if (file.exists()) {
            System.out.println(path + " found");
        } else {
            System.out.println(path + " does not exist");

            boolean b;
            if(isDir)
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

}
