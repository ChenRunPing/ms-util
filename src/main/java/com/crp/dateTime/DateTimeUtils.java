package com.crp.dateTime;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ms-util - com.crp.dateTime
 *
 * @author superChen
 * @create 2017-01-13 16:43
 */
@Slf4j
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

    public static String getAfterNDayDate(String dateString , int beforeDays){
        SimpleDateFormat   sdf   =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar   cal1   =   Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            log.error("格式化日期发生异常，日期:"+dateString);
        }
        cal1.add(Calendar.DATE,beforeDays);
        return sdf.format(cal1.getTime());
    }

    public static String getAfterNMinuteDate(String dateString , int afterMinutes){
        SimpleDateFormat   sdf   =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar   cal1   =   Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            log.error("格式化日期发生异常，日期:"+dateString);
        }
        cal1.add(Calendar.MINUTE,afterMinutes);
        return sdf.format(cal1.getTime());
    }

}
