package com.lifetools.commons.utils;

import org.json.JSONObject;

/**
 * ip工具类
 * @author zk
 */
public class IPUtils {
	public static final String BAIDU_AK = "QtgIFhRFjPs6GvjlhdbLUE1v";
	
	/**
	 * 获取省份名  城市名 纬度 经度
	 * @param ip
	 * @return
	 */
	public static String[] getAddressInfoByIp(String ip) {
		String[] info = new String[4];
		if (StringUtils.isBlankOrEmpty(ip)) {
			return null;
		}
		
		String responseStr = sendIPRequest(ip);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return null;
		}
		JSONObject jo = new JSONObject(responseStr);
		int status = jo.getInt("status");
		if (status == 2) {
			return null;
		}
		if (!jo.has("content")) {
			info[0] = "";
			info[1] = "";
			info[2] = "";
			info[3] = "";
			return info;
		}
		JSONObject joo = jo.getJSONObject("content").getJSONObject("address_detail");
		String province = joo.getString("province");
		String city = joo.getString("city");
		String lat = jo.getJSONObject("content").getJSONObject("point").getString("y");
		String lon = jo.getJSONObject("content").getJSONObject("point").getString("x");
		info[0] = province;
		info[1] = city;
		info[2] = lat;
		info[3] = lon;
		return info;
	}
	
	private static String sendIPRequest(String ip) {
		String url = "http://api.map.baidu.com/location/ip?ak=" + BAIDU_AK + "&ip=" + ip + "&coor=bd09ll";
		String responseStr = HttpUtils.sendWebRequestByGet(null, url);
		return responseStr;
	}
}
