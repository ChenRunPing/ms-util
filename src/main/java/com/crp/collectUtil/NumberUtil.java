package com.crp.collectUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ms-util - com.crp.collectUtil
 *
 * @author superChen
 * @create 2017-04-01 11:53
 */
public class NumberUtil {

    /**
     * 验证数字（包括 整数、小数、正数、负数）
     * @param str
     * @return
     */
    private static boolean validateNumber(String str) {
        Pattern pattern = Pattern.compile("-?[0-9]+.*[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if(!isNum.matches() ){
            return false;
        }
        return true;
    }


    public static void main(String[] args){

        String ss = "-1";
        System.out.println(validateNumber(ss));

    }
}
