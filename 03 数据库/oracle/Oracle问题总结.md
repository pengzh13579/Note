[TOC]

## 1 问题与解决方案
### 1.1 EXP-00003: 未找到段 (0,0) 的存储定义 
11G中有个新特性，当表没有数据的时候，不分配segment，以节省空间。
#### 1.1.1 方案一
setdeferred_segment_creation参数值默认是TRUE，当改为FALSE时，无论是空表还是非空表，都分配segment。该值设置后对以前导入的空表不产生作用，仍不能导出，只能对后面新增的表产生作用。 
```
alter system setdeferred_segment_creation=false scope=both;
```
#### 1.1.2 方案二
首先查找空表，而后查找出来对空表的alert语句，执行拼接出来的sql语句。
```
select 'alter table '||table_name||' allocate extent;' from user_tables where num_rows=0;
```
或
```
Select 'alter table '||table_name||' allocate extent;' from user_tables where segment_created= 'NO' ;  
```
#### 1.1.3 方案三
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
## 1.2 ORA-28001: the password has expired密码到期 
由于Oracle11G的新特性所致，Oracle11G创建用户时缺省密码过期限制是180天（即6个月），如果超过180天用户密码未做修改则该用户无法登录。
- 查看密码有效时间
```
SELECT * FROM dba_profiles WHERE profile='DEFAULT' AND resource_name='PASSWORD_LIFE_TIME';
```
#### 1.2.1 方案一：修改密码
```
# 修改用户密码sql
ALTER USER 用户名 IDENTIFIED BY 密码; 
# 修改密码后，会发现该账户会被锁定，这时需要通过如下SQL语句进行解锁：
ALTER USER 用户名 ACCOUNT UNLOCK;
```
#### 1.2.2 方案二：去除180天的密码生存周期的限制
```
ALTER PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
```
## 2 导出数据库内容

### 2.1 exp/imp导出远程数据库内容

#### 2.1.1 配置Oracle Net Manager

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
#### 2.1.2 导出数据库
- 导出数据库命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp full=y`
- 导出部分表命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp statistics=none TABLES=(TABLE_NAME1,TABLE_NAME2)`
- 导出符合条件数据命令
`exp NAME/PASSWORD@192.168.1.118:1521/orcl file=E:\test.dmp statistics=none TABLES=(TABLE_NAME1,TABLE_NAME2) QUERY=\"WHERE rownum<11\"`
#### 2.1.3 导入数据库
`imp 用户名/密码@实例名 file=导入的dmp文件路径 full=y`

### 2.2 expdp/impdp数据泵的使用

#### 2.2.1 创建逻辑目录
```
create or replace directory data as 'D:\app\shuhao\oradata\orcl';
# 查询逻辑目录是否创建成功
select * from dba_directories
```
#### 2.2.2 导出
```
expdp 用户名/密码@orcl directory=data(逻辑目录) dumpfile=1.DMP logfile=jeecg.log schemas=test(用户名)
```
#### 2.2.3 导入
```
impdp 用户名/密码@orcl dumpfile=1.dmp directory=data full=y
# 把用户jcpt中所有的表导入到lyxt用户下
impdp lyxt/lyxt123@127.0.0.1/orcl directory=data dumpfile=LY.DMP remap_schema=jcpt:lyxt logfile=ims20171122.log table_exists_action=replace
```
### 2.2 PLSQL导入和导出

## 4 数据库表空间创建语句
```
create temporary tablespace XXX_temp tempfile 'F:\app\lenovo\oradata\orcl\XXX_temp.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create tablespace XXX logging  datafile 'F:\app\lenovo\oradata\orcl\XXX.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create user 用户名 identified by 密码 default tablespace 表空间  temporary tablespace 临时表空间;  
grant connect,resource,dba to XXX;
alter user 用户名 identified by 密码;
```
## 5 Oracle查询语句
### 5.1 查询Oracle数据库实例
`select instance_name from v$instance;`

### 5.2 查询Oracle数据库版本
`select * from product_component_version;`

