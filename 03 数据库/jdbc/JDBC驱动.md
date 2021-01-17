[TOC]

## 1 MySQL数据库
### 1.1 驱动包
下载路径：https://mvnrepository.com/artifact/mysql/mysql-connector-java
### 1.2 驱动类名
com.mysql.jdbc.Driver
### 1.3 JDBC的URL
jdbc:mysql://IP地址:端口号/数据库名字
端口号缺省为：3306
## 2 SQL server数据库
### 2.1 驱动包
下载路径：https://mvnrepository.com/artifact/com.microsoft.sqlserver/sqljdbc4
### 2.2 驱动类名
com.microsoft.jdbc.sqlserver.SQLServerDriver
### 2.3 JDBC的URL
jdbc:microsoft:sqlserver://IP地址:端口号;DatabaseName=数据库名
端口号缺省为：1433
### 2.4 备注
sqljdbc和sqljdbc4区别：https://blog.csdn.net/cainiao_M/article/details/53404222
## 3 Oracle数据库
### 3.1 驱动包
下载路径：https://mvnrepository.com/artifact/com.oracle/ojdbc6
### 3.2 驱动类名
oracle.jdbc.driver.OracleDriver
### 3.3 JDBC的URL
jdbc:oracle:thin:@IP地址:端口号:数据库名
端口号缺省为：1521
### 3.4 备注
ojdbc6和ojdbc14的区别：ojdbc14.jar（适合java-1.4和1.5），ojdbc6（适合java-1.6）
## 4 DB2数据库
### 4.1 驱动包
下载路径：https://mvnrepository.com/artifact/com.ibm.db2.jcc/db2jcc4
### 4.2 驱动类名
com.ibm.db2.jdbc.app.DB2Driver
### 4.3 JDBC的URL
jdbc:db2://IP地址:端口号/数据库名
端口号缺省为：5000
## 5 PostgreSQL数据库
### 5.1 驱动包
下载路径：https://mvnrepository.com/artifact/org.postgresql/postgresql
### 5.2 驱动类名
org.postgresql.Driver
### 5.3 JDBC的URL
jdbc:postgresql://IP地址:端口号/数据库名
端口号缺省为：5432
