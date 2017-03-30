  package com.crp.qyUtil;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * 说明：日期时间相关的一些方法
 * @create Jul 17, 2010 12:04:08 AM
 */
public class DateUtil {

	public static final long SECOND = 1000;
	public static final long MINITE = SECOND * 60;
	public static final long HOUR = MINITE * 60;
	public static final long DAY = HOUR * 24;
	
	public static final String TIME_MIN = "00:00:00";
	
	public static final String TIME_MIN2 = "000000";
	
	public static final String TIME_MAX = "23:59:59";
	
	public static final String TIME_MAX2= "235959";

	public static final SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");

	public static final SimpleDateFormat timeSdf = new SimpleDateFormat("HHmmss");

	/**
	 * 说明：将DateTime中的Date转换成Integer
	 * @param date
	 * @return
	 */
	public static final int getDateInt(Date date){
		return Integer.valueOf(dateSdf.format(date));
	}

	/**
	 * 说明：将DateTime中的Time转换成Integer
	 * @param date
	 * @return
	 */
	public static final int getTimeInt(Date date){
		return Integer.valueOf(timeSdf.format(date));
	}


	// order is like this because the SimpleDateFormat.parse does not fail with
	// exception
	// if it can parse a valid date out of a substring of the full string given
	// the mask
	// so we have to check the most complete format first, then it fails with
	// exception
	private static final String[] RFC822_MASKS = { 
		"EEE, dd MMM yy HH:mm:ss z", 
		"EEE, dd MMM yy HH:mm z", 
		"dd MMM yy HH:mm:ss z", 
		"dd MMM yy HH:mm z" 
	};

	// order is like this because the SimpleDateFormat.parse does not fail with
	// exception
	// if it can parse a valid date out of a substring of the full string given
	// the mask
	// so we have to check the most complete format first, then it fails with
	// exception
	private static final String[] W3CDATETIME_MASKS = { 
		"yyyy-MM-dd'T'HH:mm:ss.SSSz", 
		"yyyy-MM-dd't'HH:mm:ss.SSSz", 
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", 
		"yyyy-MM-dd't'HH:mm:ss.SSS'z'",
		"yyyy-MM-dd'T'HH:mm:ssz", 
		"yyyy-MM-dd't'HH:mm:ssz", 
		"yyyy-MM-dd'T'HH:mm:ss'Z'", 
		"yyyy-MM-dd't'HH:mm:ss'z'", 
		"yyyy-MM-dd'T'HH:mmz", // together with logic in the parseW3CDateTime they
		"yyyy-MM'T'HH:mmz", // handle W3C dates without time forcing them to be GMT
		"yyyy'T'HH:mmz", 
		"yyyy-MM-dd't'HH:mmz", 
		"yyyy-MM-dd'T'HH:mm'Z'", 
		"yyyy-MM-dd't'HH:mm'z'", 
		"yyyy-MM-dd", 
		"yyyy-MM", 
		"yyyy" 
	};

	private static final String[] patterns_masks = { 
		"yyyy-MM-dd HH:mm:ss,SSS", 
		"yyyy-MM-dd HH:mm:ss.SSS", 
		"yyyy-MM-dd HH:mm:ss", 
		"yyyy-MM-dd HH:mm", 
		"yyyy-MM-dd", 
		"yyyy/MM/dd HH:mm:ss,SSS",
		"yyyy/MM/dd HH:mm:ss.SSS", 
		"yyyy/MM/dd HH:mm:ss", 
		"yyyy/MM/dd HH:mm", 
		"yyyy/MM/dd", 
		"yyyyMMddHHmmss", 
		"yyyyMMdd", 
		"hh:mm:ss,SSS", 
		"hh:mm:ss.SSS", 
		"hh:mm:ss"
	};

