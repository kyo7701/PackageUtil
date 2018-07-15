package com.cris.util;

import com.cris.constant.MethodType;
import com.cris.constant.PackageType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:Mr.Cris
 * Date:2017-09-03 23:33
 */
public class PackageUtil {

    private String moduleName;

    private String packageName;

    private String daoPackageName = "dao";

    private String entityPackageName = "entity";

    private String servicePackageName = "service";

    private String controllerPackageName = "controller";

    private final String decoratePattern = "[decoratePattern]";

    private final String namePattern = "[name]";

    private final String interfacePattern = "[interface]";

    private String suffix = ".java";

    private String packageTempLate = "package [packageName];";

    private String packageNamePattern= "[packageName]";

    private String implPackageSuffix = ".impl";

    private String importTemplate = "import [interface];";

    private SimpleDateFormat sdf = new SimpleDateFormat();

    private String codeSign = "";

    private boolean enableRestful = true;

    private String entityName = "";

    private MethodUtil methodUtil;

    private Map<String,String> classNameMap = new HashMap();

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    private static final String lineBreak = "\n";

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public PackageUtil() {
        this.codeSign = this.generateCodeSign();
    }

    public PackageUtil(String moduleName) {
        this.moduleName = moduleName;
    }

    public static void main(String[] args) throws IOException {
        PackageUtil util = new PackageUtil();
        util.setModuleName("module");
        util.setPackageName("com.cris.test");
        util.generate();
        util.generateMethod(MethodType.METHOD_TYPE_QUERYLIST);
        util.generateMethod(MethodType.METHOD_TYPE_QUERYONE);
        util.generateMethod(MethodType.METHOD_TYPE_INSERT);
        util.generateMethod(MethodType.METHOD_TYPE_UPDATE);
        util.generateMethod(MethodType.METHOD_TYPE_DELETE);
    }

    public void generate() {
        if (this.moduleName == null || this.moduleName.equals("")) {
            throw new IllegalArgumentException("请填写模块的名称");
        }

        if (this.packageName == null || this.packageName.equals("")) {
            throw new IllegalArgumentException("请填写包的名称");
        }
        this.init();
        this.generateController();
        this.generateService();
        this.generateDao();
        this.generateEntity();
    }

    private void generateService() {
        //建包
        this.createPackage(PackageType.SERVICE);
        //建interface类
        this.generateInterfaceFile(PackageType.SERVICE);
        //写文件
        this.writeInterfaceWithType(PackageType.SERVICE);
        //建impl类
        this.generateImplFile(PackageType.SERVICE);
        //写文件
        this.writeImplWithType(PackageType.SERVICE);
    }

    private void generateDao() {
        this.createPackage(PackageType.DAO);
        //建interface类
        this.generateInterfaceFile(PackageType.DAO);
        //写文件
        this.writeInterfaceWithType(PackageType.DAO);
        //建impl类
        this.generateImplFile(PackageType.DAO);
        //写文件
        this.writeImplWithType(PackageType.DAO);
    }

    private void generateController() {
        //建包
        String packageType = this.generatePackageName(PackageType.CONTROLLER).toLowerCase();
        String packageName = this.packageName + "." + packageType;
        DocumentUtil.createFolderByPackageName(packageName);
        //建类
        String moduleUpper = this.moduleName.substring(0,1).toUpperCase() + this.moduleName.substring(1);
        String fileName = moduleUpper + this.generatePackageName(PackageType.CONTROLLER) + suffix;
        String className = moduleUpper + this.generatePackageName(PackageType.CONTROLLER);
        DocumentUtil.createFileByPackageNameAndFileName(packageName,fileName);
        //生成模板
        String decorateStr = this.generateDecoratePattern(PackageType.CONTROLLER);
        String controllerTemplate = "[decoratePattern]\npublic class [name] {\n\n}";
        String classContent = controllerTemplate.replace(decoratePattern,decorateStr).replace(namePattern,className);
        String packageContent = packageTempLate.replace(packageNamePattern,packageName);
        String importContent = generateImportPattern(PackageType.CONTROLLER,false);
        String finalContent = packageContent+ lineBreak + importContent +"\n" + this.codeSign+ "\n" + classContent;
        DocumentUtil.writeToFileByPackageNameAndFileName(packageName,fileName,finalContent);
    }



