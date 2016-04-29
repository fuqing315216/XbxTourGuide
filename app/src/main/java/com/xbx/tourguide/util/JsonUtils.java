package com.xbx.tourguide.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;

/**
 * json管理
 * 
 * @author Administrator
 * 
 */
public class JsonUtils {

	public static <T> T object(String json, Class<T> classOfT) {
		return JSON.parseObject(json, classOfT);
	}
	public static <T> String toJson(T param) {
		return JSON.toJSONString(param);
				
	}
}
