package com.crp.tools;

import java.util.List;

/**
 * ms-util - com.crp.tools
 *
 * @author superChen
 * @create 2017-05-03 9:32
 */
public class Utils {

    /**
     * 判断对象是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * 判断对象不为空
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(Object obj)
    {
        return !isEmpty(obj);
    }



    public static void main(String[] args){
//        String ss = null;
//        System.out.println(isEmpty(ss));
         List list = null;
        System.out.println(isEmpty(list));


    }
}
