package com.lifetools.commons.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送http请求辅助类（支持几乎所有请求的发送）
 * @author Zheng.Ke
 * @date 2016年7月1日 下午5:10:31
 */
public class HttpUtils {
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";

	/**
	 * get请求
	 * @param scc  自定义的cookie名，若不网站不要带cookie，传null即可
	 * @param uri  URL
	 * @return
	 */
	public static String sendWebRequestByGet(String scc, String uri) {
		return sendWebRequestByGet(scc, uri, null);
	}

	/**
	 * get请求
	 * @param scc  自定义的cookie名，若不网站不要带cookie，传null即可
	 * @param uri  URL
	 * @param forceRespEncoding  返回值编码 默认UTF-8
	 * @return
	 */
	public static String sendWebRequestByGet(String scc, String uri, String forceRespEncoding) {
		BasicCookieStore bcs = null;
		if (scc != null) {
			bcs = CookieManager.getCookieStore(scc);
			if (bcs == null) {
				bcs = new BasicCookieStore();
				CookieManager.setCookieStore(scc, bcs);
			}
		}
		return sendWebRequest(uri, HttpMethod.GET, null, null, forceRespEncoding, null, bcs);
	}

	/**
	 * 发送JSON请求
	 * @param scc   自定义的cookie名，若不网站不要带cookie，传null即可
	 * @param uri   URL
	 * @param jsonStr  json字符串
	 * @return
	 */
	public static String sendWebRequestByPostJsonStr(String scc, String uri, String jsonStr) {
		return sendWebRequestByPost(scc, uri, jsonStr, null, JSON_CONTENT_TYPE);
	}

	public static String sendWebRequestByPost(String scc, String uri, String contents, String reqEncoding, String contentType) {
		BasicCookieStore bcs = null;
		if (scc != null) {
			bcs = CookieManager.getCookieStore(scc);
			if (bcs == null) {
				bcs = new BasicCookieStore();
				CookieManager.setCookieStore(scc, bcs);
			}
		}
		Map<String, String> headers = new HashMap<String, String>();
		if (contentType != null) {
			headers.put("Content-Type", contentType);
		}
		return sendWebRequest(uri, HttpMethod.POST, headers, contents, reqEncoding, null, null, bcs);
	}

	public static String sendWebRequestByForm(String scc, String uri, Map<String, String> params) {
		return sendWebRequestByForm(scc, uri, null, params, null);
	}

	public static String sendWebRequestByForm(String scc, String uri, Map<String, String> headers, Map<String, String> params, String reqEncoding) {
		BasicCookieStore bcs = null;
		if (scc != null) {
			bcs = CookieManager.getCookieStore(scc);
			if (bcs == null) {
				bcs = new BasicCookieStore();
				CookieManager.setCookieStore(scc, bcs);
			}
		}
		boolean hasContentType = false;
		Map<String, String> kvpHeaders = new HashMap<String, String>();
		if (headers != null)
			for (String key : headers.keySet()) {
				if (key.equalsIgnoreCase("Content-Type")) {
					hasContentType = true;
				}
				kvpHeaders.put(key, headers.get(key));
			}
		if (!hasContentType) {
			kvpHeaders.put("Content-Type", DEFAULT_CONTENT_TYPE);
		}
		return sendWebRequest(uri, HttpMethod.POST, kvpHeaders, formToUrlEncodedString(params, reqEncoding), reqEncoding, null, null, bcs);

	}

	public static String sendWebRequest(String uri, HttpMethod httpMethod, Map<String, String> headers, String contents, String reqEncoding, String forceRespEncoding, Integer timeout, BasicCookieStore cookieStore) {
		if (contents == null) {
			contents = "";
		}
		if (isBlankOrEmpty(reqEncoding)) {
			reqEncoding = "UTF-8";
		}
		return sendWebRequest(uri, httpMethod, headers, contents.getBytes(Charset.forName(reqEncoding)), forceRespEncoding, timeout, cookieStore);
	}

