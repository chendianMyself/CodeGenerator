package ${packagePath}.dao.impl;

import ${packagePath}.dao.base.ReflectUtil;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.execution.DefaultSqlExecutor;
import com.ibatis.sqlmap.engine.impl.ExtendedSqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public class BaseDAO extends SqlMapClientDaoSupport {
    //SQL执行器
    private DefaultSqlExecutor sqlExecutor;

    public DefaultSqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    public void setSqlExecutor(DefaultSqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    //该类初始化方法，在Spring配置文件中配置
    public void initialize() throws Exception {
        if (sqlExecutor != null) {
            SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
            if (sqlMapClient instanceof ExtendedSqlMapClient) {
                //sqlExecutor没有公共set方法，则通过反射方式设置
                ReflectUtil.setFieldValue(((ExtendedSqlMapClient) sqlMapClient)
                                .getDelegate(), "sqlExecutor", SqlExecutor.class,
                        sqlExecutor);
            }
        }
    }
}
