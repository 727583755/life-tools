package com.lifetools.commons.utils;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;

public class StringUtils {

	public static boolean isBlankOrEmpty(String s) {
		if (s == null || s.trim().equals(""))
			return true;
		return false;
	}

	public static boolean isEmpty(String s) {
		if (s == null || s.equals(""))
			return true;
		return false;
	}

	public static boolean isNotBlank(String s) {
		if (s == null || s.trim().equals(""))
			return false;
		return true;
	}

	public static boolean isSameContent(String s1, String s2) {
		if (isBlankOrEmpty(s1)) {
			if (isBlankOrEmpty(s2))
				return true;
			return false;
		} else {
			if (isBlankOrEmpty(s2))
				return false;
		}
		return s1.trim().equalsIgnoreCase(s2.trim());
	}
	
	public static boolean isNumber(String str) {
		if (isBlankOrEmpty(str)) {
			return false;
		}
		Matcher m = RegexUtils.doMatcher(str, "[^0-9\\.]{1,}");
		if (m.find()) {
			return false;
		}
		return true;
	}

	public static String byteArrayToString(byte[] bytes, String encoding) {
		return new String(bytes, Charset.forName(encoding));
	}

	public static String byteArrayToHexString(byte[] bytes) {
		return byteArrayToHexString(bytes, bytes.length);
	}

