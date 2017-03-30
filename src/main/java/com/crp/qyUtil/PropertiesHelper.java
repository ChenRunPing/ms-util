package com.crp.qyUtil;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertiesHelper {

	private String filePath = "";

	public PropertiesHelper(String filePath) {
		this.filePath = filePath;
	}

	public void setProperty(String key, Object value)
			throws ConfigurationException {
		PropertiesConfiguration pro = new PropertiesConfiguration(this.filePath);
		pro.setProperty(key, value);
		pro.save();
	}

	public String getString(String key) throws ConfigurationException {
		PropertiesConfiguration pro = new PropertiesConfiguration(this.filePath);
		return pro.getString(key);
	}

	public int getInt(String key) throws ConfigurationException {
		PropertiesConfiguration pro = new PropertiesConfiguration(this.filePath);
		return pro.getInt(key);
	}

	public double getDouble(String key) throws ConfigurationException {
		PropertiesConfiguration pro = new PropertiesConfiguration(this.filePath);
		return pro.getDouble(key);
	}

}
