package com.atat.soft.customer.bean;

import java.util.Date;
/**
 * 用户表
 * @author wuhaosoft
 * @version $Id TabCustomer.java, 2017-08-10 00:19:02 wuhaosoft Exp
 *
 */
public class TabCustomer {

    //用户ID
    private Integer  tabCustomerId;

    //电话
    private String mobelPhone;

    //密码
    private String password;

    //微信Id
    private String wxId;

    //昵称
    private String nickName;

    //出生日期
    private Date birthday;

    //性别 1男 2 女 0其他
    private Integer sex;

    //token
    private String token;

    //创建时间
    private Date createdDate;

    //修改时间
    private Date modifiedDate;

    //是否删除 1:是 2:否
    private Integer isDeleted;

    public TabCustomer(){}

    public TabCustomer(
        Integer  tabCustomerId,
        String mobelPhone,
        String password,
        String wxId,
        String nickName,
        Date birthday,
        Integer sex,
        String token,
        Date createdDate,
        Date modifiedDate,
        Integer isDeleted
    ) {
        this.mobelPhone = mobelPhone;
        this.password = password;
        this.wxId = wxId;
        this.nickName = nickName;
        this.birthday = birthday;
        this.sex = sex;
        this.token = token;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Integer  getTabCustomerId(){
        return tabCustomerId;
    }

    public void setTabCustomerId(Integer  tabCustomerId) {
        this.tabCustomerId = tabCustomerId;
    }


    public String getMobelPhone() {
        return mobelPhone;
    }

    public void setMobelPhone(String  mobelPhone) {
        this.mobelPhone = mobelPhone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String  password) {
        this.password = password;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String  wxId) {
        this.wxId = wxId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String  nickName) {
        this.nickName = nickName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date  birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer  sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String  token) {
        this.token = token;
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



