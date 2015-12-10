package ${packagePath}.dao.base;

import ${packagePath}.dao.Dialect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.execution.DefaultSqlExecutor;

import java.sql.SQLException;
import java.sql.Connection;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
//sql语句执行器
public class PageExecutor extends DefaultSqlExecutor {
    private static final Log LOG = LogFactory.getLog(PageExecutor.class);
    private boolean enablePage;//支持分页
    private Dialect dialect;//数据库方言

    public boolean isEnablePage() {
        return enablePage;
    }

    public void setEnablePage(boolean enablePage) {
        this.enablePage = enablePage;
    }

    public Dialect getDialect() {
        return dialect;
    }

    public void setDialect(Dialect dialect) {
        this.dialect = dialect;
    }

    //覆盖原默认SQL执行方法
    @Override
    public void executeQuery(StatementScope statementScope, Connection conn, String sql,
                            Object[] parameters, int skipResults, int maxResults,
                            RowHandlerCallback callback) throws SQLException {
        //若进行分页
        if ((skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)
                && isEnablePage()) {
            //封装SQL语句
            sql = getDialect().getPageString(sql, skipResults, maxResults);
            if (LOG.isDebugEnabled()) {
                LOG.debug(sql);
            }
            //将sql执行模式设为进行分页
            skipResults = NO_SKIPPED_RESULTS;
            maxResults = NO_MAXIMUM_RESULTS;
        }
        //执行sql语句【以不分页的模式】
        super.executeQuery(statementScope, conn, sql, parameters, skipResults,
                maxResults, callback);
    }
}
