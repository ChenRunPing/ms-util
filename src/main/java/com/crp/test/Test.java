package com.crp.test;

import com.crp.dateTime.DateTimeUtils;

/**
 * ms-util - com.crp.test
 *
 * @author superChen
 * @create 2017-03-17 16:53
 */
public class Test {

    public static void  main(String args[]){

        String ss = "2017-07-19 23:59:50";
        String sss = DateTimeUtils.getAfterNMinuteDate(ss,10);
        System.out.println(sss);


    }



}
