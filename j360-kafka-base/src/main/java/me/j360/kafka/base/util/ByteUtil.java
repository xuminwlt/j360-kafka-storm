package me.j360.kafka.base.util;

/**
 * Package: me.j360.kafka.base.util
 * User: min_xu
 * Date: 16/8/2 下午5:02
 * 说明：
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2015/8/31.
 */
public class ByteUtil {

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static Logger logger = LoggerFactory.getLogger(ByteUtil.class);

    public static int getBit(byte b, int f) {
        return (b >> f) & 0x1;

    }

    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);

    }

    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcdToStr(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * @功能: 10进制串转为BCD码
     * @参数: 10进制串
     * @结果: BCD码
     */
    public static byte[] strToBcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        byte abt[] = new byte[len];
        if (len >= 2) {
            len = len / 2;
        }
        byte bbt[] = new byte[len];
        abt = asc.getBytes();
        int j, k;
        for (int p = 0; p < asc.length() / 2; p++) {
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
                j = abt[2 * p] - '0';
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
                j = abt[2 * p] - 'a' + 0x0a;
            } else {
                j = abt[2 * p] - 'A' + 0x0a;
            }
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
                k = abt[2 * p + 1] - '0';
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
                k = abt[2 * p + 1] - 'a' + 0x0a;
            } else {
                k = abt[2 * p + 1] - 'A' + 0x0a;
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public static String byteArrayToBitString(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        if (bs != null) {
            for (byte b : bs) {
                sb.append(byteToBit(b));
            }

            return sb.toString();

        }

        return null;
    }

    public static String byteToString(byte[] bs) {

        //  return byteToString(bs, "");
        if (bs == null || bs.length < 1) {
            return null;
        }

        StringBuffer ss = new StringBuffer();
        for (byte b : bs) {
            int v = b & 0xFF;
            ss.append(Integer.toString(v));
        }


        return ss.toString();

    }

    public static String byteToString(byte[] bs, String token) {
        if (bs == null || bs.length < 1) {
            return null;
        }
        if (token == null)
            return null;

        StringBuffer ss = new StringBuffer();


        for (byte b : bs) {

            int v = b & 0xFF;

            ss.append(Integer.toString(v)).append(token);
        }
        ss.deleteCharAt(ss.length() - token.length());

        return ss.toString();
    }

//    public static String byteToHexString(byte[] bs, String token) {
//        if (token == null)
//            return null;
//        if (bs == null)
//            return null;
//        if (bs.length == 0) {
//            return null;
//        }
//
//        StringBuffer ss = new StringBuffer();
//        for (byte b : bs) {
//            int v = b & 0xFF;
//            ss.append(Integer.toHexString(v)).append(token);
//        }
//        ss.delete(ss.length() - token.length(), ss.length());
//
//        return ss.toString();
//    }


    public static String byteToHexString(byte[] bs, String token) {
        if (token == null)
            return null;
        if (bs == null)
            return null;
        if (bs.length == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        char[] chars = new char[2];
        for (int i = 0; i < bs.length; i++) {
            int x = 0xFF & bs[i];
            chars[0] = HEX_CHARS[x >>> 4];
            chars[1] = HEX_CHARS[0x0F & x];
            sb.append(String.valueOf(chars)).append(token);

        }
        sb.delete(sb.length() - token.length(), sb.length());
        return sb.toString();

    }


    public static String bytesToHex(byte[] bytes) {
        char[] chars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int x = 0xFF & bytes[i];
            chars[i * 2] = HEX_CHARS[x >>> 4];
            chars[1 + i * 2] = HEX_CHARS[0x0F & x];
        }
        return new String(chars);
    }


    public static String bytesToHexForShow(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        char[] chars = new char[2];
        for (int i = 0; i < bytes.length; i++) {
            int x = 0xFF & bytes[i];
            chars[0] = HEX_CHARS[x >>> 4];
            chars[1] = HEX_CHARS[0x0F & x];
            if (i == 0) {
                sb.append("\n包头序列号版本号: ");
            }
            if (i == 6) {
                sb.append("\n时间标贴:         ");
            }
            if (i == 12) {
                sb.append("\n源地址:           ");
            }
            if (i == 18) {
                sb.append("\n目标地址:         ");
            }
            if (i == 24) {
                sb.append("\n应用数据单元长度: ");
            }
            if (i == 26) {
                sb.append("\n命令:             ");
            }
            if (i == 27) {
                sb.append("\n类型标志符:       ");
            }
            if (i == 28) {
                sb.append("\n信息对象数目:     ");
            }
            if (i == 29) {
                sb.append("\n内容:             ");
            }

            sb.append(String.valueOf(chars));

        }
        return sb.toString();
    }


    public static int byteToint(byte b) {
        return b & 0xff;
    }


    public static int byte2int(byte h, byte l) {
        int targets = (l & 0xff) | ((h << 8) & 0xff00);
        return targets;
    }


    public static byte[] int2byte(int res) {
        byte[] targets = new byte[4];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
        targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
        targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }

    public static int byte2int(byte[] res) {
// 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000

        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;
    }

    public static byte[] intTobyte(int res) {
        byte[] targets = new byte[4];
        targets[0] = (byte) (res & 0xff);
        targets[1] = (byte) ((res >> 8) & 0xff);
        targets[2] = (byte) ((res >> 16) & 0xff);
        targets[3] = (byte) (res >>> 24);
        return targets;
    }


    public static byte[] RevintTobyte(int res) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (res & 0xff);
        targets[2] = (byte) ((res >> 8) & 0xff);
        targets[1] = (byte) ((res >> 16) & 0xff);
        targets[0] = (byte) (res >>> 24);
        return targets;
    }

    public static byte[]  shortTobyte(short res) {
        byte[] targets = new byte[2];
        targets[1] = (byte) ((res >> 8) & 0xff);
        targets[0] = (byte) ((res >> 0 )& 0xff);
        return targets;
    }
    public static byte[] RevshortTobyte(short res) {
        byte[] targets = new byte[2];
        targets[0] = (byte) ((res >> 8) & 0xff);
        targets[1] = (byte) ((res >> 0 )& 0xff);
        return targets;
    }





    //两个字符对应一个字节共8个字节
    public static byte[] IntStringToByte(String sres) {
        if (sres == null || sres.length() == 0) {
            return null;
        }
        int res = Integer.parseInt(sres, 16);
        byte[] targets = new byte[4];
        targets[3] = (byte) (res & 0xff);
        targets[2] = (byte) ((res >> 8) & 0xff);
        targets[1] = (byte) ((res >> 16) & 0xff);
        targets[0] = (byte) (res >>> 24);
        return targets;
    }


    //两个字符对应一个字节
    public static byte[] StringToByte(String res) {
        if (res == null || res.length() == 0) {
            return null;
        }
        byte[] bh;

        if (res.length() % 2 == 1) {
            bh = new byte[res.length() / 2 + 1];
            res = res + "0";
        } else {
            bh = new byte[res.length() / 2];
        }

        char[] cres = res.toCharArray();
        char[] tmp = new char[2];
        int len = cres.length;
        try {
            for (int i = 0; i < len; i = i + 2) {
                tmp[0] = cres[i];
                tmp[1] = cres[i + 1];
                bh[i / 2] = (byte) Integer.parseInt(String.valueOf(tmp), 16);
            }
        } catch (NumberFormatException e) {
            logger.error("解析字符串到整数出错:{" + res + "}" + e.getMessage(), e);
        }
        return bh;
    }


}
