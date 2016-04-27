package com.zl.codeGenerator.dynamic;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scripting.ScriptSource;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2015/12/11.
 * 实现自己的的数据库ScriptSource
 */
public class DatabaseScriptSource implements ScriptSource {
    //脚本名称
    private String scriptName;
    //上一次代码更新时间
    private Date lastUpdate;
    //更新时间修改监视器
    private final Object lastModifiedMonitor = new Object ();

    public DatabaseScriptSource(String scriptName){
        this.scriptName = scriptName;
    }

    /**
     * 获取脚本源代码
     * @return
     * @throws IOException
     */
    @Override
    public String getScriptAsString() throws IOException {
        //加锁，防止并发
        //当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
        //当一个线程访问object的一个synchronized(this)同步代码块时，另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
        //当一个线程访问object的一个synchronized(this)同步代码块时，其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
        //第三个例子同样适用其它同步代码块。也就是说，当一个线程访问object的一个synchronized(this)同步代码块时，它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。
        synchronized (this.lastModifiedMonitor)
        {
            this.lastUpdate = new Date ();
        }
        String script = "package com.zl.codeGenerator.dynamic;\n" +
                "\n" +
                "import com.zl.codeGenerator.entity.TemplateInterface;\n" +
                "\n" +
                "public class Hello implements TemplateInterface{\n" +
                "    public void print(){\n" +
                "        System.out.println(\"Hello world!\");\n" +
                "    }\n" +
                "}";
        return script;
    }

    /**
     * 确定脚本是否被修改
     * @return
     */
    @Override
    public boolean isModified() {
        synchronized (this.lastModifiedMonitor)
        {
            Date lastUpdated = new Date ();//模拟
            final boolean isModified = lastUpdated.after (this.lastUpdate);
            return isModified;
        }
    }

    /**
     * 脚本的建议类名
     * @return
     */
    @Override
    public String suggestedClassName() {
        return scriptName;
    }
}
