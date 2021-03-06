[TOC]
### 1 Springboot+Thymeleaf热部署
#### 1.1 概述
spring-boot-devtools是一个为开发者服务的一个模块，其中最重要的功能就是自动应用代码更改到最新的App上面去。

原理是在发现代码有更改之后，重新启动应用，但是速度比手动停止后再启动更快。

其深层原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类(第三方Jar包),另一个ClassLoader加载会更改的类，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。
即devtools会监听classpath下的文件变动，并且会立即重启应用（发生在保存时机）
#### 1.2 IDEA配置
 - Setting --> 查找 build project automatically -->选中
![1539164709290](/assets/微信截图_20191210104305.png)
 - Setting --> 查找Registry --> 找到并勾选compiler.automake.allow.when.app.running
如果IDEA比较新，可能找不到这个，使用下图方式
![1539164709290](/assets/微信截图_20191210104523.png)
 - 关闭设置，按上面设置的快键键， Ctrl + Shift + M
找到并勾选compiler.automake.allow.when.app.running
![1539164709290](/assets/微信截图_20191210104632.png)
#### 1.3 SpringBoot配置
 - 在 pom.xml 里添加spring-boot-devtools 依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```
 - 开启热部署
在 pom.xml 最后一个 </project> 标签之前插入如下代码
```
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
               <configuration>
                   <fork>true</fork>
               </configuration>
        </plugin>
    </plugins>
