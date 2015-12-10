package com.zl.codeGenerator.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class TemplateValue {
    /**
     * 实体类名称
     */
    private String className;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 实体所含字段
     */
    private List<TemplateColumn> columns = new ArrayList<TemplateColumn>();

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 对应的数据库表名
     */
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<TemplateColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TemplateColumn> columns) {
        this.columns = columns;
    }
}
