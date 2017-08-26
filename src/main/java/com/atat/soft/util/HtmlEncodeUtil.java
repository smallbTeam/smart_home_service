package com.atat.soft.util;

/**
 * 【类或接口功能描述】
 *
 * @author wangxulu
 * @date 2013-12-18
 * @version 1.0
 */
public class HtmlEncodeUtil {

    public static String encode(String str) {
        str = str.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\'", "'").replaceAll("\"", "&quot;").replaceAll("\n", "<br/>");
        return str;
    }

    public static String decode(String str) {
        str = str.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("'", "\'").replaceAll("&quot;", "\"").replaceAll("<br/>", "\n");
        return str;
    }
}
