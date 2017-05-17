1. 在配置好kafka的server.properties文件后，cmd进入命令窗口输入命令：.\\bin\\windows\\kafka-server-start.bat config\\server.properties提示错误： **错误: 找不到或无法加载主类 Files\\Java\\jdk1.7.0_80\\lib;C:\\Program** 解决方式如下：`在kafka安装目录中找到bin\\windows目录中的kafka-run-class.bat找到142行为%CLASSPATH%加上双引号`

**修改前** ：
```
set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% %KAFKA_LOG4J_OPTS% -cp %CLASSPATH% %KAFKA_OPTS% %*
```
**修改后** ：
```
set COMMAND=%JAVA% %KAFKA_HEAP_OPTS% %KAFKA_JVM_PERFORMANCE_OPTS% %KAFKA_JMX_OPTS% %KAFKA_LOG4J_OPTS% -cp "%CLASSPATH%" %KAFKA_OPTS% %*
```


2. 安装Zookeeper
--------------

Kafka的运行依赖于Zookeeper，所以在运行Kafka之前我们需要安装并运行Zookeeper
2.1 下载安装文件： http://zookeeper.apache.org/releases.html2.2 解压文件（本文解压到 G:\\zookeeper-3.4.8）2.3 打开G:\\zookeeper-3.4.8\\conf，把zoo_sample.cfg重命名成zoo.cfg2.4 从文本编辑器里打开zoo.cfg2.5 把dataDir的值改成":\\zookeeper-3.4.8\\data"2.6 添加如下系统变量：
*
  * ZOOKEEPER_HOME: G:\\zookeeper-3.4.8
  * Path: 在现有的值后面添加 ";%ZOOKEEPER_HOME%\\bin;"

2.7 运行Zookeeper: 打开cmd然后执行

```
zkserver 
```

3. 安装并运行Kafka
-------------

3.1 下载安装文件： http://kafka.apache.org/downloads.html

3.2 解压文件（本文解压到 G:\\kafka_2.11-0.10.0.1）3.3 打开G:\\kafka_2.11-0.10.0.1\\config3.4 从文本编辑器里打开 server.properties3.5 把 log.dirs的值改成 "G:\\kafka_2.11-0.10.0.1\\kafka-logs"3.6 打开cmd3.7 进入kafka文件目录: cd /d G:\\kafka_2.11-0.10.0.1\\3.8 输入并执行以打开kafka:

```
.\\bin\\windows\\kafka-server-start.bat .\\config\\server.properties
```

4. 创建topics
-----------

4.1 打开cmd 并进入G:\\kafka_2.11-0.10.0.1\\bin\\windows4.2 创建一个topic：

```
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```

5. 打开一个Producer：
----------------

```
cd /d G:\\kafka_2.11-0.10.0.1\\bin\\windows
kafka-console-producer.bat --broker-list localhost:9092 --topic test
```

6. 打开一个Consumer:
----------------

```
cd /d G:\\kafka_2.11-0.10.0.1\\bin\\windows
```

```
kafka-console-consumer.bat --zookeeper localhost:2181 --topic test
```

然后就可以在Producer控制台窗口输入消息了。在消息输入过后，很快Consumer窗口就会显示出Producer发送的消息：![](http://img.blog.csdn.net/20160821224125792?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast) 至此，Kafka运行环境的搭建就完成了:-)
