package me.j360.kafka.storm.loganalysis;

import me.j360.kafka.storm.Contants;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Package: me.j360.kafka.storm.loganalysis
 * User: min_xu
 * Date: 16/8/3 下午3:21
 * 说明：
 */
public class KafkalogMockProducter implements Runnable {

    Producer<String, String> producer;

    public KafkalogMockProducter(){
        Properties props = new Properties();
        props.put("bootstrap.servers", Contants.kafkaUrl);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);


    }

    public void run() {

        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String key = "app:main";
            String log = "2016-07-25 17:52:22,632 INFO [main] o.s.b.f.x.XmlBeanDefinitionReader [XmlBeanDefinitionReader.java:317] Loading XML bean definitions from class path resource [spring/search-mongo-context.xml]";
            producer.send(new ProducerRecord<String, String>(Contants.TOPIC.Send, key, log));
        }

    }

    public void close(){
        producer.close();
    }

}
