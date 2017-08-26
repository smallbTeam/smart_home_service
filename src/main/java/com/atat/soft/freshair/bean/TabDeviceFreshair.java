package com.atat.soft.freshair.bean;

import java.util.Date;

/**
 * 空气监测设备表
 * 
 * @author wuhaosoft
 * @version $Id TabDeviceFreshair.java, 2017-08-11 17:34:11 wuhaosoft Exp
 */
public class TabDeviceFreshair {

    // 设备ID
    private Long tabDeviceFreshairId;

    // 设备序列号(唯一)
    private String deviceSeriaNumber;

    // 设备类型
    private String deviceCategory;

    // ip
    private String ip;

    // 设备名称
    private String name;

    // 设备分组Id
    private Long tabDeviceGroupId;

    // 设备开关状态
    private Integer state;

    // 创建时间
    private Date createdDate;

    // 修改时间
    private Date modifiedDate;

    // 是否删除 1:是 2:否
    private Integer isDeleted;

    public TabDeviceFreshair(){
    }

    public TabDeviceFreshair(Long tabDeviceFreshairId, String deviceSeriaNumber, String deviceCategory, String ip,
            String name, Long tabDeviceGroupId, Integer state, Date createdDate, Date modifiedDate, Integer isDeleted){
        this.deviceSeriaNumber = deviceSeriaNumber;
        this.deviceCategory = deviceCategory;
        this.ip = ip;
        this.name = name;
        this.tabDeviceGroupId = tabDeviceGroupId;
        this.state = state;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Long getTabDeviceFreshairId() {
        return tabDeviceFreshairId;
    }

    public void setTabDeviceFreshairId(Long tabDeviceFreshairId) {
        this.tabDeviceFreshairId = tabDeviceFreshairId;
    }

    public String getDeviceSeriaNumber() {
        return deviceSeriaNumber;
    }

    public void setDeviceSeriaNumber(String deviceSeriaNumber) {
        this.deviceSeriaNumber = deviceSeriaNumber;
    }

    public String getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTabDeviceGroupId() {
        return tabDeviceGroupId;
    }

    public void setTabDeviceGroupId(Long tabDeviceGroupId) {
        this.tabDeviceGroupId = tabDeviceGroupId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
