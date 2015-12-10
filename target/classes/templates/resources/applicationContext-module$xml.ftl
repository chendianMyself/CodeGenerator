<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

        <import resource="applicationContext-jdbc.xml"/>

    <#list templateValueList as templateValue>
        <bean id="sqlMapClient-${templateValue.className?uncap_first}"
              class="org.springframework.orm.ibatis.SqlMapClientFactoryBean">
            <property name="configLocation"
                      value="classpath:/sqlMapConfig-${templateValue.className}.xml"/>
        </bean>
    </#list>
    <#--DAO-->
    <#list templateValueList as templateValue>
        <bean id="${templateValue.className?uncap_first}DAO" class="${packagePath}.dao.impl.${templateValue.className}DAOImpl"
              parent="baseDAO">
            <property name="sqlMapClient" ref="sqlMapClient-${templateValue.className?uncap_first}"/>
        </bean>
    </#list>
    <#--Service-->
    <#list templateValueList as templateValue>
        <bean id="${templateValue.className?uncap_first}Service" class="${packagePath}.service.impl.${templateValue.className}ServiceImpl">
            <property name="${templateValue.className?uncap_first}DAO" ref="${templateValue.className?uncap_first}DAO"/>
        </bean>
    </#list>

</beans>