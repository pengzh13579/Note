[TOC]

## 1 问题一：密码有效期
your password has expired.To log in you must change itusing a client that supports expired passwords
```
mysql -uroot -p
输入MySQL密码
set password = password('123456');
alter user 'root'@'localhost'password expire never;
flush privileges;
```
