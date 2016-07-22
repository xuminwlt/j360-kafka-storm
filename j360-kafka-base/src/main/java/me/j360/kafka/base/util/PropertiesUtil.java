package me.j360.kafka.base.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2016/1/6.
 */
public class PropertiesUtil {

    static private String driver = null;
    static private String user = null;
    static private String password = null;

    static private String url_g = null;
    static private String url_d0 = null;
    static private String url_d1 = null;
    static private int initialSize;
    static private int minIdle;
    static private int maxActive;


    static private String redisUrl = null;
    static private String redisPassword = null;
    static private int redisPort;
    static private int redisTimeout;

    static private String kafkaZookeeperConnect = null;
    static private String kafkaGroupId = null;
    static private String kafkaBrokerList = null;
    static private String kftel = null;

    static {
        loads();
    }



    synchronized static public void loads() {
        if (driver == null || url_g == null || user == null || password == null) {
            InputStream is = PropertiesUtil.class.getResourceAsStream("/kafka.properties");
            Properties iotproperties = new Properties();
            try {
                iotproperties.load(is);

                driver = iotproperties.getProperty("jdbc.mysql.driver").toString();
                url_g = iotproperties.getProperty("jdbc.mysql.url.g").toString();
                url_d0 = iotproperties.getProperty("jdbc.mysql.url.d0").toString();
                url_d1 = iotproperties.getProperty("jdbc.mysql.url.d1").toString();
                user = iotproperties.getProperty("jdbc.mysql.username").toString();
                password = iotproperties.getProperty("jdbc.mysql.password").toString();
                initialSize = Integer.parseInt(iotproperties.getProperty("jdbc.mysql.InitialSize"));
                minIdle = Integer.parseInt(iotproperties.getProperty("jdbc.mysql.MinIdle"));
                maxActive = Integer.parseInt(iotproperties.getProperty("jdbc.mysql.MaxActive"));

                kafkaZookeeperConnect = iotproperties.getProperty("kafka.zookeeper.connect").toString();
                kafkaGroupId = iotproperties.getProperty("kafka.group.id").toString();
                kafkaBrokerList = iotproperties.getProperty("kafka.metadata.broker.list").toString();


                redisPort = Integer.parseInt(iotproperties.getProperty("redis.port"));
                redisUrl = iotproperties.getProperty("redis.url").toString();
                redisPassword=iotproperties.getProperty("redis.password").toString();
                redisTimeout = Integer.parseInt(iotproperties.getProperty("redis.timeout"));

                try {
                    kftel = iotproperties.getProperty("syj.tel").toString();
                }catch (Exception e) {
                }

            } catch (Exception e) {
                System.err.println("不能读取属性文件. " + "请确保iot.properties在CLASSPATH指定的路径中");
            }
        }
    }

    public static String getDriver() {
        if (driver == null)
            loads();
        return driver;
    }

    public static String getUrl() {
        if (url_g == null)
            loads();
        return url_g;
    }

    public static String getUser() {
        if (user == null)
            loads();
        return user;
    }

    public static String getPassword() {
        if (password == null)
            loads();
        return password;
    }


    public static String getKftel() {
        return kftel;
    }
    public static String getUrl_d0() {
        return url_d0;
    }

    public static String getUrl_d1() {
        return url_d1;
    }


    public static String getUrl_g() {
        return url_g;
    }

    public static int getInitialSize() {
        return initialSize;
    }

    public static int getMinIdle() {
        return minIdle;
    }

    public static int getMaxActive() {
        return maxActive;
    }

    public static String getRedisUrl() {
        return redisUrl;
    }

    public static int getRedisPort() {
        return redisPort;
    }

    public static String getRedisPassword() {
        return redisPassword;
    }

    public static int getRedisTimeout() {
        return redisTimeout;
    }

    public static String getKafkaZookeeperConnect() {
        return kafkaZookeeperConnect;
    }

    public static String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public static String getKafkaBrokerList() {
        if (kafkaBrokerList == null)
            loads();
        return kafkaBrokerList;
    }

    public static void main(String[] ss) {
        System.out.println(getUrl_d1());
    }


}
