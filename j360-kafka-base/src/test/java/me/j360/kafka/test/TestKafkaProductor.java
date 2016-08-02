package me.j360.kafka.test;


import me.j360.kafka.base.Contants;
import me.j360.kafka.base.util.KafkaUtil;

/**
 * Package: PACKAGE_NAME
 * User: min_xu
 * Date: 16/7/21 下午8:46
 * 说明：
 */
public class TestKafkaProductor {

    public static void main(String args[]){
        kafka();
    }

    public static void kafka(){
        KafkaUtil.send(Contants.TOPIC.Send, "kafka test product".getBytes());
        KafkaUtil.send(Contants.TOPIC.Send, "kafka test product".getBytes());
    }
}
