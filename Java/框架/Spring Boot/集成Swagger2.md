[TOC]

## 1 概念
    REST全称是Representational State Transfer，中文意思是表征性状态转移，指的是一组架构约束条件和原则，是目前最流行的 API 设计规范，用于 Web 数据接口的设计，也是一种互联网应用程序的API设计理念。如果一个架构符合REST的约束条件和原则，我们就称它为RESTful架构。
    Swagger2，它可以轻松的整合到Spring Boot中，并与Spring MVC程序配合组织出强大RESTful API文档。它既可以减少我们创建文档的工作量，同时说明内容又整合入实现代码中，让维护文档和修改代码整合为一体，可以让我们在修改代码逻辑的同时方便的修改文档说明。另外Swagger2也提供了强大的页面测试功能来调试每个RESTful API。
## 2 整合
### 2.1 pom.xml
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.8.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.8.0</version>
</dependency>
```
### 2.2 Swagger配置类
```
@Configuration
// @EnableSwagger2注解启用Swagger2
@EnableSwagger2
public class Swagger2 {
    /***
     * 创建该Api的基本信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger2的应用")
                .description("SpringBoot+Swagger2的应用")
                .termsOfServiceUrl("https://blog.csdn.net/xiaoshiyiqie")
                .version("1.0.0")
                .build();
    }
    /***
     * select()函数返回一个ApiSelectorBuilder实例用来控制暴露给Swagger来展现
     * basePackage,这是扫描注解的配置
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pzh.util.myutil"))
                .paths(PathSelectors.any())
                .build();
    }
}
```
### 2.3 Swagger2注解解释
注解     | 参数|意义
:-------- | :-----|:----
@Api | |用在请求的类上，表示对类的说明
-| tags|说明该类的作用，可以在UI界面上看到的注解
-| value|该参数没什么意义，在UI界面上也看到，所以不需要配置
@ApiOperation||用在请求的方法上，说明方法的用途、作用
-|value|说明方法的用途、作用
-|notes|方法的备注说明
@ApiImplicitParams||用在请求的方法上，表示一组参数说明
@ApiImplicitParam||用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
-|name|参数名
-|value|参数的汉字说明、解释
-|required|参数是否必须传
-|paramType|参数放在哪个地方
-|-|header --> 请求参数的获取：@RequestHeader
-|-|query --> 请求参数的获取：@RequestParam
-|-|path（用于restful接口）--> 请求参数的获取：@PathVariable
-|-|body（不常用）
-|-|form（不常用）    
-|dataType|参数类型，默认String，其它值dataType="Integer"       
-|defaultValue|参数的默认值
@ApiResponses||用在请求的方法上，表示一组响应
@ApiResponse||用在@ApiResponses中，一般用于表达一个错误的响应信息
-|code|数字，例如400
-|message|信息，例如"请求参数没填好"
-|response|抛出异常的类
@ApiModel|用于响应类上，表示一个返回响应数据的信息请求参数无法使用@ApiImplicitParam注解进行描述的时候）
@ApiModelProperty|用在属性上，描述响应类的属性

```
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
 
@ApiModel(description= "返回响应数据")
public class RestMessage implements Serializable{
 
    @ApiModelProperty(value = "是否成功")
    private boolean success=true;
    @ApiModelProperty(value = "返回对象")
    private Object data;
    @ApiModelProperty(value = "错误编号")
    private Integer errCode;
    @ApiModelProperty(value = "错误信息")
    private String message;
```
```
@Api(tags="UtilsController:所有的工具所用", value="")
@RestController
@RequestMapping ("/utilsController")
public class UtilsController {

    @ApiOperation(value="阿拉伯数字转中文大写", notes="根据用户输入的阿拉伯数字，找到对应的中文大写返回")
    @ApiImplicitParams ({
            @ApiImplicitParam(name = "initValue", value = "初始化阿拉伯数字", required = true, dataType = "String"),
            @ApiImplicitParam(name = "response", value = "响应参数不需要传入", required = false, dataType = "HttpServletResponse")
    })
    @RequestMapping("/toggleCase")
    public String toggleCase(String initValue, HttpServletResponse response) {
```
## 3、访问
http://localhost:8080/swagger-ui.html#/