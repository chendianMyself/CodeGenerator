package com.zl.codeGenerator.dynamic;

import org.springframework.core.io.ResourceLoader;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;
import org.springframework.scripting.support.StaticScriptSource;

import javax.activation.DataSource;

/**
 * Created by Administrator on 2015/12/11.
 *
 */
public class CustomScriptFactoryPostProcessor extends ScriptFactoryPostProcessor {

    public static final String MY_SCRIPT_PREFIX = "zl:";

    /**
     * 重写获取脚本的方法
     * @param beanName
     * @param scriptSourceLocator
     * @param resourceLoader
     * @return
     */
    protected ScriptSource convertToScriptSource(String beanName, String scriptSourceLocator, ResourceLoader resourceLoader) {
        if (scriptSourceLocator.startsWith (INLINE_SCRIPT_PREFIX))
        {
            //内联脚本
            return new StaticScriptSource(scriptSourceLocator.substring (
                    INLINE_SCRIPT_PREFIX.length ()));
        } else if (scriptSourceLocator.startsWith (MY_SCRIPT_PREFIX))
        {
            //自定义：从数据库装载脚本
            return new DatabaseScriptSource (scriptSourceLocator.substring (
                    MY_SCRIPT_PREFIX.length ()));
        } else
        {
            //基于资源的脚本
            return new ResourceScriptSource(
                    resourceLoader.getResource (scriptSourceLocator));
        }
    }
}
