[TOC]

## 1 EXP-00003: 未找到段 (0,0) 的存储定义
### 1.1 原因 
11G中有个新特性，当表没有数据的时候，不分配segment，以节省空间。
### 1.2 解决
#### 1.2.1 方案一
setdeferred_segment_creation参数值默认是TRUE，当改为FALSE时，无论是空表还是非空表，都分配segment。该值设置后对以前导入的空表不产生作用，仍不能导出，只能对后面新增的表产生作用。 
```
alter system setdeferred_segment_creation=false scope=both;
```
#### 1.2.2 方案二
首先查找空表，而后查找出来对空表的alert语句，执行拼接出来的sql语句。
```
select 'alter table '||table_name||' allocate extent;' from user_tables where num_rows=0;
```
或
```
Select 'alter table '||table_name||' allocate extent;' from user_tables where segment_created= 'NO' ;  
```
#### 1.2.3 方案三
在pl/sql的命令窗口中执行。
```
set heading off;
set echo off;
set feedback off;
set termout on;
spool C:\alterTableSql.sql;
Select 'alter table '||table_name||' allocate extent;' from user_tables where num_rows=0;
spool off;
```
## 2 导出远程数据库内容
### 2.1 配置Oracle Net Manager
![20180510160641974](/assets/微信图片_20191031111209.png)
配置完成后，打开`F:\app\lenovo\product\11.2.0\dbhome_1\NETWORK\ADMIN\tnsnames.ora`文件查看内容
```
118ORCL =
  (DESCRIPTION =
    (ADDRESS_LIST =
      (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.1.118)(PORT = 1521))
    )
    (CONNECT_DATA =
      (SERVICE_NAME = ORCL)
    )
  )
```
### 2.2 执行导出命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp full=y`
### 2.3 导出部分表命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp statistics=none TABLES=(TABLE_NAME1,TABLE_NAME2)`
### 2.4 导出符合条件数据命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp statistics=none TABLES=(TABLE_NAME1,TABLE_NAME2) QUERY=\"WHERE rownum<11\"`

## 3 数据库表空间创建语句
```
create temporary tablespace XXX_temp tempfile 'F:\app\lenovo\oradata\orcl\XXX_temp.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create tablespace XXX logging  datafile 'F:\app\lenovo\oradata\orcl\XXX.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create user oracle identified by XXX default tablespace oracle  temporary tablespace XXX_temp;  
grant connect,resource,dba to XXX;
alter user XXX identified by orasys2019;
```