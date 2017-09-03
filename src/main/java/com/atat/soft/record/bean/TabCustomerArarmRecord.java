package com.atat.soft.record.bean;

import java.util.Date;
/**
 * 用户设备分组关系表
 * @author wuhaosoft
 * @version $Id TabCustomerArarmRecord.java, 2017-09-03 22:49:20 wuhaosoft Exp
 *
 */
public class TabCustomerArarmRecord {

    //给用户发送警报纪录表Id
    private Long  tabCustomerArarmRecordId;

    //用户Id
    private Long tabCustomerId;

    //设备序列号
    private String deviceSeriaNumber;

    //组名称
    private String content;

    //1微信 2短信
    private Integer type;

    //创建时间
    private Date createdDate;

    //修改时间
    private Date modifiedDate;

    //是否删除 1:是 2:否
    private Integer isDeleted;

    public TabCustomerArarmRecord(){}

    public TabCustomerArarmRecord(
        Long  tabCustomerArarmRecordId,
        Long tabCustomerId,
        String deviceSeriaNumber,
        String content,
        Integer type,
        Date createdDate,
        Date modifiedDate,
        Integer isDeleted
    ) {
        this.tabCustomerId = tabCustomerId;
        this.deviceSeriaNumber = deviceSeriaNumber;
        this.content = content;
        this.type = type;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Long  getTabCustomerArarmRecordId(){
        return tabCustomerArarmRecordId;
    }

    public void setTabCustomerArarmRecordId(Long  tabCustomerArarmRecordId) {
        this.tabCustomerArarmRecordId = tabCustomerArarmRecordId;
    }


    public Long getTabCustomerId() {
        return tabCustomerId;
    }

    public void setTabCustomerId(Long  tabCustomerId) {
        this.tabCustomerId = tabCustomerId;
    }

    public String getDeviceSeriaNumber() {
        return deviceSeriaNumber;
    }

    public void setDeviceSeriaNumber(String  deviceSeriaNumber) {
        this.deviceSeriaNumber = deviceSeriaNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String  content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer  type) {
        this.type = type;
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



