package ${packagePath}.service.impl;

import ${packagePath}.entity.${className};
import ${packagePath}.dao.${className}DAO;
import ${packagePath}.service.${className}Service;
import ${packagePath}.entity.PageList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class ${className}ServiceImpl implements ${className}Service{

    public static final Log LOG = LogFactory.getLog(${className}ServiceImpl.class);

    private ${className}DAO ${className?uncap_first}DAO;

    public ${className}DAO ${className}DAO() {
        return ${className?uncap_first}DAO;
    }

    public void set${className}DAO(${className}DAO ${className?uncap_first}DAO) {
        this.${className?uncap_first}DAO = ${className?uncap_first}DAO;
    }

    public ${className} create(${className} ${className?lower_case}){
        return ${className?uncap_first}DAO.create(${className?lower_case});
    }

    public void delete(${className} ${className?lower_case}){
        ${className?uncap_first}DAO.delete(${className?lower_case});
    }

    public void modify(${className} ${className?lower_case}){
        ${className?uncap_first}DAO.modify(${className?lower_case});
    }

    public List<${className}> search(${className} ${className?lower_case}){
        return ${className?uncap_first}DAO.search(${className?lower_case});
    }

    public PageList searchByPage(${className} ${className?lower_case}, int pageNumber, int pageSize){
        PageList pageList = new PageList();
        pageList.setObjList(${className?uncap_first}DAO.searchByPage(${className?lower_case},pageNumber,pageSize));
        pageList.setCount(${className?uncap_first}DAO.searchCount(${className?lower_case}));
        return pageList;
    }
}