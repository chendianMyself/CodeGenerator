<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE sqlMap
        PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN"
        "http://ibatis.apache.org/dtd/sql-map-2.dtd">

<sqlMap namespace="${className?uncap_first}">
    <typeAlias alias=" ${className}" type="${packagePath}.entity.${className}"/>

    <resultMap id="${className?uncap_first}" class="${className}">
    <#list columns as column>
    <#if column.type == "String" || column.type == "Date">
        <result property="${column.name}" column="${column.columnName}"/>
    <#else>
        <result property="${column.name}" column="${column.columnName}"
                nullValue="0"/>
    </#if>
    </#list>
    </resultMap>

    <insert id="create" parameterClass="${className}">
    <#list columns as column>
        <#if column.pkFlag>
        <selectKey keyProperty="${column.name}" resultClass="java.lang.${column.type}">
            SELECT S_${tableName}.NEXTVAL FROM DUAL
            <#--主键字段-->
            <#assign pkName = column.name/>
            <#assign pkColumnName = column.columnName/>
        </selectKey>
            </#if>
        </#list>
            INSERT INTO ${tableName}(
        <#list columns as column>
            <#if column_has_next>
                ${column.columnName},
            <#else>
                ${column.columnName})
            </#if>
        </#list>
            VALUES(
        <#list columns as column>
            <#if column_has_next>
                #${column.name}#,
            <#else>
                #${column.name}#)
            </#if>
        </#list>
    </insert>

    <delete id="delete" parameterClass="${className}">
    <#list columns as column>
        <#if column.pkFlag>
            DELETE FROM ${tableName} T WHERE T.${column.columnName} = #${column.name}#
        </#if>
    </#list>
    </delete>

    <!--注：isNotEmpty表示既不为空也不为null,isNotNull仅表示不为null-->
    <select id="search" resultMap="${className?uncap_first}.${className?uncap_first}"
            parameterClass="${className}">
            SELECT T.* from ${tableName} T
                <dynamic prepend="WHERE">
            <#list columns as column>
                <#if column.type == "Double" || column.type == "double"
                    || column.type == "Integer" || column.type == "int"
                    || column.type == "Long" || column.type == "long">
                    <isGreaterThan prepend="AND" property="${column.name}" compareValue="0">
                        T.${column.columnName} = #${column.name}#
                    </isGreaterThan>
                <#else>
                    <isNotEmpty prepend="AND" property="${column.name}">
                        T.${column.columnName} = #${column.name}#
                    </isNotEmpty>
                </#if>
            </#list>
            </dynamic>
            ORDER BY T.${pkColumnName} DESC
    </select>

    <update id="update" parameterClass="${className}">
            UPDATE  T_USER T
            SET
        <#list columns as column>
            <#if !column.pkFlag>
                T.${column.columnName} = #${column.name}#
            </#if>
        </#list>
            WHERE
            T.${pkColumnName} = #${pkName}#
    </update>
</sqlMap>