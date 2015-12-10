package ${packagePath}.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class Configuration {
    private static Log LOG = LogFactory.getLog(Configuration.class);
    private final static Configuration configration = new Configuration();
    private Properties properties;
    private Locale locale;
    private static final String BANDLE_NAME = "config-mg";

    private Configuration() {
        properties = new Properties();
        locale = Locale.CHINA;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BANDLE_NAME, locale,
                    Thread.currentThread().getContextClassLoader());
            for (Enumeration keys = bundle.getKeys(); keys.hasMoreElements();) {
                String key = (String) keys.nextElement();
                properties.setProperty(key, bundle.getString(key));
            }
        } catch (Exception e) {
            LOG.error("Can't read the properties file. ", e);
        }
    }

    /**
     * Use singleton pattern, only return one instance of Configuration.
     *
     * @return Configuration
     */
    public static Configuration getInstance() {
        return configration;
    }

    /**
     * Retun a value for certain key.
     *
     * @param key a certain key define in properties file.
     * @return value
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public Locale getLocale() {
        return locale;
    }
}