	public static String sendWebRequest(String uri, HttpMethod httpMethod, Map<String, String> headers, byte[] contents, String forceRespEncoding, Integer timeout, BasicCookieStore cookieStore) {
		if (timeout == null || timeout < 1000) {
			timeout = 10000;
		} else if (timeout > 100000) {
			timeout = 100000;
		}
		if (cookieStore == null)
			cookieStore = new BasicCookieStore();

		try {
			RequestConfig defaultRequestConfig = RequestConfig.custom()
					                             .setSocketTimeout(timeout)
					                             .setConnectTimeout(timeout)
					                      		 .setConnectionRequestTimeout(timeout)
					                             .build();
			CloseableHttpClient httpclient = null;
			try {
				if (uri.startsWith("https://")) {
					SSLContext ctx = SSLContext.getInstance("SSL");
					ctx.init(null, new TrustManager[] {new X509TrustManager() { // 不需要实现
						public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
						}
						public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
						}
						public X509Certificate[] getAcceptedIssuers() {
							return new X509Certificate[0];
						}
					}}, null);
					SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx);
					httpclient = HttpClients.custom()
							     .setDefaultCookieStore(cookieStore)
							     .setDefaultRequestConfig(defaultRequestConfig)
							     .setSSLSocketFactory(ssf)
							     .build();
				} else {
					httpclient = HttpClients.custom()
							     .setDefaultCookieStore(cookieStore)
							     .setDefaultRequestConfig(defaultRequestConfig)
							     .build();
				}
				CloseableHttpResponse response = null;
				HttpRequestBase request = null;
				if (httpMethod == HttpMethod.GET) {
					request = new HttpGet(uri);
				} else {
					request = new HttpPost(uri);
					ByteArrayEntity byteEntity = new ByteArrayEntity(contents);
					((HttpPost) request).setEntity(byteEntity);
				}
				if (headers != null) {
					for (String key : headers.keySet()) {
						request.setHeader(key, headers.get(key));
					}
				}
				response = httpclient.execute(request);
				try {
					if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 201) {
						if (response.getStatusLine().getStatusCode() == 302)
							return response.getHeaders("Location")[0].getValue();
						return null;
					}
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						if (forceRespEncoding == null)
							return EntityUtils.toString(entity);
						else
							return byteArrayToString(EntityUtils.toByteArray(entity), forceRespEncoding);
					}
				} finally {
					if (response != null)
						response.close();
				}
			} finally {
				if (httpclient != null)
					httpclient.close();
			}
		} catch (Exception e) {
			System.out.println("web request exception: " + uri);
			e.printStackTrace();
		}
		return null;
	}

	private static String formToUrlEncodedString(Map<String, String> params) {
		return formToUrlEncodedString(params, "UTF-8");
	}

	private static String formToUrlEncodedString(Map<String, String> params, String charset) {
		StringBuilder sb = new StringBuilder();
		if (params != null && params.size() > 0)
			for (String key : params.keySet()) {
				sb.append(key);
				sb.append('=');
				sb.append(urlEncode(params.get(key), charset));
				sb.append('&');
			}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	public static Map<String, String> urlEncodedStringToForm(String encoded) {
		return urlEncodedStringToForm(encoded, "UTF-8");
	}

	public static Map<String, String> urlEncodedStringToForm(String encoded, String charset) {
		if (isBlankOrEmpty(encoded)) {
			return null;
		}
		Map<String, String> retval = new HashMap<String, String>();
		String[] params = encoded.split("&");
		for (String param : params) {
			String[] kvp = param.split("=");
			retval.put(kvp[0], kvp.length > 1 ? urlDecode(kvp[1], charset) : "");
		}
		return retval;
	}

	/**
	 * URL编码
	 * @param origin  要编码的文本
	 * @return
	 */
	public static String urlEncode(String origin) {
		return urlEncode(origin, "UTF-8");
	}

	/**
	 * URL编码
	 * @param origin  要编码的文本
	 * @param charset   编码格式
	 * @return
	 */
	public static String urlEncode(String origin, String charset) {
		try {
			if (isBlankOrEmpty(charset)) {
				charset = "UTF-8";
			}
			return URLEncoder.encode(origin, charset);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	/**
	 * URL解码
	 * @param encoded  要解码的文本
	 * @return
	 */
	public static String urlDecode(String encoded) {
		return urlDecode(encoded, "UTF-8");
	}

	/**
	 * URL解码
	 * @param encoded  要解码的文本
	 * @param charset   解码格式
	 * @return
	 */
	public static String urlDecode(String encoded, String charset) {
		try {
			if (isBlankOrEmpty(charset)) {
				charset = "UTF-8";
			}
			return URLDecoder.decode(encoded, charset);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	private static String addParamsToUrl(String url, Map<String, String> params, boolean isNeedUrlEncode, String urlEncoding) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(url.trim());
			if (url.contains("?")) {
				if (sb.charAt(sb.length() - 1) != '?' && sb.charAt(sb.length() - 1) != '&') {
					sb.append('&');
				}
			} else {
				sb.append('?');
			}
			if (params != null && params.size() > 0)
				for (String key : params.keySet()) {
					sb.append(key);
					sb.append('=');
					if (isNeedUrlEncode) {
						sb.append(URLEncoder.encode(params.get(key), urlEncoding));
					} else {
						sb.append(params.get(key));
					}
					sb.append('&');
				}
			sb.deleteCharAt(sb.length() - 1);

			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String addParamsToContent(Map<String, String> params, boolean isNeedUrlEncode, String urlEncoding)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		if (params != null && params.size() > 0)
			for (String key : params.keySet()) {
				sb.append(key);
				sb.append('=');
				if (isNeedUrlEncode) {
					sb.append(URLEncoder.encode(params.get(key), urlEncoding));
				} else {
					sb.append(params.get(key));
				}
				sb.append('&');
			}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.toString();
	}

	private static String byteArrayToString(byte[] bytes, String encoding) {
		return new String(bytes, Charset.forName(encoding));
	}

	private static boolean isBlankOrEmpty(String s) {
		if (s == null || s.trim().equals("")) {
			return true;
		}
		return false;
	}

}
