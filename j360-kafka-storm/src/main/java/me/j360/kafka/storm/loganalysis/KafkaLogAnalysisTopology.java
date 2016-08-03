package me.j360.kafka.storm.loganalysis;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import me.j360.kafka.storm.Contants;
import storm.kafka.*;
import storm.kafka.bolt.KafkaBolt;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Package: me.j360.kafka.storm.loganalysis
 * User: min_xu
 * Date: 16/8/3 下午3:19
 * 说明：
 */
public class KafkaLogAnalysisTopology {


    public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
        //mock producter
        kafkaLogMockProducter();

        //storm read kafka log
        //Spoutconfig is an extension of KafkaConfig that supports additional fields with ZooKeeper connection info and for controlling behavior specific to KafkaSpout
        BrokerHosts hosts = new ZkHosts("172.16.10.113:2181");
        SpoutConfig spoutConfig = new SpoutConfig(hosts, Contants.TOPIC.Send, "/" + Contants.TOPIC.Send, UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringKeyValueScheme());

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", new KafkaSpout(spoutConfig));
        builder.setBolt("bolt", new ReadLogBolt()).shuffleGrouping("spout");
        builder.setBolt("count", new DateCountBolt()).shuffleGrouping("bolt");


        Config conf = new Config();
        //set producer properties.
        Properties props = new Properties();
        props.put("metadata.broker.list", Contants.kafkaUrl);
        props.put("request.required.acks", "1");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put(KafkaBolt.KAFKA_BROKER_PROPERTIES, props);
        conf.setDebug(true);

        if (args != null && args.length > 0) {
            conf.setNumWorkers(1);
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("kafkaspoltLog", conf, builder.createTopology());
            Utils.sleep(100000);
            cluster.killTopology("kafkaspoltLog");
            cluster.shutdown();
        }
    }


    public static void kafkaLogMockProducter(){
        Executor executor = Executors.newFixedThreadPool(1);
        executor.execute(new KafkalogMockProducter());
    }
}
