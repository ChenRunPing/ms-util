package com.crp.qyUtil;

import java.util.Properties;

/**
 * 说明：获取全局的定义的一些变量信息，如webRoot等
 * @create Feb 23, 2009 3:02:28 PM
 */
public class GlobalUtil {
    //读取配置文件
	
	private static final GlobalUtil global = new GlobalUtil();
	
	private String webRoot ;
	private String financePath;
	private String iceServerIp;
	private String directPlayType;

	/**
	 * 说明：获取ice server ip
	 * @return
	 */
	public static final String getIceServerIp() {
		return global.iceServerIp;
	}

	public void setIceServerIp(String iceServerIp) {
		this.iceServerIp = iceServerIp;
	}

	private GlobalUtil() {
		try{
			Properties props = new Properties();   
			props.load(FileUtil.class.getClassLoader().getResourceAsStream("resources/global.properties"));
			webRoot = props.getProperty("webRoot");
			directPlayType = props.getProperty("directPlayType");
			financePath = props.getProperty("financePath");
			iceServerIp = props.getProperty("iceServerIp");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static final GlobalUtil getInstance(){
		return global;
	}

	public String getWebRoot() {
		return webRoot;
	}
	
	public static String getWebRootPath(String contextPath){
		return global.getWebRoot()+contextPath;
	}
	
	public static String getFinancePathPath(String fileName){
		return global.getFinancePath() + fileName;
	}

	public String getFinancePath() {
		return financePath;
	}

	public String getDirectPlayType() {
		return directPlayType;
	}

	public void setDirectPlayType(String directPlayType) {
		this.directPlayType = directPlayType;
	}
	
}
