# Redis
## 1. 什么是Redis？
全称: Remote Dictionary Server

Redis本质上是一个Key-Value类型的`内存数据库`。与Memcached很像。

整个数据库`统统加载在内存当中`进行操作，定期通过`异步操作`把数据库数据flush到硬盘上进行保存。

Redis是已知性能最快的Key-Value DB, 100k/s读写

### 用途
1. 用Redis List来做FIFO双向链表，实现一个轻量级的高性能消息队列服务。
2. 用Redis Set可以做高性能tag系统。

### 优势：
1. 支持丰富数据类型
2. 速度极快
3. 可以持久化存储数据
   
## 2. Redis主要支持哪几个数据类型？
### String
**String 类型是 Redis 最基本的数据类型，String 类型的值最大能存储 512MB。**
```
    // 设置一个key为name, value为runoob的记录。
    SET name "runoob" 
    // 获得key为name的value。
    GET name
```    
### Hash
Redis hash 是一个 string 类型的 field 和 value 的映射表，hash 特别适合用于存储对象。
```
    HMSET myhash field1 "Hello" field2 "World"
    HGET myhash field1
    HGET myhash field2
```

### List
Redis 列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部（左边）或者尾部（右边）。

列表最多可存储 232 - 1 元素
```
    lpush runoob redis
    (integer) 1
    lpush runoob mongodb
    (integer) 2
    lpush runoob rabitmq
    (integer) 3
    lrange runoob 0 10
```

### Set
Redis的Set是string类型的无序集合。

集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是O(1)。
```
    sadd key member
    sadd runoob redis
    (integer) 1
```

### zset (sorted set)
redis正是通过分数来为集合中的成员进行从小到大的排序。

不同的是每个元素都会关联一个double类型的分数。redis正是通过分数来为集合中的成员进行从小到大的排序。

zset的成员是唯一的,但分数(score)却可以重复。
```
    zadd key score member
    zadd runoob 0 redis
```

## 3. 什么是Redis持久化? Redis有哪几种持久化方式？优缺点是什么？
持久化就是内存的数据写到磁盘中去，防止服务宕机来内存数据丢失。主要有两种持久化方式：RDB和AOF
### RDB (Redis DataBase)
通过两个核心函数rdbSave()和rdbLoad()来实现。
### AOF (Append-only file)
每当执行服务器定时任务或者函数时, flushAppendOnlyFile 函数都会被调用, 这个函数执行以下两个工作:
1. WRITE：根据条件，将 aof_buf 中的缓存写入到 AOF 文件
2. SAVE：根据条件，调用 fsync 或 fdatasync 函数，将 AOF 文件保存到磁盘中。
### 两者比较
1. AOF文件比rdb更新频率更高，优先使用AOF。
2. AOF更安全，也更大
3. RDB更快

## 4. 解释一下什么是Redis通讯协议(RESP)
RESP 是redis客户端和服务端之前使用的一种通讯协议。主要是在报文前加符号。
1. 回复: "+"
2. 错误: "-"
3. 整数: ":"
4. 字符串: "$"
5. 数组: "*"

## 5. 什么是一致性哈希算法？什么是哈希槽？
https://www.cnblogs.com/lpfuture/p/5796398.html
https://blog.csdn.net/z15732621582/article/details/79121213
Redis集群有16384个哈希槽，每个key通过CRC16校验后对16384取模来决定放置哪个槽，集群的每个节点负责一部分hash槽。

## 6. MySQL里有2000w数据, redis中只存储了20w数据，如何保证redis中的数据都是热点数据？
redis内存数据集大小上升到一定大小的时候，就会施行`数据淘汰策略`。
1. noeviction:返回错误, 当内存限制达到并且客户端尝试执行会让更多内存被使用的命令
2. allkeys-lru: 尝试回收最少使用的键（LRU），使得新添加的数据有空间存放。
3. volatile-lru: 尝试回收最少使用的键（LRU），但仅限于在过期集合的键,使得新添加的数据有空间存放。
4. allkeys-random: 回收随机的键使得新添加的数据有空间存放。
5. volatile-random: 回收随机的键使得新添加的数据有空间存放，但仅限于在过期集合的键。
6. volatile-ttl: 回收在过期集合的键，并且优先回收存活时间（TTL）较短的键,使得新添加的数据有空间存放。

## 7. Redis集群结构
1. 主从复制
   maste复制出很多slave，如果maste宕机了，slave抵上来。
2. 集群(直连型)
   无中心，每个节点保存数据和整个集群状态，每个节点都和其他节点连接。

## 8. 怎么理解Redis事物？
事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。

事务是一个原子操作：事务中的命令要么全部被执行，要么全部都不执行。

## 9. Redis事务相关的命令有哪些？
MULTI， EXEC， DISCARD， WATCH

## 10. Redis如何做内存优化？
尽可能使用散列表（hashes），散列表（是说散列表里面存储的数少）使用的内存非常小，所以你应该尽可能的将你的数据模型抽象到一个散列表里面。

比如你的web系统中有一个用户对象，不要为这个用户的名称，姓氏，邮箱，密码设置单独的key,而是应该把这个用户的所有信息存储到一张散列表里面。


