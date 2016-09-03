package com.lifetools.weixin.util;


import com.lifetools.commons.util.HttpUtils;
import com.lifetools.commons.util.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;

/**
 * 发送微信方面请求的工具类
 * 
 * @author zk
 */
public class WxUtils {
	private static String accessTokenAndMillis;
	
	// 测试号
	private static final String APPID = "wxc4e92f8155edc206";
	private static final String APPSECRET = "22df6d5385f5e2a76920e7bd07ae4e37";

	public static String getAccessToken() {
		if (StringUtils.isNotBlank(accessTokenAndMillis)) {
			String[] s = accessTokenAndMillis.split(",,");
			if (System.currentTimeMillis() - Long.parseLong(s[1]) <= 3600*1000) {
				return s[0];
			}
		}
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
		String responseStr = HttpUtils.sendWebRequestByGet(null, url);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return null;
		}
		JSONObject jo = new JSONObject(responseStr);
		accessTokenAndMillis = jo.getString("access_token") + ",," + System.currentTimeMillis();
		return jo.getString("access_token");
	}

	/**
	 * 生成微信菜单
	 * 
	 * @return
	 */
	public static boolean genMenu() {
		String ACCESS_TOKEN = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + ACCESS_TOKEN;
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();

//		ja.put(getMenu1());
		ja.put(getMenu2());
//		ja.put(getMenu3());
		jo.put("button", ja);

		String responseStr = HttpUtils.sendWebRequestByPostJsonStr(null, url, jo.toString());
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return false;
		}
		JSONObject joo = new JSONObject(responseStr);
		if (joo.getInt("errcode") != 0) {
			return false;
		}
		return true;
	}

	private static JSONObject getMenu3() {
		JSONObject jo = new JSONObject();
		jo.put("name", "联系我们");
		JSONArray ja = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("type", "view");
		jo1.put("name", "加入我们");
		jo1.put("url", "http://x.eqxiu.com/s/EbE0vFhT?from=timeline&isappinstalled=0");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("type", "click");
		jo2.put("name", "联系客服");
		jo2.put("key", "EVENT_CONTACT_KF");

		JSONObject jo3 = new JSONObject();
		jo3.put("type", "click");
		jo3.put("name", "与公子小白对话");
		jo3.put("key", "EVENT_CONTACT_XIAOBAI");

		JSONObject jo4 = new JSONObject();
		jo4.put("type", "view");
		jo4.put("name", "问题反馈");
		jo4.put("url", "http://gowild2016.gotoip3.com/gowild-weixin/feedback");

		ja.put(jo1);
		ja.put(jo2);
		ja.put(jo3);
		ja.put(jo4);
		jo.put("sub_button", ja);

		return jo;
	}

	private static JSONObject getMenu2() {
		JSONObject jo = new JSONObject();
		jo.put("type", "view");
		jo.put("name", "房贷计算器");
		jo.put("url", "http://123.56.195.62:8099/life-web-client/static/mortgage_calculate.html");
//		JSONObject jo = new JSONObject();
//		jo.put("name", "了解我们");
//		JSONArray ja = new JSONArray();
//
//		JSONObject jo1 = new JSONObject();
//		jo1.put("type", "view");
//		jo1.put("name", "草粉社区");
//		jo1.put("url", "http://bbs.gowild.cn/");
//		
//		JSONObject jo2 = new JSONObject();
//		jo2.put("type", "click");
//		jo2.put("name", "每日早报");
//		jo2.put("key", "EVENT_NEWSPAPER");
//
//		JSONObject jo3 = new JSONObject();
//		jo3.put("type", "click");
//		jo3.put("name", "最近新闻");
//		jo3.put("key", "EVENT_LAST_NEWS");
//		
//		ja.put(jo1);
//		ja.put(jo2);
//		ja.put(jo3);
//		jo.put("sub_button", ja);

		return jo;
	}

	private static JSONObject getMenu1() {
		JSONObject jo = new JSONObject();
		jo.put("name", "狗尾草");
		JSONArray ja = new JSONArray();

		JSONObject jo1 = new JSONObject();
		jo1.put("type", "view");
		jo1.put("name", "狗尾草官网");
		jo1.put("url", "http://www.gowild.cn/");
		
		JSONObject jo2 = new JSONObject();
		jo2.put("type", "view");
		jo2.put("name", "APP下载");
		jo2.put("url", "http://a.app.qq.com/o/simple.jsp?pkgname=com.gowild.mobileclient");

		ja.put(jo1);
		ja.put(jo2);
		jo.put("sub_button", ja);

		return jo;
	}

	/**
	 * 调用图灵机器人api接口，获取智能回复内容
	 */
	public static String textByTuling(String textFromUser) {
		String APIKEY = "a1b62b9a512d1718b35d4e14196b860e";
		String url = "http://www.tuling123.com/openapi/api?key=" + APIKEY + "&info=" + HttpUtils.urlEncode(textFromUser);
		String responseStr = HttpUtils.sendWebRequestByGet(null, url);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return null;
		}
		
		JSONObject jo = new JSONObject(responseStr);
		return jo.getString("text");
	}
	
	public static String get() {
		String ACCESS_TOKEN = getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + ACCESS_TOKEN;
		JSONObject jo = new JSONObject();
		jo.put("type", "news");
		jo.put("offset", "0");
		jo.put("count", "1");
		String responseStr = HttpUtils.sendWebRequest(url, HttpMethod.POST, null, jo.toString(), "UTF-8", "UTF-8", null, null);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return null;
		}
		return responseStr;
	}
	
	public static void shutDownSession(String openId) {
		String ACCESS_TOKEN = getAccessToken();
		String url = "https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=" + ACCESS_TOKEN + "&openid=" + openId;
		String responseStr = HttpUtils.sendWebRequestByGet(null, url);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return;
		}
		JSONObject jo = new JSONObject(responseStr);
		if (!jo.has("kf_account")) {
			return;
		}
		String kfAccount = jo.getString("kf_account");
		if (StringUtils.isBlankOrEmpty(kfAccount)) {
			return;
		}
		url = "https://api.weixin.qq.com/customservice/kfsession/close?access_token=" + ACCESS_TOKEN;
		JSONObject joo = new JSONObject();
		joo.put("kf_account", kfAccount);
		joo.put("openid", openId);
		joo.put("text", "提示：用户端已主动断开连接。");
		String s = HttpUtils.sendWebRequestByPost(null, url, joo.toString(), null, "application/json;charset=utf-8");
		System.out.println(s);
	}

	public static String textByXiaoBai(String content, String openId) {
		String url = "http://www.gowild.top:8080/AndroidServer/getDialogCatagory2.do?Rawtext=" + content + "&mac=" + openId;
		String responseStr = HttpUtils.sendWebRequestByGet(null, url);
		if (StringUtils.isBlankOrEmpty(responseStr)) {
			return null;
		}
		JSONObject jo = new JSONObject(responseStr);
		if ("null".equals(jo.get("result").toString())) {
			return null;
		}
		String result = jo.getString("result");
		result = result.replaceAll("/[a-zA-Z]{1,}", "").replaceAll("[<>a-zA-Z]+_[a-zA-Z]+", "");
		return result;
	}

}
