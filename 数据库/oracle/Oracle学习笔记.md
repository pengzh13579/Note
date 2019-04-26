[TOC]

## 1 概述
### 1.1 oracle版本号
11.1.0.1.0---主发布版本号.主发布维护号.应用服务器版本号.构建特定版本号.平台特定版本号
11g中的g代表grid---网格
### 1.2 概念
Oracle实例：是一个非固定的、基于内存的基本进程与内存结构，当服务器关闭后，实例也就不存在了。
Oracle数据库：是固定的、基于磁盘的数据文件、控制文件、日志文件、参数文件和归档日志文件等，一般情况下Oracle数据库都是一个数据库包含一个实例。
数据库服务器：是指数据库各软件部件（sqlplus/exp/imp）和实例及数据库3个部分。
表空间：用于存放数据库表、索引、回滚段等对象的磁盘逻辑空间。
数据文件：用于保存用户应用数据和oracle系统内部数据的文件。
### 1.3 SQL语言分类
- 数据查询语言-SELECT
- 数据操纵语言-INSERT/UPDATE/DELETE
- 事务控制语言-COMMIT/ROLLBACK/SAVEPOINT
- 数据定义语言-CREATE TABLE/ALTER TABLE/DROP TABLE
- 数据控制语言-GRANT/REVOKE
## 2 基本语法
### 2.1 关键字
- WHERE
- LIKE
- OR
- AND
- NOT
- BETWEEN
- IN
- ORDER BY
 - ASC
 - DESC
- DISTINCT
- HAVING 
	组函数不能用于WHERE中，因此需要使用HAVING语句
- UNION
- UNION ALL
- INTERSECT
- MINUS
- CASE....WHEN....WHEN....ELSE....END
- DECODE