### 5.3 查询并修改最大连接数
查询数据库当前进程的连接数
`SELECT count(*) FROM "V$PROCESS"`
查询数据库允许的最大连接数
`SELECT value FROM "V$PARAMETER" WHERE name = 'processes'`
修改数据库允许的最大连接数，需要重启数据库才能实现连接数的修改
`alter system set processes = 300 scope = spfile;`
### 5.4 查询被锁的表并释放session
```
SELECT A.OWNER,A.OBJECT_NAME,B.XIDUSN,B.XIDSLOT,B.XIDSQN,B.SESSION_ID,B.ORACLE_USERNAME, B.OS_USER_NAME,B.PROCESS, B.LOCKED_MODE, C.MACHINE,C.STATUS,C.SERVER,C.SID,C.SERIAL#,C.PROGRAM FROM ALL_OBJECTS A,V$LOCKED_OBJECT B,SYS.GV_$SESSION C WHERE ( A.OBJECT_ID = B.OBJECT_ID ) AND (B.PROCESS = C.PROCESS ) ORDER BY 1,2
```
释放session Sql
```
alter system kill session 'sid, serial#'
alter system kill session '379, 21132'
alter system kill session '374, 6938'
```
### 5.5 查看耗cpu较多的session
```
select a.sid,spid,status,substr(a.program,1,40) prog,a.terminal,osuser,value/60/100 value from v$session a,v$process b,v$sesstat c where c.statistic#=12 and c.sid=a.sid and a.paddr=b.addr order by value desc
```
### 5.6 查看占用系统io较大的session
```
SELECT se.sid,se.serial#,pr.SPID,se.username,se.status,se.terminal,se.program,se.MODULE,se.sql_address,st.event,st.p1text,si.physical_reads,si.block_changes FROM v$session se,　v$session_wait st,v$sess_io si,v$process pr WHERE st.sid=se.sid　AND st.sid=si.sid AND se.PADDR=pr.ADDR AND se.sid>6　AND st.wait_time=0 AND st.event NOT LIKE '%SQL%' ORDER BY physical_reads DESC
```
### 5.7 查询终端用户使用数据库的连接情况
```
select osuser,schemaname,count(*) from v$session group by schemaname,osuser;
```
### 5.8 数据库表操作
#### 5.8.1 查询数据库所有的表
`select t.table_name,t.num_rows from all_tables t;`
#### 5.8.2 查询所有表名
`select t.table_name from user_tables t;`
#### 5.8.3 查询所有字段名：
`select t.column_name from user_col_comments t;`
#### 5.8.4 查询指定表的所有字段名：
`select t.column_name from user_col_comments t where t.table_name = 'BIZ_DICT_XB';`
#### 5.8.5 查询指定表的所有字段名和字段说明：
`select t.column_name, t.column_name from user_col_comments t where t.table_name = 'BIZ_DICT_XB';`
#### 5.8.6 查询所有表的表名和表说明：
`select t.table_name,f.comments from user_tables t inner join user_tab_comments f on t.table_name = f.table_name;`
#### 5.8.7 查询模糊表名的表名和表说明：
`select t.table_name from user_tables t where t.table_name like 'BIZ_DICT%';`
```
select t.table_name,f.comments from user_tables t inner join user_tab_comments f 
on t.table_name = f.table_name where t.table_name like 'BIZ_DICT%';
```
#### 5.8.8 查询表的数据条数、表名、中文表名
`select a.num_rows, a.TABLE_NAME, b.COMMENTS from user_tables a,user_tab_comments b WHERE a.TABLE_NAME = b.TABLE_NAME  order by TABLE_NAME;`
## 6 ServiceName与SID的区别
ServiceName是由oracle8i引进的。在Oracle 8i以前，使用SID来表示标识数据库的一个实例，但是在Oracle的并行环境中，一个数据库对应多个实例，这样就需要多个网络服务名，设置繁琐。为了方便并行环境中的设置，引进了Service_name参数，该参数对应一个数据库，而不是一个实例，而且该参数有许多其它的好处。该参数的缺省值为Db_name.Db_domain，即等于Global_name。一个数据库可以对应多个Service_name，以便实现更灵活的配置。该参数与SID没有直接关系，即不必Service name 必须与SID一样。
SID是数据库实例的名字，每个实例各不相同。
## 7 Oracle JDBC连接方式
### 7.1 ServiceName方式
`jdbc:oracle:thin:@//<host>:<port>/<service_name>`
例：`jdbc:oracle:thin:@//192.168.2.1:1521/XE`
注意这里的格式，@后面有//, 这是与使用SID的主要区别。 
这种格式是Oracle 推荐的格式，因为对于集群来说，每个节点的SID 是不一样的，但是SERVICE_NAME 确可以包含所有节点。 
### 7.2 SID方式
`jdbc:oracle:thin:@<host>:<port>:<SID>`
例：`jdbc:oracle:thin:@192.168.2.1:1521:X01A`
### 7.3 TNSName方式
`jdbc:oracle:thin:@<TNSName>`
例：` jdbc:oracle:thin:@GL`
### 7.4 TNS方式
`jdbc:oracle:thin:@(description=(address=(protocol=tcp)(port=<port>)(host=<host>))(connect_data=(service_name=<service_name>)))`
## 9 常见问题
### 9.1 Oracle数据库根据表名查询表的行数
`select * from user_tables t where t.NUM_ROWS>0`
NUM_ROWS这个字段有不少问题，除了不太准确外，还有个问题就是它不是即时查询的，我刚从另外一个数据库导来的数据，自然用这个字段就查不到了。需要使用下面语句进行处理：
`analyze table table_name compute statistics;`
由于需要统计的表众多，所以写了个存储过程来解决问题。
### 9.2 Oracle中 以“BIN$”开头的表
从oracle10g开始删除数据库表的时候并不是真正删除，而是放到了recyclebin中，这个过程类似 windows里面删除的文件会被临时放到回收站中。删除的表系统会自动给他重命名为【BIN$】开头的名字。
```
# 查看被删掉的表的详细信息
show recyclebin;
select * from recyclebin;
# 收回表
flashback table 原表名 to before drop;
# 清空回收站
purge recyclebin;
# 删除表不经过回收站
drop table 表名 purge;
# 停用回收站功能
# 10.1版本中，修改隐藏参数 _recyclebin
alter system set "_recyclebin" = false;
# 10.2版本中
alter system set recyclebin = off;
```


