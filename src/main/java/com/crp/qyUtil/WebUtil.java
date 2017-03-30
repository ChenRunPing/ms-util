package com.crp.qyUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
public class WebUtil {
	public static Map getParamAsDto(HttpServletRequest request) {
		Map dto = new HashMap();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			dto.put(key, value);
			dto.put(key.toLowerCase(), value);
		}
		return dto;
	}
	
}
