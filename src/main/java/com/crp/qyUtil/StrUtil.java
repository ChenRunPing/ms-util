package com.crp.qyUtil;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 创建类的目的：String Utils类 大量参考apache jakarta commons-lang
 * 2.1,因为不希望底层Utils库依赖于第3方library, 所以直接复制其代码,目前仅复制用到的函数,其余函数需要时再进行复制.
 * 
 * @create Jul 23, 2007 9:02:39 PM
 */
public class StrUtil {

	public static final String EMPTY = "";

	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * 说明：将非单词字符替换成_
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceStrToUnderLine(String str) {
		return (isEmpty(str)) ? EMPTY : str.replaceAll("\\W+", "-");
	}

	public static String getStrings(String s) {
		return s == null ? "" : s.trim();
	}

	/**
	 * (source != null) ? source : ""; 等同于Orocle的nvl
	 * 
	 * @param source
	 */
	public static String nvl(String source) {
		return (source != null) ? source.trim() : "";
	}

	/**
	 * (source != null) ? source : ""; 等同于Orocle的nvl
	 * 
	 * @param source
	 */
	public static String nvl(String source, String defaultString) {
		return (source != null) ? source.trim() : defaultString;
	}

	/**
	 * 转换UTF-8
	 */
	public static String toUtf8(String src) {
		byte[] b = src.getBytes();
		char[] c = new char[b.length];
		for (int i = 0; i < b.length; i++) {
			c[i] = (char) (b[i] & 0x00FF);
		}
		return new String(c);
	}

	/**
	 * 说明：增加字符串,结果并赋值于source
	 * 
	 * @param source
	 * @param separated
	 * @param addStr
	 */
	public static String addToSource(String source, String separated,
			Object addStr) {
		return new StringBuffer(StrUtil.trimToEmpty(source)).append(
				StrUtil.trimToEmpty(separated)).append(
				StrUtil.trimToEmpty(addStr == null ? null : addStr.toString()))
				.toString();
	}

	/**
	 * 判断一个字符串是否为空，空格作非空处理。 StringUtils.isEmpty(null) = true
	 * StringUtils.isEmpty("") = true StringUtils.isEmpty(" ") = false
	 * StringUtils.isEmpty("bob") = false StringUtils.isEmpty(" bob ") = false
	 */
	public static boolean isEmpty(String str) {
		return ((str == null) || (str.length() == 0));
	}

	/**
	 * 判断一个字符串是否非空，空格作非空处理. StringUtils.isNotEmpty(null) = false
	 * StringUtils.isNotEmpty("") = false StringUtils.isNotEmpty(" ") = true
	 * StringUtils.isNotEmpty("bob") = true StringUtils.isNotEmpty(" bob ") =
	 * true
	 */
	public static boolean isNotEmpty(String str) {
		return ((str != null) && (str.length() > 0));
	}

	/**
	 * 判断一个字符串是否非空，空格作空处理. StringUtils.isNotBlank(null) = false
	 * StringUtils.isNotBlank("") = false StringUtils.isNotBlank(" ") = false
	 * StringUtils.isNotBlank("bob") = true StringUtils.isNotBlank(" bob ") =
	 * true
	 */
	public static boolean isNotBlank(String str) {
		int strLen;

		if ((str == null) || ((strLen = str.length()) == 0))
			return false;

		for (int i = 0; i < strLen; i++) {
			if ((!Character.isWhitespace(str.charAt(i))))
				return true;
		}

		return false;
	}

	/**
	 * 根据HTML属性及值返回HTML属性及值匹配代码 property="name" value="buttonOk" return
	 * name="buttonOk" property="name" value="" return ""
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	public static String appendHtmlTagProperty(String property, String value) {
		if (isNotEmpty(value)) {
			return new StringBuffer(" ").append(property).append("=").append(
					"\"").append(value).append("\" ").toString();
		} else
			return "";
	}

	/**
	 * 说明：根据传入的字段串及regex获取最后一个字符
	 * 
	 * @param splitStr
	 * @param regex
	 * @return
	 */
	public static String getLastSplitString(String splitStr, String regex) {
		String[] str = splitStr.split(regex);
		return str[str.length - 1];
	}

	/**
	 * 说明：根据传入的数组字段串及regex获取最后一个字符串数组
	 * 
	 * @param splitStrArray
	 * @param regex
	 * @return
	 */
	public static String[] getLastSplitStringArray(String[] splitStrArray,
			String regex) {
		String[] str = new String[splitStrArray.length];
		for (int i = 0; i < splitStrArray.length; i++) {
			str[i] = getLastSplitString(splitStrArray[i], regex);
		}
		return str;
	}

