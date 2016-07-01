package com.lifetools.commons.utils;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.apache.commons.codec.binary.Base64;

/**
 * 加密解密工具类
 * @author zk
 * @date 2016年2月23日 下午3:13:03
 */
public class EncryptUtils {

	public static String hash(String algorithm, String origin) {
		if (origin == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(origin.getBytes());
			return StringUtils.byteArrayToHexString(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String hashByMD5(String origin) {
		return hash("MD5", origin);
	}

	public static String hashBySHA1(String origin) {
		return hash("SHA-1", origin);
	}

	public static String encodeByBase64(String origin, String charset) {
		return Base64.encodeBase64String(origin.getBytes(Charset.forName(charset)));
	}

	public static String decodeFromBase64(String encrypt, String charset) {
		return new String(Base64.decodeBase64(encrypt), Charset.forName(charset));
	}
	
	public static String encodeByBase64(String origin) {
		return encodeByBase64(origin, "UTF-8");
	}
	
	public static String decodeFromBase64(String encrypt) {
		return decodeFromBase64(encrypt, "UTF-8");
	}
}
