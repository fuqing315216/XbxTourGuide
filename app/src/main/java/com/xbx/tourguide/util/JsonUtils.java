package com.xbx.tourguide.util;

import com.alibaba.fastjson.JSON;

/**
 * json管理
 * 
 * @author Administrator
 * 
 */
public class JsonUtils {
//	private static Gson gson = new Gson();

	public static <T> T object(String json, Class<T> classOfT) {
//		return gson.fromJson(json, classOfT);
		return JSON.parseObject(json, classOfT);
	}
	public static <T> String toJson(Class<T> param) {
//		return gson.toJson(param);
		return JSON.toJSONString(param);
				
	}
}
