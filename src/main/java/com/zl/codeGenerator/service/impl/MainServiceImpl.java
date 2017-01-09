package com.zl.codeGenerator.service.impl;

import com.zl.codeGenerator.Generator;
import com.zl.codeGenerator.dynamic.GroovyFactory;
import com.zl.codeGenerator.entity.TemplateColumn;
import com.zl.codeGenerator.entity.TemplateValue;
import com.zl.codeGenerator.service.MainService;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2015/12/9.
 */
public class MainServiceImpl implements MainService{

    private static Log LOG = LogFactory.getLog(MainServiceImpl.class);

    private Configuration configuration;

    private GroovyFactory groovyFactory;

    //模板路径
    private String templatePath;

    //生成业务代码的模板名称
    private List<String> templates = new ArrayList<String>();

    //Excel文件路径
    private String excelPath;

    //模块名称
    private String moduleName;

    //包路径
    private String packagePath;

    //模型数据
    private TemplateValue templateValue;

    //代码文件生成根目录
    private String moduleDir;

    //资源文件目录
    private String resourcesDir;

    //java文件根目录
    private String classRootDir;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public GroovyFactory getGroovyFactory() {
        return groovyFactory;
    }

    public void setGroovyFactory(GroovyFactory groovyFactory) {
        this.groovyFactory = groovyFactory;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public List<String> getTemplates() {
        return templates;
    }

    public void setTemplates(List<String> templates) {
        this.templates = templates;
    }

    public String getExcelPath() {
        return excelPath;
    }

    public void setExcelPath(String excelPath) {
        this.excelPath = excelPath;
    }

    public String getModuleDir() {
        return moduleDir;
    }

    public void setModuleDir(String moduleDir) {
        this.moduleDir = moduleDir;
    }


    public void generate() throws Exception {
        //设置模板路径
        configuration.setClassForTemplateLoading(Generator.class, templatePath);
        //默认编码格式
        configuration.setDefaultEncoding("UTF-8");
        //指定模板如何查看数据类型
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        //从Excel读取数据
        getTemplateValueFromExcel();
        //生成代码文件目录
        generateDir();
        //为每张表生成业务代码
        generateBusinessCode();
    }

    /**
     * 生成代码存放目录
     */
    private void generateDir(){
        LOG.info("开始生成目录...");
        resourcesDir = moduleDir+moduleName+"/src/main/resources/";
        //创建resource目录
        File file = new File(resourcesDir + "/ibatisXml");
        file.mkdirs();
        classRootDir = moduleDir+moduleName + "/src/main/java/" + packagePath.replace(".","/");
        //创建entity目录
        file = new File(classRootDir+"/entity");
        file.mkdirs();
        //创建service层目录
        file = new File(classRootDir+"/service/impl");
        file.mkdirs();
        //创建dao层目录
        file = new File(classRootDir+"/dao/impl");
        file.mkdirs();
        LOG.info("目录生成完成...");
    }

    /**
     * 生成业务代码
     */
    private void generateBusinessCode() throws IOException, TemplateException {
        LOG.info("开始生成代码文件...");
        //模板
        Template template = null;
        File file = null;
        //设置包名称
        templateValue.setPackagePath(packagePath);
        //循环处理每个模板
        for(String templateName : templates){
            template = configuration.getTemplate(templateName);
            //模板编码格式
            template.setEncoding("UTF-8");
            //获取创建文件
            String fileName = templateName.replace("classRoot",classRootDir)
                    .replace("resources", resourcesDir)
                    .replace("ClassName", templateValue.getClassName())
                    .replace("packagePath", packagePath.replace(".", "/"))
                    .replace("$", ".")
                    .replace(".ftl", "");
            file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            //输出文件为UTF-8格式
            Writer out = new OutputStreamWriter(fileOutputStream,"UTF-8");
            template.process(templateValue,out);
            out.flush();
            fileOutputStream.close();
        }
        LOG.info("代码文件生成完成...");
    }

    /**
     * 读取excel文件获取数据
     */
    private void getTemplateValueFromExcel() throws Exception{
        LOG.info("开始读取Excel文件...");
        File excel = new File(excelPath);
        WorkbookSettings settings = new WorkbookSettings();
        settings.setEncoding("ISO-8859-1"); //解决中文乱码，或GBK
        Workbook rwb = Workbook.getWorkbook(excel,settings);
        Sheet rs = rwb.getSheet(0);
        int columns = rs.getColumns();
        int rows = rs.getRows();
        if(columns != 6 || rows < 4){
            LOG.error("Excel文件数据条数错误");
            throw new Exception("Excel文件数据条数错误");
        }
        //模块名称
        moduleName = rs.getCell(0,1).getContents().trim();
        //包名称
        packagePath = rs.getCell(1,1).getContents().trim();
        //类信息
        templateValue = new TemplateValue();
        //类名
        templateValue.setClassName(rs.getCell(2,1).getContents().trim());
        //备注
        templateValue.setRemark(rs.getCell(3,1).getContents().trim());
        //数据库表名
        templateValue.setTableName(rs.getCell(4,1).getContents().trim());
        //类属性信息
        List<TemplateColumn> templateColumns = new ArrayList<TemplateColumn>();
        templateValue.setColumns(templateColumns);
        boolean pkFlag = false;
        boolean autoFlag = false;;
        for(int i = 3; i < rows; i++){
            TemplateColumn column = new TemplateColumn();
            column.setName(rs.getCell(0, i).getContents().trim());
            if(column.getName() == null || column.getName().equals("")){
                //默认无数据了
                break;
            }
            column.setType(rs.getCell(1,i).getContents().trim());
            column.setRemark(rs.getCell(2, i).getContents().trim());
            column.setColumnName(rs.getCell(3,i).getContents().trim());
            column.setPkFlag(rs.getCell(4,i).getContents().trim().equals("是") ? true:false);
            if(column.isPkFlag() && pkFlag){
                LOG.error("只能有一个主键");
                throw new Exception("只能有一个主键");
            }
            pkFlag = pkFlag ? pkFlag : column.isPkFlag();
            column.setAutoFlag(rs.getCell(5,i).getContents().trim().equals("是") ? true : false);
            if(column.isAutoFlag() && autoFlag){
                LOG.error("只能有一个自增id");
                throw new Exception("只能有一个自增id");
            }
            autoFlag = autoFlag ? autoFlag : column.isAutoFlag();
            templateColumns.add(column);
        }
        if(! pkFlag){
            LOG.error("必须要有一个主键");
            throw new Exception("必须要有一个主键");
        }
        LOG.info("读取Excel文件完成...");
    }

    public void compileAndLoad(){
        groovyFactory.loadAndInit("Hello").print();
    }
}
