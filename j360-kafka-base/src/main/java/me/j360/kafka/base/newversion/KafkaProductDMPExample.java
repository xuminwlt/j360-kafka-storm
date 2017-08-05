package me.j360.kafka.base.newversion;

import me.j360.kafka.base.Contants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * Package: me.j360.kafka.base.newversion
 * User: min_xu
 * Date: 16/8/2 下午5:51
 * 说明：
 */
public class KafkaProductDMPExample {

    public static void main(String args[]){
        Properties props = new Properties();
        props.put("bootstrap.servers", Contants.kafkaUrl);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String sss = "{\"uid\":3,\"device_id\":\"068b746ed4620d25e26055a9f804385f\",\"vp_id\":5,\"event_time\":1430204612405,\"os_type\":\"Android\",\"play_count\":6}";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 1000; i++)
            producer.send(new ProducerRecord<String, String>(Contants.TOPIC.DMP, Integer.toString(0), sss));

        producer.close();
    }
}
