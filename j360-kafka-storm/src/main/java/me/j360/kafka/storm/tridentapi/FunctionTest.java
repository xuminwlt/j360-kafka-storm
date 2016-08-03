package me.j360.kafka.storm.tridentapi;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.tridentapi
 * User: min_xu
 * Date: 16/8/3 下午8:12
 * 说明：
 *
 过滤后数据：============[Jack, 1, 186107]
 过滤后数据：============[Tome, 2, 1514697]
 过滤后数据：============[Lay, 3, 186745]
 [1, Jack, 186107, Boy]
 [3, Lay, 186745, Gril]
 过滤后数据：============[Lucy, 4, 1396478]

 */
public class FunctionTest extends BaseFunction {

    @Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String getName = tuple.getString(0);
        String getid = tuple.getString(1);
        String getTel = tuple.getString(2);
        System.out.println("过滤后数据：============"+tuple.getValues());
        collector.emit(new Values(getName,getid,getTel));
    }

}