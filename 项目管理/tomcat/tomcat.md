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