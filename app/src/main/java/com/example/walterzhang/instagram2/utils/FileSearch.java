package com.example.walterzhang.instagram2.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by mingshunc on 24/9/18.
 */

public class FileSearch {

    /**
     * Search a directory and return a list of all ** directories ** contained inside
     * @param directory
     * @return
     */
    public static ArrayList<String> getDirectoryPaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(directory);
        File[] listfiles = file.listFiles();

        for (int i = 0; i < listfiles.length; i++) {
            if (listfiles[i].isDirectory()) {
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }

        return pathArray;
    }

    /**
     * Search a directory and return a list of all ** files ** contained inside
     * @param directory
     * @return
     */
    public static ArrayList<String> getFilePaths(String directory) {
        ArrayList<String> pathArray = new ArrayList<>();

        File file = new File(directory);
        File[] listfiles = file.listFiles();

        for (int i = 0; i < listfiles.length; i++) {
            if (listfiles[i].isFile()) {
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }

        return pathArray;
    }

}
