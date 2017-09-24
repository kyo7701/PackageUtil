package com.cris.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author:Mr.Cris
 * Date:2017-09-04 09:15
 */
public class DocumentUtil {

    private static String classPath = System.getProperty("user.dir") + "\\" + "src\\main\\java\\";


    public static void createFile(String path) {
        if (path == null ||path.equals("")){
            throw new IllegalArgumentException("path argument cannot be null");
        }
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFolder(String path) {
        if (path == null ||path.equals("")){
            throw new IllegalArgumentException("path argument cannot be null");
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public static boolean writeToFile(String filePath,String content) {
        boolean flag = true;
        if (filePath == null || filePath.equals("")) {
            throw new IllegalArgumentException("filePath Can not be null!");
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter writer = new PrintWriter(new FileOutputStream(file));
            writer.write(content);
            writer.flush();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }

        return flag;
    }

    public static void writeToFileByPackageNameAndFileName(String packageName,String fileName,String content) {
        String[] packagesName = packageName.split("\\.");
        String folderPath = classPath;
        for (String s : packagesName) {
            folderPath += s + "\\";
        }
        String filePath = folderPath + fileName;
        writeToFile(filePath,content);

    }

    public static void createFolderByPackageName(String packageName) {

        String[] packagesName = packageName.split("\\.");
        String parentFolder = classPath;
        for (String s : packagesName) {
            parentFolder += s + "/";
            createFolder(parentFolder);
        }
    }

    public static void createFileByPackageNameAndFileName(String packageName,String fileName) {
        String[] packagesName = packageName.split("\\.");
        String folderPath = classPath;
        for (String s : packagesName) {
            folderPath += s+"\\";
        }
        String filePath = folderPath + fileName;
        createFile(filePath);
    }



}
