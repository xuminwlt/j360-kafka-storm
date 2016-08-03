package me.j360.kafka.storm.loganalysis;

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
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReadLogBolt extends BaseBasicBolt {

    Logger logger = LoggerFactory.getLogger(ReadLogBolt.class);

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void execute(Tuple input,BasicOutputCollector collector) {
        try {
            String msg = input.getString(0);
            if (msg != null){
                logger.info("msg:"+msg);
                String date = StringUtils.left(msg,10);
                String level = StringUtils.substring(msg,24,37);
                String info = StringUtils.substring(msg,39);

                collector.emit(new Values(date,level,info));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void declareOutputFields(
            OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("date","level"));

    }

}
