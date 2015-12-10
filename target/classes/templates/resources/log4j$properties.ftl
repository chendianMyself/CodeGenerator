log4j.rootLogger = debug, file,stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.Target=System.out
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{ABSOLUTE} %5p %c{1}\:%L - %m%n

log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=F:/${moduleName}/${moduleName}.log
log4j.appender.file.MaxFileSize=1000KB
log4j.appender.file.MaxBackupIndex=10
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=%-d...{yyyy-MM-dd HH:mm:ss} [%c]-[%p] %m%n


log4j.logger.${packagePath}.service.impl=DEBUG
log4j.logger.org.springframework=ERROR

# SqlMap   logging   configuration...
log4j.logger.com.ibatis=DEBUG
log4j.logger.com.ibatis.common.jdbc.SimpleDataSource=DEBUG
log4j.logger.com.ibatis.common.jdbc.ScriptRunner=DEBUG
log4j.logger.com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=DEBUG
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
log4j.logger.java.sql.ResultSet=ERROR