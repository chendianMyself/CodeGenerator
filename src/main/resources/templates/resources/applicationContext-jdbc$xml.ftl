<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean id="propertyConfigurer"
          class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath*:config.properties</value>
            </list>
        </property>
    </bean>

    <!--dataSource-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource"
          destroy-method="close">
        <property name="driverClass" value="${"$"+"{driverClass}"}"/>
        <property name="jdbcUrl" value="${"$"+"{jdbcUrl}"}"/>
        <property name="user" value="${"$"+"{user}"}"/>
        <property name="password" value="${"$"+"{password}"}"/>
        <property name="initialPoolSize" value="2"/>
        <property name="minPoolSize" value="2"/>
        <property name="maxPoolSize" value="100"/>
        <property name="checkoutTimeout" value="5000"/>
        <property name="maxIdleTime" value="1800"/>
        <property name="idleConnectionTestPeriod" value="3000"/>
        <property name="acquireIncrement" value="2"/>
    </bean>

    <!--事物管理器-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--使用tx标签配置的拦截器-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="get*" read-only="true"/>
            <tx:method name="*" read-only="false" rollback-for="Exception"/>
        </tx:attributes>
    </tx:advice>

    <aop:config proxy-target-class="true">
        <aop:pointcut id="DynamicDataServicePointcut"
                      expression="execution(* *..*Service.*(..))"/>
        <aop:advisor advice-ref="txAdvice"
                     pointcut-ref="DynamicDataServicePointcut"/>
    </aop:config>

    <!--以上为基本配置-->
    <bean id="sqlExecutor" class="${packagePath}.dao.base.PageExecutor">
        <property name="dialect">
            <bean class="${packagePath}.dao.base.OracleDialect"/>
        </property>
        <property name="enablePage">
            <value>true</value>
        </property>
    </bean>

    <bean id="baseDAO" abstract="true" class="${packagePath}.dao.impl.BaseDAO"
          init-method="initialize">
        <property name="dataSource">
            <ref bean="dataSource" />
        </property>
        <property name="sqlExecutor">
            <ref bean="sqlExecutor" />
        </property>
    </bean>
</beans>