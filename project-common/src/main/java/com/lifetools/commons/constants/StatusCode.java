package com.lifetools.commons.constants;

/**
 * 状态码
 * @author zk
 * @date 2016年1月20日 上午9:43:33
 */
public enum StatusCode {
    CODE_200(200, "请求成功。"),


    CODE_400(400, "请求方错误。"),
    CODE_401(401, "请求参数不正确。"),


    CODE_500(500, "服务方错误。"),
    ;

    private int code;
    private String desc;
    
    StatusCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }   
}
