package com.lifetools.commons.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileUtils {

	public static String readFileToString(String filePath) {
		return readFileToString(filePath, "UTF-8");
	}

	public static String readFileToString(String filePath, String charset) {
		StringBuilder retval = new StringBuilder();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
			String line = null;
			while ((line = reader.readLine()) != null) {
				retval.append(line);
				retval.append('\n');
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return retval.toString();
	}

	public static void writeStringToFile(String filePath, String content) {
		writeStringToFile(filePath, content, "UTF-8");
	}

	public static void writeStringToFile(String filePath, String content, String charset) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(filePath, charset);
			pw.println(content);
		} catch (IOException e) {
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	public static void appendStringToFile() {
		// TODO something
	}

}
