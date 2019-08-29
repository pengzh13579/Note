[TOC]

## 1 pom导入本地jar文件
### 1.1 创建 lib 包并放入 jar 文件
在项目根目录下创建 lib 文件夹，随后将 jar 文件放入 lib 文件夹。

### 1.2 pom.xml 配置 jar文件
```
<dependencies>
	<dependency>
        <groupId>com.XXX.pay</groupId>
        <artifactId>sdk</artifactId>
        <version>0.0.1</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/src/main/resources/lib/XXX-V3.1.6.jar</systemPath>
	</dependency>
</dependencies>
...
...

<build>
	<plugins>
		<plugin>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
				<source>1.8</source>
				<target>1.8</target>
          		<encoding>UTF-8</encoding>
          		<compilerArguments>
            		<extdirs>${project.basedir}/src/main/resources/lib</extdirs>
          		</compilerArguments>
        	</configuration>
      	</plugin>
      	<plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <executions>
                <execution>
                    <goals>
                    	<goal>repackage</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <mainClass>XXXXXXXXXXXXXXXXXX</mainClass>
                <fork>true</fork>
                <includeSystemScope>true</includeSystemScope>
            </configuration>
      	</plugin>
    </plugins>
    <resources>
        <resource>
            <directory>lib</directory>
            <targetPath>BOOT-INF/lib/</targetPath>
            <includes>
            	<include>**/*.jar</include>
            </includes>
        </resource>
    </resources>
</build>
```
### 1.3 若大成war包则追加以下内容
```
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-war-plugin</artifactId>
            <version>3.0.0</version>
            <configuration>
            	<!--如果想在没有web.xml文件的情况下构建WAR，请设置为false。-->
                <failOnMissingWebXml>false</failOnMissingWebXml>
                <webResources>
                    <resource>
                        <directory>src/main/resources/lib</directory>
                        <targetPath>WEB-INF/lib/</targetPath>
                        <includes>
                            <include>**/*.jar</include>
                        </includes>
                    </resource>
                </webResources>
            </configuration>
        </plugin>
	</plugins>
</build>
```
## 2 SpringBoot打war发布
### 2.1 修改默认打包方式
```
<groupId>com.example</groupId>
<artifactId>application</artifactId>
<version>0.0.1-SNAPSHOT</version>
<!--默认为jar方式-->
<!--<packaging>jar</packaging>-->
<!--改为war方式-->
<packaging>war</packaging>
```
### 2.2 排除内置的Tomcat容器
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
### 2.3 添加依赖
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <!--打包的时候可以不用包进去，别的设施会提供。事实上该依赖理论上可以参与编译，测试，运行等周期。
        相当于compile，但是打包阶段做了exclude操作-->
    <scope>provided</scope>
</dependency>
```
### 2.4 启动类继承SpringBootServletInitializer实现configure
```
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
```
### 2.5 注意
#### 2.5.1 事项一
application.properties中配置的server.port等参数将失效
#### 2.5.2 事项二
建议pom.xml文件中<build></build>标签下添加<finalName></finalName>标签
```
<build>
    <!-- 应与application.properties(或application.yml)中context-path保持一致 -->
    <finalName>war包名称</finalName>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
## 3 SpringBoot项目打war包后不存在resources下的资源文件
```
<build>
	<finalName>war包名称</finalName>
    <!--解决在src/main/java下不能读取xml的问题-->
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
            	<include>**/*.xml</include>
            </includes>
        </resource>
        <!--扫描src/main/resources下的全部文件-->
        <resource>
            <directory>src/main/resources</directory>
            <includes>
            	<include>**/*.*</include>
            </includes>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
