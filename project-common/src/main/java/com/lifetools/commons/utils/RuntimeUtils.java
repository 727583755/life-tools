package com.lifetools.commons.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 执行cmd命令工具类
 * @author zk
 * @date 2016年1月8日 上午10:45:40
 */
public class RuntimeUtils {
	
	/**
	 * 执行cmd命令，并获取返回字符串结果
	 * @param cmd
	 * @return
	 */
	public static String exeCmd(String cmd, String charSet) {
		String responseStr = "";
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), charSet));
			String line = "";
			while ((line=br.readLine()) != null) {
				responseStr += line + System.getProperty("line.separator", "/n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseStr;
	}
	
	public static String exeCmd(String cmd) {
		return exeCmd(cmd, "utf-8");
	}
}
