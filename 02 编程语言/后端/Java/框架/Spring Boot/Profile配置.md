[TOC]

## 1 作用及文件形式

作用：Profile是Spring对不同环境提供不同配置功能的支持，可以通过激活、  指定参数等方式快速切换环境。

文件形式：application-{profile}.properties/yml  

![20180705115212724](/assets/20180705115212724.png)

## 2 激活方式

### 2.1 idea激活 

![20180705115643423](/assets/20180705115643423.png)

### 2.2 properties/yml配置 

`spring.profiles.active=prod `

### 2.3 JVM参数
```
# 测试环境：java -jar XXX.jar –spring.profiles.active=test 
# 生产环境：java -jar XXX.jar –spring.profiles.active=prod
```

注：Spring Boot 支持多种外部配置方式 
1. 命令行参数 
2. 来自java:comp/env的JNDI属性 
3. Java系统属性（System.getProperties()） 
4. 操作系统环境变量 
5. RandomValuePropertySource配置的random.*属性值 
6. jar包外部的application-{profile}.properties或application.yml(带spring.profile)配置文件 
7. jar包内部的application-{profile}.properties或application.yml(带spring.profile)配置文件 
8. jar包外部的application.properties或application.yml(不带spring.profile)配置文件 
9. jar包内部的application.properties或application.yml(不带spring.profile)配置文件 
10. @Configuration注解类上的@PropertySource 
11. 通过SpringApplication.setDefaultProperties指定的默认属性