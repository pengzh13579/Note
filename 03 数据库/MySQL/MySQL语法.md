[TOC]

## 1 增				
### 1.1 INSERT INTO
```
INSERT INTO sys_user_back(SEQ_ID, USER_CD, USER_NAME, PASSWORD, SALT) SELECT SEQ_ID, USER_CD, USER_NAME, PASSWORD, SALT FROM sys_user WHERE USER_CD LIKE 'admin%'
```
**错误：MYSQL不支持SELECT INTO**
`SELECT SEQ_ID, USER_CD, USER_NAME INTO sys_user_back FROM sys_user WHERE USER_CD LIKE 'admin%'`
## 2 删			
### 2.1 TRUNCATE
## 3 改			
### 3.1 配置项	
## 4 查			
### 4.1 RLIKE	
```
# 查询USER_NAME以a或u开头的数据
SELECT * FROM SYS_USER WHERE USER_NAME RLIKE '^[au]' ORDER BY USER_NAME
```
**错误：MYSQL不支持LIKE '^[a]'，无返回值**
`SELECT * FROM SYS_USER WHERE USER_NAME LIKE '^[a]' ORDER BY USER_NAME`

### 4.2 LIMIT
```
# 查询SYS_USER表的一条记录
SELECT * FROM SYS_USER ORDER BY USER_NAME LIMIT 1
```
**错误：MYSQL不支持TOP，报错**
`SELECT TOP 1 * FROM sys_user ORDER BY USER_CD`
### 4.3 UNION ALL
UNION 内部的 SELECT 语句必须拥有相同数量的列。如果允许重复的值，请使用 UNION ALL。

## 5 JOIN关联

 - LEFT JOIN: 即使右表中没有匹配，也从左表返回所有的行
 - RIGHT JOIN: 即使左表中没有匹配，也从右表返回所有的行
 - FULL JOIN: 只要其中一个表中存在匹配，就返回行

![20180510160641974](/assets/20180510160641974.png)

**错误：MYSQL不支持FULL JOIN，报错**
`SELECT * FROM sys_user U FULL JOIN sys_user_info I ON U.SEQ_ID = I.SEQ_ID ORDER BY USER_CD`
**正确**
    如果要用full join，可以转变一下思维，通过联合查询，将左外和右外连接的结果集联合在一起，就可以达到full join的效果了。
```
SELECT 
    U.USER_NAME,
    U.DEL_FLAG,
    I.ADDRESS
FROM 
	sys_user U 
LEFT JOIN sys_user_info I 
ON U.SEQ_ID = I.USER_SEQ_ID 
UNION ALL 
SELECT 
    U.USER_NAME, 
    U.DEL_FLAG, 
    I.ADDRESS 
FROM 
	sys_user U 
RIGHT JOIN sys_user_info I 
ON U.SEQ_ID = I.USER_SEQ_ID 
```
## 6 函数
|函数|说明|
|-----|-----|
|AVG 函数|返回数值列的平均值。NULL 值不包括在计算中。|
|COUNT 函数|返回匹配指定条件的行数。|
|MAX 函数|返回一列中的最大值。NULL 值不包括在计算中。|
|MIN 函数|返回一列中的最小值。NULL 值不包括在计算中。|
|SUM 函数|返回数值列的总数（总额）。|
|UCASE、UPPER 函数|把字段的值转换为大写。|
|LCASE 函数|把字段的值转换为小写。|
|MID 函数|用于从文本字段中提取字符。|
|ROUND 函数|用于把数值字段舍入为指定的小数位数。|
|FORMAT 函数|用于对字段的显示进行格式化。|
|IFNULL 函数|如果值是 NULL 则返回不同值。|
|COALESCE 函数|如果值是 NULL 则返回不同值。|
```
SELECT UCASE(USER_CD) AS USER_CD FROM SYS_USER;
SELECT UPPER(USER_CD) AS USER_CD FROM SYS_USER;
SELECT MID(USER_CD, 1, 3) FROM SYS_USER;
SELECT ROUND(USER_CD, 1) FROM SYS_USER;
SELECT IFNULL(UPDATE_USER, 0) FROM SYS_USER;
SELECT COALESCE(UPDATE_USER, 0) FROM SYS_USER;
```
## 7 索引

 - MySQL索引的建立对于MySQL的高效运行是很重要的，索引可以大大提高MySQL的检索速度。
   
 - 索引分单列索引和组合索引。单列索引，即一个索引只包含单个列，一个表可以有多个单列索引，但这不是组合索引。组合索引，即一个索包含多个列。 
 - 创建索引时，你需要确保该索引是应用在 SQL 查询语句的条件(一般作为 WHERE 子句的条件)。
   
 - 实际上，索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录。
   
 - 使用索引有很多好处，但过多的使用索引将会造成滥用。因此索引也会有它的缺点：虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行INSERT、UPDATE和DELETE。因为更新表时，MySQL不仅要保存数据，还要保存一下索引文件。建立索引会占用磁盘空间的索引文件。
