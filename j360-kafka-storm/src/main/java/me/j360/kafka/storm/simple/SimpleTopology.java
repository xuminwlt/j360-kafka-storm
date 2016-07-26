package me.j360.kafka.storm.simple;

/**
 * Package: me.j360.kafka.storm.simple
 * User: min_xu
 * Date: 16/7/26 上午11:04
 * 说明：
 */
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;

public class SimpleTopology {


    public static void main(String[] args) {
        try {
            // 实例化TopologyBuilder类。
            TopologyBuilder topologyBuilder = new TopologyBuilder();
            // 设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
            topologyBuilder.setSpout("SimpleSpout", new SimpleSpout(), 1);
            // 设置数据处理节点并分配并发数。指定该节点接收喷发节点的策略为随机方式。
            topologyBuilder.setBolt("SimpleBolt", new SimpleBolt(), 3).shuffleGrouping("SimpleSpout");
            Config config = new Config();
            config.setDebug(true);
            if (args != null && args.length > 0) {
                config.setNumWorkers(1);
                StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
            } else {
                // 这里是本地模式下运行的启动代码。
                config.setMaxTaskParallelism(1);
                LocalCluster cluster = new LocalCluster();
                cluster.submitTopology("simple", config, topologyBuilder.createTopology());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
