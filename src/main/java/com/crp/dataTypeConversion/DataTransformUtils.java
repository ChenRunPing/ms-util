package com.crp.dataTypeConversion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qy.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ms-util - com.crp.dataTypeConversion
 *
 * @author superChen
 * @create 2017-01-13 14:13
 */
@Slf4j
public class DataTransformUtils {

    /**
     * 将map转成json字符串
     * @param map
     * @return
     */
    public static String mapToString(Map map){

        String result = JsonUtil.toJSONString(map);
        return result;
    }

    /**
     * 字符串转map
     * @param str
     * @return
     */
    public static Map stringToMap(String str){
        Map map = new HashMap<>();
        if (str == null || str.isEmpty()){
            log.info("字符串为空！");
            return map;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            map = objectMapper.readValue(str,Map.class);
        }catch (IOException e){
            log.info("io异常！",e);
        }
        return map;
    }

    /**
     * 将字符串转出list
     * @param str
     * @param s 分隔符号
     * @return
     */
    public static List stringToList(String str,String s){
        List list = new ArrayList<>();
        String[] result = str.split(s);
        for (int i=0;i<result.length;i++){
            list.add(result[i]);
        }
        return list;
    }


    /**
     * list转string
     * @param list
     * @param s 分隔符
     * @return
     */
    public static String listToString(List<?> list,String s){
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(listToString((List<?>) list.get(i), s));
                    sb.append(s);
                } else if (list.get(i) instanceof Map) {
                    sb.append(mapToString((Map<?, ?>) list.get(i)));
                    sb.append(s);
                } else {
                    sb.append(list.get(i));
                    sb.append(s);
                }
            }
        }
        return String.valueOf(sb);

    }


    /**
     * 将实体POJO转化为JSON
     *
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static <T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String jsonStr = "";
        try {
            if (obj != null) {
                jsonStr = mapper.writeValueAsString(obj);
            }
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }


    /**
     * 实体类POJO转map
     *
     * @param obj
     * @return
     */
    public static HashMap<String, Object> objecToMap(Object obj) {

        if (obj == null) {
            return null;
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            log.info("异常！" + e);
        }

        return map;

    }







}
