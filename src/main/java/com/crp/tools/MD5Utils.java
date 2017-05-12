package com.crp.tools;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * ms-util - com.crp.tools
 * md5加密工具类，包含16/32，以及大小写加密，字符串以及文件加密
 *
 * @author superChen
 * @create 2017-05-02 11:20
 */
@Slf4j
public class MD5Utils {

    //定义加密字符 大写
    protected static char hexDigits1[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};
    //定义加密字符 小写
    protected static char hexDigits2[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    protected static MessageDigest messageDigest = null;

    static {
        try {
            // 拿到一个MD5转换器（如果想要SHA1参数换成”SHA1”）
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.info(MD5Utils.class.getName() + "初始化失败，MessageDigest不支持MD5Util.");
        }
    }

    private static String bufferToHex(byte bytes[], int m, int n, boolean isCapital) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer, isCapital);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer, boolean isCapital) {

        char[] hexDigits = null;
        if (isCapital) {
            hexDigits = hexDigits1;
        } else {
            hexDigits = hexDigits2;
        }

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[], boolean isCapital) {
        return bufferToHex(bytes, 0, bytes.length, isCapital);
    }

    /**
     * 字符串加密（默认32位，16位加密截取8-24位即可）
     *
     * @param s         需要加密的字符
     * @param isCapital (ture-大写加密，false-小写加密)
     * @return
     */
    public static String strMd5(String s, boolean isCapital) {
        try {
            byte[] strTemp = s.getBytes("UTF-8");
            messageDigest.update(strTemp);
            byte[] resultByteArray = messageDigest.digest();
            return bufferToHex(resultByteArray, isCapital);
        } catch (Exception e) {
            log.info("异常！", e);
            return null;
        }
    }


    /**
     * 文件的md5加密 （默认32位，16位加密截取8-24位即可）
     *
     * @param inputFile (d:/1.txt)
     * @param isCapital (ture-大写加密，false-小写加密)
     * @return
     * @throws IOException
     */
    public static String fileMD5(String inputFile, boolean isCapital) {
        // 缓冲区大小（这个可以抽出一个参数）
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        String result = "";
        try {
            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);
            // read的过程中进行MD5处理，直到读完文件
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0) ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] resultByteArray = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            result = bufferToHex(resultByteArray, isCapital);
        } catch (IOException e) {
            log.error("IO操作异常", e);
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
                log.info("MD5加密异常", e);
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {
                log.info("文件读取异常", e);
            }
        }
        return result;
    }

//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
//        String ss = "123456";
//        System.out.println(strMd5(ss,true));
//    }
}




