package me.j360.kafka.storm.newversion;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import me.j360.kafka.storm.Contants;
import storm.kafka.BrokerHosts;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
import storm.kafka.trident.*;
import storm.kafka.trident.mapper.FieldNameBasedTupleToKafkaMapper;
import storm.kafka.trident.selector.DefaultTopicSelector;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

import java.util.Properties;

/**
 * Package: me.j360.kafka.storm.newversion
 * User: min_xu
 * Date: 16/8/2 下午7:47
 * 说明：
 */
public class StormKafkaTridentSpoutTopology {

    public static void main(String args[]) throws AlreadyAliveException, InvalidTopologyException {
        BrokerHosts zk = new ZkHosts(Contants.zkUrl);
        TridentKafkaConfig spoutConf = new TridentKafkaConfig(zk, Contants.TOPIC.Send);
        spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());

        Fields fields = new Fields("word", "count");
        FixedBatchSpout spout = new FixedBatchSpout(fields, 4,
                new Values("storm", "1"),
                new Values("trident", "1"),
                new Values("needs", "1"),
                new Values("javadoc", "1")
        );
        spout.setCycle(true);


        TridentTopology topology = new TridentTopology();
        Stream stream = topology.newStream("spout1", spout);

        TridentKafkaStateFactory stateFactory = new TridentKafkaStateFactory()
                .withKafkaTopicSelector(new DefaultTopicSelector("test"))
                .withTridentTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("word", "count"));
        stream.partitionPersist(stateFactory, fields, new TridentKafkaUpdater(), new Fields());

        Config conf = new Config();
        //set producer properties.
        Properties props = new Properties();
        props.put("metadata.broker.list", Contants.kafkaUrl);
        props.put("request.required.acks", "1");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        conf.put(TridentKafkaState.KAFKA_BROKER_PROPERTIES, props);
        //StormSubmitter.submitTopology("kafkaTridentTest", conf, topology.build());
        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);
            StormSubmitter.submitTopology(args[0], conf, topology.build());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("kafkaTridentTest", conf, topology.build());
            Utils.sleep(100000);
            cluster.killTopology("kafkaTridentTest");
            cluster.shutdown();
        }
    }
}
