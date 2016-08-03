package me.j360.kafka.storm.simpletrident;

import storm.trident.tuple.TridentTuple;

import java.util.Date;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:41
 * 说明：
 */
public class NotifyMessageMapper implements MessageMapper {
    public String toMessageBody(TridentTuple tuple) {
        StringBuilder sb = new StringBuilder();
        sb.append("On " + new Date(tuple.getLongByField("timestamp")) + " ");
        sb.append("the application\"" + tuple.getStringByField("logger") + "\"");
        sb.append("changed alert statebased on a threshold of "
                +tuple.getDoubleByField("threshold") + ".\n");
        sb.append("The last value was" + tuple.getDoubleByField("average") + "\n");
        sb.append("The last message was \""+ tuple.getStringByField("message") + "\"");
        return sb.toString();
    }
}
