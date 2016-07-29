package me.j360.kafka.storm.trident;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * Package: me.j360.kafka.storm.trident
 * User: min_xu
 * Date: 16/7/29 上午10:45
 * 说明：
 */
public interface Formatter {
    String format(ILoggingEvent event);
}