package me.j360.kafka.storm.tridentapi;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;

/**
 * Package: me.j360.kafka.storm.tridentapi
 * User: min_xu
 * Date: 16/8/3 下午8:15
 * 说明：
 流的合并操作，是指根据两个流的关联条件将两个流合并成一个流，然后在进行后面的处理操作，
 如果使用Spout和Bolt这种编程模型的话写起来会比较困难和繁琐，因为要设置缓冲区来保存第一次过来的数据，
 后续还要进行两者的比较，使用Trident应用起来比较方便，对原来的编程模型进行了一定的抽象。代码实例：

 需求：
 两个spout： spout1:里面的数据是 name ,id 和tel,
 spout2是sex 和id；
 首先对spout1进行过滤，过滤掉不是186的电话号码，然后显示
 然后根据将过滤后的stream和spout2进行合并
 */
public class TridentTopylogy {

    static FixedBatchSpout spout = new FixedBatchSpout(new Fields("name", "idName", "tel"),
            3, new Values("Jack", "1", "186107"), new Values("Tome", "2", "1514697"), new Values(
            "Lay", "3", "186745"), new Values("Lucy", "4", "1396478"));

    static FixedBatchSpout spout2 = new FixedBatchSpout(new Fields("sex", "idSex"),
            3, new Values("Boy", "1"), new Values("Boy", "2"), new Values(
            "Gril", "3"), new Values("Gril", "4"));

    public static void main(String[] args) {
        //设置是否循环
        spout.setCycle(false);
        //构建Trident的Topo
        TridentTopology topology = new TridentTopology();
        //定义过滤器： 电话号码不是 186开头的过滤
        FilelterTest ft = new FilelterTest();
        //定义方法 用来显示过滤后的数据
        FunctionTest function = new FunctionTest();
        //根据spout构建第一个Stream
        Stream st = topology.newStream("sp1", spout);
        //对第一个Stream数据显示。
        Stream st_1 = st.each(new Fields("name", "idName", "tel"), function, new Fields("out_name", "out_idName", "out_tel"));
        //根据第二个Spout构建Stream，为了测试join用
        Stream st2 = topology.newStream("sp2", spout2);
        /**
         * 开始Join  st和st2这两个流，类比sql中join的话是：
         * st join st2 on  joinFields1 =  joinFields2
         * 需要注意的是以st为数据基础
         * topology.join(st, new Fields("idName"), st2, new Fields("idSex"), new Fields("id","name","tel","sex"))
         * 那么结果将是以spout为数据基础，结果会将上面的4个数据信息全部打出
         */
        Stream st3 = topology.join(st, new Fields("idName"), st2, new Fields("idSex"), new Fields("Res_id", "Res_name", "Res_tel", "Res_sex"));
        //创建一个方法，为了显示合并和过滤后的结果
        FunctionTest3 t3 = new FunctionTest3();
        st3.each(new Fields("Res_id", "Res_name", "Res_tel", "Res_sex"), ft).each(new Fields("Res_id", "Res_name", "Res_tel", "Res_sex"), t3, new Fields("out1_id", "out1_name", "ou1t_tel", "out1_sex"));


        Config cf = new Config();
        cf.setNumWorkers(2);
        cf.setNumAckers(0);
        cf.setDebug(false);

        LocalCluster lc = new LocalCluster();
        lc.submitTopology("TestTopo", cf, topology.build());
    }

}
