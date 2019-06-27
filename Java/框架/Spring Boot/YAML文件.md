[TOC]

## 1 YAML基本语法

- 使用缩进表示层级关系
- 缩进时不允许使用Tab键，只允许使用空格。
- 缩进的空格数目不重要，只要相同层级的元素左侧对齐即可
- 大小写敏感
- 支持的三种数据结构 
	- 对象：键值对的集合
	- 数组：一组按次序排列的值
	- 字面量：单个的、不可再分的值

## 2 例子

```
person:
  last-name: 'zhangsan'
  age: 13
  birth: 2017/12/15
  boss: false
  maps:
    k1: v1
    k2: 14
  lists: [a,b,c]
  listsMap: {k1: v1, k2: v2}
```

## 3 获取
```
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private String lastName;
    private Integer age;
    private Boolean boss;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;

    @Override
    public String toString() {
        return "Person{" +
                "lastName='" + lastName + '\'' +
                ", age=" + age +
                ", boss=" + boss +
                ", birth=" + birth +
                ", maps=" + maps +
                ", lists=" + lists +
                '}';
    }
    //省略get/set
}
```
```
@SpringBootApplication
@Controller
@MapperScan (basePackages="cn.pzh.person.application.mapper")
public class Application {

    @Autowired
    private Person person;

    @RequestMapping("/person")
    @ResponseBody
    public String person(HttpServletRequest request){
        return person.toString();
    }
}
```
## 3 结果
`Person{lastName=’zhangsan’, age=13, boss=false, birth=Fri Dec 15 00:00:00 CST 2017, maps={k1=v1, k2=14}, lists=[a, b, c]}`