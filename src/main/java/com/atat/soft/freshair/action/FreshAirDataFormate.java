/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.freshair.action;

import com.atat.soft.util.CollectionUtil;

import java.util.*;

/**
 * @author ligw
 * @version $Id FreshAirDataFormate.java, v 0.1 2017-08-15 22:25 ligw Exp $$
 */
public class FreshAirDataFormate {

    public static List<Map<String, Object>> formateDataForEchar(List<Map<String, Object>> devicepartTimeData){
        //查找设备参数Id
        List<Map<String, Object>> categoryParameter = new ArrayList<Map<String, Object>>();
        Map<String, Object> wendu = new HashMap<String, Object>();
        wendu.put("code","wendu");
        wendu.put("name","温度");
        wendu.put("unit","℃");
        Map<String, Object> shidu = new HashMap<String, Object>();
        shidu.put("code","shidu");
        shidu.put("name","湿度");
        shidu.put("unit","％");
        Map<String, Object> pm = new HashMap<String, Object>();
        pm.put("code","pm");
        pm.put("name","PM2.5");
        pm.put("unit","μg/m³");
        Map<String, Object> voc = new HashMap<String, Object>();
        voc.put("code","voc");
        voc.put("name","VOC");
        voc.put("unit","g/L");
        Map<String, Object> co2 = new HashMap<String, Object>();
        co2.put("code","co2");
        co2.put("name","CO2");
        co2.put("unit","ppm");
        //依次取出各变量
        List<Long> dateList = new ArrayList<Long>();
        List<Double> wendu_val = new ArrayList<Double>();
        List<Double> shidu_val = new ArrayList<Double>();
        List<Double> pm_val = new ArrayList<Double>();
        List<Double> voc_val = new ArrayList<Double>();
        List<Double> co2_val = new ArrayList<Double>();
        if (CollectionUtil.isNotEmpty(devicepartTimeData)){
            for (Map<String, Object> deviceData : devicepartTimeData) {
                dateList.add(Long.parseLong(deviceData.get("recordTime").toString()));
                wendu_val.add((Double) deviceData.get("wendu"));
                shidu_val.add((Double) deviceData.get("shidu"));
                pm_val.add((Double) deviceData.get("pm"));
                voc_val.add((Double) deviceData.get("voc"));
                co2_val.add((Double) deviceData.get("co2"));
            }
            wendu.put("data",wendu_val);
            wendu.put("recordTime",dateList);
            shidu.put("data",shidu_val);
            shidu.put("recordTime",dateList);
            pm.put("data",pm_val);
            pm.put("recordTime",dateList);
            voc.put("data",voc_val);
            voc.put("recordTime",dateList);
            co2.put("data",co2_val);
            co2.put("recordTime",dateList);
        }
        categoryParameter.add(wendu);
        categoryParameter.add(shidu);
        categoryParameter.add(pm);
        categoryParameter.add(voc);
        categoryParameter.add(co2);
        return categoryParameter;
    }
}
