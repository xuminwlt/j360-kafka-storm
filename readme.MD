- 对于kafka的consumer接口，提供两种版本，

##high-level##

一种high-level版本，比较简单不用关心offset, 会自动的读zookeeper中该Consumer group的last offset
参考，https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Group+Example

不过要注意一些注意事项，对于多个partition和多个consumer
1. 如果consumer比partition多，是浪费，因为kafka的设计是在一个partition上是不允许并发的，所以consumer数不要大于partition数
2. 如果consumer比partition少，一个consumer会对应于多个partitions，这里主要合理分配consumer数和partition数，否则会导致partition里面的数据被取的不均匀
    最好partiton数目是consumer数目的整数倍，所以partition数目很重要，比如取24，就很容易设定consumer数目
3. 如果consumer从多个partition读到数据，不保证数据间的顺序性，kafka只保证在一个partition上数据是有序的，但多个partition，根据你读的顺序会有不同
4. 增减consumer，broker，partition会导致rebalance，所以rebalance后consumer对应的partition会发生变化
5. High-level接口中获取不到数据的时候是会block的

简单版，

简单的坑，如果测试流程是，先produce一些数据，然后再用consumer读的话，记得加上第一句设置
因为初始的offset默认是非法的，然后这个设置的意思是，当offset非法时，如何修正offset，默认是largest，即最新，所以不加这个配置，你是读不到你之前produce的数据的，而且这个时候你再加上smallest配置也没用了，因为此时offset是合法的，不会再被修正了，需要手工或用工具改重置offset



##SimpleConsumer##
另一种是SimpleConsumer，名字起的，以为是简单的接口，其实是low-level consumer，更复杂的接口

参考，https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+SimpleConsumer+Example

什么时候用这个接口?

Read a message multiple times
Consume only a subset of the partitions in a topic in a process
Manage transactions to make sure a message is processed once and only once


当然用这个接口是有代价的，即partition,broker,offset对你不再透明，需要自己去管理这些，并且还要handle broker leader的切换，很麻烦
所以不是一定要用，最好别用

You must keep track of the offsets in your application to know where you left off consuming.
You must figure out which Broker is the lead Broker for a topic and partition
You must handle Broker leader changes
使用SimpleConsumer的步骤：

Find an active Broker and find out which Broker is the leader for your topic and partition
Determine who the replica Brokers are for your topic and partition
Build the request defining what data you are interested in
Fetch the data
Identify and recover from leader changes
首先，你必须知道读哪个topic的哪个partition
然后，找到负责该partition的broker leader，从而找到存有该partition副本的那个broker
再者，自己去写request并fetch数据
最终，还要注意需要识别和处理broker leader的改变



##Kafka Producer接口##
参考，

https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+Example

http://kafka.apache.org/08/configuration.html , 0.8版本，关于producer，consumer，broker所有的配置



因为Producer相对于consumer比较简单，直接看代码，需要注意的点

1. 配置参数，详细参考上面链接
    1.1 metadata.broker.list， 不同于0.7，不需要给出zk的地址，而是给出一些broker地址，不用全部，这里建议给两个防止一个不可用
          Kafka会自己找到相应topic，partition的leader broker
    1.2 serializer.class，需要给出message的序列化的encoder，这里使用的是简单的StringEncoder
          并且对于key还可以单独的设定，"key.serializer.class"
          注意，除非明确知道message编码，否则不要直接使用StringEncoder，
          因为源码中的逻辑是如果没有在初始化时指定编码会默认按UTF8转码，会导致乱码
          所以不明确的时候，不要指定serializer.class，默认的encoder逻辑是直接将byte[]放入broker，不会做转码
    1.3 partitioner.class，可以不设置，默认就是random partition，当然这里可以自定义，如何根据key来选择partition
    1.4 request.required.acks， 是否要求broker给出ack，如果不设置默认是'fire and forget'， 会丢数据
          默认为0，即和0.7一样，发完不会管是否成功，lowest latency but the weakest durability
          1, 等待leader replica的ack，否则重发，折中的方案，当leader在同步数据前dead，会丢数据
          -1，等待all in-sync replicas的ack，只要有一个replica活着，就不会丢数据
    1.5 producer.type，
         sync，单条发送
         async，buffer一堆请求后，再一起发送
         如果不是对丢数据非常敏感，请设为async，因为对throughput帮助很大，但是当client crash时，会丢数据
    1.6 compression.codec
         支持"none", "gzip" and "snappy"
         可以通过，compressed.topics，来指定压缩的topic

    当producer.type选择async的时候，需要关注如下配置
    queue.buffering.max.ms (5000), 最大buffer数据的时间，默认是5秒
    batch.num.messages (200), batch发送的数目，默认是200，producer会等待buffer的messages数目达到200或时间超过5秒，才发送数据
    queue.buffering.max.messages (10000), 最多可以buffer的message数目，超过要么producer block或把数据丢掉
    queue.enqueue.timeout.ms (-1), 默认是-1，即达到buffer最大meessage数目时，producer会block
                                                       设为0，达到buffer最大meessage数目时会丢掉数据



