package com.lifetools.commons.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class VerifyUtils {

	public static boolean isJsonStr(String str) {
		try {
			new JSONObject(str);
		} catch (JSONException e) {
			return false;
		}
		return true;
	}

}
