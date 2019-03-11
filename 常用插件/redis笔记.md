[TOC]

## 1 redis

### 1.1 redis的安装
```
# 进入root目录，并下载Redis的安装包
cd
wget http://labfile.oss.aliyuncs.com/files0422/redis-2.8.9.tar.gz
# 在目录下，解压按照包，生成新的目录redis-2.8.9
tar -xzvf redis-2.8.9.tar.gz
# 进入解压之后的目录,进行编译
cd redis-2.8.9
make
# 在安装成功之后，可以运行测试，确认Redis的功能是否正常
make test
```
### 1.2 redis启动

#### 1.2.1 常用的文件
服务端：src/redis-server
客户端：src/redis-cli
默认配置文件：redis.conf

#### 1.2.2 配置环境变量
然后将可执行文件放置在$PATH环境目录下，便于以后执行程序时可以不用输入完整的路径
cp redis-server /usr/local/bin/
cp redis-cli /usr/local/bin/
#### 1.2.3 启动redis
redis-server
#### 1.2.4 查看Redis
ps -ef | grep redis
#### 1.2.5 通过端口号检查Redis服务器状态
netstat -nlt|grep 6379
#### 1.2.6 启动Redis-client
redis-cl

### 1.3 Redis数据类型
#### 1.3.1 strings
1）插入与获取
$ sudo service redis-server start
#启动redis客户端
$ redis-cli
```
#创建一个名为‘mykey’的strings
> set mykey somevalue
#检索一个名为‘mykey’的strings
> get mykey
```
set命令可以附加参数
nx参数表示如果存在键值，set命令失败
xx参数表示只有存在键值，set命令才成功。
如：
```
> set mykey newval nx
> set mykey newval xx
```
2）set的原子操作
incr、incrby、decr、decrby
如：
```
> set counter 100
OK
> incr counter
(integer) 101
> incr counter
(integer) 102
> incrby counter 50
(integer) 152
```
3）批量操作
MSET and MGET 命令完成一次性的完成多个key-value的对应关系，使用MGET命令，Redis返回一个value数组。
如：
```
> mset a 10 b 20 c 30
> mget a b c
```
#### 1.3.2 Lists
```
#右侧插入
> rpush mylist A
> rpush mylist B
#左侧插入
> lpush mylist first
#LRANGE 利用了两个检索值，0表示list的开头第一个，-1表示list的倒数第一个，即最后一个。
> lrange mylist 0 -1
```
#### 1.3.3 Hashes
```
#HMSET命令设置一个多域的hash表
> hmset user:1000 username antirez birthyear 1977 verified 1
#HGET命令获取指定的单域，HMGET类似于HGET，只是返回一个value数组。
> hget user:1000 username
#HGET命令获取指定的单域
> hget user:1000 birthyear
#HGETALL命令获取指定key的所有信息
> hgetall user:1000
#hincrby为birthyear属性加10
> hincrby user:1000 birthyear 10
> hincrby user:1000 birthyear 10
```
#### 1.3.4 Set无序集合
```
#SADD命令产生一个无序集合，返回集合的元素个数
> sadd myset 1 2 3
#SMEMBERS用于查看集合
> smembers myset
#SISMEMBER用于查看集合是否存在，匹配项包括集合名和元素个数。匹配成功返回1，匹配失败返回0
> sismember myset 3
> sismember myset 30
> sismember mys 3
```
#### 1.3.5 无序集合
```
#ZADD与SADD类似，但是在元素之前多了一个参数，这个参数便是用于排序的。形成一个有序的集合。
> zadd hackers 1940 "Alan Kay"
> zadd hackers 1957 "Sophie Wilson"
> zadd hackers 1953 "Richard Stallman"
> zadd hackers 1949 "Anita Borg"
> zadd hackers 1965 "Yukihiro Matsumoto"
> zadd hackers 1914 "Hedy Lamarr"
> zadd hackers 1916 "Claude Shannon"
> zadd hackers 1969 "Linus Torvalds"
> zadd hackers 1912 "Alan Turing"
#ZRANGE是查看正序的集合，0表示集合第一个元素，-1表示集合的倒数第一个元素。
> zrange hackers 0 -1
#ZREVRANGE是查看反序的集合
> zrevrange hackers 0 -1
#WITHSCORES 参数返回记录值。
> zrange hackers 0 -1 withscores
```
### 1.4 常用命令
#### 1.4.1 常用命令
1）EXISTS key 判断一个key是否存在;存在返回 1;否则返回0;
`> exists mykey`
2）DEL key 删除某个key,或是一系列key;DEL key1 key2 key3 key4。成功返回1，失败返回0（key值不存在）。
`> del mykey`
3）TYPE key：返回某个key元素的数据类型 ( none:不存在,string:字符,list,set,zset,hash)，key不存在返回空。
`> type mykey`
4）KEYS key—pattern ：返回匹配的key列表 (KEYS foo*:查找foo开头的keys)
`> keys my*`
5）RANDOMKEY ： 随机获得一个已经存在的key，如果当前数据库为空，则返回空字符串
`> randomkey`
6）CLEAR ：清除界面。
`> clear`
7）RENAME oldname newname：改key的名字，新键如果存在将被覆盖
`> rename mylist newlist`
8）RENAMENX oldname newname：更改key的名字,如果newname存在,则更新失败
9）DBSIZE ：返回当前数据库的key的总数
`> dbsiz`
#### 1.4.2 时间命令
1）EXPIRE：设置某个key的过期时间（秒）
EXPIRE bruce 1000：设置bruce这个key1000秒后系统自动删除
注意：如果在还没有过期的时候，对值进行了改变，那么那个值会被清除。
```
> set key some-value
> expire key 10
```
2）TTL：查找某个key还有多长时间过期,返回时间秒
```
> set key 100 ex 30
> ttl key
```
3）FLUSHDB：清空当前数据库中的所有键
`>flushdb`
4）FLUSHALL：清空所有数据库中的所有键
`>flushall`
#### 1.4.3 配置命令
CONFIG GET：用来读取运行Redis服务器的配置参数。
CONFIG SET：用于更改运行Redis服务器的配置参数。
AUTH : 认证密码
下面针对Redis密码的示例：
```
> config get requirepass （查看密码）
> config set requirepass test123 （设置密码为test123 ）
> config get requirepass  （报错，没有认证）
> auth test123
> config get requirepass
```
刚开始时Reids并未设置密码，密码查询结果为空。
然后设置密码为test123，再次查询报错。
经过auth命令认证后，可正常查询。
可以经过修改Redis的配置文件redis.conf修改密码。
CONFIG GET命令是以list的key-value对显示的，如查询数据类型的最大条目：
`> config get *max-*-entries*`
CONFIG RESETSTAT：重置数据统计报告，通常返回值为'OK’。
`> CONFIG RESETSTAT`
#### 1.4.4 查询信息
INFO [section] ：查询Redis相关信息。
INFO命令可以查询Redis几乎所有的信息，
其命令选项有如下：
|命令选项|说明|
|-----|-----|
|server| Redis server的常规信息|
|clients| Client的连接选项|
|memory| 存储占用相关信息|
|persistence| RDB and AOF 相关信息|
|stats| 常规统计|
|replication| Master/slave请求信息|
|cpu| CPU 占用信息统计|
|cluster:|Redis 集群信息|
|keyspace| 数据库信息统计|
|all|返回所有信息|
|default| 返回常规设置信息|
若命令参数为空，info命令返回所有信息。
```
> info keyspace
> info server
```
### 1.5 高级应用
#### 1.5.1 设置密码的方式
1） 使用config set 命令的requirepass 参数，具体格式为config set requirepass “password”。
2） 配置redis.conf 中设置requirepass属性，后面为密码。
#### 1.5.2 输入认证的方式也有两种：
1） 登录时可以 redis-cli -a password
2） 登录后使用 auth password
#### 1.5.3 主从复制
1）特点：
1、master可以拥有多个slave。
2、多个slave可以连接同一个master外，还可以连接到其他的slave。（当master宕机后，相连的slave转变为master）
3、主从复制不会阻塞master，再同步数据时，master可以继续处理client请求。
4、提高了系统的可伸缩性。
2）主从复制的过程：
1、Slave与master建立连接，发送sync同步命令。
2、Master会启动一个后台进程，将数据库快照保存到文件中，同时Master主进程会开始收集新的写命令并缓存。
3、后台完成保存后，就将此文件发送给Slave。
4、Slave将此文件保存到磁盘上。
`vim /etc/redis/redis.conf`
#添加Master端的IP与端口
`slaveof 192.168.33.130 6379  `

