package com.atat.soft.common.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public enum ResultCode {
    SUCCESS(0, "成功"), //
    ERROR(-1, "未知错误"), //
    PARAM_EMPTY_ERROR(-2, "重要参数为空"), //
    DB_ERROR(-3, "DAO相关错误"), //
    SYSTEM_ERROR(-4,"系统出错"),
    NOT_LOGIN(-5, "尚未登录"), //
    CATEGORY_NOT_EXIST(-6, "设备类型不存在"), //
    METHODARGUMENTNOTVALI(-7," 参数数据不合法"),
    SESSION_EXPIRED(1000, "会话过期");

    private int code;

    private String info;

    private ResultCode(int code, String info){
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfoForUrl() {
        String encodingInfo = null;
        try {
            encodingInfo = URLEncoder.encode(info, "utf-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodingInfo;
    }

    public String getInfo() {
        return info;
    }
}
