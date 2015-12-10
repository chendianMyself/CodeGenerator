package ${packagePath}.dao.impl;

import ${packagePath}.entity.${className};
import ${packagePath}.dao.${className}DAO;
import ${packagePath}.dao.impl.BaseDAO;

import java.util.List;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class ${className}DAOImpl extends BaseDAO implements ${className}DAO {
    public ${className} create(${className} ${className?uncap_first}) {
        getSqlMapClientTemplate().insert(
            "${className?uncap_first}.create", ${className?uncap_first});
        return ${className?uncap_first};
    }

    public void delete(${className} ${className?uncap_first}) {
        getSqlMapClientTemplate().delete(
            "${className?uncap_first}.delete", ${className?uncap_first});
    }

    public List<${className}> search(${className} ${className?uncap_first}) {
        return getSqlMapClientTemplate().queryForList(
            "${className?uncap_first}.search", ${className?uncap_first});
    }

    public void update(${className} ${className?uncap_first}) {
        getSqlMapClientTemplate().update(
            "${className?uncap_first}.update", ${className?uncap_first});
    }
}
