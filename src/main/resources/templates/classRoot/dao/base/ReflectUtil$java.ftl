package ${packagePath}.dao.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Modifier;
import java.lang.reflect.Field;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class ReflectUtil {
    private static final Log LOG = LogFactory.getLog(ReflectUtil.class);

    /**
     * 设置某个字段的值
     * @param target
     * @param fieldName
     * @param filedType
     * @param fieldValue
     */
    public static void setFieldValue(Object target, String fieldName, Class filedType, Object fieldValue) {
        if (target != null && fieldName != null && !"".equals(fieldName)
                && fieldValue != null && filedType.isAssignableFrom(fieldValue.getClass())) {
            Class clazz = target.getClass();
            try {
                Field field = clazz.getDeclaredField(fieldName);
                //若不是公共方法
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                //修改字段的值
                field.set(target, fieldValue);
            } catch (Exception e) {
                e.printStackTrace();
                if (LOG.isDebugEnabled()) {
                    LOG.debug(e);
                }
            }
        }
    }
}
