package com.lifetools.commons.util;

/**
 * Created by Zheng.Ke
 * Date 2016/8/26.
 */
public class ValidationResult {
    //校验结果是否有错
    private boolean hasErrors;
    //校验错误描述
    private String errorDesc;

    public boolean hasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
