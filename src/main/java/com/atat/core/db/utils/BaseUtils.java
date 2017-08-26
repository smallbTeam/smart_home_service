//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.atat.core.db.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BaseUtils {
    private static final BeanUtilsBean bean = BeanUtilsBean.getInstance();

    public BaseUtils() {
    }

    public static double mathAdd(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double mathAdd(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).doubleValue();
    }

    public static double mathSub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static double mathSub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).doubleValue();
    }

    public static double mathMul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double mathMul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double mathDiv(double v1, double v2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static double mathDiv(String v1, String v2, int scale) {
        if(scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static Object getPrivateProperty(Object object, String propertyName) throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static void setPrivateProperty(Object object, String propertyName, Object newValue) throws IllegalAccessException, NoSuchFieldException {
        Field field = object.getClass().getDeclaredField(propertyName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object[] params) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class[] types = new Class[params.length];

        for(int i = 0; i < params.length; ++i) {
            types[i] = params[i].getClass();
        }

        Method method = object.getClass().getDeclaredMethod(methodName, types);
        method.setAccessible(true);
        return method.invoke(object, params);
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object param) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        return invokePrivateMethod(object, methodName, new Object[]{param});
    }

    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if(method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
        } else {
            try {
                return method.invoke(obj, args);
            } catch (Exception var6) {
                return !(var6 instanceof IllegalAccessException) && !(var6 instanceof IllegalArgumentException) && !(var6 instanceof NoSuchMethodException)?(var6 instanceof InvocationTargetException?new RuntimeException("Reflection Exception.", ((InvocationTargetException)var6).getTargetException()):(var6 instanceof RuntimeException?(RuntimeException)var6:new RuntimeException("Unexpected Checked Exception.", var6))):new IllegalArgumentException("Reflection Exception.", var6);
            }
        }
    }

    public static Method getAccessibleMethod(Object obj, String methodName, Class<?>[] parameterTypes) {
        Class superClass = obj.getClass();

        while(superClass != Object.class) {
            try {
                Method method = superClass.getDeclaredMethod(methodName, parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException var5) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

//    public static void copyProperties(Object dest, Object orig) {
//        copyProperties(dest, orig, true, (String)null);
//    }

//    public static void copyNonNullProperties(Object dest, Object orig) {
//        copyProperties(dest, orig, false, (String)null);
//    }
//
//    public static void copyProperties(Object dest, Object orig, String split) {
//        copyProperties(dest, orig, true, split);
//    }

//    public static void copyNonNullProperties(Object dest, Object orig, String split) {
//        copyProperties(dest, orig, false, split);
//    }

    public static void copyMap(Map dest, Object orig) {
        copyMap(dest, orig, true, (String[])null);
    }

    public static void copyNonNullMap(Map dest, Object orig) {
        copyMap(dest, orig, false, (String[])null);
    }

    public static void copyMap(Map dest, Object orig, String[] ext) {
        copyMap(dest, orig, true, ext);
    }

    public static void copyNonNullMap(Map dest, Object orig, String[] ext) {
        copyMap(dest, orig, false, ext);
    }

    private static void copyMap(Map dest, Object orig, boolean copyNulls, String[] ext) {
        if(dest == null) {
            throw new IllegalArgumentException("No destination bean specified");
        } else if(orig == null) {
            throw new IllegalArgumentException("No origin bean specified");
        } else {
            int i;
            String name;
            Object value;
            if(orig instanceof DynaBean) {
                DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();

                for(i = 0; i < origDescriptors.length; ++i) {
                    name = origDescriptors[i].getName();
                    value = ((DynaBean)orig).get(name);

                    try {
                        if((value != null || copyNulls) && !searchInArray(name, ext)) {
                            dest.put(name, value);
                        }
                    } catch (Exception var11) {
                        ;
                    }
                }
            } else if(orig instanceof Map) {
                Iterator names = ((Map)orig).keySet().iterator();

                while(names.hasNext()) {
                    String nameNext = (String)names.next();
                    Object valueNext = ((Map)orig).get(nameNext);

                    try {
                        if((valueNext != null || copyNulls) && !searchInArray(nameNext, ext)) {
                            dest.put(nameNext, valueNext);
                        }
                    } catch (Exception var10) {
                        ;
                    }
                }
            } else {
                PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(orig);

                for(i = 0; i < origDescriptors.length; ++i) {
                    name = origDescriptors[i].getName();
                    if(!"class".equals(name) && bean.getPropertyUtils().isReadable(orig, name)) {
                        try {
                            value = bean.getPropertyUtils().getSimpleProperty(orig, name);
                            if((value != null || copyNulls) && !searchInArray(name, ext)) {
                                dest.put(name, value);
                            }
                        } catch (Exception var9) {
                            ;
                        }
                    }
                }
            }

        }
    }

//    private static void copyProperties(Object dest, Object orig, boolean copyNulls, String split) {
//        if(dest == null) {
//            throw new IllegalArgumentException("No destination bean specified");
//        } else if(orig == null) {
//            throw new IllegalArgumentException("No origin bean specified");
//        } else {
//            int i;
//            String dname;
//            Object value;
//            if(orig instanceof DynaBean) {
//                DynaProperty[] origDescriptors = ((DynaBean)orig).getDynaClass().getDynaProperties();
//
//                for(i = 0; i < origDescriptors.length; ++i) {
//                    dname = origDescriptors[i].getName();
//                    dname = getName(dname, split);
//                    if(bean.getPropertyUtils().isWriteable(dest, dname)) {
//                        value = ((DynaBean)orig).get(dname);
//
//                        try {
//                            if(value != null || copyNulls) {
//                                bean.copyProperty(dest, dname, value);
//                            }
//                        } catch (Exception var12) {
//                            ;
//                        }
//                    }
//                }
//            } else if(orig instanceof Map) {
//                Iterator names = ((Map)orig).keySet().iterator();
//
//                while(true) {
//                    String name;
//                    do {
//                        if(!names.hasNext()) {
//                            return;
//                        }
//
//                        name = (String)names.next();
//                        dname = getName(name, split);
//                    } while(!bean.getPropertyUtils().isWriteable(dest, dname));
//
//                    Object value = ((Map)orig).get(name);
//
//                    try {
//                        if(value != null || copyNulls) {
//                            bean.copyProperty(dest, dname, value);
//                        }
//                    } catch (Exception var11) {
//                        ;
//                    }
//                }
//            } else {
//                PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(orig);
//
//                for(i = 0; i < origDescriptors.length; ++i) {
//                    dname = origDescriptors[i].getName();
//                    dname = getName(dname, split);
//                    if(!"class".equals(dname) && bean.getPropertyUtils().isReadable(orig, dname) && bean.getPropertyUtils().isWriteable(dest, dname)) {
//                        try {
//                            value = bean.getPropertyUtils().getSimpleProperty(orig, dname);
//                            if(value != null || copyNulls) {
//                                bean.copyProperty(dest, dname, value);
//                            }
//                        } catch (Exception var10) {
//                            ;
//                        }
//                    }
//                }
//            }
//
//        }
//    }

//    public static void replaceNullProperties(Object obj) {
//        replaceNullProperties(obj, (String[])null);
//    }

//    public static void replaceNullProperties(Object obj, String[] ext) {
//        if(obj == null) {
//            throw new IllegalArgumentException("No destination bean specified");
//        } else {
//            int i;
//            String name;
//            Object value;
//            if(obj instanceof DynaBean) {
//                DynaProperty[] origDescriptors = ((DynaBean)obj).getDynaClass().getDynaProperties();
//
//                for(i = 0; i < origDescriptors.length; ++i) {
//                    name = origDescriptors[i].getName();
//                    value = ((DynaBean)obj).get(name);
//
//                    try {
//                        if(value == null && !searchInArray(name, ext)) {
//                            ((DynaBean)obj).set(name, "");
//                        }
//                    } catch (Exception var9) {
//                        ;
//                    }
//                }
//            } else if(obj instanceof Map) {
//                Iterator names = ((Map)obj).keySet().iterator();
//
//                while(names.hasNext()) {
//                    String name = (String)names.next();
//                    Object value = ((Map)obj).get(name);
//
//                    try {
//                        if(value == null && !searchInArray(name, ext)) {
//                            ((Map)obj).put(name, "");
//                        }
//                    } catch (Exception var8) {
//                        ;
//                    }
//                }
//            } else {
//                PropertyDescriptor[] origDescriptors = bean.getPropertyUtils().getPropertyDescriptors(obj);
//
//                for(i = 0; i < origDescriptors.length; ++i) {
//                    name = origDescriptors[i].getName();
//                    if(!"class".equals(name) && bean.getPropertyUtils().isReadable(obj, name) && bean.getPropertyUtils().isWriteable(obj, name)) {
//                        try {
//                            value = bean.getPropertyUtils().getSimpleProperty(obj, name);
//                            if(value == null && !searchInArray(name, ext)) {
//                                bean.setProperty(obj, name, "");
//                            }
//                        } catch (Exception var7) {
//                            ;
//                        }
//                    }
//                }
//            }
//
//        }
//    }

    private static boolean searchInArray(String key, String[] arry) {
        if(arry != null && arry.length > 0) {
            String[] var2 = arry;
            int var3 = arry.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String s = var2[var4];
                if(key.equals(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String getName(String name, String split) {
        if(StringUtils.isBlank(split)) {
            return name;
        } else {
            String[] names = name.split(split);
            String result = name;
            if(names.length > 1) {
                result = names[0];

                for(int i = 1; i < names.length; ++i) {
                    if(result.length() == 1 && i == 1) {
                        result = result + names[i];
                    } else {
                        result = result + names[i].substring(0, 1).toUpperCase() + names[i].substring(1, names[i].length());
                    }
                }
            }

            return result;
        }
    }

    public static byte[] int2bytes(int num) {
        byte[] b = new byte[4];

        for(int i = 0; i < 4; ++i) {
            b[i] = (byte)(num >>> 24 - i * 8);
        }

        return b;
    }

//    public static int bytes2int(byte[] b) {
//        int mask = 255;
//        int temp = false;
//        int res = 0;
//
//        for(int i = 0; i < 4; ++i) {
//            res <<= 8;
//            int temp = b[i] & mask;
//            res |= temp;
//        }
//
//        return res;
//    }

    public static String byte2hex(byte[] source) {
        StringBuffer result = new StringBuffer();
        String item = "";

        for(int i = 0; i < source.length; ++i) {
            item = Integer.toHexString(source[i] & 255);
            if(item.length() == 1) {
                result.append("0");
                result.append(item);
            } else {
                result.append(item);
            }
        }

        return result.toString().toUpperCase();
    }

    public static byte[] hex2bin(String hex) {
        String digital = "0123456789ABCDEF";
        char[] hex2char = hex.toCharArray();
        byte[] bytes = new byte[hex.length() / 2];

        for(int i = 0; i < bytes.length; ++i) {
            int temp = digital.indexOf(hex2char[2 * i]) * 16;
            temp += digital.indexOf(hex2char[2 * i + 1]);
            bytes[i] = (byte)(temp & 255);
        }

        return bytes;
    }

    public static String encodeSourceByUTF8(String source) {
        if(source != null && source.length() > 0) {
            try {
                return URLEncoder.encode(source, "UTF-8");
            } catch (UnsupportedEncodingException var2) {
                throw new RuntimeException(var2);
            }
        } else {
            return "";
        }
    }

    public static String StringSubContent(String source, int targetCount, String more) {
        String result = "";

        try {
            if(source.getBytes("GBK").length <= targetCount) {
                return source;
            } else {
                int t = 0;
                char[] tempChar = source.toCharArray();

                for(int i = 0; i < tempChar.length && t < targetCount; ++i) {
                    if(tempChar[i] > 256) {
                        result = result + tempChar[i];
                        t += 2;
                    } else {
                        result = result + tempChar[i];
                        ++t;
                    }
                }

                result = result + more;
                return result;
            }
        } catch (UnsupportedEncodingException var7) {
            throw new RuntimeException(var7);
        }
    }

    public static String sbc2dbc(String sbcContent) {
        if(sbcContent == null) {
            return null;
        } else {
            String outStr = "";
            String Tstr = "";
            byte[] b = null;

            for(int i = 0; i < sbcContent.length(); ++i) {
                try {
                    Tstr = sbcContent.substring(i, i + 1);
                    b = Tstr.getBytes("unicode");
                } catch (UnsupportedEncodingException var7) {
                    var7.printStackTrace();
                }

                if(b[3] == -1) {
                    b[2] = (byte)(b[2] + 32);
                    b[3] = 0;

                    try {
                        outStr = outStr + new String(b, "unicode");
                    } catch (UnsupportedEncodingException var6) {
                        var6.printStackTrace();
                    }
                } else {
                    outStr = outStr + Tstr;
                }
            }

            return outStr;
        }
    }

    public static String dbc2sbc(String dbcContent) {
        if(dbcContent == null) {
            return null;
        } else {
            String outStr = "";
            String Tstr = "";
            byte[] b = null;

            for(int i = 0; i < dbcContent.length(); ++i) {
                try {
                    Tstr = dbcContent.substring(i, i + 1);
                    b = Tstr.getBytes("unicode");
                } catch (UnsupportedEncodingException var7) {
                    var7.printStackTrace();
                }

                if(b[3] != -1) {
                    b[2] = (byte)(b[2] - 32);
                    b[3] = -1;

                    try {
                        outStr = outStr + new String(b, "unicode");
                    } catch (UnsupportedEncodingException var6) {
                        var6.printStackTrace();
                    }
                } else {
                    outStr = outStr + Tstr;
                }
            }

            return outStr;
        }
    }

    public static String array2String(String[] arrays) {
        return array2String(arrays, ",");
    }

    public static String array2String(String[] arrays, String splitChar) {
        String resultString = "";
        if(arrays != null && arrays.length != 0) {
            StringBuffer tmpstring = new StringBuffer();
            boolean flag = false;
            String[] var5 = arrays;
            int var6 = arrays.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                String tmps = var5[var7];
                if(flag) {
                    tmpstring.append(splitChar);
                }

                tmpstring.append(tmps.trim());
                flag = true;
            }

            resultString = tmpstring.toString();
        }

        return resultString;
    }

    public static String list2String(List<String> stringlist) {
        return list2String(stringlist, ",");
    }

    public static String list2String(List<String> stringlist, String splitChar) {
        String resultString = "";
        if(stringlist != null && stringlist.size() != 0) {
            StringBuffer tmpstring = new StringBuffer();
            boolean flag = false;

            for(Iterator var5 = stringlist.iterator(); var5.hasNext(); flag = true) {
                String tmps = (String)var5.next();
                if(flag) {
                    tmpstring.append(splitChar);
                }

                tmpstring.append(tmps.trim());
            }

            resultString = tmpstring.toString();
        }

        return resultString;
    }

    public static String[] string2Array(String string) {
        return string2Array(string, ",");
    }

    public static String[] string2Array(String string, String splitChar) {
        String[] tmpArray = null;
        if(string != null && !"".equals(string.trim())) {
            tmpArray = StringUtils.splitByWholeSeparator(string, splitChar);
        }

        return tmpArray;
    }

    public static List<String> string2List(String string) {
        return string2List(string, ",");
    }

    public static List<String> string2List(String string, String splitChar) {
        List<String> tmpList = null;
        if(string != null && !"".equals(string.trim())) {
            tmpList = Arrays.asList(StringUtils.splitByWholeSeparator(string, splitChar));
        }

        return tmpList;
    }

    public static String replace(String source, String[] keywords, String target) {
        if(!StringUtils.isBlank(source) && !StringUtils.isBlank(target) && keywords != null && keywords.length != 0) {
            String result = source;
            String[] var4 = keywords;
            int var5 = keywords.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String keyword = var4[var6];
                if(!StringUtils.isBlank(keyword)) {
                    result = StringUtils.replace(result, keyword, target);
                }
            }

            return result;
        } else {
            return null;
        }
    }

    public static String null2Empty(String source) {
        return source != null && !StringUtils.equalsIgnoreCase(source, "null")?source:"";
    }

    public static String StringConcat(String source1, String source2) {
        StringBuffer result = new StringBuffer("");
        if(StringUtils.isNotBlank(source1)) {
            result.append(source1);
            if(StringUtils.isNotBlank(source2)) {
                result.append(source2);
            }
        }

        return result.toString();
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.isNotBlank(str);
    }

    public static boolean isBlank(String str) {
        return StringUtils.isBlank(str);
    }

    public static String getTwoDay(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        long day = 0L;

        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = (date.getTime() - mydate.getTime()) / 86400000L;
        } catch (Exception var7) {
            return "";
        }

        return day + "";
    }

    public static long getSecond(String sj1, String sj2) {
        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0L;

        try {
            Date date = myFormatter.parse(sj1);
            Date mydate = myFormatter.parse(sj2);
            day = date.getTime() - mydate.getTime();
            return day;
        } catch (Exception var7) {
            return 0L;
        }
    }

    public static String getMonthDays(String str) {
        String nm = formatDate(addMonth(strToDate(str)), "yyyy-MM-dd");
        return getTwoDay(nm, str);
    }

    public static String formatDate(Date date, String format) {
        if(date == null) {
            date = new Date();
        }

        if(format == null || "".equalsIgnoreCase(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    public static String getWeek(String sdate) {
        Date date = strToDate(sdate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return (new SimpleDateFormat("EEEE")).format(c.getTime());
    }

    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date strToDate(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    public static Date addMonth(Date date) {
        if(date == null) {
            date = new Date();
        }

        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(2, 1);
        return cd.getTime();
    }

    public static long getDays(String date1, String date2) {
        if(date1 != null && !date1.equals("")) {
            if(date2 != null && !date2.equals("")) {
                SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                Date mydate = null;

                try {
                    date = myFormatter.parse(date1);
                    mydate = myFormatter.parse(date2);
                } catch (Exception var7) {
                    ;
                }

                long day = (date.getTime() - mydate.getTime()) / 86400000L;
                return day;
            } else {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    public static String getAddDay(String date, int i) {
        Date tempDate = strToDate(date);
        Calendar cd = Calendar.getInstance();
        cd.setTime(tempDate);
        cd.add(5, i);
        Date tempDate1 = cd.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(tempDate1);
    }

    public static String getDefaultDay() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(5, 1);
        lastDate.add(2, 1);
        lastDate.add(5, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public String getPreviousMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(5, 1);
        lastDate.add(2, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public String getFirstDayOfMonth() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(5, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public String getCurrentWeekday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getNextWeekday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 6 + 1);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String preMonday = df1.format(monday);
        return preMonday;
    }

    public static String getNowTime(String dateformat) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
        String hehe = dateFormat.format(now);
        return hehe;
    }

    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(7) - 1;
        return dayOfWeek == 1?0:1 - dayOfWeek;
    }

    public static String getMondayOFWeek() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getNextMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 7);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getNextSunday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, mondayPlus + 7 + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String getPreviousMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(2, -1);
        lastDate.set(5, 1);
        lastDate.roll(5, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static String getNextMonthFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(2, 1);
        lastDate.set(5, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static String getNextMonthEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(2, 1);
        lastDate.set(5, 1);
        lastDate.roll(5, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static String getNextYearEnd() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(1, 1);
        lastDate.set(6, 1);
        lastDate.roll(6, -1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    public static String getNextYearFirst() {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(1, 1);
        lastDate.set(6, 1);
        str = sdf.format(lastDate.getTime());
        return str;
    }

    private static int getMaxYear() {
        Calendar cd = Calendar.getInstance();
        cd.set(6, 1);
        cd.roll(6, -1);
        int MaxYear = cd.get(6);
        return MaxYear;
    }

    private static int getYearPlus() {
        Calendar cd = Calendar.getInstance();
        int yearOfNumber = cd.get(6);
        cd.set(6, 1);
        cd.roll(6, -1);
        int MaxYear = cd.get(6);
        return yearOfNumber == 1?-MaxYear:1 - yearOfNumber;
    }

    public static String getCurrentYearFirst() {
        int yearPlus = getYearPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(5, yearPlus);
        Date yearDay = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preYearDay = df.format(yearDay);
        return preYearDay;
    }

    public static String getCurrentYearEnd() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String years = dateFormat.format(date);
        return years + "-12-31";
    }

    public static String getPreviousYearFirst() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        --years_value;
        return years_value + "-1-1";
    }

    public static String getThisSeasonTime(int month) {
        int[][] array = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}, {10, 11, 12}};
        int season = 1;
        if(month >= 1 && month <= 3) {
            season = 1;
        }

        if(month >= 4 && month <= 6) {
            season = 2;
        }

        if(month >= 7 && month <= 9) {
            season = 3;
        }

        if(month >= 10 && month <= 12) {
            season = 4;
        }

        int start_month = array[season - 1][0];
        int end_month = array[season - 1][2];
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String years = dateFormat.format(date);
        int years_value = Integer.parseInt(years);
        int start_days = 1;
        int end_days = getLastDayOfMonth(years_value, end_month);
        String seasonDate = years_value + "-" + start_month + "-" + start_days + ";" + years_value + "-" + end_month + "-" + end_days;
        return seasonDate;
    }

    private static int getLastDayOfMonth(int year, int month) {
        return month != 1 && month != 3 && month != 5 && month != 7 && month != 8 && month != 10 && month != 12?(month != 4 && month != 6 && month != 9 && month != 11?(month == 2?(isLeapYear(year)?29:28):0):30):31;
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public static boolean compareMonth(int year1, int month1, int year, int month) {
        return year1 > year?true:year1 == year && month1 >= month;
    }

    public static boolean compareDate(String d1, String d2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = df.parse(d1);
            Date date2 = df.parse(d2);
            Calendar c1 = Calendar.getInstance();
            Calendar c2 = Calendar.getInstance();
            c1.setTime(date1);
            c2.setTime(date2);
            return c2.before(c1);
        } catch (ParseException var7) {
            return false;
        }
    }

    public static String decreaseMonth(String d1) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = df.parse(d1);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            c1.add(2, -1);
            return df.format(c1.getTime());
        } catch (ParseException var4) {
            return "";
        }
    }

    public static String addDay(String d1, int day) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date1 = df.parse(d1);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            c1.add(5, day);
            return df.format(c1.getTime());
        } catch (ParseException var5) {
            return "";
        }
    }

    public static String addHour(String d1, int hour) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = df.parse(d1);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            c1.add(11, hour);
            return df.format(c1.getTime());
        } catch (ParseException var5) {
            return "";
        }
    }

    public static String addMin(String d1, int min) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date1 = df.parse(d1);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            c1.add(12, min);
            return df.format(c1.getTime());
        } catch (ParseException var5) {
            return "";
        }
    }

    public static List getDateList(int year, int month) {
        List list = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String strDate = df.format(date);
        String thisDate = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(26);
        String thisDate1 = decreaseMonth(thisDate);
        String thisDate2 = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(25);
        if(compareDate(thisDate1, strDate)) {
            return null;
        } else {
            long days;
            int i;
            if(compareDate(strDate, thisDate2)) {
                days = getDays(thisDate2, thisDate1);

                for(i = 0; (long)i < days - 1L; ++i) {
                    list.add(addDay(thisDate1, i));
                }
            } else if(compareDate(strDate, thisDate1)) {
                days = getDays(strDate, thisDate1);

                for(i = 0; (long)i < days; ++i) {
                    list.add(addDay(thisDate1, i));
                }
            }

            return list;
        }
    }

    public static List getDateListInDate(List list, String sdate) {
        if(list != null && list.size() != 0) {
            List upList = new ArrayList();
            List downList = new ArrayList();
            List aList = new ArrayList();
            String tstr = sdate;

            while(true) {
                tstr = getSubDateUp(list, tstr);
                if("".equalsIgnoreCase(tstr)) {
                    tstr = sdate;

                    while(true) {
                        tstr = getSubDateDown(list, tstr);
                        if("".equalsIgnoreCase(tstr)) {
                            int i;
                            String tempStr;
                            for(i = upList.size() - 1; i >= 0; --i) {
                                tempStr = (String)upList.get(i);
                                aList.add(tempStr);
                            }

                            aList.add(sdate);

                            for(i = 0; i < downList.size(); ++i) {
                                tempStr = (String)downList.get(i);
                                aList.add(tempStr);
                            }

                            return aList;
                        }

                        downList.add(tstr);
                    }
                }

                upList.add(tstr);
            }
        } else {
            return null;
        }
    }

    public static String getSubDateDown(List list, String sdate) {
        if(list != null && list.size() != 0) {
            String sdate1 = addDay(sdate, 1);

            for(int i = 0; i < list.size(); ++i) {
                String tempStr = (String)list.get(i);
                if(sdate1.equalsIgnoreCase(tempStr)) {
                    return tempStr;
                }
            }

            return "";
        } else {
            return "";
        }
    }

    public static String getSubDateUp(List list, String sdate) {
        if(list != null && list.size() != 0) {
            String sdate1 = addDay(sdate, -1);

            for(int i = 0; i < list.size(); ++i) {
                String tempStr = (String)list.get(i);
                if(sdate1.equalsIgnoreCase(tempStr)) {
                    return tempStr;
                }
            }

            return "";
        } else {
            return "";
        }
    }

    public static List getWeekInYear(String year) {
        List list = new ArrayList();
        int iyear = Integer.parseInt(year);
        int[] days = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if(iyear % 4 == 0 && iyear % 100 != 0 || iyear % 400 == 0) {
            days[2] = 29;
        }

        for(int i = 1; i < days.length; ++i) {
            for(int j = 1; j <= days[i]; ++j) {
                Date date = new Date(iyear - 1900, i - 1, j);
                int week = date.getDay();
                if(week == 0 || week == 6) {
                    list.add(formatDate(date, "yyyy-MM-dd"));
                }
            }
        }

        return list;
    }

    public static int getMaxDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        int maxDate = cal.getActualMaximum(5);
        return maxDate;
    }

    public static boolean isMobileNo(String mobileNo) {
        String reg = "(^13[0-9]\\d{8}$)|(^15[0,7,8,9]\\d{8}$)|(^[1-9]{2}\\d{6}$)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(mobileNo);
        return m.find();
    }

    public static boolean isIdentityCard(String strIdCard) {
        boolean result = false;
        Map<String, String> mapArea = new HashMap();
        mapArea.put("11", "北京");
        mapArea.put("12", "天津");
        mapArea.put("13", "河北");
        mapArea.put("14", "山西");
        mapArea.put("15", "内蒙古");
        mapArea.put("21", "辽宁");
        mapArea.put("22", "吉林");
        mapArea.put("23", "黑龙江");
        mapArea.put("31", "上海");
        mapArea.put("32", "江苏");
        mapArea.put("33", "浙江");
        mapArea.put("34", "安徽");
        mapArea.put("35", "福建");
        mapArea.put("36", "江西");
        mapArea.put("37", "山东");
        mapArea.put("41", "河南");
        mapArea.put("42", "湖北");
        mapArea.put("43", "湖南");
        mapArea.put("44", "广东");
        mapArea.put("45", "广西");
        mapArea.put("46", "海南");
        mapArea.put("50", "重庆");
        mapArea.put("51", "四川");
        mapArea.put("52", "贵州");
        mapArea.put("53", "云南");
        mapArea.put("54", "西藏");
        mapArea.put("61", "陕西");
        mapArea.put("62", "甘肃");
        mapArea.put("63", "青海");
        mapArea.put("64", "宁夏");
        mapArea.put("65", "新疆");
        mapArea.put("71", "台湾");
        mapArea.put("81", "香港");
        mapArea.put("82", "澳门");
        mapArea.put("91", "国外");
        if(mapArea.containsKey(StringUtils.substring(strIdCard, 0, 2))) {
            String regRYear15 = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";
            String regPYear15 = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";
            String regRYear18 = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$";
            String regPYear18 = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$";
            String reqYear = "";
            //int intYear = false;
            Pattern p;
            Matcher m;
            int intYear;
            if(strIdCard.length() == 15) {
                intYear = Integer.valueOf(StringUtils.substring(strIdCard, 6, 8)).intValue() + 1900;
                if(intYear % 4 != 0 && (intYear % 100 != 0 || intYear % 4 != 0)) {
                    reqYear = regPYear15;
                } else {
                    reqYear = regRYear15;
                }

                p = Pattern.compile(reqYear);
                m = p.matcher(strIdCard);
                result = m.find();
                if(!result) {
                    ;
                }
            } else if(strIdCard.length() == 18) {
                intYear = Integer.valueOf(StringUtils.substring(strIdCard, 6, 10)).intValue() + 1900;
                if(intYear % 4 == 0 || intYear % 100 == 0 && intYear % 4 == 0) {
                    reqYear = regRYear18;
                } else {
                    reqYear = regPYear18;
                }

                p = Pattern.compile(reqYear);
                m = p.matcher(strIdCard);
                result = m.find();
                if(result) {
                    String[] tmpSplit = strIdCard.split("");
                    int calcText = (Integer.valueOf(tmpSplit[1]).intValue() + Integer.valueOf(tmpSplit[11]).intValue()) * 7 + (Integer.valueOf(tmpSplit[2]).intValue() + Integer.valueOf(tmpSplit[12]).intValue()) * 9 + (Integer.valueOf(tmpSplit[3]).intValue() + Integer.valueOf(tmpSplit[13]).intValue()) * 10 + (Integer.valueOf(tmpSplit[4]).intValue() + Integer.valueOf(tmpSplit[14]).intValue()) * 5 + (Integer.valueOf(tmpSplit[5]).intValue() + Integer.valueOf(tmpSplit[15]).intValue()) * 8 + (Integer.valueOf(tmpSplit[6]).intValue() + Integer.valueOf(tmpSplit[16]).intValue()) * 4 + (Integer.valueOf(tmpSplit[7]).intValue() + Integer.valueOf(tmpSplit[17]).intValue()) * 2 + Integer.valueOf(tmpSplit[8]).intValue() * 1 + Integer.valueOf(tmpSplit[9]).intValue() * 6 + Integer.valueOf(tmpSplit[10]).intValue() * 3;
                    int x = calcText % 11;
                    String JYM = "10X98765432";
                    String src = StringUtils.substring(JYM, x, x + 1);
                    result = StringUtils.equalsIgnoreCase(tmpSplit[18], src);
                }
            }
        }

        return result;
    }

    public static boolean isIdentityCardAndInRange(String strIdCard, int begin, int end) {
        boolean result = isIdentityCard(strIdCard);
        if(result) {
            String birthYear = "";
            String birthDate = "";
            if(strIdCard.length() == 15) {
                birthYear = "19" + strIdCard.substring(6, 8);
                birthDate = strIdCard.substring(8, 12);
            } else if(strIdCard.length() == 18) {
                birthYear = strIdCard.substring(6, 10);
                birthDate = strIdCard.substring(10, 14);
            }

            Date date = new Date();
            int age = Integer.parseInt(formatDate(date, "yyyy")) - Integer.valueOf(birthYear).intValue();
            String currentDate = formatDate(date, "MMdd");
            if(age < begin || age > end) {
                result = false;
            }

            if(age == begin && currentDate.compareTo(birthDate) < 0) {
                result = false;
            }

            if(age == end && currentDate.compareTo(birthDate) > 0) {
                result = false;
            }
        }

        return result;
    }

    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException var2) {
            throw new IllegalStateException("Hex Decoder exception", var2);
        }
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.encodeBase64(input));
    }

    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", var2);
        }
    }

    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", var2);
        }
    }

    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static String replaceStringNull(String str) {
        if(str == null) {
            str = "";
        }

        return str;
    }

    public static Timestamp getSysDate() {
        return new Timestamp((new GregorianCalendar()).getTimeInMillis());
    }

    public static List<Map<String, Object>> checknull(List<Map<String, Object>> topic, List<Map<String, Object>> details) {
        try {
            Iterator var2 = topic.iterator();

            while(var2.hasNext()) {
                Map<String, Object> tmap = (Map)var2.next();
                Iterator var4 = details.iterator();

                while(var4.hasNext()) {
                    Map<String, Object> dmap = (Map)var4.next();
                    if(dmap.get(tmap.get("Ename").toString()) == null) {
                        dmap.put(tmap.get("Ename").toString(), "");
                    }
                }
            }
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return details;
    }

    public static List<Map<String, Object>> changeTime(List<Map<String, Object>> list) {
        try {
            Iterator var1 = list.iterator();

            while(var1.hasNext()) {
                Map<String, Object> map = (Map)var1.next();
                if(map.get("jc_conn") == null) {
                    map.put("jc_conn", "");
                }

                Iterator var3 = map.keySet().iterator();

                while(var3.hasNext()) {
                    String key = (String)var3.next();
                    if(map.get(key) instanceof Date) {
                        Date date = (Date)map.get(key);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String str = sdf.format(date);
                        map.put(key, str);
                    }
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return list;
    }

    public static List<HashMap<String, Object>> changeHashTime(List<HashMap<String, Object>> list) {
        try {
            Iterator var1 = list.iterator();

            while(var1.hasNext()) {
                Map<String, Object> map = (Map)var1.next();
                Iterator var3 = map.keySet().iterator();

                while(var3.hasNext()) {
                    String key = (String)var3.next();
                    if(map.get(key) == null) {
                        map.put(key, "");
                    }

                    if(map.get(key) instanceof Date) {
                        Date date = (Date)map.get(key);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String str = sdf.format(date);
                        map.put(key, str);
                    }
                }
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return list;
    }
}
