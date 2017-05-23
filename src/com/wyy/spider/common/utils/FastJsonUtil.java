package com.wyy.spider.common.utils;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json转换工具类
 * 
 * @author lzh
 *
 */
public class FastJsonUtil {

	/**
	 * 序列化方法
	 * @param bean
	 * @param type
	 * @return
	 */
	public static String bean2json(Object bean) {  
//		 JSON.DEFFAULT_DATE_FORMAT = "YYYYMMDDHHMM";
		   //返回json有部分数据有bug，转换json异常$ref,SerializerFeature.DisableCircularReferenceDetect
		 return JSON.toJSONString(bean,SerializerFeature.DisableCircularReferenceDetect); 
	}

	/**
	 * 反序列化方法
	 * @param <T>
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T> T json2bean(String json, Class<T> clazz) {  
	  return JSON.parseObject(json, clazz);  
	}  

	  /**
     * fastJson 数组解析
	 * @param <T>
	 * @param <T>
     * @param result
     * @return
     */
    public static  <T> List<T> getStringArray(String result, Class<T> tt){
        List<T> jsonList = JSON.parseArray(result, tt);
        return jsonList;
        
    }
    
    /**
     * 将不同类型数据转换为json文本
     * @param object
     * @return
     */
    public static String toJsonString(Object object){
    	return JSON.toJSONString(object);
    }
   

    /**
     * 将json文本信息转换成map
     * @param json
     * @return
     * Map<String,Object>
     *
     */
    @SuppressWarnings("unchecked")
	public static Map<String,Object> toMap(String json){
    	return (Map<String, Object>) JSON.parse(json);
    }
    
    /**
     * json转list
     * @param json
     * @param clazz
     * @return
     * List<T>
     *
     */
    public static <T> List<T>  toList(String json,Class<T> clazz){
        return JSON.parseArray(json, clazz);
    }
    
}
