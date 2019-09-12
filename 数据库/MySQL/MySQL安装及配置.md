[TOC]

## 1 windows环境
### 1.1 mysql解压版安装
 - 下载并解压mysql
 - 新增系统环境变量
```
键名：MYSQL_HOME
值为：C:\software\mysql-5.7.21-winx64

在Path中添加：%MYSQL_HOME%\bin
```
 - my.ini文件
	以前的版本解压后或许会存在my-default.ini文件，但是5.7.21版本没有，因此要自己手动创建该文件，文件的内容如下：
```
[mysqld]
port = 3306
basedir=C:/software/mysql-5.7.21-winx64
datadir=C:/software/mysql-5.7.21-winx64/data 
max_connections=200
character-set-server=utf8
default-storage-engine=INNODB
sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
[mysql]
default-character-set=utf8
```
并且文件夹之间用“/”而不是“\”，否则在可能会出错。
 - MySQL安装`mysqld -install`
以管理员身份打开cmd命令窗口。
如果出现报错`Install/Remove of the Service Denied`。
解决：打开cmd.exe程序的时候选择“用管理员身份打开”。
 - MySQL初始化`mysqld --initialize-insecure --user=mysql`
 - 启动mysql服务`net start mysql`
 - 重置密码`mysqladmin -u root -p password 新密码`
启动MySQL之后，root用户的密码为空，设置密码，需要输入旧密码时，由于旧密码为空，所以直接回车即可。
