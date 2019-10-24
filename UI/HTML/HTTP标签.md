[TOC]

## 1 META标签
### 1.1 http-equiv属性
相当于http文件头作用，它可以向浏览器传回一些有用的信息，以帮助正确和精确地显示网页内容。
1.1.1、Content-Type/Content-Language：用以说明主页制作所使用的文字以及语言；又如英文是ISO-8859-1字符集，还有BIG5、utf-8、shift-Jis、Euc、Koi8-2等字符集。
```
<meta http-equiv="content-Type" content="text/html;charset=gb2312">
<meta http-equiv="Content-Language" content="zh-CN">
```
1.1.2、Refresh：定时让网页在指定的时间n内，跳转到你的页面。
```<meta http-equiv="Refresh" content="n;url=http://yourlink">```
1.1.3、Expires：可以用于设定网页的到期时间，一旦过期则必须到服务器上重新调用。需要注意的是必须使用GMT时间格式.
```<meta http-equiv="Expires" content="Mon,12 May 2001 00:20:00GMT">```
1.1.4、Pragma：是用于设定禁止浏览器从本地机的缓存中调阅页面内容，设定后一旦离开网页就无法从Cache中再调出.
```<meta http-equiv="Pragma"content="no-cache">```
1.1.5、set-cookie：cookie设定，如果网页过期，存盘的cookie将被删除。需要注意的也是必须使用GMT时间格式.
```<meta http-equiv="set-cookie" content="Mon,12 May 2001 00:20:00GMT">```
1.1.6、Pics-label：网页等级评定，在IE的internet选项中有一项内容设置，可以防止浏览一些受限制的网站，而网站的限制级别就是通过meta属性来设置的.
```<meta http-equiv="Pics-label" content="">```
1.1.7、windows-Target：强制页面在当前窗口中以独立页面显示，可以防止自己的网页被别人当作一个frame页调用.
```<meta http-equiv="windows-Target" content="_top">```
1.1.8、Page-Enter：设定进入和离开页面时的特殊效果，这个功能即FrontPage中的"格式/网页过渡"，不过所加的页面不能够是一个frame页面。
```
<meta http-equiv="Page-Enter" content="revealTrans（duration=10,transition=50）">
<meta http-equiv="Page-Exit" content="revealTrans（duration=20，transition=6）">
```
1.1.9、Cache-Control：指定请求和响应遵循的缓存机制。
Cache-Control指定请求和响应遵循的缓存机制。在请求消息或响应消息中设置Cache-Control并不会修改另一个消息处理过程中的缓存处理过程。请求时的缓存指令包括no-cache、no-store、max-age、max-stale、min-fresh、only-if-cached，响应消息中的指令包括public、private、no-cache、no-store、no-transform、must-revalidate、proxy-revalidate、max-age。
 - Public指示响应可被任何缓存区缓存
 - Private指示对于单个用户的整个或部分响应消息，不能被共享缓存处理。这允许服务器仅仅描述当用户的部分响应消息，此响应消息对于其他用户的请求无效
 - no-cache指示请求或响应消息不能缓存
 - no-store用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用缓存。
 - max-age指示客户机可以接收生存期不大于指定时间（以秒为单位）的响应
 - min-fresh指示客户机可以接收响应时间小于当前时间加上指定时间的响应
 - max-stale指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可以接收超出超时期指定值之内的响应消息。
```<meta http-equiv="Cache-Control"content="参数变量值"/>```
### 1.2 charset属性
定义当前文档的编码方式，告诉浏览器如何解析当前页面。
```<meta charset=" utf-8">```
### 1.3 name属性
此属性主要用于描述网页，与之对应的属性为content。content中的内容主要是便于搜索引擎机器人查找信息和分类信息用的。
1.3.1、keywords(关键字)：用来告诉搜索引擎你网页的关键字是什么。
```<meta name="keywords"content="html教程,html代码实例,兴趣部落">```
1.3.2、description(网站内容描述)：description用来告诉搜索引擎你的页面主要内容。
```<meta name="兴趣部落是一个面向前端开发基础知识分享平台">```
1.3.3、robots(机器人向导)：robots用来告诉搜索机器人哪些页面需要索引，哪些页面不需要索引。content的参数有all,none,index,noindex,follow,nofollow。默认是all。
 - all：文件将被检索，且页面上的链接可以被查询；
 - none：文件将不被检索，且页面上的链接不可以被查询；
 - index：文件将被检索；
 - follow：页面上的链接可以被查询；
 - noindex：文件将不被检索，但页面上的链接可以被查询；
 - nofollow：文件将被检索，但页面上的链接不可以被查询；
```<meta name="robots"content="none">```
1.3.4、author(作者)：标注网页的作者
```<meta name="author"content="蚂蚁部落">```
1.3.5、generator：说明网站的采用的什么软件制作
```<meta name="generator"content="信息参数"/>```
1.3.6、COPYRIGHT：说明网站版权信息。
```<META NAME="COPYRIGHT"CONTENT="信息参数">```