    private void generateEntity() {
        //建包
        String packageType = this.generatePackageName(PackageType.ENTITY).toLowerCase();
        String packageName = this.packageName + "." + packageType;
        DocumentUtil.createFolderByPackageName(packageName);
        //建类
        String moduleUpper = this.moduleName.substring(0,1).toUpperCase() + this.moduleName.substring(1);
        String fileName = moduleUpper + this.generatePackageName(PackageType.ENTITY) + suffix;
        String className = moduleUpper + this.generatePackageName(PackageType.ENTITY);
        DocumentUtil.createFileByPackageNameAndFileName(packageName,fileName);
        //生成内容
        String decorateStr = this.generateDecoratePattern(PackageType.ENTITY);
        String entityTemplate = "[decoratePattern]\npublic class [name] {\n\n}";
        String classContent = entityTemplate.replace(decoratePattern,decorateStr).replace(namePattern,className);
        String packageContent = packageTempLate.replace(packageNamePattern,packageName);
        String finalContent = packageContent+ lineBreak + lineBreak + this.codeSign +"\n\n" + classContent;
        DocumentUtil.writeToFileByPackageNameAndFileName(packageName,fileName,finalContent);
    }

    private void createPackage(PackageType type) {
        String packageName = this.packageName;
        switch (type){
            case SERVICE:
                packageName += "." + servicePackageName;
                break;
            case CONTROLLER:
                packageName += "." + controllerPackageName;
                break;
            case DAO:
                packageName += "." + daoPackageName;
                break;
            case ENTITY:
                packageName += "." + entityPackageName;
                break;
        }

        String implPackageName = packageName + "." + implPackageSuffix;

        DocumentUtil.createFolderByPackageName(packageName);
        DocumentUtil.createFolderByPackageName(implPackageName);

    }

    private String generateImplClassName(PackageType type) {
        String upperName = this.moduleName.substring(0,1).toUpperCase() + this.moduleName.substring(1);
        String implNameTemplate = "[name][packageType]Impl";
        String result = implNameTemplate.replace("[name]",upperName);
        String typeStr = this.generatePackageName(type);
        result = result.replace("[packageType]",typeStr);
        return result;
    }

    private String generateInterfaceName(PackageType type) {
        String upperName = this.moduleName.substring(0,1).toUpperCase() + this.moduleName.substring(1);
        String interfaceNameTemplate = "I[name][packageType]";
        String packageName = this.generatePackageName(type);
        return interfaceNameTemplate.replace(namePattern,upperName).replace("[packageType]",packageName);
    }

    private String generatePackageName(PackageType type) {
        StringBuilder packageType = new StringBuilder();
        switch (type) {
            case DAO:
                packageType = new StringBuilder(this.daoPackageName);
                break;
            case ENTITY:
                packageType = new StringBuilder(this.entityPackageName);
                break;
            case SERVICE:
                packageType = new StringBuilder(this.servicePackageName);
                break;
            case CONTROLLER:
                packageType = new StringBuilder(this.controllerPackageName);
                break;
        }

        return packageType.toString().substring(0,1).toUpperCase() + packageType.toString().substring(1);
    }

    private String generateInterfaceCodeTemplate(PackageType type) {
        //指定包名
        String packageType = this.generatePackageName(type).toLowerCase();
        String Package = this.packageName + "." + packageType;
        //指定interface名称
        String interfaceName = this.generateInterfaceName(type);
        String interfaceFileName = interfaceName + suffix;
        DocumentUtil.createFileByPackageNameAndFileName(Package,interfaceFileName);
        //生成interface模板
        String packageContent = packageTempLate.replace(packageNamePattern,Package);

        String interfaceTemplate = "public interface [name] {\n\n}";
        String importPattern = generateImportPattern(type,true);
        String interfaceContent = interfaceTemplate.replace(namePattern,interfaceName);
        return packageContent +lineBreak + lineBreak+ importPattern + lineBreak + lineBreak+ this.codeSign + lineBreak + lineBreak + interfaceContent;
    }

