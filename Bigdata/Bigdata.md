# 大数据面试题
## 1. 什么是Hadoop?
Hadoop 是一个开源软件框架，用于存储大量数据，并发处理/查询在具有多个商用硬件（即低成本硬件）节点的集群上的那些数据。总之，Hadoop 包括以下内容：

HDFS（Hadoop Distributed File System，Hadoop 分布式文件系统）：HDFS 允许你以一种分布式和冗余的方式存储大量数据。例如，1 GB（即 1024 MB）文本文件可以拆分为 16 * 128MB 文件，并存储在 Hadoop 集群中的 8 个不同节点上。每个分裂可以复制 3 次，以实现容错，以便如果 1 个节点故障的话，也有备份。HDFS 适用于顺序的“一次写入、多次读取”的类型访问。

MapReduce：一个计算框架。它以分布式和并行的方式处理大量的数据。当你对所有年龄> 18 的用户在上述 1 GB 文件上执行查询时，将会有“8 个映射”函数并行运行，以在其 128 MB 拆分文件中提取年龄> 18 的用户，然后“reduce”函数将运行以将所有单独的输出组合成单个最终结果。

YARN（Yet Another Resource Nagotiator，又一资源定位器）：用于作业调度和集群资源管理的框架。

Hadoop 生态系统，拥有 15 多种框架和工具，如 Sqoop，Flume，Kafka，Pig，Hive，Spark，Impala 等，以便将数据摄入 HDFS，在 HDFS 中转移数据（即变换，丰富，聚合等），并查询来自 HDFS 的数据用于商业智能和分析。某些工具（如 Pig 和 Hive）是 MapReduce 上的抽象层，而 Spark 和 Impala 等其他工具则是来自 MapReduce 的改进架构/设计，用于显著提高的延迟以支持近实时（即 NRT）和实时处理。

## 2. 海量日志数据提取出某日访问百度次数最多的IP，怎么做?
IP是32位，最多有个2^32个IP。同样可以采用映射的方法，比如模1000，把整个大文件映射为1000个小文件，再找出每个小文件中出现频率最大的IP(可以采用hash_map进行频率统计，然后再找出频率最大的几个)及相应的频率。然后再在这1000个最大的IP中，找出那个频率最大的IP。

## 3. 有一个1G大小的文件，里面每一行是一个词，词的大小不超过16字节，内存限制大小是1M。返回频数最高的100个词。
方案：顺序读文件中，对于每个词x，取hash(x)%5000，然后按照该值存到5000个小文件。这样每个文件大概200k左右。

## 4. 运行Hadoop集群需要哪些守护进程？
DataNode，NameNode，TaskTracker和JobTracker都是运行Hadoop集群需要的守护进程。

## 5. Hadoop常见输入格式是什么？
文本输入；Key值；序列

## 6. 给定a，b两个文件，各存放50亿个url，每个url各占64字节，内存限制是4G，让你找出a，b文件共同url？
方案：可以估计每个文件的大小为5G*64=320G，远远大于内存限制的4G。所以不可能将其完全加载到内存中处理。考虑采取分而治之的方法。<br>
遍历文件a，对每个url求取hash(url)%1000，然后根据所取得的值将url分别存储到1000个小文件。这样每个小文件的大约为300M<br>
遍历b，采取和a相同的方式将url分别存储到1000个小文件。这样处理后，所有可能相同的url都在对应的小文件，不对应的小文件不可能有相同的url。然后我们只要求出1000对小文件中相同的url。<br>
求每对小文件中相同的url时，可以把其中一个小文件的url存储到hash_set中。然后遍历另一个小文件的每个url，看其是否在刚才构建的hash_set中，如果是，那么就是共同的url，存到文件里面就可以了。

## 7. 添加新datanode后，作为Hadoop管理员需要做什么？
需要启动平衡器才能在所有节点之间重新平均分配数据，以便Hadoop集群自动查找新的datanode。要优化集群性能，应该重新启动平衡器以在数据节点之间重新分配数据。

## 8. namenode的重要性是什么？
namenonde的作用在Hadoop中非常重要。它是Hadoop的大脑，主要负责管理系统上的分配块，还为客户提出请求时的数据提供特定地址。

## 9. 当NameNode关闭时会发生什么？
如果NameNode关闭，文件系统将脱机。

## 10. 是否可以在不同集群之间复制文件？如果是的话，怎么能做到这一点？
是的，可以在多个Hadoop集群之间复制文件，这可以使用分布式复制来完成。

## 11. Distcp是什么？
Distcp是一个Hadoop复制工具，主要用于执行MapReduce作业来复制数据。Hadoop环境中的主要挑战是在各集群之间复制数据，distcp也将提供多个datanode来并行复制数据。

