--查询表空间使用情况（BYYTES）
SELECT Upper(F.TABLESPACE_NAME)         "表空间名",
       D.TOT_GROOTTE_MB                 "表空间大小(M)",
       D.TOT_GROOTTE_MB - F.TOTAL_BYTES "已使用空间(M)",
       To_char(Round(( D.TOT_GROOTTE_MB - F.TOTAL_BYTES ) / D.TOT_GROOTTE_MB * 100, 2), '990.99')
       || '%'                           "使用比",
       F.TOTAL_BYTES                    "空闲空间(M)",
       F.MAX_BYTES                      "最大块(M)"
FROM   (SELECT TABLESPACE_NAME,
               Round(Sum(BYTES) / ( 1024 * 1024 ), 2) TOTAL_BYTES,
               Round(Max(BYTES) / ( 1024 * 1024 ), 2) MAX_BYTES
        FROM   SYS.DBA_FREE_SPACE
        GROUP  BY TABLESPACE_NAME) F,
       (SELECT DD.TABLESPACE_NAME,
               Round(Sum(DD.BYTES) / ( 1024 * 1024 ), 2) TOT_GROOTTE_MB
        FROM   SYS.DBA_DATA_FILES DD
        GROUP  BY DD.TABLESPACE_NAME) D
WHERE  D.TABLESPACE_NAME = F.TABLESPACE_NAME
ORDER  BY 1;

--查询表空间的free space
select tablespace_name, count(*) AS extends,round(sum(bytes) / 1024 / 1024, 2) AS MB,sum(blocks) AS blocks from dba_free_space group BY tablespace_name;

--查询表空间的总容量
select tablespace_name, sum(bytes) / 1024 / 1024 as MB from dba_data_files group by tablespace_name;

--查询表空间使用率（MB）
SELECT total.tablespace_name,
       Round(total.MB, 2)           AS Total_MB,
       Round(total.MB - free.MB, 2) AS Used_MB,
       Round(( 1 - free.MB / total.MB ) * 100, 2)
       || '%'                       AS Used_Pct
FROM   (SELECT tablespace_name,
               Sum(bytes) / 1024 / 1024 AS MB
        FROM   dba_free_space
        GROUP  BY tablespace_name) free,
       (SELECT tablespace_name,
               Sum(bytes) / 1024 / 1024 AS MB
        FROM   dba_data_files
        GROUP  BY tablespace_name) total
WHERE  free.tablespace_name = total.tablespace_name;

-- 查看表所属的表空间
SELECT TABLE_NAME,TABLESPACE_NAME FROM DBA_TABLES where table_name like 'BO%';

-- 查看表所属的表空间
select * from user_tablespaces where table_name like 'BO%';

-- 查看表所属的用户
select  table_name,Owner  from all_tab_columns where table_name like 'BO%';

-- 创建表空间及用户-start
create temporary tablespace oracle_temp tempfile 'F:\app\lenovo\oradata\orcl\oracle_temp.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create tablespace oracle logging  datafile 'F:\app\lenovo\oradata\orcl\oracle.dbf' size 50m  autoextend on  next 50m maxsize 20480m  extent management local;  
create user oracle identified by oracle default tablespace oracle  temporary tablespace oracle_temp;  
grant connect,resource,dba to oracle;
alter user oracle identified by orasys2019;
-- 创建表空间及用户-end


