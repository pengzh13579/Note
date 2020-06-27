-- 范围分区：根据数据库表中某一字段的值的范围来划分分区，支持多列
create table graderecord  
  (  
  sno varchar2(10),  
  sname varchar2(20),  
  dormitory varchar2(3),  
  grade int  
)  
partition by range(grade)  
(  
  partition bujige values less than(60), --不及格  
  partition jige values less than(85), --及格  
  partition youxiu values less than(maxvalue) --优秀  
)  

-- 散列分区：根据字段的hash值进行均匀分布，尽可能的实现各分区所散列的数据相等，支持多列
create table graderecord  
(  
  sno varchar2(10),  
  sname varchar2(20),  
  dormitory varchar2(3),  
  grade int  
)  
partition by hash(sno)  
(  
  partition p1,  
  partition p2,  
  partition p3  
); 

-- 列表分区：明确指定了根据某字段的某个具体值进行分区，而不是像范围分区那样根据字段的值范围来划分的，不支持多列
create table graderecord  
(  
  sno varchar2(10),  
  sname varchar2(20),  
  dormitory varchar2(3),  
  grade int  
)  
partition by list(dormitory)  
(  
  partition d229 values('229'),  
  partition d228 values('228'),  
  partition d240 values('240')  
) 

-- 复合分区：范围-散列分区，范围-列表分区，以grade划分范围，然后以sno和sname划分散列分区，当数据量大的时候散列分区则趋于“平均”
create table graderecord  
(  
  sno varchar2(10),  
  sname varchar2(20),  
  dormitory varchar2(3),  
  grade int  
)  
partition by range(grade)  
subpartition by hash(sno,sname)  
(  
  partition p1 values less than(75)  
            (  
               subpartition sp1,subpartition sp2  
            ),  
  partition p2 values less than(maxvalue)  
            (  
               subpartition sp3,subpartition sp4  
            )  
); 

-- 查询分区数据
select * from graderecord partition(p1);  
select * from graderecord partition(p2);  
select * from graderecord partition(p3); 