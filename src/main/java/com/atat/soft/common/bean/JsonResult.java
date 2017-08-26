package com.atat.soft.common.bean;

import java.io.Serializable;

/**
 * Created by DELL on 2017/6/6.
 */
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1717488731978480120L;
    private int code;
    private String msg;
    private T obj;

    public JsonResult() {
    }

    public JsonResult(T obj) {
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return msg;
    }

    public void setErrorMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
