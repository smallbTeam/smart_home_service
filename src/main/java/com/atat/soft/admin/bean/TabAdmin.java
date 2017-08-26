package com.atat.soft.admin.bean;

import java.util.Date;
/**
 * 管理员表
 * @author wuhaosoft
 * @version $Id TabAdmin.java, 2017-08-10 01:20:00 wuhaosoft Exp
 *
 */
public class TabAdmin {

    //用户ID
    private Integer  tabAdminId;

    //电话
    private String mobelPhone;

    //密码
    private String password;

    //昵称
    private String nickName;

    //权限级别
    private Integer permissionLevel;

    //token
    private String token;

    //创建时间
    private Date createdDate;

    //修改时间
    private Date modifiedDate;

    //是否删除 1:是 2:否
    private Integer isDeleted;

    public TabAdmin(){}

    public TabAdmin(
        Integer  tabAdminId,
        String mobelPhone,
        String password,
        String nickName,
        Integer permissionLevel,
        String token,
        Date createdDate,
        Date modifiedDate,
        Integer isDeleted
    ) {
        this.mobelPhone = mobelPhone;
        this.password = password;
        this.nickName = nickName;
        this.permissionLevel = permissionLevel;
        this.token = token;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.isDeleted = isDeleted;
    }

    public Integer  getTabAdminId(){
        return tabAdminId;
    }

    public void setTabAdminId(Integer  tabAdminId) {
        this.tabAdminId = tabAdminId;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String  nickName) {
        this.nickName = nickName;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer  permissionLevel) {
        this.permissionLevel = permissionLevel;
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