## 12. 什么是检查点？
检查点是一种采用FsImage的方法。它编辑日志并将它们压缩成一个新的FsImage。因此，不用重放一个编辑日志，NameNode可以直接从FsImage加载到最终的内存状态，这肯定会降低NameNode启动时间。

## 13. 什么是机架感知？
这是一种决定如何根据机架定义放置块的方法。Hadoop将尝试限制存在于同一机架中的datanode之间的网络流量。为了提高容错能力，namenode会尽可能把数据块的副本放到多个机架上。 综合考虑这两点的基础上Hadoop设计了机架感知功能。

## 14. 什么是投机性执行？
如果一个节点正在执行比主节点慢的任务。那么就需要在另一个节点上冗余地执行同一个任务的一个实例。所以首先完成的任务会被接受，另一个可能会被杀死。这个过程被称为“投机执行”。

## 15. Hadoop的基本特性是什么？
Hadoop框架有能力解决大数据分析的许多问题。它是基于Google大数据文件系统的Google MapReduce设计的。

## 16. 如何安装配置apache的一个开源hadoop。
1. 配置hadoop的核心文件 hadoop-env.sh，core-site.xml , mapred-site.xml ， hdfs-site.xml
2. 配置hadoop环境变量
3. 格式化 hadoop namenode-format
4. 启动节点start-all.sh

## 17. 写出shell命令
1. 杀死一个job
    
    hadoop job –list 得到job的id
    hadoop job -kill jobId就可以杀死一个指定jobId的job工作了。
2. 删除hdfs上的/tmp/aaa目录
    
    hadoopfs -rmr /tmp/aaa
3. 增加一个新的节点在新的节点上执行
    
    Hadoop daemon.sh start datanode
    Hadooop daemon.sh start tasktracker/nodemanager
    下线时，要在conf目录下的excludes文件中列出要下线的datanode机器主机名
    在主节点中执行 hadoop dfsadmin -refreshnodes à 下线一个datanode
    删除一个节点的时候，只需要在主节点执行
    hadoop mradmin -refreshnodes 下线一个tasktracker/nodemanager

## 18. MapReduce的大致流程
1. 对文件进行切片规划
2. 启动相应数量的maptask进程
3. 调用FileInputFormat中的RecordReader，读一行数据并封装为k1v1
4. 调用自定义的map函数，并将k1v1传给map
5. 收集map的输出，进行分区和排序
6. reduce task任务启动，并从map端拉取数据
7. reduce task调用自定义的reduce函数进行处理
8. 调用outputformat的recordwriter将结果数据输出

## 19. MapReduce的原理
MapReduce的原理就是将一个MapReduce框架由一个单独的master JobTracker和每个集群节点一个slave TaskTracker共同组成。master负责调度构成一个作业的所有任务，这些的slave上，master监控它们的执行，重新执行已经失败的任务。而slave仅负责执行由maste指派的任务。

## 20. 如何确认Hadoop集群的健康状况？
有完善的集群监控体系（ganglia，nagios）

    Hdfs dfsadmin –report
    Hdfs haadmin –getServiceState nn1

## 21. ArrayList、Vector、LinkedList 的区别及其优缺点？HashMap、HashTable 的区别及其优缺点？
ArrayList和Vector是采用数组方式存储数据，Vector由于使用了synchronized方法（线程安全）所以性能上比ArrayList要差，LinkedList使用双向链表实现存储，按序号索引数据需要进行向前或向后遍历，但是插入数据时只需要记录本项的前后项即可，所以插入数度较快！

HashMap和HashTable：Hashtable的方法是同步的，而HashMap的方法不是，Hashtable是基于陈旧的Dictionary类的，HashMap是Java 1.2引进的Map接口的一个实现。HashMap是一个线程不同步的，那么就意味着执行效率高，HashTable是一个线程同步的就意味着执行效率低，但是HashMap也可以将线程进行同步，这就意味着，我们以后再使用中，尽量使用HashMap这个类。

## 22. 怎样预防全表扫描
1. 应尽量避免在where 子句中对字段进行null 值判断，否则将导致引擎放弃使用索引而进行全表扫描
2. 应尽量避免在 where 子句中使用!=或<>操作符，否则将引擎放弃使用索引而进行全表扫描
3. 描应尽量避免在 where 子句中使用or 来连接条件，否则将导致引擎放弃使用索引而进行全表扫描
4. in 和 not in，用具体的字段列表代替，不要返回用不到的任何字段。in 也要慎用，否则会导致全表扫描
5. 避免使用模糊查询
6. 任何地方都不要使用select* from t