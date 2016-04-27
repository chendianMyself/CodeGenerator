package com.zl.codeGenerator.dynamic;

import com.zl.codeGenerator.entity.TemplateInterface;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;



/**
 * Created by Administrator on 2015/12/11.
 */
public class GroovyFactory implements ApplicationContextAware {

    //父级ApplicationContext
    private ApplicationContext parentContext;
    //动态算法ApplicationContext
    private GenericApplicationContext dynamicContext;
    //刷新时间
    private static final int DEFAULT_REFRESH_CHECK_DELAY = 10 * 60 * 1000;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        parentContext = applicationContext;
    }

    public TemplateInterface loadAndInit(String scriptName){
        //创建子ApplicationContext
        dynamicContext = new GenericApplicationContext ();
        //设置父ApplicationContext
        dynamicContext.setParent (parentContext);
        //该bean负责用由工厂创建的实际对象将工厂bean替换
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .rootBeanDefinition(
                        "com.zl.codeGenerator.dynamic.CustomScriptFactoryPostProcessor")
                .setLazyInit (false);
        //设置自动判断代码是否已经更新的时间
        beanDefinitionBuilder.addPropertyValue("defaultRefreshCheckDelay",DEFAULT_REFRESH_CHECK_DELAY);
        dynamicContext.registerBeanDefinition ("scriptFactoryPostProcessor",
                beanDefinitionBuilder.getBeanDefinition ());
        dynamicContext.refresh ();
        //定义工厂bean，通过取得脚本创建实际对象
        String location = CustomScriptFactoryPostProcessor.MY_SCRIPT_PREFIX + scriptName;
        BeanDefinitionBuilder scriptBuilder = BeanDefinitionBuilder
                .rootBeanDefinition (
                        "org.springframework.scripting.groovy.GroovyScriptFactory")
                .addConstructorArg(location);
        dynamicContext.registerBeanDefinition (scriptName,
                scriptBuilder.getBeanDefinition ());
        //获取实体返回
        return (TemplateInterface) dynamicContext.getBean(scriptName);
    }
}
