package com.crp.qyUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 说明：request请求参数转化为实体对象
 * 
 * @create Jul 26, 2011 3:04:15 PM
 */
public abstract class RequestIntrospectHelper {

    protected static Log log = LogFactory.getLog(RequestIntrospectHelper.class);

    public static void introspect(Object form, ServletRequest request) {
        Map map = request.getParameterMap();
        Iterator iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String name = (String) entry.getKey();
            try {
                Class clazz = PropertyUtils.getPropertyType(form, name);
                if (clazz == null)
                    continue;

                String value[] = (String[]) entry.getValue();
                if (value == null)
                    continue;

                if (clazz.isArray()) {
                    BeanUtils.setProperty(form, name, value);
                } else {
                    if (StringUtils.isBlank(value[0]))
                        continue;

                    BeanUtils.setProperty(form, name, value[0]);
                }
            } catch (Exception e) {
                log.warn("Cannot Set bean.property.", e);
            }
        }
    }

    public static Map introspectToMap(HttpServletRequest request) {
        Map dto = new HashMap();
        Map map = request.getParameterMap();
        Iterator keyIterator = (Iterator) map.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            String value = ((String[]) (map.get(key)))[0];
            dto.put(key, value);
        }
        return dto;
    }
}