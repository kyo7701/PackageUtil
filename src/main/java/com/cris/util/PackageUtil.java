package com.cris.util;

import com.cris.constant.PackageType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        String fileName =util.generateInterfaceName(PackageType.DAO);
        String fileFullName = util.getPackageName()+ "." + fileName;
//        DocumentUtil.readFileByPackageType(fileFullName,PackageType.DAO);
        DocumentUtil.writeNewMethod(fileFullName,"lalalalal",PackageType.DAO);
    }

    public void generate() {
        if (this.moduleName == null || this.moduleName.equals("")) {
            throw new IllegalArgumentException("请填写模块的名称");
        }

        if (this.packageName == null || this.packageName.equals("")) {
            throw new IllegalArgumentException("请填写包的名称");
        }
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
        String importContent = importTemplate.replace(interfacePattern,"org.springframework.stereotype.Controller");
        String finalContent = packageContent+ lineBreak + lineBreak + this.codeSign +"\n\n" +importContent+ "\n\n" + classContent;

        DocumentUtil.writeToFileByPackageNameAndFileName(packageName,fileName,finalContent);
    }

    private void generateEntity() {
        String packageType = this.generatePackageName(PackageType.ENTITY).toLowerCase();
        DocumentUtil.createFolderByPackageName(this.packageName + "." + packageType);
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
        String interfaceContent = interfaceTemplate.replace(namePattern,interfaceName);
        return packageContent + lineBreak + lineBreak+ this.codeSign + lineBreak + lineBreak + interfaceContent;
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
        String importContent = this.generateImportPattern(type);
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
                secondaryImport = "import org.springframework.stereotype.Controller;";
                break;
            case DAO:
                secondaryImport = "import org.springframework.stereotype.Repository;";
                break;
        }

        return basicImportPattern +"\n" +secondaryImport;
    }

    private String generateDecoratePattern(PackageType type) {
        String decoratePattern;
        switch (type) {
            case SERVICE:
                decoratePattern = "@Service";
                break;
            case CONTROLLER:
                decoratePattern = "@Controller";
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

    public String generateMethodContent(PackageType type,boolean isInterface){

        return null;
    }


}
