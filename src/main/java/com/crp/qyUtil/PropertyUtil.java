package com.crp.qyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

public class PropertyUtil {
    private static Properties properties;
    private static Properties msgProperties;
    static {
    	readPropFile();
    }

	/**
	 * 读取属性文件common.properties
	 */
	private static void readPropFile() {
        properties = new Properties();
        try {
        	InputStream inputStream = new FileInputStream(new File(
					"c:\\config\\ms-reconAdmConsole_common.properties"));
			properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 读取属性文件msg.properties
	 */
	private static void readMsgPropFile() {
		msgProperties = new Properties();
		try {
			InputStream inputStream = new FileInputStream(new File(
					"c:\\config\\ms-reconAdmConsole_common.properties"));
			msgProperties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static String getValue(String key) {
		if (properties == null) {
			readPropFile();
		}
		return properties.getProperty(key);
    }

    public static String[] getValues(String key) {
        String values = properties.getProperty(key);
        if (values != null) {
            return properties.getProperty(key).split(",");
        }
        return null;
    }
    
    public static boolean checkValueInProperty(String value, String propertyKey) {
    	String[] values = getValues(propertyKey);
    	String tmpValue;
    	for (int i = 0; values != null && i < values.length; i++) {
    		tmpValue  = values[i];
			if (tmpValue != null && tmpValue.trim().length() > 0 && tmpValue.equals(propertyKey)) {
				return true;
			}
		}
        return false;
    }
    
    /**
     * 读取msg的翻译内容
     * @param key
     * @return
     */
    public static String getMsgValue(String key) {
		if (msgProperties == null) {
			readMsgPropFile();
		}
		return msgProperties.getProperty(key);
    }
    
    public static String geneRtnMsg(String key) {
    	return "{\"respCode\":\"" + key + "\" ,\"msg\":" + "\""+getMsgValue(key)+"\"}";
    }
}
