package com.crp.qyUtil;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.support.WebApplicationObjectSupport;


/**
 * 说明：获得全局的对象，方便代码操作
 * @create Jan 21, 2009 11:25:26 AM
 */
public class AppHelper extends WebApplicationObjectSupport implements InitializingBean {

	private static AppHelper instance = null;
	
	public AppHelper() {
		instance = this;// singleton
	}

	public static AppHelper getInstance() {
		return instance;
	}

	public static ServletContext getApplication() {
		return instance.getServletContext();
	}

	public void afterPropertiesSet() throws Exception {

	}
	
}
