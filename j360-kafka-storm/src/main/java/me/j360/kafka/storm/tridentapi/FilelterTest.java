package me.j360.kafka.storm.tridentapi;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.tridentapi
 * User: min_xu
 * Date: 16/8/3 下午8:11
 * 说明：
 */
public class FilelterTest  extends BaseFilter {

    @Override
    public boolean isKeep(TridentTuple tuple) {
        String get =tuple.getString(2);
        if(get.startsWith("186")){
            return true;
        }
        return false;
    }
}
