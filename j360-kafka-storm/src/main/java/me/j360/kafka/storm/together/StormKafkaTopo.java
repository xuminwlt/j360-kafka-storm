package me.j360.kafka.storm.together;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import scala.collection.mutable.HashMap;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;
import storm.kafka.bolt.KafkaBolt;

/**
 * Package: me.j360.kafka.storm
 * User: min_xu
 * Date: 16/7/22 下午2:06
 * 说明：
 */
public class StormKafkaTopo {
    public static void main(String[] args) throws Exception {
        // 配置Zookeeper地址
        BrokerHosts brokerHosts = new ZkHosts("172.16.10.113:2181");
        // 配置Kafka订阅的Topic，以及zookeeper中数据节点目录和名字
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, "topic_send", "/zkkafkaspout" , "kafkaspout");

        // 配置KafkaBolt中的kafka.broker.properties
        Config conf = new Config();
        HashMap map = new HashMap();
        // 配置Kafka broker地址
        map.put("metadata.broker.list", "172.16.10.113:9092");
        // serializer.class为消息的序列化类
        map.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put("kafka.broker.properties", map);
        // 配置KafkaBolt生成的topic
        conf.put("topic", "topic_send_bolt");

        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig));
        builder.setBolt("bolt", new SenqueceBolt()).shuffleGrouping("spout");
        //builder.setBolt("kafkabolt", new KafkaBolt<String, Integer>()).shuffleGrouping("bolt");

        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("Topo", conf, builder.createTopology());
            Utils.sleep(100000);
            cluster.killTopology("Topo");
            cluster.shutdown();
        }
    }
}