package com.atat.soft.record.bean;

import java.util.Date;
/**
 * 给用户发送警报纪录表
 * @author wuhaosoft
 * @version $Id TabCustomerAlarmRecord.java, 2017-09-10 19:43:03 wuhaosoft Exp
 *
 */
public class TabCustomerAlarmRecord {

    //给用户发送警报纪录表Id
    private Long  tabCustomerAlarmRecordId;

    //用户Id
    private Long tabCustomerId;

    //设备序列号
    private String deviceSeriaNumber;

    //报警部位
    private String partName;

    //报警标记
    private String mark;

    //发送消息内容
    private String content;

    //1微信 2短信
    private Integer type;

    //报警次数
    private Integer count;

    //创建时间
    private Date createdDate;

    //修改时间
    private Date modifiedDate;

    //是否删除 1:是 2:否
    private Integer isDeleted;

    public TabCustomerAlarmRecord(){}

    public TabCustomerAlarmRecord(
        Long  tabCustomerAlarmRecordId,
        Long tabCustomerId,
        String deviceSeriaNumber,
        String partName,
        String mark,
        String content,
        Integer type,
        Integer count,
        Date createdDate,
        Date modifiedDate,
        Integer isDeleted
    ) {
        this.tabCustomerId = tabCustomerId;
        this.deviceSeriaNumber = deviceSeriaNumber;
        this.partName = partName;
        this.mark = mark;
        this.content = content;
        this.type = type;
        this.count = count;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Long  getTabCustomerAlarmRecordId(){
        return tabCustomerAlarmRecordId;
    }

    public void setTabCustomerAlarmRecordId(Long  tabCustomerAlarmRecordId) {
        this.tabCustomerAlarmRecordId = tabCustomerAlarmRecordId;
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

    public String getPartName() {
        return partName;
    }

    public void setPartName(String  partName) {
        this.partName = partName;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String  mark) {
        this.mark = mark;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer  count) {
        this.count = count;
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



