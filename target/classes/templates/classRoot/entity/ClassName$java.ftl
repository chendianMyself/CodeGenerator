package ${packagePath}.entity;

<#--获取要导入的类型-->
<#assign DateFlag=false/>
<#list columns as column>
    <#if column.type == "Date">
        <#assign DateFlag=true/>
    </#if>
</#list>
<#if DateFlag == true>
import java.util.Date;
</#if>

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class ${className}{
    <#list columns as column>
    //${column.remark}
    private ${column.type} ${column.name};
    </#list>

    <#list columns as column>
    public void set${column.name?cap_first}(${column.type} ${column.name}){
        this.${column.name} = ${column.name};
    };
    public ${column.type} get${column.name?cap_first}(){
        return ${column.name};
    }
    </#list>
}