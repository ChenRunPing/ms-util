package com.crp.qyUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

	public static final String CHARSET = "UTF-8";
	
	/**
	 * 数据格式化
	 */
	private static DecimalFormat df = new DecimalFormat("#.##");

	/**
	 * 获取客户端真是IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	/**
	 * SQL注入检测
	 * 
	 * @param str
	 * @return
	 */
	public static boolean sqlValidate(String str) {
		str = str.toLowerCase();// 统一转为小写
		String reg = "^[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}$";
        if(!str.matches(reg)){
    		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|"
    				+ "char|declare|sitename|net user|xp_cmdshell|or|like'|and|exec|execute|insert|create|drop|"
    				+ "table|from|grant|use|group_concat|column_name|"
    				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
    				+ "chr|mid|master|truncate|char|declare|or|like";// 过滤掉的sql关键字，可以手动添加
    		String[] badStrs = badStr.split("\\|");
    		for (int i = 0; i < badStrs.length; i++) {
    			if (str.equals(badStrs[i])) {
    				return true;
    			}
    		}
    	}
        return false;
	}

	/**
	 * 
	 * 获得保留小数点后两位有效数字的字符串
	 * 
	 * @param doubleValue
	 * @return xx.xx
	 */
	public static String getFormatDouble(String doubleValue) {
		double b = Double.valueOf(doubleValue);
		String result = df.format(b);
		return result;
	}

	public static boolean compareDate(String date1, String date2) {
		boolean flag = false;
		try {
			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Calendar calen1 = java.util.Calendar.getInstance();
			java.util.Calendar calen2 = java.util.Calendar.getInstance();
			calen1.setTime(sd.parse(date1));
			calen2.setTime(sd.parse(date2));
			int result = calen1.compareTo(calen2);
			if (result > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 标题中含有特殊字符替换 如:●▲@◎※ 主要在标题中使用
	 * 
	 * @param Content
	 * @return
	 */
	public static String replaceSign(String content) {
		String strContent = "";
		strContent = content.replaceAll("\\*", "");
		strContent = strContent.replaceAll("\\$", "");
		strContent = strContent.replaceAll("\\+", "");
		String arrStr[] = { ":", "：", "●", "▲", "■", "@", "＠", "◎", "★", "※",
				"＃", "〓", "＼", "§", "☆", "○", "◇", "◆", "□", "△", "＆", "＾",
				"￣", "＿", "♂", "♀", "Ю", "┭", "①", "「", "」", "≮", "§", "￡",
				"∑", "『", "』", "⊙", "∷", "Θ", "の", "↓", "↑", "Ф", "~", "Ⅱ",
				"∈", "┣", "┫", "╋", "┇", "┋", "→", "←", "!", "Ж", "#", "<", ">" };
		for (int i = 0; i < arrStr.length; i++) {
			if ((strContent.indexOf(arrStr[i])) >= 0) {
				strContent = strContent.replaceAll(arrStr[i], "");
			}
		}

		return strContent;
	}

	public static String StringFilter(String str) throws PatternSyntaxException {
		// 清除掉所有特殊字符
		String result = "";
		String regEx = "[|&;$%@<>()+,]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		result = m.replaceAll("").trim();
		result = result.replaceAll("CR", "");
		result = result.replaceAll("LF", "");
		result = result.replaceAll("'", "");
		result = result.replaceAll("\"", "");
		result = result.replaceAll("\'", "");
		result = result.replaceAll("\\\"", "");
		result = result.replaceAll("\\\\", "");
		return result;
	}

	/**
	 * 验证是否含有特殊字符
	 * 
	 * @param str
	 *            验证字符串
	 * @return
	 */
	public static boolean checkTS(String str) {
		// 过滤的特殊字符
		String tsStr = "<>";
		int num = 0;
		str = str.trim();
		if (str != null && str.length() > 0) {
			for (int i = 0; i < str.length(); i++) {
				num = tsStr.indexOf(str.charAt(i));
				if (num != -1) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断特殊字符
	 * 
	 * @param input
	 * @return
	 */
	public static boolean hasSpecialChars(String inputStr) {
		boolean flag = false;
		if ((inputStr != null) && (inputStr.length() > 0)) {
			char c;
			for (int i = 0; i <= inputStr.length() - 1; i++) {
				c = inputStr.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				case '|':
					flag = true;
					break;
				case ';':
					flag = true;
					break;
				// case ',': flag = true; break;
				case '$':
					flag = true;
					break;
				case '%':
					flag = true;
					break;
				case '\\':
					flag = true;
					break;
				case '(':
					flag = true;
					break;
				case ')':
					flag = true;
					break;
				case '+':
					flag = true;
					break;
				case '?':
					flag = true;
					break;
				case 0x0d:
					flag = true;
					break;
				case 0x0a:
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static void main(String args[]) {
		System.out.println(CommonUtil.hasSpecialChars("的身份的身份是发的fdfdfd"));
		System.out.println(sqlValidate("likes"));
	}
}