	/**
	 * 修正 new Date() 函数返回的时间是北京时间.
	 * @since 2008-10-31
	 */
	public static void fixedChinaTimeZone() {
		TimeZone userTimeZone = TimeZone.getTimeZone(System.getProperty("user.timezone"));
		TimeZone chinaTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
		if (!chinaTimeZone.hasSameRules(userTimeZone)) {
			TimeZone.setDefault(chinaTimeZone);
			System.out.println("fixed current date:" + format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		}
	}

	/**
	 * get date string use pattern
	 * 
	 * @param pattern
	 *            see {@link SimpleDateFormat}
	 * @return a date string
	 */
	public static String format(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * get date string use pattern
	 * 
	 * @param pattern
	 *            see {@link SimpleDateFormat}
	 * @return a date string
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
		//dateFormater.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 2008-02-22 fixed by subchen
		return dateFormater.format(date);
	}

	/**
	 * get current date and time string
	 * 
	 * @return a local datetime string
	 */
	public static String getNowStr() {
		return format(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * get current date string
	 * 
	 * @return a local date string
	 */
	public static String getDateStr() {
		return format(new Date(), "yyyy-MM-dd");
	}
	
	/**
	 * get current date string
	 * 
	 * @return a local date string
	 */
	public static String getDateStr(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * get current time string
	 * 
	 * @return a local time string
	 */
	public static String getTimeStr() {
		return format(new Date(), "HH:mm:ss");
	}

	/**
	 * @since 2008-01-02
	 */
	public static Calendar getCalendar(long millis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		return c;
	}

	/**
	 * @since 2008-01-02
	 */
	public static Calendar getCalendar(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * @since 2008-01-02
	 */
	public static Date add(Date date, int field, int diff) {
		Calendar c = getCalendar(date);
		c.add(field, diff);
		return c.getTime();
	}

	/**
	 * @since 2008-01-02
	 */
	public static Date add(int field, int diff) {
		return add(new Date(), field, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间
	 */
	public static Date nextYears(int diff) {
		return add(new Date(), Calendar.YEAR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间
	 */
	public static Date nextYears(Date date, int diff) {
		return add(date, Calendar.YEAR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 年的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextYears(String sDate, int diff) {
		return format(nextYears(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 月的时间
	 */
	public static Date nextMonths(int diff) {
		return add(new Date(), Calendar.MONTH, diff);
	}

	/**
	 * 得到与当前时间相差 diff 月的时间
	 */
	public static Date nextMonths(Date date, int diff) {
		return add(date, Calendar.MONTH, diff);
	}

	/**
	 * 得到与当前时间相差 diff 月的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextMonths(String sDate, int diff) {
		return format(nextMonths(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 天的时间
	 */
	public static Date nextDays(int diff) {
		return add(new Date(), Calendar.DATE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 天的时间
	 */
	public static Date nextDays(Date date, int diff) {
		return add(date, Calendar.DATE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 天的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextDays(String sDate, int diff) {
		return format(nextDays(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间
	 */
	public static Date nextHours(int diff) {
		return add(new Date(), Calendar.HOUR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间
	 */
	public static Date nextHours(Date date, int diff) {
		return add(date, Calendar.HOUR, diff);
	}

	/**
	 * 得到与当前时间相差 diff 小时的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextHours(String sDate, int diff) {
		return format(nextHours(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 分的时间
	 */
	public static Date nextMinutes(int diff) {
		return add(new Date(), Calendar.MINUTE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 分的时间
	 */
	public static Date nextMinutes(Date date, int diff) {
		return add(date, Calendar.MINUTE, diff);
	}

	/**
	 * 得到与当前时间相差 diff 分的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextMinutes(String sDate, int diff) {
		return format(nextMinutes(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间
	 */
	public static Date nextSeconds(int diff) {
		return add(new Date(), Calendar.SECOND, diff);
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间
	 */
	public static Date nextSeconds(Date date, int diff) {
		return add(date, Calendar.SECOND, diff);
	}

	/**
	 * 得到与当前时间相差 diff 秒的时间 必须使用yyyy-MM-dd HH:mm:ss格式
	 */
	public static String nextSeconds(String sDate, int diff) {
		return format(nextSeconds(parse(sDate), diff), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 清除时间，得到日期
	 * @since 2007-12-10
	 */
	public static Date clearTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 清除时间，得到日期
	 * @since 2007-12-10
	 */
	public static Date clearDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.YEAR, 0);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 0);
		return c.getTime();
	}

	/**
	 * 计算 d1 - d2 的天数差
	 * @since 2007-12-10
	 */
	public static long diffDays(Date d1, Date d2) {
		d1 = clearTime(d1);
		d2 = clearTime(d2);
		return (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算 d1 - d2 的毫秒差
	 * @since 2007-12-10
	 */
	public static long diffMillis(Date d1, Date d2) {
		return d1.getTime() - d2.getTime();
	}

	/**
	 * 计算 d1 - d2 的秒差
	 * @since 2007-12-10
	 */
	public static long diffSeconds(Date d1, Date d2) {
		return (d1.getTime() - d2.getTime())/1000;
	}
	/**
	 * 得到 Calendar 类中的 Field Value.
	 * @see Calendar
	 * @since 2007-12-10
	 */
	public static int getCalendarField(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 得到当前时间
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getTimestamp(Date date) {
		return date == null ? null : new Timestamp(date.getTime());
	}

	/**
	 * 用指定的格式解析日期时间.
	 * 
	 * @param datetime
	 *            时间字符串
	 * @param pattern
	 *            see {@link SimpleDateFormat}
	 * @throws UnhandledException
	 */
	public static Date parse(String datetime, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(true);
		try {
			ParsePosition pp = new ParsePosition(0);
			Date d = sdf.parse(datetime, pp);
			if (pp.getIndex() != datetime.length()) {
				d = null;
			}
			return d;
		} catch (Exception e) {
			throw new NestableRuntimeException(e);
		}
	}

	/**
	 * Parses a Date out of a string using an array of masks. <p/> It uses the
	 * masks in order until one of them succedes or all fail. <p/>
	 * 
	 * @param masks
	 *            array of masks to use for parsing the string
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given string using one of the given
	 *         masks. It returns <b>null</b> if it was not possible to parse
	 *         the the string with any of the masks.
	 */
	private static Date parseUsingMask(String[] masks, String sDate) {
		sDate = (sDate != null) ? sDate.trim() : null;
		ParsePosition pp = null;
		Date d = null;
		for (int i = 0; d == null && i < masks.length; i++) {
			DateFormat df = new SimpleDateFormat(masks[i]);
			// df.setLenient(false);
			df.setLenient(true);
			try {
				pp = new ParsePosition(0);
				d = df.parse(sDate, pp);
				if (pp.getIndex() != sDate.length()) {
					d = null;
				}
				// System.out.println("pp["+pp.getIndex()+"]
				// s["+sDate+"m["+masks[i]+"] d["+d+"]");
			} catch (Exception e) {
				// System.out.println("s: "+sDate+" m: "+masks[i]+" d: "+null);
				// try next pattern
			}
		}
		return d;
	}

	/**
	 * Parses a Date out of a String with a date in RFC822 format. <p/> It
	 * parsers the following formats:
	 * <ul>
	 * <li>"EEE, dd MMM yyyy HH:mm:ss z"</li>
	 * <li>"EEE, dd MMM yyyy HH:mm z"</li>
	 * <li>"EEE, dd MMM yy HH:mm:ss z"</li>
	 * <li>"EEE, dd MMM yy HH:mm z"</li>
	 * <li>"dd MMM yyyy HH:mm:ss z"</li>
	 * <li>"dd MMM yyyy HH:mm z"</li>
	 * <li>"dd MMM yy HH:mm:ss z"</li>
	 * <li>"dd MMM yy HH:mm z"</li>
	 * </ul>
	 * <p/> Refer to the java.text.SimpleDateFormat javadocs for details on the
	 * format of each element. <p/>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given RFC822 string. It returns
	 *         <b>null</b> if it was not possible to parse the given string
	 *         into a Date.
	 * 
	 */
	public static Date parseRFC822(String sDate) {
		int utIndex = sDate.indexOf(" UT");
		if (utIndex > -1) {
			String pre = sDate.substring(0, utIndex);
			String post = sDate.substring(utIndex + 3);
			sDate = pre + " GMT" + post;
		}
		return parseUsingMask(RFC822_MASKS, sDate);
	}

	/**
	 * Parses a Date out of a String with a date in W3C date-time format. <p/>
	 * It parsers the following formats:
	 * <ul>
	 * <li>"yyyy-MM-dd'T'HH:mm:ssz"</li>
	 * <li>"yyyy-MM-dd'T'HH:mmz"</li>
	 * <li>"yyyy-MM-dd"</li>
	 * <li>"yyyy-MM"</li>
	 * <li>"yyyy"</li>
	 * </ul>
	 * <p/> Refer to the java.text.SimpleDateFormat javadocs for details on the
	 * format of each element. <p/>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It
	 *         returns <b>null</b> if it was not possible to parse the given
	 *         string into a Date.
	 * 
	 */
	public static Date parseW3CDateTime(String sDate) {
		// if sDate has time on it, it injects 'GTM' before de TZ displacement
		// to
		// allow the SimpleDateFormat parser to parse it properly
		int tIndex = sDate.indexOf("T");
		if (tIndex > -1) {
			if (sDate.endsWith("Z")) {
				sDate = sDate.substring(0, sDate.length() - 1) + "+00:00";
			}
			int tzdIndex = sDate.indexOf("+", tIndex);
			if (tzdIndex == -1) {
				tzdIndex = sDate.indexOf("-", tIndex);
			}
			if (tzdIndex > -1) {
				String pre = sDate.substring(0, tzdIndex);
				int secFraction = pre.indexOf(",");
				if (secFraction > -1) {
					pre = pre.substring(0, secFraction);
				}
				String post = sDate.substring(tzdIndex);
				sDate = pre + "GMT" + post;
			}
		} else {
			sDate += "T00:00GMT";
		}
		return parseUsingMask(W3CDATETIME_MASKS, sDate);
	}

	/**
	 * Parses a Date out of a String with a date in W3C date-time format or in a
	 * RFC822 format or in a humpic-default format. <p/> 用尝试多种格式解析日期时间,
	 * 修改自：http://www.koders.com/java/fidDBC85D14D02AA458CE8B8A25256E176EAC6EA748.aspx
	 * <p>
	 * 
	 * @param sDate
	 *            string to parse for a date.
	 * @return the Date represented by the given W3C date-time string. It
	 *         returns <b>null</b> if it was not possible to parse the given
	 *         string into a Date.
	 */
	public static Date parse(String sDate) {
		Date d = parseW3CDateTime(sDate);
		if (d == null) {
			d = parseRFC822(sDate);
		}
		if (d == null) {
			d = parseUsingMask(patterns_masks, sDate);
		}
		if (d == null) {
			try {
				d = DateFormat.getInstance().parse(sDate);
			} catch (ParseException e) {
				d = null;
			}
		}
		return d;
	}

	/**
	 * create a RFC822 representation of a date. <p/> Refer to the
	 * java.text.SimpleDateFormat javadocs for details on the format of each
	 * element. <p/>
	 * 
	 * @param date
	 *            Date to parse
	 * @return the RFC822 represented by the given Date It returns <b>null</b>
	 *         if it was not possible to parse the date.
	 */
	public static String formatRFC822(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}

	/**
	 * create a W3C Date Time representation of a date. <p/> Refer to the
	 * java.text.SimpleDateFormat javadocs for details on the format of each
	 * element. <p/>
	 * 
	 * @param date
	 *            Date to parse
	 * @return the W3C Date Time represented by the given Date It returns
	 *         <b>null</b> if it was not possible to parse the date.
	 */
	public static String formatW3CDateTime(Date date) {
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormater.format(date);
	}
	
	/**
	 * 
	 * 说明：获得两个时间的天数
	 * @param d1 开始时间
	 * @param d2 结束时间
	 * @return
	 */
	public static int getDays(Date d1,Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (3600L * 1000 * 24)); 
	}
	
	/**
	 * 
	 * 说明：获得两个时间的小时数
	 * @param d1 开始时间
	 * @param d2 结束时间
	 * @return
	 */
	public static int getHours(Date d1,Date d2) {
		return (int) ((d2.getTime() - d1.getTime()) / (3600L * 1000)); 
	}
	
	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
		return sdf.format(date);
	}

	public static String formatTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return sdf.format(date);
	}

	public static String formatTimeMonth(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
		return sdf.format(date);
	}
	
	public static String formatTime2(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	public static String getYear(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(date);
	}
	public static Date getMaxTodayDate(){
		String todayDateStr = format(new Date(), "yyyy-MM-dd 23:59:59");
		return parse(todayDateStr, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static Date getMinTodayDate() {
		String todayDateStr = format(new Date(), "yyyy-MM-dd 00:00:00");
		return parse(todayDateStr, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(DateUtil.getYear(new Date()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("测试下:   "+DateUtil.diffDays(DateUtil.parse("2011-09-07 23:36:30"), new Date()));
		System.out.println("测试下:   "+DateUtil.diffDays(DateUtil.parse("2011-10-22 23:36:30"), new Date()));
		Date t = DateUtil.parse("2009-03-04");
		System.out.println(sdf.format(t));
		Date d = nextWorkDays(new Date(), 3);
		System.out.println("date==================="+sdf.format(d));
		System.out.println("date==================="+sdf.format(DateUtil.nextDays(7)));
		//nextDays("2011-03-25",)
		
		System.out.println("date==================="+diffDays(parse("2011-09-01"),new Date()));
		StringBuffer sb = new StringBuffer("");
		for(int x = 9; x < 19;x++) {
			for(int i = 0;i < 60;i++) {
				sb.append("\"");
				if(x < 10) sb.append("0");
				sb.append(x).append(":");
				if(i < 10) sb.append("0");
				sb.append(i).append("\",");
			}
		}
		System.out.println(sb.toString());
	}
	
	/**
	 * 
	 * 说明：返回当前的diff个工作日后的时间--暂时只排除周六周日
	 * sunday-1  thursday-5 saturday-7
	 * @param date
	 * @param diff
	 * @return
	 */
	public static Date nextWorkDays(Date date, int diff) {
		//得到当前的时间是星期几
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int perDays = 0;
		if(!dayOfWeek.equals(1)&&!dayOfWeek.equals(7)) {
			perDays = 7-dayOfWeek;
		}
		diff = diff-perDays;
		int allDiffDays = 0;
		if(diff > 0) {
			int numOfWeek = diff/5;
			int restDays = diff%5;
			allDiffDays = numOfWeek*7+restDays; 
			if(dayOfWeek.equals(1)) {
				allDiffDays = allDiffDays+1;
			} else {
				allDiffDays = allDiffDays+2+perDays;
			} 
		} else {
			return nextDays(date, diff+perDays);
		}
		return nextDays(date, allDiffDays);
	}
	/**
	 * 得到本周周一
	 * @param w 0: 表示为本周，-1：表示为上周 ，1：表示为下周，以此类推
	 * @return yyyy-MM-dd
	 */
	public static String getMondayOfThisWeek(int w) {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 1 + 7 * w);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 *得到本周周日
	 * @param w 0: 表示为本周，-1：表示为上周 ，1：表示为下周，以此类推
	 * @return yyyy-MM-dd
	 */
	public static String getSundayOfThisWeek(int w) {
		Calendar c = Calendar.getInstance();
		int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0)
			dayofweek = 7;
		c.add(Calendar.DATE, -dayofweek + 7 + 7 * w);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	/**
	 * 得到本月最后一天
	 * @param dt 某个月的某一天
	 * @return
	 */
	public static String getLastDateOfMonth(Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		//System.out.println("一个月最后一天" + result);
		return result;
	}

	/**
	 * 得到本月第一天
	 * @param dt 某个月的某一天
	 * @return
	 */
	public static String getFristDateOfMonth(Date dt) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int days = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, days);
		String result = format.format(cal.getTime());
		//System.out.println("一个月第一天" + result);
		return result;
	}
	
	/**
	 * 以YYYY-MM-DD HH:MM:SS格式返回系统日期时间
	 * 
	 * @return 系统日期时间 add by zg 2003-12-9
	 */
	public static String getSysDateStringYMDHMS() {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dd = new Date();
		date = sdf.format(dd);

		return date;
	}
	
	/**
	 * 以yyyyMMddHHmmss格式返回系统日期时间
	 * 
	 * @return 系统日期时间 add by zg 2004-09-25
	 */
	public static String getSysDateStringYMDHMS2() {
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dd = new Date();
		date = sdf.format(dd);

		return date;
	}
}
