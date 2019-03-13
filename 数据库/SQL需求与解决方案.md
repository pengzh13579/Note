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