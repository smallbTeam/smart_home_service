/**
 * Company
 * Copyright (C) 2004-2017 All Rights Reserved.
 */
package com.atat.soft.common.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ligw
 * @version $Id DeviceCategoryTabMap.java, v 0.1 2017-08-10 19:28 ligw Exp $$
 */
public enum DeviceCategoryTabMap {
    FRESH_AIR("freshAir", "tab_device_freshair");

    private String deviceCategory;
    private String tableName;

    private DeviceCategoryTabMap(String deviceCategory,String tableName){
        this.deviceCategory = deviceCategory;
        this.tableName = tableName;
    }

    public String getTableNameForUrl() {
        String encodingInfo = null;
        try {
            encodingInfo = URLEncoder.encode(tableName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodingInfo;
    }

    public String getTableName() {
        return tableName;
    }

}
