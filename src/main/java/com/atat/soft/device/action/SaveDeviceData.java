/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.device.action;

import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.freshair.bean.DataFreshairNow;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.freshair.service.DataFreshairNowService;
import com.atat.soft.freshair.service.TabDeviceFreshairService;

import java.util.Date;
import java.util.Map;

/**
 * 保存设备数据
 * 
 * @author ligw
 * @version $Id SaveDeviceData.java, v 0.1 2017-08-10 19:43 ligw Exp $$
 */
public class SaveDeviceData {

    /**
     * 新增或更新设备数据
     * 设备已存在-返回1 更新设备数据
     * 设备不存在-返回0 新增设备数据
     * 
     * @param tabDevice
     * @return
     */
    public static Integer saveFreshAirData(TabDeviceFreshair tabDevice,DataFreshairNow dataFreshairNow) {
        String deviceSeriaNumber = tabDevice.getDeviceSeriaNumber();
        TabDeviceFreshairService tabDeviceFreshairService = (TabDeviceFreshairService) SpringBeanFactoryUtils.getBean("tabDeviceFreshairService");
        DataFreshairNowService dataFreshairNowService = (DataFreshairNowService) SpringBeanFactoryUtils.getBean("dataFreshairNowService");
        // 依据设备号获取设备ID
        Map<String, Object> tabDeviceFreshairinfo = tabDeviceFreshairService.getTabDeviceFreshairByDeviceSeriaNumber(deviceSeriaNumber);
        Long tabDeviceFreshairId;
        if (null != tabDeviceFreshairinfo){
            tabDeviceFreshairId = Long.parseLong(tabDeviceFreshairinfo.get("tabDeviceFreshairId").toString());
        } else {
            tabDeviceFreshairId = null;
        }
        tabDevice.setDeviceCategory("freshair");
        // 设备不存在-新增数据-返回0
        if (null == tabDeviceFreshairId) {
            tabDevice.setCreatedDate(new Date());
            tabDeviceFreshairService.addTabDeviceFreshair(tabDevice);
            return 0;
        } else {
            // 设备已存在-更新数据
            tabDevice.setModifiedDate(new Date());
            tabDevice.setTabDeviceFreshairId(tabDeviceFreshairId);
            tabDeviceFreshairService.updateTabDeviceFreshairById(tabDevice);
            // 存入实时表
            dataFreshairNow.setDataFreshairNowId(new Date().getTime());
            dataFreshairNow.setTabDeviceFreshairId(tabDeviceFreshairId);
            dataFreshairNowService.addDataFreshairNow(dataFreshairNow);
            // 返回
            return 1;
        }
    }
}