    private String generateImplCodeTemplate(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String implPackageName = this.packageName + "." + packageType + implPackageSuffix;
        String implName = this.generateImplClassName(type);
        String interfaceName = this.generateInterfaceName(type);
        String implTemplate = "[decoratePattern]\npublic class [name] implements [interface] {\n\n}";
        String implContent = implTemplate.replace(namePattern,implName).replace(interfacePattern,interfaceName);
        String decorateStr = this.generateDecoratePattern(type);
        implContent = implContent.replace(this.decoratePattern,decorateStr);
        String packageContent = packageTempLate.replace(packageNamePattern,implPackageName);
        String importContent = this.generateImportPattern(type,false);
        return packageContent +lineBreak + lineBreak+ this.codeSign + lineBreak + lineBreak + importContent + lineBreak + lineBreak + implContent;
    }

    private void generateInterfaceFile(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String packageName = this.packageName + "." + packageType;
        String interfaceName = this.generateInterfaceName(type);
        String interfaceFileName = interfaceName + suffix;
        DocumentUtil.createFileByPackageNameAndFileName(packageName,interfaceFileName);
    }

    private void generateImplFile(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String packageName = this.packageName + "." + packageType + implPackageSuffix;
        String implName = this.generateImplClassName(type);
        String implFileName = implName + suffix;
        DocumentUtil.createFileByPackageNameAndFileName(packageName,implFileName);

    }

    private void writeInterfaceWithType(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String packageName = this.packageName + "." + packageType;
        //指定interface名称
        String interfaceName = this.generateInterfaceName(type);
        String interfaceFileName = interfaceName + suffix;
        String interfaceContent = this.generateInterfaceCodeTemplate(type);
        DocumentUtil.writeToFileByPackageNameAndFileName(packageName,interfaceFileName,interfaceContent);
    }

    private void writeImplWithType(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String packageName = this.packageName + "." + packageType+ implPackageSuffix;
        String implName = this.generateImplClassName(type);
        String implFileName = implName + suffix;
        String content = this.generateImplCodeTemplate(type);
        DocumentUtil.writeToFileByPackageNameAndFileName(packageName,implFileName,content);
    }

    private String generateImportPattern(PackageType type) {
        String packageType = this.generatePackageName(type).toLowerCase();
        String interfaceName = this.generateInterfaceName(type);
        String fullInterfaceName = this.packageName + "." + packageType + "." + interfaceName;
        String basicImportPattern = importTemplate.replace(interfacePattern,fullInterfaceName);
        String secondaryImport = "";

        switch (type) {
            case SERVICE:
                secondaryImport = "import org.springframework.stereotype.Service;";
                break;
            case CONTROLLER:
                basicImportPattern = "";
                if (enableRestful) {
                    secondaryImport = "import org.springframework.web.bind.annotation.RestController;";
                }else {
                    secondaryImport = "import org.springframework.stereotype.Controller;";
                }

                break;
            case DAO:
                secondaryImport = "import org.springframework.stereotype.Repository;";
                break;
        }

        return basicImportPattern +"\n" +secondaryImport;
    }

    private String generateImportPattern(PackageType type,boolean isInterface) {
        String basicImportPattern = generateImportPattern(type) + "\n";
        String finalContent = "";
        String entityPackageName = generatePackageName(PackageType.ENTITY);
        String entityClassName = upperString(this.moduleName) + entityPackageName;
        entityPackageName = entityPackageName.toLowerCase();
        String importEntityPattern = "import "+ this.packageName+ "." + entityPackageName +"." + entityClassName +";";
        this.setEntityName(entityClassName);
        if (isInterface) {
            finalContent = importEntityPattern;
        }else {
            finalContent = basicImportPattern + importEntityPattern + "\n";
        }
        return finalContent;
    }



    private String generateDecoratePattern(PackageType type) {
        String decoratePattern;
        switch (type) {
            case SERVICE:
                decoratePattern = "@Service";
                break;
            case CONTROLLER:
                if (enableRestful) {
                    decoratePattern = "@RestController";
                }else {
                    decoratePattern = "@Controller";
                }

                break;
            case DAO:
                decoratePattern = "@Repository";
                break;
            default:
                decoratePattern = "";
            break;
        }
        return decoratePattern;
    }

