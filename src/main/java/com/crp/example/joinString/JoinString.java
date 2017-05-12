package com.crp.example.joinString;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ms-util - com.crp.example.joinString
 *
 * @author superChen
 * @create 2017-05-12 15:30
 */
@Slf4j
public class JoinString {

    /**
     * +
     */
    public static void testPlus() {
        String s = "";
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            s = s + String.valueOf(i);
        }
        System.out.println(s);
        long te = System.currentTimeMillis();
        log.info("+ cost {} ms", te - ts);
    }

    /**
     * contact
     */
    public static void testConcat() {
        String s = "";
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            s = s.concat(String.valueOf(i));
        }
        System.out.println(s);
        long te = System.currentTimeMillis();
        log.info("concat cost {} ms", te - ts);
    }

    /**
     * join
     */
    public static void testJoin() {
        List<String> list = new ArrayList<String>();
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            list.add(String.valueOf(i));
        }
        String s = StringUtils.join(list, "");
        System.out.println(s);
        long te = System.currentTimeMillis();
        log.info("StringUtils.join cost {} ms", te - ts);
    }

    /**
     * stringBuffer
     */
    public static void testStringBuffer() {
        StringBuffer sb = new StringBuffer();
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            sb.append(String.valueOf(i));
        }
        sb.toString();
        System.out.println(sb.toString());
        long te = System.currentTimeMillis();
        log.info("StringBuffer cost {} ms", te - ts);
    }

    /**
     * stringBuilder
     */
    public static void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        long ts = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            sb.append(String.valueOf(i));
        }
        sb.toString();
        System.out.println(sb.toString());
        long te = System.currentTimeMillis();
        log.info("StringBuilder cost {} ms", te - ts);
    }


    /**
     * 总结：
     用+的方式效率最差，concat由于是内部机制实现，比+的方式好了不少。
     Join 和 StringBuffer，相差不大，Join方式要快些，可见这种JavaScript中快速拼接字符串的方式在Java中也非常适用。
     StringBuilder 的速度最快，但其有线程安全的问题，而且只有JDK5支持。
     * @param args
     */
    public static void main(String[] args){
        testPlus();
        testConcat();
        testJoin();
        testStringBuffer();
        testStringBuilder();

    }
}
