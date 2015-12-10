package ${packagePath}.dao.base;

import ${packagePath}.dao.Dialect;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
//Oracle数据库方言
public class OracleDialect implements Dialect {
    //sql语句结尾符
    private static final String SQL_END_DELIMITER = ";";

    public String getPageString(String sql, int offset, int limit) {
        int first = (offset - 1) * limit + 1;
        int last = offset * limit;
        StringBuffer sb = new StringBuffer()
            .append(" SELECT * FROM (SELECT ROW_.*, ROWNUM ROWNNUM_ FROM ( ")
            .append(trim(sql))
            .append(" ) ROW_ WHERE ROWNUM <= ")
            .append(last)
            .append(")  WHERE ROWNNUM_ >= ")
            .append(first);
        return sb.toString();
    }

    /**
     * 去掉SQL语句结尾符号
     * @param sql
     * @return
     */
    private String trim(String sql) {
        sql = sql.trim();
        if (sql.endsWith(SQL_END_DELIMITER)) {
            sql = sql.substring(0, sql.length() - 1
                    - SQL_END_DELIMITER.length());
        }
        return sql;
    }
}
