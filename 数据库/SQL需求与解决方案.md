[TOC]

## 1 去重并取最大值
    场景：存在一个学生表（student），具有字段：id、等级（lel）、成绩（score），一个等级下会存在多个成绩，求得该某等级下的最大成绩和id
```
CREATE TABLE IF NOT EXISTS `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `lel` int(11) DEFAULT NULL COMMENT '等级',
  `score` int(11) DEFAULT NULL COMMENT '成绩',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='学生表';

DELETE FROM `student`;
INSERT INTO `student` (`id`, `lel`, `score`) VALUES
	(1, 1, 15),
	(2, 1, 16),
	(3, 2, 60),
	(4, 1, 78),
	(5, 3, 88);
```
解决方案：
`select min(id) as id,lel,max(score) from student group by lel`

## 2 id在A表中存在，在B表中不存在，插入到B表中
    场景：同步表A/B中的ID，将A表中存在的ID，但是在B表中不存在的ID去重插入到B表之中。
解决方案：
```
insert into B(
select DISTINCT '11111111',A.ID,A.NAME from A A where A.ID not in (select b.ID from B B));
```
## 3 获取小数点位数大于2位的数据
```
select * from TB where TO_NUMBER(amt) * 100 - floor(TO_NUMBER(amt) * 100) > 0
```
## 4 替换like的解决方案
数据库中存储了海量的数据，当查询时使用like，速度明显变慢。
### 4.1 MYSQL中替换like
 - LOCATE语句
```
SELECT column from table where locat('keyword', condition)>0 
```
 - locate 的別名 position
```
SELECT column from table where position('keyword' IN condition) 
```
 - INSTR语句
```
SELECT column from table where instr(condition, 'keyword')>0 
locate、position 和 instr 的差別只是参数的位置不同，同时locate 多一个起始位置的参数外，两者是一样的。
mysql> SELECT LOCATE('bar', 'foobarbar',5); 
 - 以字段开头模糊查询的left(字段,长度)的方法查询
select * from t  where left(t.user_code, 1)='A';
```
 - findinset
```
select * from t  where FINDINSET('1',t.name);//t.name所查询的字段,所有包含1的数据
```
### 4.2 Oracle中替换like
 - %a%方式：
```
select * from pub_yh_bm t where instr(t.chr_bmdm,'2')>0 
↓
select * from pub_yh_bm t where t.chr_bmdm like '%2%'
```
 - %a方式：
```
select * from pub_yh_bm t where instr(t.chr_bmdm,'110101')=length(t.chr_bmdm)-length('110101')+1
↓
select * from pub_yh_bm t where t.chr_bmdm like '%110101'
```
 - a%方式
```
select * from pub_yh_bm t where instr(t.chr_bmdm,'11010101')=1
↓
select * from pub_yh_bm t where t.chr_bmdm like '11010101%'
```
- not like 方式
```
select * from pub_yh_bm t where instr(t.chr_bmdm,'2')=0 
↓
select * from pub_yh_bm t where t.chr_bmdm not like '%2%'
```

