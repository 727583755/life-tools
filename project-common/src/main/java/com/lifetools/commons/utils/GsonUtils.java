package com.lifetools.commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 处理json字符串与对象之间关系的工具类
 * @author zk
 * @date 2015年8月27日 下午4:53:45
 */
public class GsonUtils {
	
	public static String toJson(Object obj) {
		return toJson(obj, null, false);
	}
	
	public static String toJson(Object obj, Boolean isExportNull) {
		return toJson(obj, null, false, isExportNull);
	}
	
	public static String toJson(Object obj, String datePattern) {
		return toJson(obj, datePattern, false);
	}
	
	public static String toJson(Object obj, String datePattern, Boolean isPrettyPrinting) {
		return toJson(obj, datePattern, isPrettyPrinting, null);
	}
	
	public static String toJson(Object obj, String datePattern, Boolean isPrettyPrinting, Boolean isExportNull) {
		GsonBuilder builder = new GsonBuilder();
		
		if (isPrettyPrinting != null && isPrettyPrinting) {
			builder.setPrettyPrinting();
		}
		if (StringUtils.isBlankOrEmpty(datePattern)) {
			datePattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (isExportNull != null && isExportNull) {
			builder.serializeNulls();
		}
		builder.disableHtmlEscaping();
		builder.setDateFormat(datePattern);
		Gson gson = builder.create();
		return gson.toJson(obj);
	}
	
	public static <T> T jsonStrToEntity(String jsonStr, Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonStr, clazz);
	}
	
//	public static List<T> jsonStrToList(String josnStr, class) {
//		
//	}
	
}
