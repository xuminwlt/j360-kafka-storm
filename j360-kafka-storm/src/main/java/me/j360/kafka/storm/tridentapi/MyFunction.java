package me.j360.kafka.storm.tridentapi;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

/**
 * Package: me.j360.kafka.storm.tridentapi
 * User: min_xu
 * Date: 16/7/28 下午11:26
 * 说明：
 * http://storm.apache.org/releases/current/Trident-tutorial.html
 *
 * 一个function收到一个输入tuple后可以输出0或多个tuple，输出tuple的字段被追加到接收到的输入tuple后面。如果对某个tuple执行function后没有输出tuple，则该tuple被过滤（filter），否则，就会为每个输出tuple复制一份输入tuple的副本。假设有如下的function：

 1
 2
 3
 4
 5
 6
 7
 public class MyFunction extends BaseFunction {
 public void execute(TridentTuple tuple, TridentCollector collector) {
 for(int i=0; i < tuple.getInteger(0); i++) {
 collector.emit(new Values(i));
 }
 }
 }
 假设有个叫“mystream”的流(stream)，该流中有如下tuple（ tuple的字段为["a", "b", "c"] ），

 [1, 2, 3]

 [4, 1, 6]

 [3, 0, 8]

 运行下面的代码：

 1
 mystream.each(new Fields("b"), new MyFunction(), new Fields("d")))
 则输出tuple中的字段为["a", "b", "c", "d"]，如下所示

 [1, 2, 3, 0]

 [1, 2, 3, 1]

 [4, 1, 6, 0]
 */
public class MyFunction extends BaseFunction {
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for(int i=0; i < tuple.getInteger(0); i++) {
            collector.emit(new Values(i));
            }
        }
}