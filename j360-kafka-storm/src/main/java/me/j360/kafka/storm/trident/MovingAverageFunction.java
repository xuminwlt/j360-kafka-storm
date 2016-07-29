package me.j360.kafka.storm.trident;

import backtype.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:44
 * 说明：
 */
public class MovingAverageFunction extends
        BaseFunction {
    private static final Logger LOG = LoggerFactory.getLogger(BaseFunction.class);
    private EWMA ewma;
    private EWMA.Time emitRatePer;
    public MovingAverageFunction(EWMA ewma,EWMA.Time emitRatePer){
        this.ewma = ewma;
        this.emitRatePer = emitRatePer;
    }
    public void execute(TridentTuple tuple,
                        TridentCollector collector) {
        this.ewma.mark(tuple.getLong(0));
        LOG.debug("Rate: {}",this.ewma.getAverageRatePer(this.emitRatePer));
        collector.emit(new Values(this.ewma.getAverageRatePer(this.emitRatePer)));
    }
}
