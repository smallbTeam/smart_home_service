package com.atat.soft.record.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.record.bean.TabCustomerArarmRecord;
import com.atat.soft.record.service.TabCustomerArarmRecordService;
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
 * @version $Id TabCustomerArarmRecordController.java, 2017-09-03 22:49:20 wuhaosoft Exp
 *
 */
@Api("用户设备分组关系表接口")
@RestController
@RequestMapping("/record")
public class TabCustomerArarmRecordController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabCustomerArarmRecordService tabCustomerArarmRecordService;

    @ApiOperation("查询 所有 给用户发送警报纪录表 分页")
    @RequestMapping(value = "/tabCustomerArarmRecords", method = RequestMethod.POST)
    public Object getTabCustomerArarmRecordPageTurn(
            @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
            @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
            @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String content,
            @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
            @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabCustomerId) {
           rs.put("tabCustomerId", tabCustomerId);
        }
        if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
           rs.put("deviceSeriaNumber", "%" + deviceSeriaNumber + "%");
        }
        if (StringUtil.isNotEmpty(content)) {
           rs.put("content", "%" + content + "%");
        }
        if (null != type) {
           rs.put("type", type);
        }
        if (null != createdDate) {
           rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
           rs.put("modifiedDate", modifiedDate);
        }
        if (null != isDeleted) {
           rs.put("isDeleted", isDeleted);
        }
        return tabCustomerArarmRecordService.getTabCustomerArarmRecordPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 给用户发送警报纪录表")
    @RequestMapping(value = "/allTabCustomerArarmRecords", method = RequestMethod.POST)
    public JsonResult<Object> selectTabCustomerArarmRecordList(
        @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
        @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
        @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String content,
        @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
        @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
        @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
        @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
        )throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabCustomerId) {
            rs.put("tabCustomerId", tabCustomerId);
        }
         if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
            rs.put("deviceSeriaNumber", "%" + deviceSeriaNumber + "%");
         }
         if (StringUtil.isNotEmpty(content)) {
            rs.put("content", "%" + content + "%");
         }
        if (null != type) {
            rs.put("type", type);
        }
        if (null != createdDate) {
            rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
            rs.put("modifiedDate", modifiedDate);
        }
        if (null != isDeleted) {
            rs.put("isDeleted", isDeleted);
        }
        result.setObj(tabCustomerArarmRecordService.selectTabCustomerArarmRecordList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerArarmRecord", method = RequestMethod.POST)
    public JsonResult<Object> addTabCustomerArarmRecord(
                @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
                @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
                @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String content,
                @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
                @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
                @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
                @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomerArarmRecord tabCustomerArarmRecord = new TabCustomerArarmRecord();
        if (null != tabCustomerId) {
            tabCustomerArarmRecord.setTabCustomerId(tabCustomerId);
        }
        if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
           tabCustomerArarmRecord.setDeviceSeriaNumber(deviceSeriaNumber);
        }
        if (StringUtil.isNotEmpty(content)) {
           tabCustomerArarmRecord.setContent(content);
        }
        if (null != type) {
            tabCustomerArarmRecord.setType(type);
        }
        if (null != createdDate) {
            tabCustomerArarmRecord.setCreatedDate(createdDate);
        }
        if (null != modifiedDate) {
            tabCustomerArarmRecord.setModifiedDate(modifiedDate);
        }
        if (null != isDeleted) {
            tabCustomerArarmRecord.setIsDeleted(isDeleted);
        }
        //tabCustomerArarmRecord.setCreatedDate(new Date());
        tabCustomerArarmRecordService.addTabCustomerArarmRecord(tabCustomerArarmRecord);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerArarmRecord/{tabCustomerArarmRecordId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabCustomerArarmRecordById(
            @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerArarmRecordId,
             @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
             @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
             @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String content,
             @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
             @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
             @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomerArarmRecord tabCustomerArarmRecord = new TabCustomerArarmRecord();
        tabCustomerArarmRecord.setTabCustomerArarmRecordId(tabCustomerArarmRecordId);

         if (null != tabCustomerId) {
            tabCustomerArarmRecord.setTabCustomerId(tabCustomerId);
         }
         if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
            tabCustomerArarmRecord.setDeviceSeriaNumber(deviceSeriaNumber);
         }
         if (StringUtil.isNotEmpty(content)) {
            tabCustomerArarmRecord.setContent(content);
         }
         if (null != type) {
            tabCustomerArarmRecord.setType(type);
         }
         if (null != createdDate) {
            tabCustomerArarmRecord.setCreatedDate(createdDate);
         }
         if (null != modifiedDate) {
            tabCustomerArarmRecord.setModifiedDate(modifiedDate);
         }
         if (null != isDeleted) {
            tabCustomerArarmRecord.setIsDeleted(isDeleted);
         }
        //tabCustomerArarmRecord.setModifiedDate(new Date());
        tabCustomerArarmRecordService.updateTabCustomerArarmRecordById(tabCustomerArarmRecord);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerArarmRecord/{tabCustomerArarmRecordId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabCustomerArarmRecordById(
             @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerArarmRecordId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabCustomerArarmRecordService.delTabCustomerArarmRecordById(tabCustomerArarmRecordId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerArarmRecord/{tabCustomerArarmRecordId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getTabCustomerArarmRecordById(
            @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerArarmRecordId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabCustomerArarmRecordService.getTabCustomerArarmRecordById(tabCustomerArarmRecordId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}

