package com.atat.soft.admin.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.admin.bean.TabAdmin;
import com.atat.soft.admin.service.TabAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wuhaosoft
 * @version $Id TabAdminController.java, 2017-08-10 01:20:00 wuhaosoft Exp
 *
 */
@Api("管理员表接口")
@RestController
@RequestMapping("/admin")
public class TabAdminController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabAdminService tabAdminService;

    @ApiOperation("查询 所有 管理员表 分页")
    @RequestMapping(value = "/tabAdmins", method = RequestMethod.POST)
    public Object getTabAdminPageTurn(
            @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
            @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
            @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
            @ApiParam(value = "权限级别 (非必传参数)") @RequestParam(required = false) Integer permissionLevel,
            @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(mobelPhone)) {
           rs.put("mobelPhone", "%" + mobelPhone + "%");
        }
        if (StringUtil.isNotEmpty(password)) {
           rs.put("password", "%" + password + "%");
        }
        if (StringUtil.isNotEmpty(nickName)) {
           rs.put("nickName", "%" + nickName + "%");
        }
        if (null != permissionLevel) {
           rs.put("permissionLevel", permissionLevel);
        }
        if (StringUtil.isNotEmpty(token)) {
           rs.put("token", "%" + token + "%");
        }
        if (null != createdDate) {
           rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
           rs.put("modifiedDate", modifiedDate);
        }
        return tabAdminService.getTabAdminPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 管理员表")
    @RequestMapping(value = "/allTabAdmins", method = RequestMethod.POST)
    public JsonResult<Object> selectTabAdminList(
        @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
        @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
        @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
        @ApiParam(value = "权限级别 (非必传参数)") @RequestParam(required = false) Integer permissionLevel,
        @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token,
        @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
        @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate
        )throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
         if (StringUtil.isNotEmpty(mobelPhone)) {
            rs.put("mobelPhone", "%" + mobelPhone + "%");
         }
         if (StringUtil.isNotEmpty(password)) {
            rs.put("password", "%" + password + "%");
         }
         if (StringUtil.isNotEmpty(nickName)) {
            rs.put("nickName", "%" + nickName + "%");
         }
        if (null != permissionLevel) {
            rs.put("permissionLevel", permissionLevel);
        }
         if (StringUtil.isNotEmpty(token)) {
            rs.put("token", "%" + token + "%");
         }
        if (null != createdDate) {
            rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
            rs.put("modifiedDate", modifiedDate);
        }
        result.setObj(tabAdminService.selectTabAdminList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 管理员表")
    @RequestMapping(value = "/tabAdmin", method = RequestMethod.POST)
    public JsonResult<Object> addTabAdmin(
                @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
                @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
                @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
                @ApiParam(value = "权限级别 (非必传参数)") @RequestParam(required = false) Integer permissionLevel,
                @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabAdmin tabAdmin = new TabAdmin();
        if (StringUtil.isNotEmpty(mobelPhone)) {
           tabAdmin.setMobelPhone(mobelPhone);
        }
        if (StringUtil.isNotEmpty(password)) {
           tabAdmin.setPassword(password);
        }
        if (StringUtil.isNotEmpty(nickName)) {
           tabAdmin.setNickName(nickName);
        }
        if (null != permissionLevel) {
            tabAdmin.setPermissionLevel(permissionLevel);
        }
        if (StringUtil.isNotEmpty(token)) {
           tabAdmin.setToken(token);
        }
        tabAdmin.setCreatedDate(new Date());
        tabAdminService.addTabAdmin(tabAdmin);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 管理员表")
    @RequestMapping(value = "/tabAdmin/{tabAdminId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabAdminById(
            @ApiParam(value = "用户ID (必传参数)") @PathVariable Integer  tabAdminId,
             @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
             @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
             @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
             @ApiParam(value = "权限级别 (非必传参数)") @RequestParam(required = false) Integer permissionLevel,
             @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabAdmin tabAdmin = new TabAdmin();
        tabAdmin.setTabAdminId(tabAdminId);

         if (StringUtil.isNotEmpty(mobelPhone)) {
            tabAdmin.setMobelPhone(mobelPhone);
         }
         if (StringUtil.isNotEmpty(password)) {
            tabAdmin.setPassword(password);
         }
         if (StringUtil.isNotEmpty(nickName)) {
            tabAdmin.setNickName(nickName);
         }
         if (null != permissionLevel) {
            tabAdmin.setPermissionLevel(permissionLevel);
         }
         if (StringUtil.isNotEmpty(token)) {
            tabAdmin.setToken(token);
         }
         if (null != isDeleted) {
            tabAdmin.setIsDeleted(isDeleted);
         }
        tabAdmin.setModifiedDate(new Date());
        tabAdminService.updateTabAdminById(tabAdmin);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 管理员表")
    @RequestMapping(value = "/tabAdmin/{tabAdminId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabAdminById(
             @ApiParam(value = "用户ID (必传参数)") @PathVariable Integer  tabAdminId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabAdminService.delTabAdminById(tabAdminId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 管理员表")
    @RequestMapping(value = "/tabAdmin/{tabAdminId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getTabAdminById(
            @ApiParam(value = "用户ID (必传参数)") @PathVariable Integer  tabAdminId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabAdminService.getTabAdminById(tabAdminId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}

