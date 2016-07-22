package me.j360.kafka.base.util;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by Administrator on 2015/8/31.
 */
public class KafkaUtil {

    private static  Properties prop_customer = null;
    public static Properties getCustomerPro(){
        if(prop_customer==null) {
            prop_customer=new Properties();
            prop_customer.put("zookeeper.connect", PropertiesUtil.getKafkaZookeeperConnect());
            prop_customer.put("group.id", PropertiesUtil.getKafkaGroupId());
            prop_customer.put("zookeeper.session.timeout.ms", "6000");
            prop_customer.put("zookeeper.sync.time.ms", "2000");
            prop_customer.put("auto.commit.interval.ms", "1000");

            prop_customer.put("zookeeper.connection.timeout.ms","10000");

            //每次最少接收的字节数，默认是1
            //props.put("fetch.min.bytes", "1024");
            //每次最少等待时间，默认是100
            //props.put("fetch.wait.max.ms", "600000");
        }
        return prop_customer;
    }




    private   static ProducerConfig producerConfig =null;
    private   static Producer<String,byte[]> producer =null;

    public  static ProducerConfig getProducerConfig(){
        if (producerConfig==null){
            Properties properties = new Properties();
            properties.put("metadata.broker.list", PropertiesUtil.getKafkaBrokerList());
          //  properties.put("serializer.class","kafka.serializer.StringEncoder");
            properties.put("serializer.class","kafka.serializer.DefaultEncoder");
            //properties.put("metadata.broker.list", "172.16.10.108:9092");

            producerConfig = new ProducerConfig(properties);
        }
        return  producerConfig;
    }
    public static Producer<String,byte[]> getProducer(){
        if(producer==null){
            producer = new Producer<String, byte[]>( getProducerConfig());
       }
        return producer;
    }

    public static void send(String topic,byte[] dd){
        Producer<String,byte[]> producer =getProducer();
        KeyedMessage<String, byte[]> message =new KeyedMessage<String, byte[]>(topic,dd );

        producer.send(message);


    }
}
