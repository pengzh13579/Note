[TOC]

### 1 tomcat配置SSL
#### 1.1 上传pfx到服务器
在tomcat的bin同级目录下，新建cert文件夹，将.pfx文件上传到cert中。
#### 1.2 配置server.xml文件
```
<Connector port="443" protocol="org.apache.coyote.http11.Http11Protocol"
		maxThreads="150" SSLEnabled="true" scheme="https" secure="true"
		clientAuth="false" sslProtocol="TLS" keystoreFile="cert/123456.pfx"
		keystoreType="PKCS12" keystorePass="123456" useBodyEncodingForURI="true"/>
```
|属性|意义|
|---|---|
|port|代表Tomcat端口号，默认8080。|
|protocol|协议类型，可选类型有4种，BIO（阻塞型IO），NIO，NIO2和APR。见附录一|
|maxThreads|连接器创建处理请求线程的最大数目，处理同事请求的最大数目，默认值为200。|
|SSLEnabled|启动SSL配置|
|scheme|调用request.getScheme()时返回的协议名称。比如，在SSL Connector上可能将这个属性设为“https”。缺省值为“http”|
|secure|如果希望在该Connector接收到的请求上调用request.isSecure()返回true，设置该属性为true。缺省值为false。|
|clientAuth|如果在接受某个连接之前，需要客户端发送有效证书链，将该值设为true。如果为false（缺省值），不需要使用证书链。除非客户端请求被CLIENT-CERT认证保护的资源。|
|sslProtocol|SSL协议的版本号，缺省值是TLS|
|keystoreFile|pfx证书存放位置|
|keystoreType|用于存储服务器证书的keystore文件的类型。缺省值为"JKS"|
|keystorePass|证书密码|
|useBodyEncodingForURI|使用http header中指定charset进行decode(例如：Content-Type: charset=UTF-8)，若未指定，则使用默认值ISO-8859-1|
主要配置点：
keystoreFile/certificateKeystoreFile：证书地址，可使用绝对路径，也可以配置相对路径
keystorePass/certificateKeystorePassword：生成证书时输入的密钥
### 2 SpringBoot配置SSL
#### 2.1 放置pfx证书到springboot工程
将pfx文件放置于resource资源文件夹下。
#### 2.2 配置application.yml
```
server:
  port: 443
  ssl:
    key-store: classpath:123456.pfx
    key-store-password: 123456
    keyStoreType: PKCS12
```
### 附录
#### 附录一
```
# BIO
BIO(Blocking I/O) 阻塞式I/O操作，传统的Java I/O操作(即java.io包及其子包)。Tomcat在默认情况下，是以bio模式运行的，bio模式是三种运行模式中性能最低的一种。BIO配置采用默认即可。
BIO更适合处理简单流程，如程序处理较快可以立即返回结果。简单项目及应用可以采用BIO。

# NIO
NIO(New I/O)是Java SE 1.4及后续版本提供的一种新的I/O操作方式(即java.nio包及其子包)。Java nio是一个基于缓冲区、非阻塞I/O操作的Java API它拥有比传统I/O操作(bio)更好的并发运行性能。
NIO更适合后台需要耗时完成请求的操作，如程序接到了请求后需要比较耗时的处理这已请求，所以无法立即返回结果，这样如果采用BIO就会占用一个连接，而使用NIO后就可以将此连接转让给其他请求，直至程序处理完成返回为止。

# APR
APR(Apache Portable Runtime/Apache可移植运行时)，是Apache HTTP服务器的支持库。你可以简单地理解为:Tomcat将以JNI的形式调用 Apache HTTP服务器的核心动态链接库来处理文件读取或网络传输操作，从而大大地提高 Tomcat对静态文件的处理性能。 
APR可以大大提升Tomcat对静态文件的处理性能，同时如果你使用了HTTPS方式传输的话，也可以提升SSL的处理性能。

# 修改方式
//BIO 
protocol="HTTP/1.1" 
//NIO 
protocol="org.apache.coyote.http11.Http11NioProtocol" 
//NIO2 
protocol="org.apache.coyote.http11.Http11Nio2Protocol" 
//APR 
protocol="org.apache.coyote.http11.Http11AprProtocol"
```