package com.zl.codeGenerator.entity;


/**
 * Created by Administrator on 2015/12/9.
 */
public class TemplateColumn {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段类型
     */
    private String type;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对应的数据库字段名
     */
    private String columnName;

    /**
     * 是否为主键
     */
    private boolean pkFlag;

    /**
     * 是否自增id
     */
    private boolean autoFlag;

    public boolean isAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(boolean autoFlag) {
        this.autoFlag = autoFlag;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isPkFlag() {
        return pkFlag;
    }

    public void setPkFlag(boolean pkFlag) {
        this.pkFlag = pkFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