    private String generateCodeSign() {
        String time = DateUtil.dateToString(new Date(),DateUtil.FORMAT_FIVE);
        Date date = new Date();
        sdf.format(date);
        String sign = "/**\n" +
                " * Code generating Template by Mr.Cris\n" +
                " * Date:" + time +"\n"+
                " */";


        return sign;
    }

    public String generateMethod(MethodType methodType){
        //上下文
        //方法名称 方法参数 返回类型 使用的
        //拿到所有类全名
        String controllerName = classNameMap.get("controller");
        String serviceName = classNameMap.get("service");
        String serviceImplName = classNameMap.get("serviceImpl");
        String daoName = classNameMap.get("dao");
        String daoImplName = classNameMap.get("daoImpl");
        String controllerContent = methodUtil.generateMethodBody(PackageType.CONTROLLER,methodType,false,this.moduleName);
        String serviceContent = methodUtil.generateMethodBody(PackageType.SERVICE,methodType,true,this.moduleName);
        String serviceImplContent = methodUtil.generateMethodBody(PackageType.SERVICE,methodType,false,this.moduleName);
        String daoContent = methodUtil.generateMethodBody(PackageType.DAO,methodType,true,this.moduleName);
        String daoImplContent = methodUtil.generateMethodBody(PackageType.DAO,methodType,false,this.moduleName);
        DocumentUtil.writeNewMethod(controllerName,controllerContent,methodUtil.importList,PackageType.CONTROLLER);
        DocumentUtil.writeNewMethod(serviceName,serviceContent,methodUtil.importList,PackageType.SERVICE);
        DocumentUtil.writeNewMethod(serviceImplName,serviceImplContent,methodUtil.importList,PackageType.SERVICE);
        DocumentUtil.writeNewMethod(daoName,daoContent,methodUtil.importList,PackageType.DAO);
        DocumentUtil.writeNewMethod(daoImplName,daoImplContent,methodUtil.importList,PackageType.DAO);

        return null;
    }


    private String upperString (String str) {
        if (str == null || str == "") return str;
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
    
    /**
    * TODO
    * Created by mr_cris
    * 2018/6/18
    * 添加依赖注入,创建方法体
    */

    private void init() {//初始化相关类全名
        methodUtil = new MethodUtil();
        methodUtil.setModuleName(this.getModuleName());
        methodUtil.setBasePackagename(this.getPackageName());
        String moduleUpper = this.moduleName.substring(0,1).toUpperCase() + this.moduleName.substring(1);
        String controllerClassName = moduleUpper + this.generatePackageName(PackageType.CONTROLLER);
        String serviceClassName = "I" + moduleUpper + this.generatePackageName(PackageType.SERVICE);
        String serviceImplClassName = moduleUpper + this.generatePackageName(PackageType.SERVICE) + "Impl";
        String daoClassName = "I" +moduleUpper + this.generatePackageName(PackageType.DAO);
        String daoImplClassName = moduleUpper + this.generatePackageName(PackageType.DAO)+ "Impl";
        String entityClassName = moduleUpper + this.generatePackageName(PackageType.ENTITY);

        String controllerName = this.packageName + "." + this.controllerPackageName + "." + controllerClassName;
        String serviceName = this.packageName + "." + this.servicePackageName + "." + serviceClassName;
        String serviceImplName = this.packageName + "." + this.servicePackageName  + implPackageSuffix + "." + serviceImplClassName;
        String daoName = this.packageName + "." + this.daoPackageName + "." + daoClassName;
        String daoImplName = this.packageName + "." + this.daoPackageName  + implPackageSuffix + "." + daoImplClassName;
        String entityName = this.packageName + "." + this.entityPackageName + "." + entityClassName;
        classNameMap.put("controller",controllerName);
        classNameMap.put("service",serviceName);
        classNameMap.put("serviceImpl",serviceImplName);
        classNameMap.put("dao",daoName);
        classNameMap.put("daoImpl",daoImplName);
        classNameMap.put("entity",entityName);
    }




}
