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
## 3 导入数据库
`imp 用户名/密码@实例名 file=导入的dmp文件路径 full=y`
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
## 8 数据泵的使用
### 8.1 创建逻辑目录
```
create or replace directory data as 'D:\app\shuhao\oradata\orcl';
```
### 8.2 导出
```expdp 用户名/密码@orcl directory=data(逻辑目录) dumpfile=1.DMP logfile=jeecg.log schemas=test(用户名)```
### 8.2 导入
```impdp 用户名/密码@orcl dumpfile=1.dmp directory=data full=y```