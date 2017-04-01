package com.crp.collectUtil;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ms-util - com.crp.md5
 *
 * @author superChen
 * @create 2017-01-13 10:36
 */
@Slf4j
public class Md5EncodeUtil {

    /**
     *
     * @param Md5Map 加密的参数
     * @param key    加密的秘钥
     * @param keySet 读取参数的编码方式
     * @return
     */
    public static String createMD5Map(Map<String, String> Md5Map, String key,String keySet) {
        if(keySet == null || keySet.isEmpty()){
            keySet = "utf-8";
        }
        try {
            if(Md5Map == null) {
                log.info("Invalid map\n");
                return null;
            } else if(key == null) {
                log.info("Invalid key\n");
                return null;
            } else if(key.length() >= 7 && key.length() <= 32) {
                StringBuffer e = new StringBuffer();
                Set set = Md5Map.keySet();
                int i = 0;
                String[] array = new String[set.size()];

                for(Iterator md = set.iterator(); md.hasNext(); ++i) {
                    String byteDigest = (String)md.next();
                    array[i] = byteDigest + "=" + (String)Md5Map.get(byteDigest);
                }

                Arrays.sort(array);

                for(int var9 = 0; var9 < array.length; ++var9) {
                    e.append("|" + array[var9]);
                }

                e.append(key);
                e.deleteCharAt(0);
                MessageDigest var10 = MessageDigest.getInstance("MD5");
                var10.update(e.toString().getBytes(keySet));
                byte[] var11 = var10.digest();
                return String.valueOf(byte2Hex(var11));
            } else {
                log.info("out of key len range\n");
                return null;
            }
        } catch (Exception var8) {
            System.out.println(var8);
            return null;
        }
    }
    public static String byte2Hex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer();
        byte[] var2 = buf;
        int var3 = buf.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            if(b == 0) {
                strbuf.append("00");
            } else if(b == -1) {
                strbuf.append("ff");
            } else {
                String str = Integer.toHexString(b).toLowerCase();
                if(str.length() == 8) {
                    str = str.substring(6, 8);
                } else if(str.length() < 2) {
                    str = "0" + str;
                }

                strbuf.append(str);
            }
        }

        return strbuf.toString();
    }
}
