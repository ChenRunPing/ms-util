package com.crp.dateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ms-util - com.crp.dateTime
 *
 * @author superChen
 * @create 2017-01-13 16:43
 */
public class DateTimeUtils {

    /**
     * 获取昨天所在月的第一天的日期字符串 "yyyy-MM-dd"
     * @return
     */
    public static String getYestodayMonthFirstDay() {
        String date = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.DATE,-1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        date = format.format(cal_1.getTime());
        return date;
    }



}
