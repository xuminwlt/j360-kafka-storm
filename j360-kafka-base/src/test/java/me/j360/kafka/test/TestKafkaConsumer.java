package me.j360.kafka.test;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import me.j360.kafka.base.Contants;
import me.j360.kafka.base.util.ArrayUtil;
import me.j360.kafka.base.util.ByteUtil;
import me.j360.kafka.base.util.KafkaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Package: PACKAGE_NAME
 * User: min_xu
 * Date: 16/7/21 下午8:46
 * 说明：
 */
public class TestKafkaConsumer {
    private static Logger logger = LoggerFactory.getLogger(TestKafkaConsumer.class);

    public static void main(String args[]){
        kafka();
    }

    public static void kafka(){
        Properties props = KafkaUtil.getCustomerPro();
        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(Contants.TOPIC.Send, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        KafkaStream<byte[], byte[]> stream = consumerMap.get(Contants.TOPIC.Send).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
//        DaoHelp daoHelp = new DaoHelp();

        while (it.hasNext()) {
            try {
                byte[] bf = it.next().message();
                logger.debug("read kafka:"+ ByteUtil.byteToString(bf));
                //发送到netty的api 暂时不加入netty 注释掉
                //logger.debug("DefaultChannelGroup size: " + ChannelHanlderGroup.getDefaultChannelGroup().size());
                //ChannelHanlderGroup.getDefaultChannelGroup().writeAndFlush(Unpooled.copiedBuffer(bf), new LanTianChannelMatcher(ByteUtil.bytesToHex(deviceId)));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
