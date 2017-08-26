package com.atat.soft.deviceGroup.bean;

import java.util.Date;
/**
 * 设备分组表(家)
 * @author wuhaosoft
 * @version $Id TabDeviceGroup.java, 2017-08-10 01:19:29 wuhaosoft Exp
 *
 */
public class TabDeviceGroup {

    //设备分组ID
    private Long  tabDeviceGroupId;

    //系统分组编号
    private String uid;

    //设备分组所在地地址
    private String address;

    //创建时间
    private Date createdDate;

    //修改时间
    private Date modifiedDate;

    //是否删除 1:是 2:否
    private Integer isDeleted;

    public TabDeviceGroup(){}

    public TabDeviceGroup(
        Long  tabDeviceGroupId,
        String uid,
        String address,
        Date createdDate,
        Date modifiedDate,
        Integer isDeleted
    ) {
        this.uid = uid;
        this.address = address;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Long  getTabDeviceGroupId(){
        return tabDeviceGroupId;
    }

    public void setTabDeviceGroupId(Long  tabDeviceGroupId) {
        this.tabDeviceGroupId = tabDeviceGroupId;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String  uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String  address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date  createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date  modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer  isDeleted) {
        this.isDeleted = isDeleted;
    }

}