---------------------
|索引语法|说明|
|-----|-----|
|CREATE INDEX indexName ON tableName(columnName(length)); |在表上创建一个简单的索引。允许使用重复的值，BLOB和TEXT类型，必须指定 length|
|CREATE UNIQUE INDEX indexName ON tableName(columnName(length)); |在表上创建一个唯一的索引。索引列的值必须唯一，但允许有空值。如果是组合索引，则列值的组合必须唯一|
|ALTER TABLE tableName ADD INDEX indexName(columnName); |修改表结构(添加索引)|
|ALTER TABLE tableName ADD UNIQUE indexName (columnName(length)) |修改表结构(添加索引)|
|DROP INDEX indexName ON tableName;|命令删除表格中的索引|
|SHOW INDEX FROM tableName;|查看表中的索引|
|ALTER TABLE tableName ADD PRIMARY KEY (columnList)|该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL|
|ALTER TABLE tableName ADD UNIQUE indexName (columnList)|这条语句创建索引的值必须是唯一的（除了NULL外，NULL可能会出现多次）|
|ALTER TABLE tableName ADD INDEX indexName (columnList)|添加普通索引，索引值可出现多次|
|ALTER TABLE tableName ADD FULLTEXT indexName (columnList|该语句指定了索引为 FULLTEXT ，用于全文索引|
|ALTER TABLE tableName ADD INDEX indexName (column1, column2, column3)|添加多列索引|
`CREATE INDEX SYS_USER_INDEX ON SYS_USER (USER_CD, PASSWORD)`

使用 ALTER 命令添加和删除主键
```
# 先修改为不为空
ALTER TABLE tableName MODIFY columnName [INT] NOT NULL;
# 然后添加主键
ALTER TABLE tableName ADD PRIMARY KEY (columnName);
# 删除主键
ALTER TABLE tableName DROP PRIMARY KEY;
```
## 8 视图
    视图是基于 SQL 语句的结果集的可视化的表。
    数据库的设计和结构不会受到视图中的函数、where 或 join 语句的影响。
    视图总是显示最近的数据。每当用户查询视图时，数据库引擎通过使用 SQL 语句来重建数据。

### 8.1 创建视图
```
CREATE VIEW USER_ADMIN_CD AS
SELECT *
FROM SYS_USER
WHERE USER_CD='admin'
```
### 8.2 更新视图
```
CREATE OR REPLACE VIEW USER_ADMIN_CD AS
SELECT *
FROM SYS_USER
WHERE USER_CD LIKE 'u%'
```
### 8.3 撤销视图
```
DROP VIEW USER_ADMIN_CD
```
## 9 约束
|约束|说明|
|-----|-----|
|NOT NULL|强制列不接受 NULL 值。Id_P int NOT NULL|
|UNIQUE|唯一标识数据库表中的每条记录。UNIQUE (Id_P)|
|PRIMARY KEY|唯一标识数据库表中的每条记录。PRIMARY KEY (Id_P)|
|FOREIGN KEY|一个表中的 FOREIGN KEY 指向另一个表中的 PRIMARY KEY。FOREIGN KEY (Id_P) |
|REFERENCES| Persons(Id_P)|
|CHECK|用于限制列中的值的范围。CHECK (Id_P>0)|
|DEFAULT|用于向列中插入默认值。City varchar(255) DEFAULT 'Sandnes'|

## 10 事务
一般来说，事务是必须满足4个条件（ACID）：` Atomicity（原子性）`、`Consistency（稳定性）`、`Isolation（隔离性）`、`Durability（可靠性）`
1）事务的原子性：一组事务，要么成功；要么撤回。 
2）稳定性 ： 有非法数据（外键约束之类），事务撤回。 
3）隔离性：事务独立运行。一个事务处理后的结果，影响了其他事务，那么其他事务会撤回。事务的100%隔离，需要牺牲速度。 
4）可靠性：软、硬件崩溃后，InnoDB数据表驱动会利用日志文件重构修改。可靠性和高速度不可兼得， innodb_flush_log_at_trx_commit选项 决定什么时候吧事务保存到日志里。 
    在MySQL中只有使用了Innodb数据库表才支持事务
```
begin； ＃开始一个事务
rollback; #回滚 ， 这样数据是不会写入的
commit; #写代码片
```