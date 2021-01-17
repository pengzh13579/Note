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
## 2 Linux环境
### 2.1 mysql下载
![20180510160641974](/assets/url.png)
### 2.1 mysql下载
```
yum install libaio
# 解压到/usr/local目录下
tar -zxvf mysql-5.7.22-linux-glibc2.12-x86_64.tar.gz -C /usr/local/
# 进入/usr/local目录
cd /usr/local/
# mysql安装目录创建软链接
ln -s mysql-5.7.22-linux-glibc2.12-x86_64 mysql
# 进入安装mysql软件的目录
cd /usr/local/mysql
# 
useradd mysql
# 
chown -R mysql:mysql /usr/local/mysql
# 安装mysql
./bin/mysqld --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data --initialize
# 开启mysql服务
./support-files/mysql.server start
# 将mysql进程放入系统进程中
cp support-files/mysql.server /etc/init.d/mysqld
# 重新启动mysql服务
service mysqld restart
# 配置mysql环境变量
vi /etc/profile
export PATH=$PATH:/usr/local/mysql/bin
# 编译
source /etc/profile
# 使用随机密码登录mysql数据库
mysql -u root -p
# root用户设置新密码
alter user 'root'@'localhost' identified by 'root';
# 设置允许远程连接数据库
use mysql
update user set user.Host='%' where user.User='root';
select user,host from user;
flush privileges;	
# 如果还是无法远程连接，查看/etc/my.cnf
找到bind-address = 127.0.0.1这一行
改为bind-address = 0.0.0.0即可
```




