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

import java.util.HashMap;
import java.util.Map;


public class DateCountBolt extends BaseBasicBolt {

    Logger logger = LoggerFactory.getLogger(DateCountBolt.class);
    private Map<String,Long> map = new HashMap<String,Long>();

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void execute(Tuple input,BasicOutputCollector collector) {
        String str = input.getString(0);
        if (!map.containsKey(str)) {
            map.put(str, 1l);
        } else {
            Long c = map.get(str) + 1;
            map.put(str, c);
            logger.info("count:"+c);
        }
    }

    public void declareOutputFields(
            OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("date","count"));

    }

}
