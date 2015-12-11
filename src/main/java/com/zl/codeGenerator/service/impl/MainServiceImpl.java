package com.zl.codeGenerator.service.impl;

import com.zl.codeGenerator.dynamic.GroovyFactory;
import com.zl.codeGenerator.entity.TemplateValue;
import com.zl.codeGenerator.service.MainService;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2015/12/9.
 */
public class MainServiceImpl implements MainService{
    private Configuration configuration;

    private GroovyFactory groovyFactory;

    //模板路径
    private String templatePath;

    //生成公共代码的模板名称
    private List<String> publicTemplates = new ArrayList<String>();

    //生成业务代码的模板名称
    private List<String> templates = new ArrayList<String>();

    //模块名称
    private String moduleName;

    //包路径
    private String packagePath;

    //模型数据
    private List<TemplateValue> templateValueList = new ArrayList<TemplateValue>();

    //代码文件生成根目录
    private String moduleDir;

    //资源文件目录
    private String resourcesDir;

    //java文件根目录
    private String classRootDir;

    public GroovyFactory getGroovyFactory() {
        return groovyFactory;
    }

    public void setGroovyFactory(GroovyFactory groovyFactory) {
        this.groovyFactory = groovyFactory;
    }

    public String getResourcesDir() {
        return resourcesDir;
    }

    public void setResourcesDir(String resourcesDir) {
        this.resourcesDir = resourcesDir;
    }

    public String getClassRootDir() {
        return classRootDir;
    }

    public void setClassRootDir(String classRootDir) {
        this.classRootDir = classRootDir;
    }

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }

    public List<String> getPublicTemplates() {
        return publicTemplates;
    }

    public void setPublicTemplates(List<String> publicTemplates) {
        this.publicTemplates = publicTemplates;
    }

    public List<String> getTemplates() {
        return templates;
    }

    public void setTemplates(List<String> templates) {
        this.templates = templates;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public List<TemplateValue> getTemplateValueList() {
        return templateValueList;
    }

    public void setTemplateValueList(List<TemplateValue> templateValueList) {
        this.templateValueList = templateValueList;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generate() throws Exception {
        //设置模板路径
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        //指定模板如何查看数据类型
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        //生成代码文件目录
        generateDir();
        //生成公共部分代码
        generatePublicCode();
        //为每张表生成业务代码
        generateBusinessCode();
    }

    /**
     * 生成代码存放目录
     */
    private void generateDir(){
        resourcesDir = moduleDir+moduleName+"/src/main/resources";
        //创建resource目录
        File file = new File(resourcesDir + "/" + packagePath.replace(".","/")+"/dao/impl/ibatisXml");
        file.mkdirs();
        classRootDir = moduleDir+moduleName + "/src/main/java/" + packagePath.replace(".","/");
        //创建entity目录
        file = new File(classRootDir+"/entity");
        file.mkdirs();
        //创建util目录
        file = new File(classRootDir+"/util");
        file.mkdirs();
        //创建service层目录
        file = new File(classRootDir+"/service/impl");
        file.mkdirs();
        //创建dao层目录
        file = new File(classRootDir+"/dao/impl");
        file.mkdirs();
        file = new File(classRootDir+"/dao/base");
        file.mkdirs();
    }


    /**
     * 生成公共部分代码
     */
    private void generatePublicCode() throws IOException, TemplateException {
        //模板
        Template template = null;
        //输出文件
        File file = null;
        //先生成公共代码
        for(String templateName : publicTemplates){
            template = configuration.getTemplate(templateName);
            //获取创建文件
            String fileName = templateName.replace("classRoot",classRootDir)
                    .replace("resources",resourcesDir)
                    .replace("module",moduleName)
                    .replace("$",".")
                    .replace(".ftl","");
            file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Map<String,Object> root = new HashMap();
            //设置填充数据
            root.put("packagePath",packagePath);
            root.put("moduleName",moduleName);
            root.put("templateValueList",templateValueList);
            Writer out = new OutputStreamWriter(fileOutputStream);
            template.process(root,out);
            out.flush();
            fileOutputStream.close();
        }
    }

    /**
     * 为每张表生成业务代码
     */
    private void generateBusinessCode() throws IOException, TemplateException {
        //模板
        Template template = null;
        File file = null;
        //循环处理每张表
        for(TemplateValue templateValue : templateValueList){
            //设置包名称
            templateValue.setPackagePath(packagePath);
            //循环处理每个模板
            for(String templateName : templates){
                template = configuration.getTemplate(templateName);
                //获取创建文件
                String fileName = templateName.replace("classRoot",classRootDir)
                        .replace("resources",resourcesDir)
                        .replace("ClassName",templateValue.getClassName())
                        .replace("packagePath",packagePath.replace(".","/"))
                        .replace("$",".")
                        .replace(".ftl","");
                file = new File(fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Writer out = new OutputStreamWriter(fileOutputStream);
                template.process(templateValue,out);
                out.flush();
                fileOutputStream.close();
            }
        }
    }

    public void compileAndLoad(){
        groovyFactory.loadAndInit("Hello").print();
    }
}
