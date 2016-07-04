package com.lifetools.commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 处理json字符串与对象之间关系的工具类
 * @author Zheng.Ke
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

	/**
	 * 对象转成json字符串
	 * @param obj  要转的对象
	 * @param datePattern   日期格式
	 * @param isPrettyPrinting  是否格式化json字符串
	 * @param isExportNull   是否输出null值
	 * @return
	 */
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

	/**
	 * json字符串转化为对象
	 * @param jsonStr
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> T jsonStrToEntity(String jsonStr, Class<T> clazz) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonStr, clazz);
	}
	
//	public static List<T> jsonStrToList(String josnStr, class) {
//		
//	}
	
}