#### 1.5.4 事务处理
当一个client在连接中发出multi命令时，这个连接就进入一个事务的上下文，该连接后续的命令不会执行，而是存放到一个队列中，当执行exec命令时，redis会顺序的执行队列中的所有命令。
```
> multi
> set name a
> set name b
> exec
> get name
```
#### 1.5.5 持久化机制
##### 1.5.5.1 Redis支持两种持久化方式：
1、snapshotting（快照），将数据存放到文件里，默认方式。
    是将内存中的数据已快照的方式写入到二进制文件中，默认文件dump.rdb，可以通过配置设置自动做快照持久化的方式。可配置Redis在n秒内如果超过m个key被修改就自动保存快照。
save 900 1 #900秒内如果超过1个key被修改，则发起快照保存
save 300 10 #300秒内如果超过10个key被修改，则快照保存

2、Append-only file（缩写为aof），将读写操作存放到文件中。
	由于快照方式在一定间隔时间做一次，所以如果Redis意外down掉的话，就会丢失最后一次快照后的所有修改。
    aof比快照方式有更好的持久化性，是由于使用aof时，redis会将每一个收到的写命令都通过write函数写入到文件中当redis启动时会通过重新执行文件中保存的写命令来在内存中重新建立整个数据库的内容。
	由于os会在内核中缓存write做的修改，所以可能不是立即写到磁盘上，这样aof方式的持久化也还是有可能会丢失一部分数据。可以通过配置文件告诉redis我们想要通过fsync函数强制os写入到磁盘的时机。

##### 1.5.5.2 配置文件中的可配置参数：
appendonly   yes     //启用aof持久化方式
#appendfsync  always //收到写命令就立即写入磁盘，最慢，但是保证了数据的完整持久化
appendfsync   everysec  //每秒中写入磁盘一次，在性能和持久化方面做了很好的折中
#appendfsync  no     //完全依赖os，性能最好，持久化没有保证
##### 1.5.5.3 在redis-cli的命令中，SAVE命令是将数据写入磁盘中。
```
> help save
> save
```
### 1.6 虚拟内存
Redis的虚拟内存是暂时把不经常访问的数据从内存交换到磁盘中，从而腾出内存空间用于其他的访问数据，尤其对于redis这样的内存数据库，内存总是不够用的。除了分隔到多个redis server外，提高数据库的容量的方法就是使用虚拟内存，把那些不常访问的数据交换到磁盘上。
通过配置vm相关的redis.config配置：
vm-enable  yes                   #开启vm功能
vm-swap-file    /tmp/redis.swap  #交换出来的value保存的文件路径
vm-max-memory    10000000        #redis使用的最大内存上限
vm-page-size   32       #每个页面的大小32字节
vm-pages     123217729    #最多使用多少个页面
vm-max-threads     4        #用于执行value对象换入的工作线程数量