[TOC]

## 1 将字符串用逗号分隔后放到in中
### 1.1 使用参数
- REGEXP_SUBSTR
```
function REGEXP_SUBSTR(String, pattern, position, occurrence, modifier)
string:需要进行正则处理的字符串
pattern：进行匹配的正则表达式
position：起始位置，从字符串的第几个字符开始正则表达式匹配（默认为1） 注意：字符串最初的位置是1而不是0
occurrence：获取第几个分割出来的组（分割后最初的字符串会按分割的顺序排列成组）
modifier：模式（‘i’不区分大小写进行检索；‘c’区分大小写进行检索。默认为’c’）针对的是正则表达式里字符大小写的匹配
```
### 1.2 例子
```
SELECT * FROM S_USR 
WHERE USR_NAME IN (
    SELECT REGEXP_SUBSTR('AA,BB,CC','[^,]+', 1, level) FROM DUAL
    CONNECT BY REGEXP_SUBSTR('AA,BB,CC', '[^,]+', 1, level) IS NOT NULL
)
```
## 2 分组并用逗号分隔转行显示
### 2.1 使用参数
- wm_concat
- LISTAGG(USR_NAME,’,’) WITHIN GROUP(ORDER BY USR_BCH)
### 2.2 例子
```
# LISTAGG
SELECT LISTAGG(USR_NAME, ',') WITHIN GROUP(ORDER BY USR_BCH), USR_BCH
FROM S_USR
GROUP BY USR_BCH;

# WM_CONCAT
SELECT WM_CONCAT(USR_NAME), USR_BCH
FROM S_USR
GROUP BY USR_BCH
```
## 3 相同ID取时间最近的一条
```
SELECT * FROM (
  SELECT T.*,               
  ROW_NUMBER() OVER(PARTITION BY T.id ORDER BY T.cu_date DESC) rn
  FROM b_push T
) 
WHERE rn = 1;
```
## 4 NULL和’’
    NULL和’’（空字符串）是一个意思，由于''（空串）默认被转换成了NULL，不能使用=''作为查询条件。也不能用 IS ''。虽然不会有语法错误，但是不会有结果集返回。只能用 IS NULL 。排序时，NULL作为无穷大处理。