</build>
```
 - application.properties 里添加
```spring.devtools.restart.enabled=true```
#### 1.4 Chrome配置
Chrome禁用缓存，右键审查元素或者检查或者快捷键 F12 
![1539164709290](/assets/微信截图_20191211170911.png)
#### 1.5 注意
 - 对应的spring-boot版本是否正确。
 - 是否加入plugin了，以及属性`<fork>true</fork>`。
 - Intellij IDEA是否开启了Make Project Automatically。
 - 如果设置SpringApplication.setRegisterShutdownHook(false)，则自动重启将不起作用。

### 2 调试工具
#### 2.1 调试方式	
- step over    F8：一行一行的执行。
- step into    F7：进入方法。
- force step into：强行进入方法,和step into功能类似，只不过step into只会进入自定义方法，而force step into可以进入jdk方法内部。
- step out     Shift + F8：跳出方法，step out和step into/force step into配合使用，后者是进入方法，前者是退出方法。
- drop frame：在方法调用栈中回到上个方法。
- run to cursor    Alt + F9：运行到光标处。
- evaluate expression    Alt + F8：计算表达式。
- trace current stream chain：跟踪stream链，这个功能有点强，专门用来跟踪调试jdk 1.8后的stream功能的。图形化界面非常棒。
#### 2.2 调试运行	
- resume program     F9：恢复程序，直到遇到下个断点停下。
- view breakpoints     Ctrl + Shift + F8：查看所有的断点，可以选择性的选中或者删除。使之有效或者无效。
- mute breakpoints：使所有的断点沉默(无效)。

### 3 插件
#### 3.1 ignore
通过该插件可以生成各种ignore文件，一键创建git ignore文件的模板，解决了手动去配置的麻烦。
#### 3.2 lombok
通过注解的形式去自动生成GET/SET方法，同时还可以通过注解去完成构造函数等。
#### 3.3 p3c
阿里巴巴出品的java代码规范插件，可以扫描整个项目 找到不规范的地方 并且大部分可以自动修复。
https://github.com/alibaba/p3c/tree/master/idea-plugin
#### 3.4 FindBugs-IDEA
检测代码中可能的bug及不规范的位置，检测的模式相比p3c更多，写完代码后检测下，避免低级bug，强烈建议用一下，一不小心就发现很多老代码的bug
#### 3.5 GsonFormat
一键根据json文本生成java类。
#### 3.6 Maven Helper
一键查看maven依赖，查看冲突的依赖，一键进行exclude依赖。
#### 3.7 VisualVM Launcher
运行java程序的时候启动visualvm，方便查看jvm的情况，比如堆内存大小的分配，某个对象占用了多大的内存，jvm调优必备工具。
#### 3.8 GenerateAllSetter
一键调用一个对象的所有set方法并且赋予默认值，在对象字段多的时候非常方便。
#### 3.9 MyBatisCodeHelperPro
mybatis代码自动生成插件，大部分单表操作的代码可自动生成，减少重复劳动，大幅提升效率。
#### 3.10 Translation
最好用的翻译插件，功能很强大，界面很漂亮。
#### 3.11 BashSupport
Bash 插件，可以支持高亮，语法提示等等。
#### 3.12 Free Mybatis plugin
mybatis 扩展工具插件，提供了一系列方便的操作，具体情况自己发掘，是 MyBatisCodeHelperPro 的复制版，最大的好处就是可以免费使用。
#### 3.13 GenerateAllSetter
自动生成类实例的 set 赋值方法，并且提供默认值赋值。当创建了一个类实例，需要挨个赋值的时候，插件可以提供极大的便捷，减少出现个别属性忘记赋值的情况。
#### 3.14 Grep Console
工作台输出扩展，可以给工作台输出上色，根据不同的日志等级设置不同的前景色或者背景色，以及查找等功能。
#### 3.15 JRebel for IntelliJ
热部署神器 jrebel 的插件，这个不多解释。
#### 3.16 CamelCase
快捷转换选定变量的风格。插件介绍：Switch easily between CamelCase, camelCase, snake_case and SNAKE_CASE. See Edit menu or use SHIFT + ALT + U。

### 附录一 常用快捷键

| 快捷键                      | 说明                                                         |
| --------------------------- | ------------------------------------------------------------ |
| Ctrl + Space                | 补全代码，由于经常与操作系统的输入法的切换冲突，所以实际很少用。一般直接在 idea 中开启输入自动补全机制。 |
| Ctrl + Shift + Space        | 在列出的可选项中只显示出你所输入的关键字最相关的信息。（常用） |
| Ctrl + Shift + Enter        | 代码补全后，自动在代码末尾添加分号结束符                     |
| Ctrl + P                    | 在某个方法中，调用该按键后，会展示出这个方法的调用参数列表信息。 |
| Ctrl + Q                    | 展示某个类或者方法的 API 说明文档                            |
| Ctrl + mouse                | 跳进到某个类或者方法源代码中进行查看。（常用）               |
| Alt + Insert                | 自动生成某个类的 Getters, Setters, Constructors, hashCode/equals, toString 等代码。（常用） |
| Ctrl + O                    | 展示该类中所有覆盖或者实现的方法列表，注意这里是字母小写的 O！ |
| Ctrl + Alt + T              | 自动生成具有环绕性质的代码，比如：if..else,try..catch, for, synchronized 等等，使用前要先选择好需要环绕的代码块。（常用） |
| Ctrl + /                    | 对单行代码，添加或删除注释。分为两种情况：如果只是光标停留在某行，那么连续使用该快捷键，会不断注释掉下一行的代码；如果选定了某行代码（选定了某行代码一部分也算这种情况），那么连续使用该快捷键，会在添加或删除该行注释之间来回切换。（常用） |
| Ctrl + Shift + /            | 对代码块，添加或删除注释。它与 Ctrl + / 的区别是，它只会在代码块的开头与结尾添加注释符号！（常用） |
| Ctrl + W                    | 选中当前光标所在的代码块，多次触发，代码块会逐级变大。（常用） |
| Ctrl + Shift + W            | 是 Ctrl + W 的反向操作，多次触发，代码块会逐级变小，最小变为光标。 |
| Alt + Q                     | 展示包含当前光标所在代码的父节点信息，比如在 java 方法中调用，就会展示方法签名信息。 |
| Alt + Enter                 | 展示当前当前光标所在代码，可以变化的扩展操作                 |
| Ctrl + Alt + L              | 格式化代码 （常用）                                          |
| Ctrl + Alt + O              | 去除没有实际用到的包，这在 java 类中特别有用。（常用）       |
| Ctrl + Alt + I              | 按照缩进的设定，自动缩进所选择的代码段。                     |
| Tab / Shift + Tab           | 缩进或者不缩进一次所选择的代码段。（常用）                   |
| Ctrl + X 或 Shift Delete    | 剪切当前代码。 （常用）                                      |
| Ctrl + C 或 Ctrl + Insert   | 拷贝当前代码。 （常用）                                      |
| Ctrl + V 或 Shift + Insert  | 粘贴之前剪切或拷贝的代码。（常用）                           |
| Ctrl + Shift + V            | 从之前的剪切或拷贝的代码历史记录中，选择现在需要粘贴的内容。（常用） |
| Ctrl + D                    | 复制当前选中的代码。（常用）                                 |
| Ctrl + Y                    | 删除当前光标所在的代码行。（常用）                           |
| Ctrl + Shift + J            | 把下一行的代码接续到当前的代码行。                           |
| Ctrl + Enter                | 当前代码行与下一行代码之间插入一个空行，原来所在的光标不变。（常用） |
| Shift + Enter               | 当前代码行与下一行代码之间插入一个空行，原来光标现在处于新加的空行上。（常用） |
| Ctrl + Shift + U            | 所选择的内容进行大小写转换。。（常用）                       |
| Ctrl + Shift + ]/[          | 从当前光标所在位置开始，一直选择到当前光标所在代码段起始或者结束位置。 |
| Ctrl + Delete               | 删除从当前光标所在位置开始，直到这个单词的结尾的内容。       |
| Ctrl + NumPad(+/-)          | 展开或收缩代码段。 （常用）                                  |
| Ctrl + Shift + NumPad(+)    | 展开所有代码段。                                             |
| Ctrl + Shift + NumPad(-)    | 收缩所有代码段。                                             |
| Ctrl + F4                   | 关闭当前标签页。                                             |
| Shift + F6                  | 修改名字。（常用）                                           |
| Ctrl + F                    | 在当前标签页中进行查找，还支持正则表达式哦。（常用）         |
| F3                          | 如果找到了多个查找结果，每调用一次就会跳到下一个结果，很方便哦。 |
| Shift + F3                  | 是 F3 的反向操作，即每调用一次就会跳到上一个结果。           |
| Ctrl + R                    | 在当前标签页中进行替换操作。（常用）                         |
| Ctrl + Shift + F            | 通过路径查找。（常用）                                       |
| Ctrl + Shift + R            | 通过路径替换。（常用）                                       |
| Alt + F7                    | 在当前项目中的使用情况，会打开一个使用情况面板。             |
| Ctrl + F7                   | 在当前文件中的使用情况，找的内容会低亮显示。                 |
| Ctrl + Shift + F7           | 在当前文件中的使用情况，找的内容会高亮显示。                 |
| Ctrl + Alt + F7             | 打开使用情况列表。 （常用）                                  |
| Ctrl + N                    | 打开类查询框。（常用）                                       |
| Ctrl + Shift + N            | 打开文件查询框。（常用）                                     |
| Ctrl + Alt + Shift + N      | 打开文本查询框。                                             |
| Alt + 右箭头/左箭头         | 跳到下一个/上一个编辑器标签。                                |
| F12                         | 如果当前在编辑窗口，触发后，会跳到之前操作过的工具栏上。     |
| ESC                         | 从工具栏上，再跳回原来的编辑窗口，一般与 F12 配合使用。      |
| Shift + ESC                 | 隐藏最后一个处于活跃状态的工具窗口。                         |
| Ctrl + Shift + F4           | 同时关闭处于活动状态的某些工具栏窗口。                       |
| Ctrl + G                    | 跳转至某一行代码。。（常用）                                 |
| Ctrl + E                    | 打开曾经操作过的文件历史列表。                               |
| Ctrl + Alt + 右箭头/左箭头  | 在曾经浏览过的代码行中来回跳                                 |
| Ctrl + Shift + Backspace    | 跳转到最近的编辑位置（如果曾经编辑过代码）。                 |
| Alt + F1                    | 打开一个类型列表，选择后会导航到当前文件或者内容的具体与类型相关的面板中。 |
| Ctrl + B 或 Ctrl + 鼠标左键 | 如果是类，那么会跳转到当前光标所在的类定义或者接口；如果是变量，会打开一个变量被引用的列表。（常用） |
| Ctrl + Alt + B              | 跳转到实现类，而不是接口。（常用）                           |
| Ctrl + Shift + I            | 打开一个面板，里面包含类代码。                               |
| Ctrl + Shift + B            | 打开变量的类型所对应的类代码，只对变量有用。                 |
| Ctrl + U                    | 打开方法的超类方法或者类的超类，只对有超类的方法或者类有效。 |
| Alt + 上/下箭头             | 在某个类中，跳到上一个/下一个方法的签名上。                  |
| Ctrl + ]/[                  | 移动光标到类定义的终止右大括号或者起始左大括号。             |
| Ctrl + F12                  | 打开类的结构列表。（常用）                                   |
| Ctrl + H                    | 打开类的继承关系列表。（常用）                               |
| Ctrl + Shift + H            | 打开某个类方法的继承关系列表。                               |
| Ctrl + Alt + H              | 打开所有类的方法列表，这些方法都调用了当前光标所处的某个类方法。（常用） |
| F2/Shift + F2               | 在编译错误的代码行中来回跳。                                 |
| F4                          | 打开当前光标所在处的方法或类源码。                           |
| Alt + Home                  | 激活包路径的导航栏。                                         |
| F11                         | 把光标所处的代码行添加为书签或者从书签中删除。（常用）       |
| Ctrl + F11                  | 把光标所处的代码行添加为带快捷键的书签或者从快捷键书签中删除。 |
| Ctrl + [0-9]                | 跳转到之前定义的快捷键书签。                                 |
| Shift + F11                 | 打开书签列表。（常用）                                       |

### 