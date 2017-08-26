package com.atat.soft.freshair.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
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
 * @version $Id TabDeviceFreshairController.java, 2017-08-11 17:34:11 wuhaosoft Exp
 *
 */
@Api("空气监测设备表接口")
@RestController
@RequestMapping("/freshair")
public class TabDeviceFreshairController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabDeviceFreshairService tabDeviceFreshairService;

    @ApiOperation("查询 所有 空气监测设备表 分页")
    @RequestMapping(value = "/tabDeviceFreshairs", method = RequestMethod.POST)
    public Object getTabDeviceFreshairPageTurn(
            @ApiParam(value = "设备序列号(唯一) (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
            @ApiParam(value = "设备类型 (非必传参数)") @RequestParam(required = false) String deviceCategory,
            @ApiParam(value = "ip (非必传参数)") @RequestParam(required = false) String ip,
            @ApiParam(value = "设备名称 (非必传参数)") @RequestParam(required = false) String name,
            @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
            @ApiParam(value = "设备开关状态 (非必传参数)") @RequestParam(required = false) Integer state,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
           rs.put("deviceSeriaNumber", "%" + deviceSeriaNumber + "%");
        }
        if (StringUtil.isNotEmpty(deviceCategory)) {
           rs.put("deviceCategory", "%" + deviceCategory + "%");
        }
        if (StringUtil.isNotEmpty(ip)) {
           rs.put("ip", "%" + ip + "%");
        }
        if (StringUtil.isNotEmpty(name)) {
           rs.put("name", "%" + name + "%");
        }
        if (null != tabDeviceGroupId) {
           rs.put("tabDeviceGroupId", tabDeviceGroupId);
        }
        if (null != state) {
           rs.put("state", state);
        }
        if (null != createdDate) {
           rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
           rs.put("modifiedDate", modifiedDate);
        }
        return tabDeviceFreshairService.getTabDeviceFreshairPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 空气监测设备表")
    @RequestMapping(value = "/allTabDeviceFreshairs", method = RequestMethod.POST)
    public JsonResult<Object> selectTabDeviceFreshairList(
        @ApiParam(value = "设备序列号(唯一) (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
        @ApiParam(value = "设备类型 (非必传参数)") @RequestParam(required = false) String deviceCategory,
        @ApiParam(value = "ip (非必传参数)") @RequestParam(required = false) String ip,
        @ApiParam(value = "设备名称 (非必传参数)") @RequestParam(required = false) String name,
        @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
        @ApiParam(value = "设备开关状态 (非必传参数)") @RequestParam(required = false) Integer state,
        @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
        @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate
        )throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
         if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
            rs.put("deviceSeriaNumber", "%" + deviceSeriaNumber + "%");
         }
         if (StringUtil.isNotEmpty(deviceCategory)) {
            rs.put("deviceCategory", "%" + deviceCategory + "%");
         }
         if (StringUtil.isNotEmpty(ip)) {
            rs.put("ip", "%" + ip + "%");
         }
         if (StringUtil.isNotEmpty(name)) {
            rs.put("name", "%" + name + "%");
         }
         if (null != tabDeviceGroupId) {
            rs.put("tabDeviceGroupId",  tabDeviceGroupId );
         }
        if (null != state) {
            rs.put("state", state);
        }
        if (null != createdDate) {
            rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
            rs.put("modifiedDate", modifiedDate);
        }
        result.setObj(tabDeviceFreshairService.selectTabDeviceFreshairList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 空气监测设备表")
    @RequestMapping(value = "/tabDeviceFreshair", method = RequestMethod.POST)
    public JsonResult<Object> addTabDeviceFreshair(
                @ApiParam(value = "设备序列号(唯一) (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
                @ApiParam(value = "设备类型 (非必传参数)") @RequestParam(required = false) String deviceCategory,
                @ApiParam(value = "ip (非必传参数)") @RequestParam(required = false) String ip,
                @ApiParam(value = "设备名称 (非必传参数)") @RequestParam(required = false) String name,
                @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
                @ApiParam(value = "设备开关状态 (非必传参数)") @RequestParam(required = false) Integer state
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabDeviceFreshair tabDeviceFreshair = new TabDeviceFreshair();
        if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
           tabDeviceFreshair.setDeviceSeriaNumber(deviceSeriaNumber);
        }
        if (StringUtil.isNotEmpty(deviceCategory)) {
           tabDeviceFreshair.setDeviceCategory(deviceCategory);
        }
        if (StringUtil.isNotEmpty(ip)) {
           tabDeviceFreshair.setIp(ip);
        }
        if (StringUtil.isNotEmpty(name)) {
           tabDeviceFreshair.setName(name);
        }
        if (null != tabDeviceGroupId) {
           tabDeviceFreshair.setTabDeviceGroupId(tabDeviceGroupId);
        }
        if (null != state) {
            tabDeviceFreshair.setState(state);
        }
        tabDeviceFreshair.setCreatedDate(new Date());
        tabDeviceFreshairService.addTabDeviceFreshair(tabDeviceFreshair);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 空气监测设备表")
    @RequestMapping(value = "/tabDeviceFreshair/{tabDeviceFreshairId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabDeviceFreshairById(
            @ApiParam(value = "设备ID (必传参数)") @PathVariable Long  tabDeviceFreshairId,
             @ApiParam(value = "设备序列号(唯一) (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
             @ApiParam(value = "设备类型 (非必传参数)") @RequestParam(required = false) String deviceCategory,
             @ApiParam(value = "ip (非必传参数)") @RequestParam(required = false) String ip,
             @ApiParam(value = "设备名称 (非必传参数)") @RequestParam(required = false) String name,
             @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
             @ApiParam(value = "设备开关状态 (非必传参数)") @RequestParam(required = false) Integer state,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabDeviceFreshair tabDeviceFreshair = new TabDeviceFreshair();
        tabDeviceFreshair.setTabDeviceFreshairId(tabDeviceFreshairId);

         if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
            tabDeviceFreshair.setDeviceSeriaNumber(deviceSeriaNumber);
         }
         if (StringUtil.isNotEmpty(deviceCategory)) {
            tabDeviceFreshair.setDeviceCategory(deviceCategory);
         }
         if (StringUtil.isNotEmpty(ip)) {
            tabDeviceFreshair.setIp(ip);
         }
         if (StringUtil.isNotEmpty(name)) {
            tabDeviceFreshair.setName(name);
         }
         if (null != tabDeviceGroupId) {
            tabDeviceFreshair.setTabDeviceGroupId(tabDeviceGroupId);
         }
         if (null != state) {
            tabDeviceFreshair.setState(state);
         }
         if (null != isDeleted) {
            tabDeviceFreshair.setIsDeleted(isDeleted);
         }
        tabDeviceFreshair.setModifiedDate(new Date());
        tabDeviceFreshairService.updateTabDeviceFreshairById(tabDeviceFreshair);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 空气监测设备表")
    @RequestMapping(value = "/tabDeviceFreshair/{tabDeviceFreshairId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabDeviceFreshairById(
             @ApiParam(value = "设备ID (必传参数)") @PathVariable Long  tabDeviceFreshairId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabDeviceFreshairService.delTabDeviceFreshairById(tabDeviceFreshairId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 空气监测设备表")
    @RequestMapping(value = "/tabDeviceFreshair/{tabDeviceFreshairId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getTabDeviceFreshairById(
            @ApiParam(value = "设备ID (必传参数)") @PathVariable Long  tabDeviceFreshairId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabDeviceFreshairService.getTabDeviceFreshairById(tabDeviceFreshairId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}

