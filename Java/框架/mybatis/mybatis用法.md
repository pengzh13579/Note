[TOC]

## 1 #{}和${}的区别、及注入问题
### 1.1 区别
在查询之前 mybatis 会对其进行动态解析，#{} 和 ${} 在预编译中的处理是不一样的：
例如：`select * from t_user where userName = #{name};`
#{}预编译：用一个占位符 ? 代替参数：`select * from t_user where userName = ?`
#{}预编译：会将参数值一起进行编译：`select * from t_user where userName = 'zhangsan'`
### 1.2 使用场景
一般情况首选#{}，因为这样能避免sql注入；如果需要传参 动态表名、动态字段名时，需要使用${}
比如：`select * from ${tableName} where id > #{id};`
### 1.3 SQL注入问题
使用${}出现的注入问题：
`select * from ${tableName};`
如果传参`t_user;delete from t_user`，则预编译后的sql如下，将会导致系统不可用：
`select * from t_user;delete from t_user;`
### 1.4 like语句防注入
使用concat函数：
`select * from t_user where name like concat('%', #{name}, '%')`
## 2 mybatis几种传参方式
### 2.1 非注解
#### 2.1.1 单参数
```
public User getUserByUuid(String uuid); 
<select id="getUserByUuid"  resultMap="BaseResultMap" parameterType="Object">
    SELECT * FROM   t_user  WHERE  uuid = #{uuid}
</select>
```
#### 2.1.2 多参数
```
public User getUserByNameAndPass(String name,String pass);  
<select id="getUserByNameAndPass"  resultMap="BaseResultMap" parameterType="Object">
    SELECT * FROM t_user  WHERE  t_name = #{0} and t_pass = #{1}
</select>
```
#### 2.1.3 Map参数
```
public User getUserByMap(Map<String,Object> map);
<select id="getUserByMap"  resultMap="BaseResultMap" parameterType="java.util.Map">
    SELECT * FROM t_user  WHERE  t_name = #{name} and t_pass = #{pass}
</select>
```
#### 2.1.4 实体对象参数
```
public int updateUser(User user);   
<select id="updateUser"  resultMap="BaseResultMap" parameterType="Object">
    update t_user set t_name = #{name}, t_pass = #{pass} where uuid=#{uuid}
</select>
```
#### 2.1.5 List集合参数
```
public int batchDelUser(List<String> uuidList);
<delete id="batchDelUser" parameterType="java.util.List">
    DELETE FROM t_user WHERE uuid IN
    <foreach collection="list" index="index" item="uuid" open="(" separator="," close=")">
        #{uuid}
    </foreach>
</delete>
```
### 2.2 注解
```
public List<User> getUserByTime(@Param("startTime")String startTime, @Param("endTime")String endTime);
<select id="getUserByTime" resultMap="BaseResultMap"  parameterType="Object">
    SELECT * from t_user where createTime &gt;= #{startTime} and createTime &lt;= #{endTime}
</select>
```
## 3 choose when otherwise的使用
```
//JAVA 代码
public List<Group> getUserRoleRelByUserUuid(@Param("groupUuid") String userUuid,@Param("roleList")List<String> roleUuidList);
//SQL
SELECT * from user_role where groupUuid=#{groupUuid}
<choose>
    <when test="roleList!=null&amp;&amp;roleList.size()&gt;0">
        AND roleUuid IN
        <foreach collection="roleList" index="index" item="roleUuid" open="(" separator="," close=")">
        	#{roleUuid}
        </foreach>
    </when>
    <otherwise>
    	AND roleUuid IN ('')
    </otherwise>
</choose>
```
## 4 判断字符串相等
### 4.1 判断字符串相等
```
//JAVA 代码
public int getOrderCountByParams(Map<String, Object> params);
//SQL
<select id="getOrderCountByParams" resultType="java.lang.Integer"  parameterType="Object">
    SELECT count(*) FROM itil_publish_order where 1=1
       <if test="timeType == '1'.toString()" >
            AND create_time &gt;= #{timeStr}
        </if>
        <if test="timeType == '2'.toString()" >
            AND end_time &lt;= #{timeStr}
        </if>
  </select>
或者
<if test = 'timeType== "1"'> </if>
```
### 4.2 大于等于、小于等于
大于：&gt;
小于：&lt;
```
//JAVA代码
public List<PublishOrder> getOrderCount(@Param("startTime") String startTime,@Param("startTime")List<String> startTime);
//SQL
<select id="getOrderCount" resultType="java.lang.String"  parameterType="Object">
        SELECT * FROM itil_publish_order
        WHERE createTime &gt;= #{startTime} and &lt;= #{startTime}
</select>
```