从producer，broker，consumer的角度，分别看看
a. Producer到broker

    把request.required.acks设为1，丢会重发，丢的概率很小

b. Broker

    b.1 对于broker，落盘的数据，除非磁盘坏了，不会丢的

    b.2 对于内存中没有flush的数据，broker重启会丢
        可以通过log.flush.interval.messages和log.flush.interval.ms来配置flush间隔，interval大丢的数据多些，小会影响性能
        但在0.8版本，可以通过replica机制保证数据不丢，代价就是需要更多资源，尤其是磁盘资源，kafka当前支持GZip和Snappy压缩，来缓解这个问题
        是否使用replica取决于在可靠性和资源代价之间的balance

c. Consumer

    和其他的平台不同的是，其实Kafka真正比较复杂的是consumer，Kafka有两种consumer

    c.1 High-level consumer

    这个使用比较简单，已经封装了对partition和offset的管理，默认是会定期自动commit offset，这样可能会丢数据的，因为consumer可能拿到数据没有处理完crash
    之前我们在0.7上是使用这个接口的，为了保证不丢数据，把自动commit关掉，consumer处理完所有数据，再手动commit，这样丢数据的概率比较小

    对于storm，没法这样做，因为spout是会预读大量数据的，当然只要spout线程不crash，也是可以保证这些数据基本不会丢失（通过storm的acker机制）
    但如果spout线程crash，就会丢数据
    所以High-level接口的特点，就是简单，但是对kafka的控制不够灵活

    c.2 Simple consumer，low-level

    这套接口比较复杂的，你必须要考虑很多事情，优点就是对Kafka可以有完全的控制
    You must keep track of the offsets in your application to know where you left off consuming.
    You must figure out which Broker is the lead Broker for a topic and partition
    You must handle Broker leader changes

    在考虑如何将storm和kafka结合的时候，有一些开源的库，基于high-level和low-level接口的都有

    我比较了一下，还是kafka官方列的storm-kafka-0.8-plus比较靠谱些
    这个库是基于simple consumer接口实现的，看着挺复杂，所以我先读了遍源码，收获挺大，除了发现我自己代码的问题，还学到些写storm应用的技巧呵呵

    这个库会自己管理spout线程和partition之间的对应关系和每个partition上的已消费的offset(定期写到zk)
    并且只有当这个offset被storm ack后，即成功处理后，才会被更新到zk，所以基本是可以保证数据不丢的
    即使spout线程crash，重启后还是可以从zk中读到对应的offset

So, 结论就是kafka和storm的结合可靠性还是可以的，你真心不想丢数据，还是可以做到的
Kafka只是能保证at-least once逻辑，即数据是可能重复的，这个在应用上需要可以容忍
当然通过Storm transaction也是可以保证only once逻辑的，但是代价比较大，后面如果有这样的需求可以继续深入调研一下
对于kafka consumer，一般情况下推荐使用high-level接口，最好不要直接使用low-level，太麻烦

当前其实Kafka对consumer的设计不太到位，high-level太不灵活，low-level又太难用，缺乏一个medium-level
所以在0.9中consumer会被重新design，https://cwiki.apache.org/confluence/display/KAFKA/Consumer+Client+Re-Design