package com.crp.qyUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

public class DictUtil {
	private static String URL = PropertyUtil.getValue("dictUrl");
	
	/**
	 * 根据字典类型，获取全部小类信息
	 * @param type 字典类型
	 * @return
	 */
    public  Map getDictMapByType(String type) {
		Map codeMap = new LinkedHashMap();
		try {
			String urlStr = URL + "?typeId=" + type;
			byte[] ret = HttpUtils.webGet(new URL(urlStr), null, 3000, 3000);
			if (!"empty".equals(new String(ret, "GBK"))) {
				ObjectMapper objMapper = new ObjectMapper();
				codeMap = objMapper.readValue(new String(ret, "GBK"), Map.class);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return codeMap;
    }
    
    public  List getDictListByType(String type) {
    	Map codeMap =  new LinkedHashMap();
    	List list = new ArrayList();
		try {
			String urlStr = URL + "?typeId=" + type;
			byte[] ret = HttpUtils.webGet(new URL(urlStr), null, 3000, 3000);
			if ("empty".equals(new String(ret, "GBK"))) {
				// 未查到该字典
				return list;
			}
			ObjectMapper objMapper = new ObjectMapper();
			codeMap = objMapper.readValue(new String(ret, "GBK"), Map.class);
			if (codeMap != null) {
				Set<String> codeSet = codeMap.keySet();
				LinkedHashMap tmpMap = new LinkedHashMap();
				for (String key : codeSet) {
					tmpMap.put("key", key);
					tmpMap.put("value", codeMap.get(key));
					list.add(tmpMap.clone());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return list;
    }
    
	/**
	 * 根据字典类型、字典数据ID获取小类名称
	 * @param codeMap 字典Map
	 * @param code 字典数据ID
	 * @return 小类名称
	 */
    public static String getDictDataByCode(Map codeMap, String code) {
		String rtn = "";
		rtn = codeMap != null ? (String) codeMap.get(code) : code;
		if (rtn == null || rtn.trim().length() == 0) {
			rtn = code;
		}
		return rtn;
    }
    
    public static void getDictLoad() throws Exception {
		String urlStr = URL+"?status=load";
		HttpUtils.webGet(new URL(urlStr), null, 3000, 3000);
    }
    
    
}
