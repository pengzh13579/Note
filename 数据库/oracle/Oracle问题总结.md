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