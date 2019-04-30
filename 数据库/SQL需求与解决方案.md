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