### 2.2 函数
#### 2.2.1 字符函数
- ASCII
返回字符串的ASCII码。
`SELECT ASCII('A') FROM DUAL`
结果：`65`
- CHR
返回十进制数字表示的字符。
`SELECT CHR(65) FROM DUAL`
结果：`A`
- CONCAT
连接2个字符串，如果其中一个字符串为null，则只返回非空的字符串，如果都为null，则返回null。
`SELECT CONCAT('AAA','BBB') FROM DUAL`
结果：`AAABBB`
- INITCAP
将每一个单词的首字母大写，其余字母小写返回，单词由空格/控制字符/标点限制
`SELECT INITCAP('AAA，BBB oooo') FROM DUAL`
结果：`Aaa，Bbb Oooo`
- INSTR(A,B,i,j)
B在A中第j此出现的位置，搜索从A的第i位开始，没有发现所需要的字符返回0，若i为负数，则从右到左进行查找，但是位置的计算还是从左到右，i,j默认为1。
`SELECT INSTR('helloworld','l',-1,2) FROM DUAL`
结果：`4`
- INSTRB(A,B,i,j)
与INSTR一样，只是此处返回的是字节，对于单字节效果一致。
`SELECT INSTRB('helloworld','l',-1,2) FROM DUAL`
结果：`4`
- LENGTH
返回字符串的长度，如果字符串为努力了，则返回null
`SELECT LENGTH('helloworld') FROM DUAL`
结果：`10`
- LENGTHB
与LENGTH一样，只是此处返回的是字节。
`SELECT LENGTHB('helloworld') FROM DUAL`
结果：`10`
- LOWER
返回小写字符。
`SELECT LOWER('hellOWorld') FROM DUAL`
结果：`helloworld`
- UPPER
返回大写字符。
`SELECT UPPER('hellOWorld') FROM DUAL`
结果：`HELLOWORLD`
- LPAD(A,i,B)
在A的左侧用B补足长度i，可多次重复，如果i小于A的长度，只返回长度为i的A，其他截取，B默认为单空格。
`SELECT LPAD('helloworld',30,'0') FROM DUAL`
结果：`00000000000000000000helloworld`
- RPAD(A,i,B)
在A的右侧用B补足长度i，可多次重复，如果i小于A的长度，只返回长度为i的A，其他截取，B默认为单空格。
`SELECT RPAD('helloworld',30,'01') FROM DUAL`
结果：`helloworld01010101010101010101`
- LTRIM
将字符最左边的字符去掉。
`SELECT LTRIM('helloworld','hel') FROM DUAL`
结果：`oworld`
- RTRIM
将字符最左边的字符去掉。
`SELECT RTRIM('helloworld','ld') FROM DUAL`
结果：`hellowor`
- TRIM
将字符最左边与最右边符合条件的字符去掉。
`SELECT TRIM('d' from 'helloworld') FROM DUAL`
结果：`helloworl`
- REPLACE
替换字符串。
`SELECT REPLACE('helloworld','l','o') FROM DUAL`
结果：`heoooworod`
- SUBSTR
截取字符串从i开始返回长度为j的子字符串。
`SELECT SUBSTR('helloworld',2,5) FROM DUAL`
结果：`ellow`
- SUBSTRB
ij以字节计算。
`SELECT SUBSTRB('helloworld',2,5) FROM DUAL`
结果：`ellow`
- SOUNDEX
返回发音相似的词。
`SELECT SOUNDEX('helloworld') FROM DUAL`
结果：`H464`
- TRANSLATE(A,B,C)
将A中与B相同的字符以C代替。
`SELECT TRANSLATE('helloworld','l','o') FROM DUAL`
结果：`heoooworod`
#### 2.2.2 数字函数
- ABS
返回绝对值。
`SELECT ABS(-1) FROM DUAL`
结果：`1`
- EXP
返回e的n次幂。
`SELECT EXP(1) FROM DUAL`
结果：`2.71828182845904523536028747135266249776`
- FLOOR
返回小于等于n的最大整数。
`SELECT FLOOR(1.1) FROM DUAL`
结果：`1`
- ROUND(A,B)
返回舍入小数点右边B位的A的值，B默认0，返回与小数点最接近的证书，如果B为负数就舍入到小数点左边相应的位。
`SELECT ROUND(25.32,-1) FROM DUAL`
结果：`30`
- TRUNC(A,B)
返回截尾到B位小数的A的值，B默认0，当B为负数就截尾到左边相应的位。
`SELECT TRUNC(1.8) FROM DUAL`
结果：`1`
- LN
返回n的自然对数，n必须大于0。
`SELECT LN(1) FROM DUAL`
结果：`0`
- LOG
返回以2为底的3的对数。
`SELECT LOG(2,3) FROM DUAL`
结果：`1.58496250072115618145373894394781650876`
- MOD
返回2除以3的余数。
`SELECT MOD(2,3) FROM DUAL`
结果：`2`
- POWER
返回2的3次方。
`SELECT POWER(2,3) FROM DUAL`
结果：`8`
- ACOS
反余弦函数。
`SELECT ACOS(1) FROM DUAL`
结果：`0`
- ASIN
反余弦函数。
`SELECT ASIN(1) FROM DUAL`
结果：`1.5707963267948966192313216916397514421`
- ATAN
反正切函数。
`SELECT ATAN(1) FROM DUAL`
结果：`0.7853981633974483096156608458198757210546`
- COS
返回余弦值。
`SELECT COS(30) FROM DUAL`
结果：`0.15425144988758405071866214661421019673`
- COSH
返回双曲余弦值。
`SELECT COSH(30) FROM DUAL`
结果：`5343237290762.2310734952343721588156692`
- SIN
返回正弦值。
`SELECT SIN(30) FROM DUAL`
结果：`0.988031624092861789987748907294458150502`
- SINH
返回双曲正弦值。
`SELECT SINH(30) FROM DUAL`
结果：`5343237290762.231073495234278582585980771`
- TAN
返回正切值。
`SELECT TAN(30) FROM DUAL`
结果：`6.40533119664627578489607550566795862761`
- TANH
返回双曲正切值。
`SELECT TANH(30) FROM DUAL`
结果：`0.9999999999999999999999999824869784746017`
- SIGN
如果为负数返回-1，为正数返回1，为0返回0。
`SELECT SIGN(-100) FROM DUAL`
结果：`-1`
- SQRT
返回平方根
`SELECT SQRT(4) FROM DUAL`
结果：`2`
#### 2.2.3 日期函数
- ADD_MONTHS
添加几个月后的时间。
`SELECT ADD_MONTHS(TO_DATE('2019-04-17', 'yyyy-MM-dd'), 4) FROM DUAL`
结果：`2019-08-17 00:00:00`
- LAST_DAY
返回包含日期中月的最后一天。
`SELECT LAST_DAY(TO_DATE('2019-04-17', 'yyyy-MM-dd')) FROM DUAL`
结果：`2019-04-30 00:00:00`
- MONTHS_BETWEEN
返回2个日期间的日数，若日期不同则会返回分数。
`SELECT MONTHS_BETWEEN(TO_DATE('2019-05-17', 'yyyy-MM-dd'), TO_DATE('2019-04-17', 'yyyy-MM-dd')) FROM DUAL`
结果：`1`
- NEW_TIME
-NEXT_DAY
返回指定日期后的第一个工作日。
`SELECT NEXT_DAY(TO_DATE('2019-04-17', 'yyyy-MM-dd'),1) FROM DUAL`
结果：`2019-04-21 00:00:00`
- SYADATE
返回包含日期中月的最后一天。
`SELECT SYSDATE FROM DUAL`
结果：`2019-04-17 15:11:47`
- TRUNC
返回包含日期中月的最后一天。
`SELECT TRUNC(TO_DATE('2019-04-17', 'yyyy-MM-dd'),'MM') FROM DUAL`
结果：`2019-04-01 00:00:00`
#### 2.2.4 转换函数
- CHARTORWID
- CONVERT
- HEXTORAW
- RAWTOHEX
- ROWIDTOCHAR
- TO_CHAR
- TO_DATE
- TO_MULTI_BYTE
- TO_NUMBER
- TO_SINGLE_BYTE
#### 2.2.5 组函数
- AVG 平均值
- MAX 最大值
- MIN 最小值
- STDDEV 标准差
- SUM 和
- VARIANCE 统计方差
#### 2.2.6 运算函数
- DECODE
比较值。
`SELECT DECODE('A', 'A', 'IS A', 'B', 'IS B', 'C', 'IS C', '0') FROM DUAL`
结果：`IS A`
### 2.3 DDL语句
#### 2.3.1 常用数据类型
![20180510160641974](/assets/20180510160641974.png)
#### 2.3.2 常用语法
- CREATE TABLE
```
CREATE TABLE T_USER(
 ID NUMBER(10) NOT NULL,
 NAME VARCHAR(20),
 PRIMARY KEY(ID)
)
CREATE TABLE T_DEPT(
 ID NUMBER(10) NOT NULL,
 NAME VARCHAR(20),
 USER_ID NUMBER(10),
 CONSTRAINT FK_USER FOREIGH KEY(USER_ID) REFERENCES T_USER(ID) ON DELETE CASCADE
);
```
- ALTER TABLE
```
-- 删除主键
ALTER TABLE T_USER DROP CONSTRAINT PK_NAME;
ALTER TABLE T_USER DROP CONSTRAINT PRIMARY KEY;
-- 没有主键的表添加主键
ALTER TABLE T_USER ADD CONSTRAINT PK_NAME PRIMARY KEY (NAME);
-- 主键失效
ALTER TABLE T_USER DISABLE PRIMARY KEY;
-- 主键有效
ALTER TABLE T_USER ENABLE PRIMARY KEY;
-- 修改主键名
ALTER TABLE T_USER RENAME CONSTRAINT PK_ID TO PK_USER_ID;
-- 如果不知道主键名称，则查询主键名称
SELECT OWNER, CONSTRAINT_NAME,TABLE_NAME,COLUMN_NAME FROM USER_CONS_COLUMNS WHERE TABLE_NAME='T_USER';
-- 如果不知道索引名称，则查询索引名称
SELECT TABLE_NAME, INDEX_NAME FROM USER_INDEXES WHERE TABLE_NAME='T_USER';
```
- 约束
 - CHECK
