package com.lifetools.commons.vo;

import com.lifetools.commons.constants.StatusCode;
import com.lifetools.commons.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 接口回复基础类
 * @author zk
 * @date 2016年1月20日 上午9:43:33
 */
public class ResponseBase {
	/** 状态码 */
	private int statusCode;
	/** 描述信息 */
	private String statusDesc;
	/** 数据域 */
	private Object data;
	/** 页码信息 */
	private PageInfo pageInfo;
	/** 参数信息 */
	private Object paramsInfo;
	
	public ResponseBase(StatusCode statusCode) {
		this.statusCode = statusCode.getCode();
		this.statusDesc = statusCode.getDesc();
	}
	
	public ResponseBase(StatusCode statusCode, Object data) {
		this(statusCode);
		this.data = data;
	}
	
	public ResponseBase(StatusCode statusCode, Exception e) {
		this(statusCode);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		String  expMessage = buf.toString();
		if (buf != null) {
			try {
				buf.close();
			} catch (IOException e1) {
				
			}
		}
		this.statusDesc += ";-------" + expMessage;
	}
	
	public ResponseBase(StatusCode statusCode, Object datas, String appendDesc) {
		this(statusCode);
		if (StringUtils.isNotBlank(appendDesc)) {
			appendDesc = ";-------" + appendDesc;
		}
		this.statusDesc += appendDesc;
		this.data = data;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public Object getParamsInfo() {
		return paramsInfo;
	}

	public void setParamsInfo(Object paramsInfo) {
		this.paramsInfo = paramsInfo;
	}
}
