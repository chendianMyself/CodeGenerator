package ${packagePath}.dao;

/**
 * @author <a href="mailto:luckylucky3210@163.com">ZL</a>
 * @version $Id$
 *          Date: ${.now?date}
 *          Time: ${.now?time}
 * 通过模板自动生成
 */
public interface Dialect {
    /**
     * 获取带页数的Sql语句
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public String getPageString(String sql, int offset, int limit);
}
