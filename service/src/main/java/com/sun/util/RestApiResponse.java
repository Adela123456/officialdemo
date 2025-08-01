package com.sun.util;

import org.apache.commons.lang3.StringUtils;

public class RestApiResponse<T> {
    private static final String SUCCESS_RESULT_CODE = "00100000";
    private String resCode;
    private String msg;
    private T obj;

    public RestApiResponse() {
    }

    public RestApiResponse(T data) {
        this.resCode = "00100000";
        this.msg = "操作成功";
        this.obj = data;
    }

    public RestApiResponse(String resCode, String msg) {
        this.resCode = resCode;
        this.msg = msg;
    }

    public String getResCode() {
        return this.resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return this.obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public boolean success() {
        return StringUtils.isNotBlank(this.resCode) && "00100000".equals(this.resCode);
    }
}

