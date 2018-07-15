package com.cris.util;

import com.cris.constant.MethodType;
import com.cris.constant.PackageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:Mr.Cris
 * Date:2018-07-14 20:03
 */
public class MethodUtil {

    public static final String QUERY_ONE_METHOD_NAME = "queryOne";
    public static final String QUERY_LIST_METHOD_NAME = "queryList";
    public static final String INSERT_METHOD_NAME = "insert";
    public static final String UPDATE_METHOD_NAME = "update";
    public static final String DELETE_METHOD_NAME = "delete";
    public static final String ACCESS_SCOPE = "public";
    public static final String RETURN_TYPE_LIST = "List";
    public static final String RETURN_TYPE_ENTITY = "[Entity]";
    public static final String RETURN_TYPE_RESULT = "boolean";
    public static final String METHOD_BRACKET = "([Entity] entity)";
    public static final String METHOD_BODY = "  {\n[content]\n  }";
    public static final String CONTENT_TEMPLATE = "[content]";
    public static final String SPACE = " ";
    public static final String LINE_BREAK = "\n";
    public List<String> importList = new ArrayList<String>();


    private String entityName;

    private String basePackagename = "";

    private String moduleName = "";

    public String getBasePackagename() {
        return basePackagename;
    }

    public void setBasePackagename(String basePackagename) {
        this.basePackagename = basePackagename;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String generateMethodQueryOne(PackageType type,boolean isInterface,String moudleName){
        return "";
    }

    public String generateMethodQueryList(PackageType type,boolean isInterface,String moudleName){
        return "";
    }

    public String generateMethodBody(PackageType packageType, MethodType methodType, boolean isInterface, String moduleName) {
        String entityPackageName = generatePackageName(PackageType.ENTITY);
        String entityClassName = upperString(this.moduleName) + entityPackageName;
        entityPackageName = entityPackageName.toLowerCase();
        String importEntityPattern = "import "+ this.basePackagename+ "." + entityPackageName +"." + entityClassName +";";
        String methodBody = "  ";

        if (packageType == PackageType.CONTROLLER || !isInterface) {
            methodBody += ACCESS_SCOPE + SPACE;
        }

        if (methodType == MethodType.METHOD_TYPE_QUERYLIST) {
            methodBody += RETURN_TYPE_LIST + SPACE;
            String listImport  = "import java.util.List;";
            if (!importList.contains(listImport)) importList.add(listImport);
        }else if (methodType == MethodType.METHOD_TYPE_QUERYONE){
            methodBody += entityClassName + SPACE;//要保证大小写
        }else {
            methodBody += "boolean" + SPACE;//要保证大小写
        }



        String entityImport  = importEntityPattern;
        String methodName = generateMethodName(methodType);
        methodBody += methodName;
        String params = METHOD_BRACKET.replace(RETURN_TYPE_ENTITY,entityClassName);
        methodBody += params;
        String content = "";
        String methodContent = "";
        if (!isInterface) {
            if (methodType == MethodType.METHOD_TYPE_QUERYLIST||methodType == MethodType.METHOD_TYPE_QUERYONE) {
                content = "    return null;";
            }else {
                content = "    return false;";
            }
            methodContent = METHOD_BODY.replace(CONTENT_TEMPLATE,content);
            methodBody += methodContent;
        }else {
            methodBody += ";";
        }

        return methodBody;
    }

    private String generateMethodName(MethodType methodType) {
        return methodType.getValue();
    }

    private String upperString (String str) {
        if (str == null || str == "") return str;
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }

    private String generatePackageName(PackageType type) {
        StringBuilder packageType = new StringBuilder();
        switch (type) {
            case DAO:
                String daoPackageName = "dao";
                packageType = new StringBuilder(daoPackageName);
                break;
            case ENTITY:
                String entityPackageName = "entity";
                packageType = new StringBuilder(entityPackageName);
                break;
            case SERVICE:
                String servicePackageName = "service";
                packageType = new StringBuilder(servicePackageName);
                break;
            case CONTROLLER:
                String controllerPackageName = "controller";
                packageType = new StringBuilder(controllerPackageName);
                break;
        }

        return packageType.toString().substring(0,1).toUpperCase() + packageType.toString().substring(1);
    }




}
