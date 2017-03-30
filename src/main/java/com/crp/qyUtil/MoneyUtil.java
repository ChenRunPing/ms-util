/**
 * 
 */
package com.crp.qyUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;


/**  
 * @Title: MoneyUtil.java
 * @Description: 货币类型常用方法支持
 * @date 2011-6-17 下午07:58:06
 * @version V1.0  
 */

public class MoneyUtil {
	/**
	 * 获取元到分
	 * @param dollar
	 * @return
	 */
	public static Long getDollarToCent(String dollar){
		 Money money =new Money(dollar);
		 return money.getCent();
	}
	/**
	 * 获取分到元
	 * @param dollar
	 * @return
	 */
	public static String getCentToDollar(long cent){
		Money m=new Money();
		m.setCent(cent);
		return m.toString();
	}
	
	/**
	 * 
	 *  Created on 2014-8-13 
	 * <p>Discription:[金额正转负，负转正]</p>
	 * @author:[叶东平]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return Long .
	 */
	public static Long getPositiveAndNegative(Long amount){
	    if(null != amount){
	        if(amount<0){
	            return Math.abs(amount);
	        }else{
	            return -amount;
	        }
	    }else{
	        throw new NullPointerException();
	    }
	}


    /**
     * 金额(是分)的四舍五入,精度要分
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingFenUp(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(0, BigDecimal.ROUND_HALF_UP);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五舍,精度要分
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingFenDown(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(0, BigDecimal.ROUND_DOWN);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五入,精度要厘
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingLiUP(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(2, BigDecimal.ROUND_HALF_UP);
        return tp1;
    }
    
    /**
     * 金额(是分)的四舍五舍,精度要厘
     *
     * @param tp0
     * @return
     */
    public static BigDecimal RoundingLiDOWN(BigDecimal tp0) {
        BigDecimal tp1 = tp0.setScale(2, BigDecimal.ROUND_HALF_DOWN);
        return tp1;
    }
    
    /**
     *  Created on 2014-9-28 
     * <p>Discription:[全部进位]</p>
     * @author:[叶东平]
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     * @return BigDecimal .
     */
    public static BigDecimal RoundingCeiling(BigDecimal a){
        a = a.setScale(4, BigDecimal.ROUND_HALF_UP);
        BigDecimal tp1 = a.setScale(0, BigDecimal.ROUND_CEILING);
        return tp1;
    }
    
	
	/**
	 * 
	 *  Created on 2014年10月20日 
	 * <p>Discription:[金额格式化   1. 保留两位小数
	 * 						   2.三个单位一个逗号分割]</p>
	 * @author:[杨龙平]
	 * @update:[日期yyyy-MM-dd] [author]
	 * @return String .
	 */
	public static String insertComma(String s) {
	    if (s == null || s.length() < 1||s.equals("0.00")||s.equals("0")||s.equals("0.0")) {
	        return "0.00";
	    }
	    double num = Double.parseDouble(s);
		if (num >= 1000 || num <= -1000){
	    	DecimalFormat myformat = new DecimalFormat();
	    	myformat.applyPattern("##,###.00");
	    	return myformat.format(num);
	    }else{
	    	return s;
	    }
	}
	
	/**
	 * 
	 *  Created on 2014年10月21日 
	 * <p>Discription:[
	 * 分到元再格式化金额
	 * 例：5000000 --> 50,000.00
	 * ]</p>
	 * @author:[谭燊]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return String .
	 */
	public static String getCommaMoney(Long money) {
		return insertComma(getCentToDollar(money));
	}
	
	/**
	 * 金额去掉“,”
	 * @param s 金额
	 * @return 去掉“,”后的金额
	 */
	public static String delComma(String s) {
	    String formatString = "";
	    if (s != null && s.length() >= 1) {
	        formatString = s.replaceAll(",", "");
	    }
	 
	    return formatString;
	}

	public static void main(String[] args) {
		System.out.println(insertComma("-1000.99"));
		System.out.println(delComma("1000.00"));
	}

}
