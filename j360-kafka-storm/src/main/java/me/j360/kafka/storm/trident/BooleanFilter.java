package me.j360.kafka.storm.trident;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:43
 * 说明：
 */
public class BooleanFilter extends BaseFilter {
    public boolean isKeep(TridentTuple tuple) {
        return tuple.getBoolean(0);
    }
}
