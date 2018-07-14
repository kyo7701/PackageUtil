package com.cris.util;

import com.cris.constant.PackageType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

    public static String readFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println(file.getPath());
            System.out.println("文件不存在");
            return null;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        System.out.println("line:---");
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println("line:---");
        return null;

    }

    public static String readFileByPackageType(String fileName,PackageType type) throws IOException {
        String filePath = fileNameResolveByPackageType(fileName, type);
        return readFile(filePath);
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

    public static String fileFullNameResolve(String fileName) {
        String[] packagesName = fileName.split("\\.");
        String parentFolder = classPath;
        Integer i = 0;
        String fileSuffix = ".java";
        for (String s : packagesName) {
            if (i != packagesName.length -1) {
                parentFolder += s + "\\";
            }else {
                parentFolder += s + fileSuffix;
            }
            i++;
        }
        System.out.println(parentFolder);
        return parentFolder;
    }

    public static String fileNameResolveByPackageType(String fileFullName, PackageType type) {
        String[] packagesName = fileFullName.split("\\.");
        String parentFolder = classPath;
        Integer i = 0;
        String packageType = type.getValue();
        String fileSuffix = ".java";
        for (String s : packagesName) {
            if (i != packagesName.length -1) {
                parentFolder += s + "\\";
            }else {
                parentFolder += packageType + "\\";
                parentFolder += s + fileSuffix;
            }
            i++;
        }
        System.out.println(parentFolder);
        return parentFolder;

    }

    /**
     * 将原有空行替换为将要创建的新方法
     * @param content 方法内容
     * @param fileName 文件名
     * @param type 类类型
     */
    public static boolean writeNewMethod(String fileName,String content,PackageType type){
        String filePath = fileNameResolveByPackageType(fileName,type);
        //读取文件
        //遇到类体
        //遇到第一个空行
        //将新方法写入
        Stack stack = new Stack();
        boolean isInClassBody = false;
        boolean isEmptyLine = false;
        boolean insertComplete = false;
        File file = new File(filePath);
        List<String> previousContent = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("{")) {
                    stack.push("{");
                }
                if (line.contains("}")) {
                    String s=  (String)stack.pop();
                }
                if (line.equals("")) {
                    isEmptyLine = true;
                }
                System.out.println("stack中操作数数量" + stack.size());
                //在类体内 并且不是在方法体内
                if (isInClassBody && isEmptyLine &&stack.size() == 1 && !insertComplete) {
                    //将方法内容写入到方法体中
                    String methodLine = "\n"+ content + "\n";
                    previousContent.add(methodLine);
                    insertComplete = true;
                }

                previousContent.add(line);
                if (line.contains("interface") || line.contains("class")) {
                    System.out.println("contains class|interface");
                    isInClassBody = true;
                }

            }
            reader.close();
            PrintWriter writer = new PrintWriter(new FileWriter(file));
            for (String s : previousContent) {
                s+= "\n";
                writer.write(s);
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


}
