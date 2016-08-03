package me.j360.kafka.storm.simpletrident;

import storm.trident.tuple.TridentTuple;

import java.io.Serializable;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:42
 * 说明：
 */
public interface MessageMapper extends Serializable {
    public String toMessageBody(TridentTuple tuple);
}
