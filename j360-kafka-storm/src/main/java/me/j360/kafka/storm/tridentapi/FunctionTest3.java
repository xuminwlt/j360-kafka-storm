package me.j360.kafka.storm.tridentapi;

import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.tridentapi
 * User: min_xu
 * Date: 16/8/3 下午8:15
 * 说明：
 [1, Jack, 186107, Boy]
 [3, Lay, 186745, Gril]
 */
public class FunctionTest3 extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {

        System.out.println(tuple.getValues());
    }

}