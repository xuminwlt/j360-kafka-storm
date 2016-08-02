package me.j360.kafka.base.util;

/**
 * Package: me.j360.kafka.base.util
 * User: min_xu
 * Date: 16/8/2 下午5:02
 * 说明：
 */
public class ArrayUtil {
    public static void copy(byte[] original, byte[] copy, int start, int newLength) {
        System.arraycopy(original, start, copy, 0, Math.min(original.length, newLength));

    }

    public static void replace(byte[] original, byte[] repl, int index) {
        int len = original.length;
        for (int i = 0; i < repl.length; i++) {
            if (index + i >= len) {
                return;
            }
            original[index + i] = repl[i];
        }


    }

}