```
CREATE TABLE T_USER(
 ID NUMBER(10) NOT NULL,
 NAME VARCHAR(20),
 AGE NUMBER(20) CHECK (AGE BETWEEN 18 AND 65),
 PRIMARY KEY(ID)
)
```

 - NOT NULL
 - UNIQUE INDEX
```
CREATE TABLE T_USER(
 ID NUMBER(10) NOT NULL,
 NAME VARCHAR(20),
 AGE NUMBER(20) CHECK (AGE BETWEEN 18 AND 65),
 CONSTRAINT UNIQUE_INFO UNIQUE(ID,NAME)
)
```
- 索引
```
-- 创建唯一索引
CREATE UNIQUE INDEX IDX_ID ON T_USER(ID);
-- 修改索引
-- storage(initial 64k next 128k pctincrease 100):指定参数设置，第一个分区64k，第二个分区128k，区的增长百分率是100%
ALTER INDEX IDX_ID REBUILD STORAGE(INITIAL 1M NEXT 512K);
```
- 视图
```
-- 创建视图
CREATE OR REPLACE VIEW V_USER_SELECT AS SELECT NAME FROM T_USER;
-- 删除视图
DROP VIEW V_USER_SELECT;
```
- 序列
序列可以生产唯一的整数，一般使用序列自动生成主码值
```
-- 创建序列
CREATE SEQUENCE USER_ID_SEQ
 START WITH 1000
 MAXVALUE 99999999999
 MINVALUE 1
 CYCLE 
 CACHE 20
 NOORDER;
-- 修改序列
ALTER SEQUENCE USER_ID_SEQ MAXVALUE 1000000000;
-- 删除序列
DROP SEQUENCE USER_ID_SEQ;
-- 查询序列
SELECT USER_ID_SEQ.nextval FROM DUAL
```
### 2.4 DML语句
- INSERT
- UPDATE
- DELETE
- MARGE
- TRUNCATE
### 2.5 其他
- DESCRIBE
## 3 PL/SQL
PL/SQL是在SQL语言中扩充了面向过程语言中使用的程序结构，如：
- 变量和类型
- 控制语句
- 过程和函数
- 对象类型和方法

