[TOC]

## 1 控制台乱码问题
### 1.1 问题描述
![20180705115212724](/assets/20190827162122.png)
### 1.2 原因
因为windows默认编码集为GBK，由于使用startup.bat启动tomcat时，它会读取catalina.bat的代码并打开一个新窗口运行。打开的cmd默认编码可能不是utf-8，与系统编码不一致，所以导致乱码
### 1.3 解决方案
1、找到/conf/logging.properties
2、语句：java.util.logging.ConsoleHandler.encoding = UTF-8
   改为：java.util.logging.ConsoleHandler.encoding = GBK
3、重启tomcat！

## 2 根目录访问项目
### 2.1 解决方案

在<Host>  ......</Host>中间添加一行，docBase即为默认访问的文件夹名称。

```
<Host name="localhost"  appBase="webapps"
            unpackWARs="true" autoDeploy="true">

        <!-- SingleSignOn valve, share authentication between web applications
             Documentation at: /docs/config/valve.html -->
        <!--
        <Valve className="org.apache.catalina.authenticator.SingleSignOn" />
        -->

        <!-- Access log processes all example.
             Documentation at: /docs/config/valve.html
             Note: The pattern used is equivalent to using pattern="common" -->
        <Valve className="org.apache.catalina.valves.AccessLogValve" directory="logs"
               prefix="localhost_access_log" suffix=".txt"
               pattern="%h %l %u %t &quot;%r&quot; %s %b" />
			   
		<Context docBase="E:/apache-tomcat-8.5.43/webapps/AAA" path="" debug="0"  reloadable="true"/>

      </Host>
```
## 3 请求获取乱码
### 3.1 问题描述
前台GET请求到服务器，请求的参数中包含了中文字符。前台参数为UTF-8格式，且经过了UTF-8 URL编码再发送。服务器端后台程序中取到的参数的中文是乱码。
如`%E7%B2%BE%E7%AE%97%E9%AA%8C%E8%AF%81%E4%BB%A3%E7%A0%81%E6%96%B9%E4%BE%BF%E5%AD%A6%E4%B9%A0.doc`
### 3.2 原因
经过分析，应该是Tomcat在解析参数的时候没有使用正确的编码格式（UTF-8）去解码。
查看$TOMCAT_HOME/webapps/tomcat-docs/config/http.html这个说明文档，有如下说明： 
URIEncoding：This specifies the character encoding used to decode the URI bytes, after %xx decoding the URL. If not specified, ISO-8859-1 will be used.
也就是说，如果没有设置URIEncoding， Tomcat默认是按ISO-8859-1进行URL解码，ISO-8859-1并未包括中文字符，这样的话中文字符肯定就不能被正确解析了。
### 3.3 解决方案
修改Tomcat的Server.xml，在Connector标签中加上URLEncoding参数：
```
<Connector port="8080" maxThreads="150" minSpareThreads="25" 
maxSpareThreads="75" enableLookups="false" redirectPort="8443" 
acceptCount="100" debug="99" connectionTimeout="20000" 
disableUploadTimeout="true" URIEncoding="UTF-8"/>
```