	/**
	 * 说明：将数组转换成String
	 * 
	 * @param array
	 * @param seperator
	 * @return
	 */
	public static String arrayToString(Object[] array, String seperator) {
		if (isArrayEmpty(array))
			return EMPTY;
		StringBuffer str = new StringBuffer(String.valueOf(array[0]));
		for (int i = 1; i < array.length; i++) {
			str.append(seperator).append(String.valueOf(array[i]));
		}
		return str.toString();
	}

	public static boolean isNum(String content) {
		if (content == null)
			return false;
		Pattern p = Pattern.compile("^(-?\\d+)|((-?\\d+)(\\.\\d+))$");
		Matcher m = p.matcher(content);
		return m.matches();
	}

	/**
	 * 说明：查询数组里匹配的字符串
	 * 
	 * @param array
	 * @param lastString
	 * @return
	 */
	public static String searchArrayByLastString(String[] array,
			String lastString) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].lastIndexOf(lastString) >= 0)
				return array[i];
		}
		return EMPTY;
	}

	/**
	 * 说明：判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	/**
	 * 根据HTML属性及值返回HTML属性及值匹配代码,如果Value为空则由默认值填充 property="name"
	 * value="buttonOk",defaultValue="btn" return name="buttonOk"
	 * property="name" value="" defaultValue="btn" return name="btn"
	 * 
	 * @param property
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static final String appendHtmlTagProperty(String property,
			String value, String defaultValue) {
		return appendHtmlTagProperty(property, isEmpty(value) ? defaultValue
				: value);
		// if(isNotEmpty(value)){
		// return new StringBuffer(" ")
		// .append(property)
		// .append("=")
		// .append("\"")
		// .append(value)
		// .append("\" ").toString();
		// }else{
		// return new StringBuffer(" ")
		// .append(property)
		// .append("=")
		// .append("\"")
		// .append(defaultValue)
		// .append("\" ").toString();
		// }
	}


	public static String insertString(String[] name, String insertString) {

		StringBuffer linkMan = new StringBuffer("");
		for (int i = 0; i < name.length; i++) {
			linkMan.append(name[i]).append(insertString);
		}
		return linkMan.toString().trim();
	}

	/**
	 * 将数组中的字符转换为一个字符串,分隔符默认为"," String[3] s={"a","b","c"}
	 * StrUtil.convString（s)="a,b,c"
	 */
	static public String converString(String[] strToConv) {
		return converString(strToConv, ",");
	}

	/**
	 * 将数组中的字符转换为一个字符串 String[3] s={"a","b","c"}
	 * StrUtil.convString（s,"@")="a@b@c"
	 */
	static public String converString(String[] strToConv, String seperator) {
		String convStr = "";

		if (strToConv != null) {
			for (int i = 0; i < strToConv.length; i++) {
				if (i > 0) {
					convStr = convStr + seperator;
				}

				convStr = convStr + strToConv[i];
			}
		}

		return convStr;
	}

	/**
	 * 去掉一个字符串中的空格，有非空判断处理； StringUtils.trim(null) = null StringUtils.trim("") =
	 * "" StringUtils.trim(" ") = "" StringUtils.trim(" abc ") = "abc"
	 */
	public static String trim(String str) {
		return ((str == null) ? null : str.trim());
	}

	/**
	 * 判断两个字符串是否相等，有非空处理。 StringUtils.equals(null, null) = true
	 * StringUtils.equals(null, "abc") = false
	 */
	public static boolean equals(String str1, String str2) {
		return ((str1 == null) ? (str2 == null) : str1.equals(str2));
	}

	/**
	 * 判断两个字符串是否相等，有非空处理。忽略大小写 StringUtils.equalsIgnoreCase(null, null) = true
	 * StringUtils.equalsIgnoreCase(null, "abc") = false
	 * StringUtils.equalsIgnoreCase("abc", null) = false
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return ((str1 == null) ? (str2 == null) : str1.equalsIgnoreCase(str2));
	}

	/**
	 * 返回要查找的字符串所在位置，有非空处理 StringUtils.indexOf(null, *) = -1
	 * StringUtils.indexOf(*, null) = -1 StringUtils.indexOf("", "") = 0
	 * StringUtils.indexOf("aabaabaa", "a") = 0 StringUtils.indexOf("aabaabaa",
	 * "b") = 2 StringUtils.indexOf("aabaabaa", "ab") = 1
	 * StringUtils.indexOf("aabaabaa", "") = 0
	 */
	public static int indexOf(String str, String searchStr) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		return str.indexOf(searchStr);
	}

	/**
	 * 返回要由指定位置开始查找的字符串所在位置，有非空处理 StringUtils.indexOf("aabaabaa", "a", 0) = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0) = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3) = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9) = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2) = 2 StringUtils.indexOf("abc", "",
	 * 9) = 3
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if ((str == null) || (searchStr == null)) {
			return -1;
		}

		if ((searchStr.length() == 0) && (startPos >= str.length())) {
			return str.length();
		}

		return str.indexOf(searchStr, startPos);
	}

	/**
	 * 返回指定位置开始的字符串中的所有字符 StringUtils.substring(null, *) = null
	 * StringUtils.substring("", *) = "" StringUtils.substring("abc", 0) = "abc"
	 * StringUtils.substring("abc", 2) = "c" StringUtils.substring("abc", 4) =
	 * "" StringUtils.substring("abc", -2) = "bc" StringUtils.substring("abc",
	 * -4) = "abc"
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}

		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * 返回由开始位置到结束位置之间的子字符串 StringUtils.substring(null, *, *) = null
	 * StringUtils.substring("", * , *) = ""; StringUtils.substring("abc", 0, 2)
	 * = "ab" StringUtils.substring("abc", 2, 0) = ""
	 * StringUtils.substring("abc", 2, 4) = "c" StringUtils.substring("abc", 4,
	 * 6) = "" StringUtils.substring("abc", 2, 2) = ""
	 * StringUtils.substring("abc", -2, -1) = "b" StringUtils.substring("abc",
	 * -4, 2) = "ab"
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}

		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}

		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * 返回指定字符串之前的所有字符 StringUtils.substringBefore(null, *) = null
	 * StringUtils.substringBefore("", *) = ""
	 * StringUtils.substringBefore("abc", "a") = ""
	 * StringUtils.substringBefore("abcba", "b") = "a"
	 * StringUtils.substringBefore("abc", "c") = "ab"
	 * StringUtils.substringBefore("abc", "d") = "abc"
	 * StringUtils.substringBefore("abc", "") = ""
	 * StringUtils.substringBefore("abc", null) = "abc"
	 */
	public static String substringBefore(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)) {
			return str;
		}

		if (separator.length() == 0) {
			return EMPTY;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 返回指定字符串之后的所有字符 StringUtils.substringAfter(null, *) = null
	 * StringUtils.substringAfter("", *) = "" StringUtils.substringAfter(*,
	 * null) = "" StringUtils.substringAfter("abc", "a") = "bc"
	 * StringUtils.substringAfter("abcba", "b") = "cba"
	 * StringUtils.substringAfter("abc", "c") = ""
	 * StringUtils.substringAfter("abc", "d") = ""
	 * StringUtils.substringAfter("abc", "") = "abc"
	 */
	public static String substringAfter(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if (separator == null) {
			return EMPTY;
		}

		int pos = str.indexOf(separator);

		if (pos == -1) {
			return EMPTY;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 返回最后一个指定字符串之前的所有字符 StringUtils.substringBeforeLast(null, *) = null
	 * StringUtils.substringBeforeLast("", *) = ""
	 * StringUtils.substringBeforeLast("abcba", "b") = "abc"
	 * StringUtils.substringBeforeLast("abc", "c") = "ab"
	 * StringUtils.substringBeforeLast("a", "a") = ""
	 * StringUtils.substringBeforeLast("a", "z") = "a"
	 * StringUtils.substringBeforeLast("a", null) = "a"
	 * StringUtils.substringBeforeLast("a", "") = "a"
	 */
	public static String substringBeforeLast(String str, String separator) {
		if ((str == null) || (separator == null) || (str.length() == 0)
				|| (separator.length() == 0)) {
			return str;
		}

		int pos = str.lastIndexOf(separator);

		if (pos == -1) {
			return str;
		}

		return str.substring(0, pos);
	}

	/**
	 * 返回最后一个指定字符串之后的所有字符 StringUtils.substringAfterLast(null, *) = null
	 * StringUtils.substringAfterLast("", *) = ""
	 * StringUtils.substringAfterLast(*, "") = ""
	 * StringUtils.substringAfterLast(*, null) = ""
	 * StringUtils.substringAfterLast("abc", "a") = "bc"
	 * StringUtils.substringAfterLast("abcba", "b") = "a"
	 * StringUtils.substringAfterLast("abc", "c") = ""
	 * StringUtils.substringAfterLast("a", "a") = ""
	 * StringUtils.substringAfterLast("a", "z") = ""
	 */
	public static String substringAfterLast(String str, String separator) {
		if ((str == null) || (str.length() == 0)) {
			return str;
		}

		if ((separator == null) || (separator.length() == 0)) {
			return EMPTY;
		}

		int pos = str.lastIndexOf(separator);

		if ((pos == -1) || (pos == (str.length() - separator.length()))) {
			return EMPTY;
		}

		return str.substring(pos + separator.length());
	}

	/**
	 * 转换首字母小写，
	 * 
	 * @param str
	 *            StringUtils.uncapitalize(null) = null
	 *            StringUtils.uncapitalize("") = ""
	 *            StringUtils.uncapitalize("Cat") = "cat"
	 *            StringUtils.uncapitalize("CAT") = "cAT"
	 */
	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(
				Character.toLowerCase(str.charAt(0))).append(str.substring(1))
				.toString();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning <code>null</code> if the String is empty ("") after the trim or
	 * if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToNull(String)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.trimToNull(null)          = null
	 *  StringUtils.trimToNull(&quot;&quot;)            = null
	 *  StringUtils.trimToNull(&quot;     &quot;)       = null
	 *  StringUtils.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
	 *         empty or null String input
	 * @since 2.0
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return isEmpty(ts) ? null : ts;
	}

	public static String isNotNullToString(Object str) {
		return (str == null) ? null : str.toString();
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning an empty String ("") if the String is empty ("") after the trim
	 * or if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToEmpty(String)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.trimToEmpty(null)          = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String trimToEmpty(Object str) {
		return str == null ? EMPTY : str.toString().trim();
	}

	// Stripping
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of a String.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trim(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.strip(null)     = null
	 *  StringUtils.strip(&quot;&quot;)       = &quot;&quot;
	 *  StringUtils.strip(&quot;   &quot;)    = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove whitespace from, may be null
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning
	 * <code>null</code> if the String is empty ("") after the strip.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToNull(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.strip(null)     = null
	 *  StringUtils.strip(&quot;&quot;)       = null
	 *  StringUtils.strip(&quot;   &quot;)    = null
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the stripped String, <code>null</code> if whitespace, empty or
	 *         null String input
	 * @since 2.0
	 */
	public static String stripToNull(String str) {
		if (str == null) {
			return null;
		}
		str = strip(str, null);
		return str.length() == 0 ? null : str;
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning an empty
	 * String if <code>null</code> input.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToEmpty(String)} but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.strip(null)     = &quot;&quot;
	 *  StringUtils.strip(&quot;&quot;)       = &quot;&quot;
	 *  StringUtils.strip(&quot;   &quot;)    = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String stripToEmpty(String str) {
		return str == null ? EMPTY : strip(str, null);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of a String.
	 * This is similar to {@link String#trim()} but allows the characters to be
	 * stripped to be controlled.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}. Alternatively use
	 * {@link #strip(String)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.strip(null, *)          = null
	 *  StringUtils.strip(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.stripStart(null, *)          = null
	 *  StringUtils.stripStart(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.stripStart(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;abc  &quot;, null)    = &quot;abc  &quot;
	 *  StringUtils.stripStart(&quot; abc &quot;, null)    = &quot;abc &quot;
	 *  StringUtils.stripStart(&quot;yxabc  &quot;, &quot;xyz&quot;) = &quot;abc  &quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the end of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.stripEnd(null, *)          = null
	 *  StringUtils.stripEnd(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.stripEnd(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot;  abc&quot;, null)    = &quot;  abc&quot;
	 *  StringUtils.stripEnd(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot; abc &quot;, null)    = &quot; abc&quot;
	 *  StringUtils.stripEnd(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	// StripAll
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of every String in an array.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.stripAll(null)             = null
	 *  StringUtils.stripAll([])               = []
	 *  StringUtils.stripAll([&quot;abc&quot;, &quot;  abc&quot;]) = [&quot;abc&quot;, &quot;abc&quot;]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null])  = [&quot;abc&quot;, null]
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove whitespace from, may be null
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of every String
	 * in an array.
	 * </p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. </p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored. A
	 * <code>null</code> stripChars will strip whitespace as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.stripAll(null, *)                = null
	 *  StringUtils.stripAll([], *)                  = []
	 *  StringUtils.stripAll([&quot;abc&quot;, &quot;  abc&quot;], null) = [&quot;abc&quot;, &quot;abc&quot;]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null], null)  = [&quot;abc&quot;, null]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null], &quot;yz&quot;)  = [&quot;abc  &quot;, null]
	 *  StringUtils.stripAll([&quot;yabcz&quot;, null], &quot;yz&quot;)  = [&quot;abc&quot;, null]
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs, String stripChars) {
		int strsLen;
		if (strs == null || (strsLen = strs.length) == 0) {
			return strs;
		}
		String[] newArr = new String[strsLen];
		for (int i = 0; i < strsLen; i++) {
			newArr[i] = strip(strs[i], stripChars);
		}
		return newArr;
	}

	// IndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.indexOf(null, *)         = -1
	 *  StringUtils.indexOf(&quot;&quot;, *)           = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'a') = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b') = 2
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the first index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#indexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position is treated as zero. A start position greater than
	 * the string length returns <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.indexOf(null, *, *)          = -1
	 *  StringUtils.indexOf(&quot;&quot;, *, *)            = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 0)  = 2
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 3)  = 5
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 9)  = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', -1) = 2
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	// LastIndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.lastIndexOf(null, *)         = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, *)           = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'a') = 7
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b') = 5
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the last index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position returns <code>-1</code>. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.lastIndexOf(null, *, *)          = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, *,  *)           = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 8)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 4)  = 2
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 0)  = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 9)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', -1) = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'a', 0)  = 0
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.lastIndexOf(null, *)          = -1
	 *  StringUtils.lastIndexOf(*, null)          = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, &quot;&quot;)           = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;)  = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;)  = 2
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;ab&quot;) = 1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;&quot;)   = 8
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the last index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.lastIndexOf(null, *, *)          = -1
	 *  StringUtils.lastIndexOf(*, null, *)          = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 8)  = 7
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 8)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 8) = 4
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 9)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, -1) = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 0)  = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 0)  = -1
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	// Contains
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if String contains a search character, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.contains(null, *)    = false
	 *  StringUtils.contains(&quot;&quot;, *)      = false
	 *  StringUtils.contains(&quot;abc&quot;, 'a') = true
	 *  StringUtils.contains(&quot;abc&quot;, 'z') = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, char searchChar) {
		if (isEmpty(str)) {
			return false;
		}
		return str.indexOf(searchChar) >= 0;
	}

	/**
	 * <p>
	 * Checks if String contains a search String, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 *  StringUtils.contains(null, *)     = false
	 *  StringUtils.contains(*, null)     = false
	 *  StringUtils.contains(&quot;&quot;, &quot;&quot;)      = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;&quot;)   = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;a&quot;)  = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;z&quot;)  = false
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search String, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	public static String join(String seperator, Iterator objects) {
		StringBuffer buf = new StringBuffer();
		if (objects.hasNext())
			buf.append(objects.next());
		while (objects.hasNext()) {
			buf.append(seperator).append(objects.next());
		}
		return buf.toString();
	}

	/**
	 * String类型转换成Date类型 要求dateString与format格式一致
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String dateString, String format) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				format);
		Date date = null;
		try {
			date = dateFormat.parse(dateString);
		} catch (java.text.ParseException pe) {
			pe.printStackTrace();
		}
		return date;
	}

	/**
	 * Date类型转换成String类型 要求dateString与format格式一致
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static String dateToString(Date date, String format) {
		return (new java.text.SimpleDateFormat(format)).format(date);
	}

	/**
	 * 说明：解决所有的字符串数组转成int数组
	 * 
	 * @param recUserIds
	 */
	public static int[] parseStringsToints(String[] recUserIds) {
		int[] ids = new int[recUserIds.length];
		for (int i = 0; i < recUserIds.length; i++) {
			ids[i] = Integer.valueOf(recUserIds[i]);
		}
		return ids;
	}

	/**
	 * 说明：解决所有的字符串数组转成long数组
	 * 
	 * @param strIds
	 */
	public static Long[] stringsToLongs(String[] strIds) {
		if (strIds == null) {
			return new Long[] {};
		}
		Long[] ids = new Long[strIds.length];
		for (int i = 0; i < strIds.length; i++) {
			ids[i] = Long.valueOf(strIds[i]);
		}
		return ids;
	}

	/**
	 * 说明：解决所有的字符串数组转成integer数组
	 * 
	 * @param strIds
	 */
	public static Integer[] stringsToIntegers(String[] strIds) {
		Integer[] ids = new Integer[strIds.length];
		for (int i = 0; i < strIds.length; i++) {
			ids[i] = Integer.valueOf(strIds[i]);
		}
		return ids;
	}

	/**
	 * 说明：解决所有的Long转抱成String
	 * 
	 * @param longIds
	 */
	public static String[] longsToStrings(Long[] longIds) {
		String[] strIds = new String[longIds.length];
		for (int i = 0; i < longIds.length; i++) {
			strIds[i] = String.valueOf(longIds[i]);
		}
		return strIds;
	}

	/**
	 * 说明：解决所有的Integer转抱成String
	 * 
	 * @param intIds
	 */
	public static String[] integersToStrings(Integer[] intIds) {
		String[] strIds = new String[intIds.length];
		for (int i = 0; i < intIds.length; i++) {
			strIds[i] = String.valueOf(intIds[i]);
		}
		return strIds;
	}

	/**
	 * 说明：占时只是将html中的自动换行信息转换成br
	 * 
	 * @param str
	 * @return
	 */
	public static String strToHtml(String str) {
		if (isEmpty(str)) {
			return EMPTY;
		}
		return str.replace("\n", "<br/>");
	}

	/**
	 * 
	 * 说明：根据给定的character来分隔str
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static String[] spiltStrByChar(String str, String character) {
		if (str != null && !"".equals(str.trim())) {
			String[] returnStr = str.split(character);
			return returnStr;
		}
		return null;
	}

	/**
	 * 
	 * 说明：根据给定的character来分隔str,返回List
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static List<String> spiltStrToListByChar(String str, String character) {
		if (str != null && !"".equals((str.trim()))) {
			List<String> retList = new ArrayList<String>();
			String[] returnStr = str.split(character);
			for (int i = 0; i < returnStr.length; i++) {
				retList.add(returnStr[i]);
			}
			return retList;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * 
	 * 说明：根据给定的character来分隔str,返回List
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static List<Long> spiltStrToLongListByChar(String str,
			String character) {
		List<Long> retList = new ArrayList<Long>();
		if (str != null && !"".equals(str.trim())) {
			String[] returnStr = str.split(character);
			for (int i = 0; i < returnStr.length; i++) {
				if (returnStr[i] != null && !("".equals(returnStr[i].trim()))) {
					retList.add(Long.parseLong(returnStr[i]));
				}
			}
		}
		return retList;
	}

	/**
	 * 
	 * 说明：根据给定的character来分隔str,返回List
	 * 
	 * @param str
	 * @param character
	 * @return
	 */
	public static List<Integer> spiltStrToIntegerListByChar(String str,
			String character) {
		List<Integer> retList = new ArrayList<Integer>();
		if (str != null && !"".equals(str.trim())) {
			String[] returnStr = str.split(character);
			for (int i = 0; i < returnStr.length; i++) {
				if (returnStr[i] != null && !("".equals(returnStr[i].trim()))) {
					retList.add(Integer.parseInt(returnStr[i]));
				}
			}
		}
		return retList;
	}

	/**
	 * 
	 * 说明：得到Stringlist组成的String,以逗号分隔
	 * 
	 * @param sourceList
	 * @return
	 */
	public static String changeStringListToString(List<String> sourceList) {
		StringBuffer stringBuffer = new StringBuffer("");
		for (int i = 0; i < sourceList.size(); i++) {
			if (i < sourceList.size() - 1) {
				stringBuffer.append(sourceList.get(i)).append(",");
			} else {
				stringBuffer.append(sourceList.get(i));
			}
		}
		return stringBuffer.toString();
	}

	/**
	 * 
	 * 说明：得到Longlist组成的String，以逗号分隔
	 * 
	 * @param sourceList
	 * @return
	 */
	public static String changeLongListToString(List<Long> sourceList) {
		StringBuffer stringBuffer = new StringBuffer("");
		for (int i = 0; i < sourceList.size(); i++) {
			if (i < sourceList.size() - 1) {
				stringBuffer.append(sourceList.get(i).toString()).append(",");
			} else {
				stringBuffer.append(sourceList.get(i).toString());
			}
		}
		return stringBuffer.toString();
	}

	public static boolean checkEmail(String email) {
		// String regex =
		// "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		// String regex = "^\\w+@([^@]+\\.)[a-zA-Z]{2,3}$";
		// String regex ="^\\w+@\\w+\\.(com\\.cn)|\\w+@\\w+\\.(com|cn)$";
		String regex = "\\w+@(\\w+.)+[a-z]{2,3}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}

	// 全角转半角的 转换函数
	public static final String full2HalfChange(String QJstr)
			throws UnsupportedEncodingException {

		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);

			// 全角空格转换成半角空格
			if (Tstr.equals("　")) {
				outStrBuf.append(" ");
				continue;
			}

			b = Tstr.getBytes("unicode"); // 得到 unicode 字节数据

			if (b[3] == -1) { // 表示全角？
				b[2] = (byte) (b[2] + 32);
				b[3] = 0;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}

		} // end for.

		return outStrBuf.toString();
	}

	// 半角转全角
	public static final String half2Fullchange(String QJstr)
			throws UnsupportedEncodingException {
		StringBuffer outStrBuf = new StringBuffer("");
		String Tstr = "";
		byte[] b = null;

		for (int i = 0; i < QJstr.length(); i++) {

			Tstr = QJstr.substring(i, i + 1);
			if (Tstr.equals(" ")) {// 半角空格
				outStrBuf.append(Tstr);
				continue;
			}

			b = Tstr.getBytes("unicode");

			if (b[3] == 0) { // 半角?
				b[2] = (byte) (b[2] - 32);
				b[3] = -1;
				outStrBuf.append(new String(b, "unicode"));
			} else {
				outStrBuf.append(Tstr);
			}

		}

		return outStrBuf.toString();
	}

	public static boolean regex(String s) {
		// 不包含%，&,$的任何字符串
		String regex = "[^%$&;,]{1,}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	private static void deleteFullSpace(String testStr) {
		// ^[ *| *]*"表示以全角空格或半角空格开始的所有集合
		// [ *| *]*$"表示以全角空格或半角空格结尾的所有集合
		String resultStr = testStr.replaceAll("^[　*| *]*", "").replaceAll(
				"[　*| *]*$", "");
		System.out.println("|" + resultStr + "|");
	}

	public static void main(String[] args) {
		try {
				System.out.println(18%9);
			  System.out.println("".indexOf("sss"));
			  System.out.println("leftTrim:" + leftTrim(" 　　123　　123　　123　　 ") + ":");
			  System.out.println("rightTrim:" + rightTrim(" 　　123　　123　　123　 　") + ":");
			  System.out.println("trim:" + trimAll(" 　　123　　123　　123 　　") + ":");
			                System.out.println("leftTrim:" + trims(" 　　123　　123　　123　　 ","l") + ":");
			  System.out.println("rightTrim:" + trims(" 　　123　　123　　123　 　","r") + ":");
			  System.out.println("trim:" + trims(" 　　123　　123　　123 　　","a") + ":");
			  
			String str1 = "  　1　1   深圳市百思勤工贸有限公司 ";
			
			System.out.println("trim:" + trimAll(str1));
			System.out.println(full2HalfChange(str1));
			for (int i = 0; i < str1.length(); i++) {
				System.out.println(str1.charAt(i));
				System.out.println(str1.valueOf(i));
			}
			byte[] bytes = str1.getBytes();
			for (int i = 0; i < bytes.length; i++) {
				System.out.println(bytes[i]);
			}

			System.out.println("11111  深圳市百思勤工贸有限公司 ".trim());
			System.out.println("11111  深圳市百思勤工贸有限公司    ".trim());
			System.out.println("11111  深圳市百思勤工贸有限公司 ".trim());
			System.out.println("11111  深圳市百思勤工贸有限公司".trim());
			System.out.println("ssss#d#".replace("#d#",
					"11111  深圳市百思勤工贸有限公司 ** "));

			StringBuffer sb = new StringBuffer("");
			for (int i = 0, j = 16001; i < 100; i++) {
				sb.append(j++).append(",");
			}
			System.out.println(sb.toString());
			JsonObject json = new JsonObject();
			json.addProperty("isError", "sss");
			json.addProperty("tip", "ss\"s");
			JsonObject json2 = new JsonObject();
			json2.addProperty("isError", "sss");
			json2.addProperty("tip", "ss\"s");
			JsonArray jsona = new JsonArray();
			jsona.add(json2);
			jsona.add(json2);
			jsona.add(json2);
			json.add("jsona", jsona);
			System.out.println(json.toString());

			System.out.println("20113333".substring(0, 4));
			String input = "/js/tiny_mce/plugins/advimage/image.htm";

			Pattern p = Pattern.compile("[*.htm]");
			Matcher m = p.matcher(input);

			if (m.matches()) {
				System.out.println("false ");
			} else {
				System.out.println("true ");
			}
			System.out.println(StrUtil.isNum(""));
			// StrUtil.regex("");
			String s = "W11010000757";
			System.out.println(s.substring(1, s.length()));
			System.out
					.println("sdfsdffffff,22_33,sdfdsf".replace(",22_33", ""));
			// 全角转半角
			String QJstr = "hello!３２好甜！ 全角转换，ＤＡＯ ５３２３２　";
			String result = full2HalfChange(QJstr);

			System.out.println(QJstr);
			System.out.println(result);
			System.out.println("------------------------------------");
			// 半角转全角
			String str = "java 汽车 召回 2345";
			System.out.println(str);
			System.out.println(half2Fullchange(str));

			JsonObject jsonmember = new JsonObject();
			jsonmember.addProperty("id", "1");
			jsonmember.addProperty("memberId", "1"); // 展商ID
			jsonmember.addProperty("exhId", "1"); // 展会ID
			jsonmember.addProperty("type", "1"); // 标识
			jsonmember.addProperty("activityId", "1"); // 活动ID,默认为0
			jsonmember.addProperty("url", "1"); // 活动地址
			jsonmember.addProperty("memo", "恭喜你中奖了，请到服务台领奖");
			System.out.println(jsonmember.toString());

			JsonObject obj = new JsonParser().parse(jsonmember.toString())
					.getAsJsonObject();
			System.out.println(obj.get("memberId"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("sssss----22,".toUpperCase());
		System.out.println("ssss,".substring(0, 4));

		if (StrUtil.checkEmail("xx--++sdf383*x@alibaba-inc.com.cn")) {
			System.out.println("fff");
		} else {
			System.out.println("ddd");
		}
		List<String> same = new ArrayList<String>();
		same.add("s");
		same.add("3");
		same.add("4");
		same.add("5");
		String b = "2";
		if (same.contains(b)) {
			System.out.println("same");
		} else {
			System.out.println("no");
		}

		Map<String, Long> systemCodeMap = new HashMap<String, Long>();
		systemCodeMap.put("a", 1L);
		systemCodeMap.put("B", 2L);
		systemCodeMap.put("c", 3L);
		if (systemCodeMap.get("a") != null) {
			System.out.println("same aaaaaa");
		} else {
			System.out.println("no  aaaaaaa");
		}
		String maxNo = "XW000100000009";
		if (StrUtil.isNotEmpty(maxNo)) {
			System.out.print(maxNo.substring(5));
		}
	}

	public static String trimAll(String str) {
		if (str == null || str.equals("")) {
			return str;
		} else {
			// return leftTrim(rightTrim(str));
			return str.replaceAll("^[　  ]+|[　  ]+$", "");
		}
	}

	public static String leftTrim(String str) {
		if (str == null || str.equals("")) {
			return str;
		} else {
			return str.replaceAll("^[　 ]+", "");
		}
	}

	public static String rightTrim(String str) {
		if (str == null || str.equals("")) {
			return str;
		} else {
			return str.replaceAll("[　 ]+$", "");
		}
	}

	public static String trims(String Str, String Flag) {
		if (Str == null || Str.equals("")) {
			return Str;
		} else {
			Str = "" + Str;
			if (Flag == "l" || Flag == "L")/* trim left side only */
			{
				String RegularExp = "^[　 ]+";
				return Str.replaceAll(RegularExp, "");
			} else if (Flag == "r" || Flag == "R")/* trim right side only */
			{
				String RegularExp = "[　 ]+$";
				return Str.replaceAll(RegularExp, "");
			} else/* defautly, trim both left and right side */
			{
				String RegularExp = "^[　 ]+|[　 ]+$";
				return Str.replaceAll(RegularExp, "");
			}
		}
	}

	public static String changeStringToStableLength(String sourceStr, int length) {
		if(sourceStr.length() < length) {
			int strLength = length - sourceStr.length();
			StringBuffer retStr = new StringBuffer("");
			for(int i=0; i<strLength; i++) {
				retStr.append("0");
			}
			retStr.append(sourceStr);
			return retStr.toString();
		}
		return sourceStr.substring(sourceStr.length()-6, sourceStr.length());
	}

}
