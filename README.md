# Package Util - Java Web自动建包小工具

### 自动建包工具类

---
**适用场景:**

后端代码使用基于注解式的Spring框架进行开发

---

可以用本工具自动建包.执行建包工具后会自动分层，并生成一套从Controller到Dao的类(包含接口类及实现类),其中包名可以自行定制.

使用效果:

![](http://7xwp5w.com1.z0.glb.clouddn.com/packageUtil%E6%95%88%E6%9E%9C%E5%9B%BE.png)

使用方法:

<del>1、引用本jar包，可以下载本工程自行打包,也可以下载我这里打包好的

<del>[jar包下载地址](http://7xwp5w.com1.z0.glb.clouddn.com/PackageUtil.jar)

<del>2、将jar包丢到src目录下(后续考虑丢到maven中心仓)

1、pom中添加对jar包的引用

```xml
<dependency>
     <groupId>com.github.kyo7701</groupId>
     <artifactId>PackageUtil</artifactId>
     <version>1.0</version>
</dependency>
```



2、新建一个类并新建main方法,执行下列代码

![](http://7xwp5w.com1.z0.glb.clouddn.com/%E9%9C%80%E8%A6%81%E6%89%A7%E8%A1%8C%E7%9A%84%E4%BB%A3%E7%A0%81.png)

```
PackageUtil util = new PackageUtil();
util.setModuleName("module");
util.setPackageName("com.cris.test");
util.generate();
```
其中packagName是你要建包的父包名,要求书写全路径,比如你的包名是com.test.lalala,这里默认你的后端代码全部存放在src/main/java下面,如果你的项目的目录结构不是这样的你可以自行指定classPath;

moduleName及你要生成代码模板的模块名 


etc:student --> StudentController 
    --> IStudentService 
	--> IStudentDao 


