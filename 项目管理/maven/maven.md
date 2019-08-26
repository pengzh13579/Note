[TOC]

## 1 pom导入本地jar文件
### 1.1 创建 lib 包并放入 jar 文件

在项目根目录下创建 lib 文件夹，随后将 jar 文件放入 lib 文件夹。

### 1.2 pom.xml 配置 jar文件

```
<dependencies>
	<dependency>
        <groupId>com.abc.pay</groupId>
        <artifactId>sdk</artifactId>
        <version>0.0.1</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/src/main/resources/lib/TrustPayClient-V3.1.6.jar</systemPath>
	</dependency>
</dependencies>

......

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

