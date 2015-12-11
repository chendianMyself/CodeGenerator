package com.zl.codeGenerator.dynamic;

import org.springframework.core.io.ResourceLoader;
import org.springframework.scripting.ScriptSource;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.scripting.support.ScriptFactoryPostProcessor;
import org.springframework.scripting.support.StaticScriptSource;

import javax.activation.DataSource;

/**
 * Created by Administrator on 2015/12/11.
 */
public class FactoryPostProcessor extends ScriptFactoryPostProcessor {

    public static final String MY_SCRIPT_PREFIX = "zl:";
        protected ScriptSource convertToScriptSource(String beanName, String scriptSourceLocator, ResourceLoader resourceLoader) {
            if (scriptSourceLocator.startsWith (INLINE_SCRIPT_PREFIX))
            {
                return new StaticScriptSource(scriptSourceLocator.substring (
                        INLINE_SCRIPT_PREFIX.length ()));
            } else if (scriptSourceLocator.startsWith (MY_SCRIPT_PREFIX))
            {
                return new GroovyScriptSource (scriptSourceLocator.substring (
                        MY_SCRIPT_PREFIX.length ()));
            } else
            {
                return new ResourceScriptSource(
                        resourceLoader.getResource (scriptSourceLocator));
            }
        }
}
