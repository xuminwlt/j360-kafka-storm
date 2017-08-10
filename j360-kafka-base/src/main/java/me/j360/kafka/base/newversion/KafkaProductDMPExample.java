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
        props.put("bootstrap.servers", Contants.testKafkaUrl);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String sss = "{\"targetId\":100,\"type\":\"play\",\"duration\":100,\"playDuration\":90,\"view\":\"recommend\",\"count\":1,\"sourceIp\":\"\",\"uid\":96630108742967296,\"clientAgent\":\"{\\\"appName\\\":\\\"b35d7751bc8ef46b87892a6abcb8f8\\\",\\\"version\\\":\\\"3.0.4\\\",\\\"buildVersion\\\":2,\\\"osNumber\\\":1,\\\"osVersion\\\":\\\"9.0.2\\\",\\\"deviceModel\\\":\\\"6S\\\",\\\"deviceUUID\\\":\\\"AAAAAAA\\\",\\\"net\\\":null,\\\"channel\\\":null}\",\"timestamp\":1502118468307}";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        for(int i = 0; i < 1000; i++)
            producer.send(new ProducerRecord<String, String>(Contants.TOPIC.DMP, Integer.toString(0), sss));

        producer.close();
    }
}
