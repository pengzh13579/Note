[TOC]

## 1 Can’t create driver instance
![20180510160641974](/assets/20190523165005306.png)
配置连接参数时，这一部分没显示出来，一直找不到在哪里配置jar或者jar路径失效了。
![20180510160641974](/assets/20190523165157836.png)
## 2 DBeaver指定JDK
修改dbeaver.ini文件
```
-vm 
jdk路径
```
注意：-vm  和 参数之间 一定要换行，如：
```
-vm 
C:/Program Files/Java/jdk1.8.0_72/bin
```