package ${packagePath}.service;

import ${packagePath}.entity.${className};
import java.util.List;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public interface ${className}Service{

    /**
     * 新建${remark}
     * @param ${className?uncap_first}
     * @return
     */
    ${className} create(${className} ${className?uncap_first});

    /**
     * 删除${remark}
     * @param ${className?uncap_first}
     * @return
     */
    void delete(${className} ${className?uncap_first});

    /**
     * 更新${remark}
     * @param ${className?uncap_first}
     * @return
     */
    void update(${className} ${className?uncap_first});

    /**
     * 查询${remark}
     * @param ${className?uncap_first}
     * @return
     */
    List<${className}> search(${className} ${className?uncap_first});
}