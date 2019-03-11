### 1.1 包								
	log4j-1.2.15.jar	
	commons-logging.jar								
### 1.2 配置项				
#### 1.2.1 rootLogger=级别，参数...
|级别|值|
|-----|-----|
|FATAL|0|
|ERROR|3|
|WARN|4|
|INFO|6|
|DEBUG|7|
#### 1.2.2 Appender 为日志输出目的地							
org.apache.log4j.ConsoleAppender（控制台）							
org.apache.log4j.FileAppender（文件）							
org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件）		
org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件）
org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）										
#### 1.2.3 Layout 日志输出格式											
org.apache.log4j.HTMLLayout（以HTML表格形式布局）
org.apache.log4j.PatternLayout（可以灵活地指定布局模式）
org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串）
org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）			

#### 1.2.4 格式化日志信息
Log4J采用类似C语言中的printf函数的打印格式格式化日志信息
 - %m   输出代码中指定的消息
 - %p    输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL 
 - %r     输出自应用启动到输出该log信息耗费的毫秒数 
 - %c     输出所属的类目，通常就是所在类的全名 
 - %t     输出产生该日志事件的线程名 
 - %n    输出一个回车换行符，Windows平台为“\r\n”，Unix平台为“\n” 
 - %d    输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyy MMM dd HH:mm:ss , SSS}，输出类似：2002年10月18日 22 ： 10 ： 28 ， 921 
 - %l     输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main(TestLog4.java: 10 ) 

可以在%与模式字符之间加上修饰符来控制其最小宽度、最大宽度、和文本的对齐方式。如：
1)%20c:指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，默认的情况下右对齐。
2)%-20c:指定输出category的名称，最小的宽度是20，如果category的名称小于20的话，"-"号指定左对齐。
3)%.30c:指定输出category的名称，最大的宽度是30，如果category的名称大于30的话，就会将左边多出的字符截掉，但小于30的话也不会有空格。
4)%20.30c:如果category的名称小于20就补空格，并且右对齐，如果其名称长于30字符，就从左边较远输出的字符截掉。

#### 1.2.5 输出DEBUG级别以上的日志	
log4j.appender.R.Threshold = debug	
#### 1.2.6 控制台选项			
Threshold=DEBUG:指定日志消息的输出最低层次。
ImmediateFlush=true:默认值是true,意谓着所有的消息都会被立即输出。
Target=System.err：默认情况下是：System.out,指定输出控制台
#### 1.2.7 FileAppender 选项
Threshold=DEBUF:指定日志消息的输出最低层次。
ImmediateFlush=true:默认值是true,意谓着所有的消息都会被立即输出。
File=mylog.txt:指定消息输出到mylog.txt文件。
Append=false:默认值是true,即将消息增加到指定文件中，false指将消息覆盖指定的文件内容。
#### 1.2.8 RollingFileAppender 选项
Threshold=DEBUG:指定日志消息的输出最低层次。
ImmediateFlush=true:默认值是true,意谓着所有的消息都会被立即输出。
File=mylog.txt:指定消息输出到mylog.txt文件。
Append=false:默认值是true,即将消息增加到指定文件中，false指将消息覆盖指定的文件内容。
MaxFileSize=100KB: 后缀可以是KB, MB 或者是 GB. 在日志文件到达该大小时，将会自动滚动，即将原来的内容移到mylog.log.1文件。
MaxBackupIndex=2:指定可以产生的滚动文件的最大数。

### 1.3 样例					
```
log4j.rootLogger=info,A1,R						
log4j.appender.A1=org.apache.log4j.ConsoleAppender	
log4j.appender.A1.Target=System.out								
log4j.appender.A1.layout=org.apache.log4j.PatternLayout		
log4j.appender.A1.layout.ConversionPattern=[%c]%m%n	
```
```
log4j.appender.R=org.apache.log4j.DailyRollingFileAppender	
log4j.appender.R.File=../logs/log_.txt								
log4j.appender.R.layout=org.apache.log4j.PatternLayout			
log4j.appender.R.Append = true	# 在之前文件下追加	false为覆盖文件		
log4j.appender.R.ImmediateFlush = true							
log4j.appender.R.DatePattern = '.' yyyy - MM - dd '.txt'		
log4j.appender.R.layout.ConversionPattern=[%p][%d{yyyy-MM-dd HH\:mm\:ss,SSS}][%c]%m%n
```