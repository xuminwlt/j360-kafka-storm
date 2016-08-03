package me.j360.kafka.storm.newversion;

/**
 * Package: me.j360.kafka.storm.simple
 * User: min_xu
 * Date: 16/7/26 上午11:03
 * 说明：
 */
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleBolt extends BaseBasicBolt {

    Logger logger = LoggerFactory.getLogger(SimpleBolt.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void execute(Tuple input,BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);
            if (msg != null){
                logger.info("msg:"+msg);
                collector.emit(new Values(msg + "msg is processed!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void declareOutputFields(
            OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));

    }

}