	public static String byteArrayToHexString(byte[] bytes, int length) {
		if (bytes == null || length > bytes.length) {
			return null;
		}
		StringBuilder strb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			String hv = Integer.toHexString(bytes[i] & 0xFF);
			if (hv.length() < 2) {
				strb.append(0);
			}
			strb.append(hv);
		}
		return strb.toString();
	}

	public static byte[] hexStringToByteArray(String hex) {
		if (hex == null || !hex.matches("^[0-9a-fA-F]*$"))
			return null;
		hex = hex.toLowerCase();
		byte[] retval = new byte[hex.length() / 2];
		for (int i = 0; i < retval.length; i++) {
			byte b = (byte) ("0123456789abcdef".indexOf(hex.charAt(i * 2)) << 4 | "0123456789abcdef".indexOf(hex
					.charAt(i * 2 + 1)));
			retval[i] = b;
		}
		return retval;
	}

	public static Set<String> getIntersection(String[] a1, String[] a2) {
		if (a1 == null || a2 == null)
			return null;
		Set<String> retval = new HashSet<String>();
		for (String s1 : a1) {
			for (String s2 : a2) {
				if (s1.equalsIgnoreCase(s2)) {
					retval.add(s1);
					break;
				}
			}
		}
		if (retval.size() == 0)
			return null;
		return retval;
	}

	/**
	 * 从某个位置（包含）开始向前查找
	 * 
	 * @param source
	 * @param from
	 * @param ec
	 *            结束字符
	 * @return
	 */
	public static String findForward(String source, int from, char ec) {
		if (source == null || from < 0 || from >= source.length())
			return null;
		StringBuilder retval = new StringBuilder();
		while (from < source.length()) {
			if (source.charAt(from) == ec)
				break;
			retval.append(source.charAt(from++));
		}
		return retval.toString();
	}

	public static String findForward(String source, String from, char ec) {
		if (source == null || from == null)
			return null;
		return findForward(source, source.indexOf(from), ec);
	}

	/**
	 * 从某个位置（不包含）开始往回查找
	 * 
	 * @param source
	 * @param from
	 * @param ec
	 *            结束字符
	 * @return
	 */
	public static String findBackward(String source, int from, char ec) {
		if (source == null || from < 0 || from > source.length())
			return null;
		StringBuilder retval = new StringBuilder();
		while (--from >= 0) {
			if (source.charAt(from) == ec)
				break;
			retval.append(source.charAt(from));
		}
		return retval.reverse().toString();
	}

	public static String findBackward(String source, String from, char ec) {
		if (source == null || from == null)
			return null;
		return findBackward(source, source.indexOf(from), ec);
	}

	/**
	 * 取第一个标志（不包含）后面的部分
	 * 
	 * @param source
	 * @param flag
	 * @return
	 */
	public static String findAfterFlag(String source, String flag) {
		if (source == null || flag == null)
			return null;
		return source.substring(source.indexOf(flag) + flag.length());
	}

	/**
	 * 取第一个标志（包含）前面的部分
	 * 
	 * @param source
	 * @param flag
	 * @return
	 */
	public static String findBeforeFlag(String source, String flag) {
		if (source == null || flag == null)
			return null;
		return source.substring(0, source.indexOf(flag) + flag.length());
	}

	/**
	 * 第一个标志（不包含）和第二个标志（不包含）之间的字符串
	 * 
	 * @return
	 */
	public static String findBetweenTwoFlag(String source, String startFlag, String endFlag) {
		if (source == null || startFlag == null || endFlag == null) {
			return null;
		}
		int start = source.indexOf(startFlag);
		if (start < 0) {
			return null;
		}
		int end = source.substring(start + startFlag.length()).indexOf(endFlag);
		if (end < 0) {
			return null;
		}

		return source.substring(start + startFlag.length(), end + start + startFlag.length());
	}

	/**
	 * 取链接中间的部分 <a href="www.baidu.com">要取的数据</a>
	 * 
	 */
	public static String findLinkInner(String source) {
		if (source == null) {
			return null;
		}
		Matcher m = RegexUtils.doMatcher(source, RegexUtils.REGEX_PATTERN_A);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}
	
	/**
	 * 取中间的部分 <span href="www.baidu.com">要取的数据</span>
	 * 
	 */
	public static String findSpanInner(String source) {
		if (source == null) {
			return null;
		}
		Matcher m = RegexUtils.doMatcher(source, RegexUtils.REGEX_PATTERN_SPAN);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	public static String genRandomCode(int cnt, boolean numberOnly) {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int scope = numberOnly ? 10 : chars.length();
		StringBuffer sb = new StringBuffer();
		Random r = new Random();
		for (int i = 0; i < cnt; i++) {
			int index = r.nextInt(scope);
			sb.append(chars.charAt(index));
		}
		return sb.toString();
	}

	public static String standardizeEngText(String input) {
		String retval = input.replaceAll("[ÄÃ]", "A").replaceAll("Ç", "C").replaceAll("É", "E")
				.replaceAll("[Íì¡]", "i").replaceAll("Ö", "O");
		retval = retval.replaceAll("['`]", "").replaceAll("[(\\[{/]", ",").replaceAll("[^0-9a-zA-Z ,]", " ")
				.replaceAll("[ ]+", " ");
		return retval.trim();
	}

	/**
	 * 去除开始部分
	 * 
	 * @param full
	 * @param start
	 * @return
	 */
	public static String removeStartsWith(String full, String start) {
		full = full.trim();
		if (full.startsWith(start)) {
			return full.substring(start.length());
		}
		return full;
	}

	/**
	 * 去除结尾部分
	 * 
	 * @param full
	 * @param end
	 * @return
	 */
	public static String removeEndsWith(String full, String end) {
		full = full.trim();
		if (full.endsWith(end)) {
			return full.substring(0, full.length() - end.length());
		}
		return full;
	}

	/**
	 * 去除字符串前后空格，如果字符串为null，则返回null
	 */
	public static String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	/**
	 * 以逗号分隔，返回第一段，如果没有逗号返回全部
	 * 
	 * @param input
	 * @return
	 */
	public static String getFirstPartByComma(String input) {
		int index = input.indexOf(',');
		if (index != -1)
			return input.substring(0, index).trim();
		return input.trim();
	}

	/**
	 * 以逗号分隔，返回最后一段，如果没有逗号返回全部
	 * 
	 * @param input
	 * @return
	 */
	public static String getLastPartByComma(String input) {
		int index = input.lastIndexOf(',');
		if (index != -1)
			return input.substring(index + 1).trim();
		return input.trim();
	}
	
	/**
	 * 处理文件名（中文变拼音，只保留字母、数字、下划线、点）
	 * @param fileName
	 * @return
	 */
	public static String processFileName(String fileName) {
		if (StringUtils.isBlankOrEmpty(fileName)) {
			return fileName;
		}
		Matcher m = RegexUtils.doMatcher(fileName, "[\u4E00-\u9FA5]");
		while (m.find()) {
			String ss = m.group();
			fileName = fileName.replaceAll(ss, PinyinUtils.getPingYin(ss));
		}
		fileName = fileName.replaceAll("[^0-9a-zA-Z\\._]", "");
		return fileName;  
	}

	public static String cleanNormalUTF8String(String input) {
		if (isBlankOrEmpty(input))
			return input;
		byte[] in = input.getBytes(Charset.forName("utf-8"));
		byte[] out = new byte[in.length];
		int length = 0;
		for (int i = 0; i < in.length; i++) {
			int t = 0xff & in[i];
			if (t >= 240) {
				i += 3;
			} else {
				out[length++] = in[i];
			}
		}
		return new String(out, 0, length, Charset.forName("utf-8"));
	}
}
