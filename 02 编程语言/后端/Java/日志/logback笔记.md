```
<?xml version="1.0" encoding="UTF-8"?>

<!-- 根节点<configuration>，包含下面三个属性：
		scan: 当此属性设置为true时，配置文件如果发生改变，将会被重新加载，默认值为true。
		scanPeriod: 设置监测配置文件是否有修改的时间间隔，如果没有给出时间单位，默认单位是毫秒。当scan为true时，此属性生效。默认的时间间隔为1分钟。
		debug: 当此属性设置为true时，将打印出logback内部日志信息，实时查看logback运行状态。默认值为false。-->
<configuration>

	<!-- --------------定义变量值，用于后期的引用-------------- -->
    <springProperty scope="context" name="env" source="spring.profiles"/>
    <springProperty scope="context" name="raw_log_path" source="log.path.all"/>
    <springProperty scope="context" name="raw_error_log_path" source="log.path.error"/>
    <springProperty scope="context" name="log_level" source="log.level.app"/>
    <springProperty scope="context" name="root_log_level" source="log.level.root"/>

	<!-- 用来设置上下文名称，每个logger都关联到logger上下文，默认上下文名称为default。但可以使用<contextName>设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改。 -->
	<contextName>default</contextName>
	
    <!-- 子节点<appender>：负责写日志的组件，它有两个必要属性name和class。name指定appender名称，class指定appender的全限定名 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<!-- ConsoleAppender 把日志输出到控制台
			target:字符串System.out(默认)或者System.err
			encoder:对日志进行格式化 -->
        <target>System.out</target>
        <encoder>
            <pattern>%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%thread] %logger{5} \(%file:%line\) [%X{batch_loop_id}] [%X{batch_request_id}] [%X{batch_job_name}] - ${env} - ${HOSTNAME} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE_RAW" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<!-- RollingFileAppender 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件。
			file:被写入的文件名，可以是相对目录，也可以是绝对目录。
			append:如果是true，日志被追加到文件结尾，如果是false，清空现存文件，默认是true。
			rollingPolicy:当发生滚动时，决定RollingFileAppender的行为，涉及文件移动和重命名。属性class定义具体的滚动策略类 -->
        <file>${raw_log_path}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!-- 必要节点，包含文件名及“%d”转换符，“%d”可以包含一个java.text.SimpleDateFormat指定的时间格式，如：%d{yyyy-MM}。 -->
            <fileNamePattern>${raw_log_path}.%d{yyyy-MM-dd}</fileNamePattern>
			<!-- 控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每个月滚动，且<maxHistory>是14，则只保存最近14个月的文件，删除之前的旧文件。 -->
            <maxHistory>14</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%thread] %logger{5} \(%file:%line\) [%X{batch_loop_id}] [%X{batch_request_id}] [%X{batch_job_name}] - ${env} - ${HOSTNAME} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE_RAW_ERROR" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<!-- <filter> 过滤节点 过滤器的返回值只能是 ACCEPT、DENY 和 NEUTRAL 的其中一个。
					FilterReply.DENY, 直接退出，不执行后续流程
					FilterReply.NEUTRA，继续向下执行
					FilterReply.ACCEPT，不进行步骤二,即类型输出类型检查
				级别过滤器（LevelFilter）:LevelFilter 根据记录级别对记录事件进行过滤。
					<filter class="ch.qos.logback.classic.filter.LevelFilter">
						 <!-- 过滤掉非INFO级别 -->
						 <level>INFO</level>
						 <onMatch>ACCEPT</onMatch>
						 <onMismatch>DENY</onMismatch>
					</filter>
				临界值过滤器（ThresholdFilter）:ThresholdFilter 过滤掉低于指定临界值的事件 . 当记录的级别等于或高于临界值时
				求值过滤器（EvaluatorFilter）:评估 是否符合指定的条件
					<filter class="ch.qos.logback.classic.filter.EvaluatorFilter">  
						  <evaluator>
							<!--过滤掉所有日志中不包含hello字符的日志-->
							 <expression>
								 message.contains("hello")
							 </expression>
							 <onMatch>NEUTRAL</onMatch>
							 <onMismatch>DENY</onMismatch>
						  </evaluator>
					</filter>
				匹配器（Matchers）:
		-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <file>${raw_error_log_path}</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${raw_error_log_path}.%d{yyyy-MM-dd}</fileNamePattern>
            <maxHistory>14</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX} %-5level [%thread] %logger{5} \(%file:%line\) [%X{batch_loop_id}] [%X{batch_request_id}] [%X{batch_job_name}] - ${env} - ${HOSTNAME} - %msg%n</pattern>
        </encoder>
    </appender>

	<!-- 子节点<loger>：用来设置某一个包或具体的某一个类的日志打印级别、以及指定<appender>。 
			name:用来指定受此loger约束的某一个包或者具体的某一个类。
			addtivity:是否向上级loger传递打印信息。默认是true。
			level 大小 ：ERROR > WARN > INFO > DEBUG > TRACE-->
    <logger name="jp.co.happinet"  additivity="false">
        <level value="${log_level}" />
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE_RAW" />
        <appender-ref ref="FILE_RAW_ERROR" />
    </logger>

    <root level="${root_log_level}">
        <appender-ref ref="STDOUT" />
        <appender-ref ref="FILE_RAW" />
        <appender-ref ref="FILE_RAW_ERROR" />
    </root>

    <jmxConfigurator />
</configuration>

<!-- ConsoleAppender 把日志输出到控制台
		target:字符串System.out(默认)或者System.err
		encoder:对日志进行格式化 -->
<!-- FileAppender 把日志添加到文件
		file:被写入的文件名，可以是相对目录，也可以是绝对目录。
		append:如果是true，日志被追加到文件结尾，如果是false，清空现存文件，默认是true。
		rollingPolicy:当发生滚动时，决定RollingFileAppender的行为，涉及文件移动和重命名。属性class定义具体的滚动策略类
		encoder:对日志进行格式化
		prudent:如果是 true，日志会被安全的写入文件，即使其他的FileAppender也在向此文件做写入操作，效率低，默认是 false。-->
<!-- RollingFileAppender 滚动记录文件，先将日志记录到指定文件，当符合某个条件时，将日志记录到其他文件。
		file:被写入的文件名，可以是相对目录，也可以是绝对目录。
		append:如果是true，日志被追加到文件结尾，如果是false，清空现存文件，默认是true。
		rollingPolicy:当发生滚动时，决定RollingFileAppender的行为，涉及文件移动和重命名。属性class定义具体的滚动策略类
			fileNamePattern:必要节点，包含文件名及“%d”转换符，“%d”可以包含一个java.text.SimpleDateFormat指定的时间格式，如：%d{yyyy-MM}。
			maxHistory:控制保留的归档文件的最大数量，超出数量就删除旧文件。假设设置每个月滚动，且<maxHistory>是14，则只保存最近14个月的文件，删除之前的旧文件。 -->
<!-- SizeBasedTriggeringPolicy 查看当前活动文件的大小，如果超过指定大小会告知RollingFileAppender 触发当前活动文件滚动。
		还有SocketAppender、SMTPAppender、DBAppender、SyslogAppender、SiftingAppender m-->

<!--PATTERN:
		输出日志的logger名，可有一个整形参数，功能是缩短logger名，设置为0表示只输入logger最右边点符号之后的字符串。
			c {length } 
			lo {length } 
			logger {length }
			%logger{5}：o.s.j.e.a.AnnotationMBeanExporter
		输出执行记录请求的调用者的全限定名。参数与上面的一样。尽量避免使用，除非执行速度不造成任何问题。
			C {length } 
			class {length }
		输出上下文名称。
			contextName 
			cn
		输出日志的打印日志，模式语法与java.text.SimpleDateFormat 兼容。 Conversion Pattern Result 
			d {pattern } 
			date {pattern }
			%d{yyyy-MM-dd'T'HH:mm:ss.SSSXXX}：2017-09-21T14:10:46.933+08:00
		输出执行记录请求的java源文件名。尽量避免使用，除非执行速度不造成任何问题。
			F / file
			\(%file:%line\)：(MBeanExporter.java:671)
		输出生成日志的调用者的位置信息，整数选项表示输出信息深度。 
			caller{depth} caller{depth, evaluator-1, ... evaluator-n}
		输出执行日志请求的行号。尽量避免使用，除非执行速度不造成任何问题。 	
			L / line 
		输出应用程序提供的信息。
			m / msg / message 
		输出执行日志请求的方法名。尽量避免使用，除非执行速度不造成任何问题。 
			M / method 
		输出平台先关的分行符“\n”或者“\r\n”。 
			n 
		输出日志级别。 
			p / le / level 
			%-5level：INFO 
		输出从程序启动到创建日志记录的时间，单位是毫秒 
			r / relative 
		输出产生日志的线程名。 
			t / thread
			[%thread]：[main]
		p 为日志内容，r 是正则表达式，将p 中符合r 的内容替换为t 。
			replace(p ){r, t}
		输出主机名
			${HOSTNAME}
	-->


```