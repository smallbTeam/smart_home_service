/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ligw
 * @version $Id IpUtil.java, v 0.1 2017-08-13 18:16 ligw Exp $$
 */
public class IpUtil {
    public static  Map<String, Object> getIpInfo(HttpServletRequest request) throws Exception {
        Map<String, Object> ipInfoMap = new HashMap<String, Object>();
        //请求1
        //是获得客户端的ip地址。
        String ipLocal = request.getHeader("x-forwarded-for");
        // 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
        if ((null != ipLocal) && (!"".equals(ipLocal.trim()))
                && (!"unknown".equalsIgnoreCase(ipLocal.trim())) && (ipLocal.indexOf(",") > -1)) {
            String[] array = ipLocal.split(",");
            for (String element : array) {
                if (isIP(element)) {
                    ipLocal = element;
                    break;
                }
            }
        }
        if (!isIP(ipLocal)) {
            ipLocal = request.getHeader("X-Real-IP");
        }
        if(!isIP(ipLocal)){
            ipLocal = request.getHeader("Proxy-Client-IP");
        }
        if(!isIP(ipLocal)){
            ipLocal = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!isIP(ipLocal)) {
            ipLocal = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!isIP(ipLocal)) {
            ipLocal = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if(!isIP(ipLocal)){
            ipLocal = request.getRemoteAddr();
        }
        ipLocal = ipLocal.equals("0:0:0:0:0:0:0:1")? "127.0.0.1" :ipLocal;

        //是获得客户端的主机名。
        String reshostName = request.getRemoteHost();

        // mac地址
        String line = "";
        String macAddress = "";
        final String MAC_ADDRESS_PREFIX = "MAC Address = ";
        final String LOOPBACK_ADDRESS = "127.0.0.1";
        // 如果为127.0.0.1,则获取本地MAC地址。
        if (LOOPBACK_ADDRESS.equals(ipLocal)) {
            InetAddress inetAddress = InetAddress.getLocalHost();
            // 貌似此方法需要JDK1.6。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            // 下面代码是把mac地址拼装成String
            StringBuilder sb = new StringBuilder();
            for (int i = 0;i < mac.length;i++) {
                if (i != 0) {
                    sb.append("-");
                }
                // mac[i] & 0xFF 是为了把byte转化为正整数
                String s = Integer.toHexString(mac[i] & 0xFF);
                sb.append(s.length() == 1 ? 0 + s : s);
            }
            // 把字符串所有小写字母改为大写成为正规的mac地址并返回
            macAddress = sb.toString().trim().toUpperCase();
        }else {
            //获取非本地IP的MAC地址
            try {
                Process p = Runtime.getRuntime().exec("nbtstat -A " + ipLocal);
                InputStreamReader isr = new InputStreamReader(p.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                while ((line = br.readLine()) != null) {
                    if (line != null) {
                        int index = line.indexOf(MAC_ADDRESS_PREFIX);
                        if (index != -1) {
                            macAddress = line.substring(index + MAC_ADDRESS_PREFIX.length()).trim().toUpperCase();
                        }
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }

        //如果是本地IP则通过http请求获取因特网IP
        String ipNet = ipLocal;
        if (LOOPBACK_ADDRESS.equals(ipLocal)) {
            //结果：var returnCitySN = {"cip": "111.166.15.236", "cid": "120000", "cname": "天津市"};
            ipNet = getHttpURLConnection("http://pv.sohu.com/cityjson?ie=utf-8",null, "utf-8");
            String temp[]=ipNet.split("=");
            String JsonString = temp[1].replaceAll(";","");
            System.out.println(JsonString);
            //将String转化成json对象 并获取到ip
            JSONObject jsonObject =  JSON.parseObject(JsonString);
            ipNet=jsonObject.getString("cip");
        }
        if(!isIP(ipNet)){
            //结果：|111.166.15.236
            ipNet = getHttpURLConnection("http://www.w3dev.cn/getip.ashx",null, "utf-8");
            String temp[]=ipNet.split("\\|");
            ipNet = temp[1];
        }

        //依据IP获取位置信息
        Map<String, String> addressInfoMap = new HashMap<String, String>();
        if(isIP(ipNet)){
            addressInfoMap = getAddressInfoByIP(ipNet);
        }
        ipInfoMap.put("IpInfo", ipLocal);
        ipInfoMap.put("ipNet", ipNet);
        ipInfoMap.put("hostName", reshostName);
        ipInfoMap.put("macAddress", macAddress);
        ipInfoMap.put("addressInfoMap", addressInfoMap);
        return ipInfoMap;
    }

    public  static  boolean isIP(final String remoteAddr) {
        boolean isIP = false;
        if ((null != remoteAddr) && (!"".equals(remoteAddr.trim()))
                && (!"unknown".equalsIgnoreCase(remoteAddr.trim())) && (remoteAddr.length() > 7)
                && (remoteAddr.length() < 16)) {
            /**
             * 判断IP格式和范围
             */
            String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
            Pattern pat = Pattern.compile(rexp);
            Matcher mat = pat.matcher(remoteAddr);
            isIP = mat.find();
        }
        return isIP;
    }

    public  static  Map<String, String> getAddressInfoByIP(String ip_address) throws IOException{
        Map<String, String> addressInfoMap = new HashMap<String, String>();
        //结果：var remote_ip_info = {"ret":1,"start":-1,"end":-1,"country":"\u4e2d\u56fd","province":"\u5929\u6d25","city":"\u5929\u6d25","district":"","isp":"","type":"","desc":""};
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ip", ip_address);
        String address_str = getHttpURLConnection("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js",null, "utf-8");
        String temp[]=address_str.split("=");
        String JsonString = temp[1].replaceAll(";","");
        //将String转化成json对象 并获取到ip
        JSONObject jsonObject =  JSON.parseObject(JsonString);
        String ret = jsonObject.getString("ret");
        String start = jsonObject.getString("start");
        String end = jsonObject.getString("end");
        String country = decodeUnicode(jsonObject.getString("country"));
        String province = decodeUnicode(jsonObject.getString("province"));
        String city = decodeUnicode(jsonObject.getString("city"));
        String district = jsonObject.getString("district");
        String isp = jsonObject.getString("isp");
        String type = jsonObject.getString("type");
        String desc =jsonObject.getString("desc");

        addressInfoMap.put("ret", ret);
        addressInfoMap.put("start", start);
        addressInfoMap.put("end", end);
        addressInfoMap.put("country", country);
        addressInfoMap.put("province", province);
        addressInfoMap.put("city", city);
        addressInfoMap.put("district", district);
        addressInfoMap.put("isp", isp);
        addressInfoMap.put("type", type);
        addressInfoMap.put("desc", desc);
        return addressInfoMap;
    }

    public  static  String getHttpURLConnection(String urlParam,Map<String, Object> params, String charset){
        StringBuffer resultBuffer = null;
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection con = null;
        BufferedReader br = null;
        try {
            URL url = null;
            if ((sbParams != null) && (sbParams.length() > 0)) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (con != null) {
                        con.disconnect();
                        con = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }

    public  static  String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }
}
