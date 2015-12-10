<?xml version="1.0" encoding="UTF-8" ?>

<!DOCTYPE sqlMapConfig
        PUBLIC "-//ibatis.apache.org//DTD SQL Map Config 2.0//EN"
        "http://ibatis.apache.org/dtd/sql-map-config-2.dtd">

<sqlMapConfig>
    <settings lazyLoadingEnabled="true" useStatementNamespaces="true" />
    <#list templateValueList as templateValue>
        <sqlMap resource="${packagePath?replace(".","/")}/dao/impl/ibatisXml/${templateValue.className}.xml"></sqlMap>
    </#list>
</sqlMapConfig>