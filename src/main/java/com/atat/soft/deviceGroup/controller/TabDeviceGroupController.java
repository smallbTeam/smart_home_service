package com.atat.soft.deviceGroup.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.deviceGroup.bean.TabDeviceGroup;
import com.atat.soft.deviceGroup.service.TabDeviceGroupService;
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
 * @version $Id TabDeviceGroupController.java, 2017-08-10 01:19:29 wuhaosoft Exp
 *
 */
@Api("设备分组表(家)接口")
@RestController
@RequestMapping("/deviceGroup")
public class TabDeviceGroupController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabDeviceGroupService tabDeviceGroupService;

    @Autowired
    private TabDeviceFreshairService tabDeviceFreshairService;

    @ApiOperation("查询 所有 设备分组表(家) 分页")
    @RequestMapping(value = "/tabDeviceGroups", method = RequestMethod.POST)
    public Object getTabDeviceGroupPageTurn(
            @ApiParam(value = "系统分组编号 (非必传参数)") @RequestParam(required = false) String uid,
            @ApiParam(value = "设备分组所在地地址 (非必传参数)") @RequestParam(required = false) String address,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(uid)) {
           rs.put("uid", "%" + uid + "%");
        }
        if (StringUtil.isNotEmpty(address)) {
           rs.put("address", "%" + address + "%");
        }
        if (null != createdDate) {
           rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
           rs.put("modifiedDate", modifiedDate);
        }
        return tabDeviceGroupService.getTabDeviceGroupPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 设备分组表(家)")
    @RequestMapping(value = "/allTabDeviceGroups", method = RequestMethod.POST)
    public JsonResult<Object> selectTabDeviceGroupList(
        @ApiParam(value = "系统分组编号 (非必传参数)") @RequestParam(required = false) String uid,
        @ApiParam(value = "设备分组所在地地址 (非必传参数)") @RequestParam(required = false) String address,
        @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
        @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate
        )throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
         if (StringUtil.isNotEmpty(uid)) {
            rs.put("uid", "%" + uid + "%");
         }
         if (StringUtil.isNotEmpty(address)) {
            rs.put("address", "%" + address + "%");
         }
        if (null != createdDate) {
            rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
            rs.put("modifiedDate", modifiedDate);
        }
        result.setObj(tabDeviceGroupService.selectTabDeviceGroupList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 设备分组表(家)")
    @RequestMapping(value = "/tabDeviceGroup", method = RequestMethod.POST)
    public JsonResult<Object> addTabDeviceGroup(
                @ApiParam(value = "系统分组编号 (非必传参数)") @RequestParam(required = false) String uid,
                @ApiParam(value = "设备分组所在地地址 (非必传参数)") @RequestParam(required = false) String address
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabDeviceGroup tabDeviceGroup = new TabDeviceGroup();
        if (StringUtil.isNotEmpty(uid)) {
           tabDeviceGroup.setUid(uid);
        }
        if (StringUtil.isNotEmpty(address)) {
           tabDeviceGroup.setAddress(address);
        }
        tabDeviceGroup.setCreatedDate(new Date());
        tabDeviceGroupService.addTabDeviceGroup(tabDeviceGroup);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 设备分组表(家)")
    @RequestMapping(value = "/tabDeviceGroup/{tabDeviceGroupId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabDeviceGroupById(
            @ApiParam(value = "设备分组ID (必传参数)") @PathVariable Long  tabDeviceGroupId,
             @ApiParam(value = "系统分组编号 (非必传参数)") @RequestParam(required = false) String uid,
             @ApiParam(value = "设备分组所在地地址 (非必传参数)") @RequestParam(required = false) String address,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabDeviceGroup tabDeviceGroup = new TabDeviceGroup();
        tabDeviceGroup.setTabDeviceGroupId(tabDeviceGroupId);

         if (StringUtil.isNotEmpty(uid)) {
            tabDeviceGroup.setUid(uid);
         }
         if (StringUtil.isNotEmpty(address)) {
            tabDeviceGroup.setAddress(address);
         }
         if (null != isDeleted) {
            tabDeviceGroup.setIsDeleted(isDeleted);
         }
        tabDeviceGroup.setModifiedDate(new Date());
        tabDeviceGroupService.updateTabDeviceGroupById(tabDeviceGroup);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 设备分组表(家)")
    @RequestMapping(value = "/tabDeviceGroup/{tabDeviceGroupId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabDeviceGroupById(
             @ApiParam(value = "设备分组ID (必传参数)") @PathVariable Long  tabDeviceGroupId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabDeviceGroupService.delTabDeviceGroupById(tabDeviceGroupId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 设备分组表(家) 以及分组下所有设备")
    @RequestMapping(value = "/tabDeviceGroup/{tabDeviceGroupId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getTabDeviceGroupById(
            @ApiParam(value = "设备分组ID (必传参数)") @PathVariable Long  tabDeviceGroupId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabDeviceGroupService.getTabDeviceGroupById(tabDeviceGroupId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("绑定设备与分组(家)")
    @RequestMapping(value = "/boundDeviceAndGroup", method = RequestMethod.PUT)
    public JsonResult<Object> boundDeviceAndGroup(
            @ApiParam(value = "设备分组ID (必传参数)") @PathVariable Long  tabDeviceGroupId,
            @ApiParam(value = "设备表Id (必传参数)") @RequestParam Long tabdeviceId,
            @ApiParam(value = "设备类型 (必传参数)") @RequestParam String deviceCategory
    ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabDeviceGroup tabDeviceGroup = new TabDeviceGroup();
        result.setCode(ResultCode.SUCCESS.getCode());
        if ("freshair".equals(deviceCategory)){
            //绑定空气检测设备
            TabDeviceFreshair tabDeviceFreshair = new TabDeviceFreshair();
            tabDeviceFreshair.setTabDeviceFreshairId(tabdeviceId);
            tabDeviceFreshair.setTabDeviceGroupId(tabDeviceGroupId);
            tabDeviceFreshair.setModifiedDate(new Date());
            tabDeviceFreshairService.updateTabDeviceFreshairById(tabDeviceFreshair);
        } else {
            result.setCode(ResultCode.CATEGORY_NOT_EXIST.getCode());
            result.setErrorMsg(ResultCode.CATEGORY_NOT_EXIST.getInfo());
        }
        return result;
    }
}