## 4 SQLPLUS
```
sqlplus sys/orcl as sysdba
-- 用sqlplus远程连接oracle命令(例：sqlplus risenet/1@//192.168.130.99:1521/risenet)
sqlplus username/password@//host:port/sid

CONNECT 用户名／口令 as sysdba
```
## 5 管理文件
### 5.1 管理控制文件

### 5.2 管理日志文件

### 5.3 管理数据文件
　　
## 6 表空间
### 6.1 默认表空间
![20190418105720](/assets/20190418105720.png)
### 6.2 查询表空间
```
-- 查询SYSTEM表空间内存放的对象及拥有者
SELECT DISTINCT SEGMENT_TYPE, OWNER, TABLESPACE_NAME FROM DBA_SEGMENTS WHERE TABLESPACE_NAME='SYSTEM'
-- 查询各个用户在SYSTEM表空间中存放对象数量
SELECT OWNER, COUNT(*) FROM DBA_SEGMENTS WHERE TABLESPACE_NAME='SYSTEM' GROUP BY OWNER
```
### 6.3 建立表空间
```
CREATE TABLESPACE TBS_1
DATAFILE 'D:\app\lenovo\oradata\orcl\TAIJIOA_DATA.DBF'
SIZE 10G EXTENT MANAGEMENT LOCAL AUTOALLOCATE
SEGMENT SPACE MANAGEMENT AUTO;
```
```
create tablespace mydata
datafile 'D:\app\lenovo\oradata\orcl\mydata.DBF'
size 100m
autoextend on next 32m maxsize 2048m
-- stbss 是表空间名称
-- xxxxx.dbf 是你表空间数据的存放地址和文件名称
-- size 100m 开始是100M的大小
-- autoextend on next 32m 不够的话会自动增长32M
-- maxsize 2048m 最多增加到 2048m
```
```
-- 创建大文件表空间
CREATE BIGFILE TABLESPACE
TBS_2 DATAFILE 'D:\app\lenovo\oradata\orcl\DB1.DBF'
SIZE 25G
-- 修改表空间
ALTER DATABASE DATAFILE 'D:\app\lenovo\oradata\orcl\DB1.DBF'
RESIZE 3G
```


