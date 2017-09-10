package com.atat.soft.record.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.record.bean.TabCustomerAlarmRecord;
import com.atat.soft.record.service.TabCustomerAlarmRecordService;
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
 * @version $Id TabCustomerAlarmRecordController.java, 2017-09-10 19:43:03 wuhaosoft Exp
 *
 */
@Api("给用户发送警报纪录表接口")
@RestController
@RequestMapping("/record")
public class TabCustomerAlarmRecordController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabCustomerAlarmRecordService tabCustomerAlarmRecordService;

    @ApiOperation("查询 所有 给用户发送警报纪录表 分页")
    @RequestMapping(value = "/tabCustomerAlarmRecords", method = RequestMethod.POST)
    public Object getTabCustomerAlarmRecordPageTurn(
            @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
            @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
            @ApiParam(value = "报警部位 (非必传参数)") @RequestParam(required = false) String partName,
            @ApiParam(value = "报警标记 (非必传参数)") @RequestParam(required = false) String mark,
            @ApiParam(value = "发送消息内容 (非必传参数)") @RequestParam(required = false) String content,
            @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
            @ApiParam(value = "报警次数 (非必传参数)") @RequestParam(required = false) Integer count,
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
        if (StringUtil.isNotEmpty(partName)) {
           rs.put("partName", "%" + partName + "%");
        }
        if (StringUtil.isNotEmpty(mark)) {
           rs.put("mark", "%" + mark + "%");
        }
        if (StringUtil.isNotEmpty(content)) {
           rs.put("content", "%" + content + "%");
        }
        if (null != type) {
           rs.put("type", type);
        }
        if (null != count) {
           rs.put("count", count);
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
        return tabCustomerAlarmRecordService.getTabCustomerAlarmRecordPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 给用户发送警报纪录表")
    @RequestMapping(value = "/allTabCustomerAlarmRecords", method = RequestMethod.POST)
    public JsonResult<Object> selectTabCustomerAlarmRecordList(
        @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
        @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
        @ApiParam(value = "报警部位 (非必传参数)") @RequestParam(required = false) String partName,
        @ApiParam(value = "报警标记 (非必传参数)") @RequestParam(required = false) String mark,
        @ApiParam(value = "发送消息内容 (非必传参数)") @RequestParam(required = false) String content,
        @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
        @ApiParam(value = "报警次数 (非必传参数)") @RequestParam(required = false) Integer count,
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
         if (StringUtil.isNotEmpty(partName)) {
            rs.put("partName", "%" + partName + "%");
         }
         if (StringUtil.isNotEmpty(mark)) {
            rs.put("mark", "%" + mark + "%");
         }
         if (StringUtil.isNotEmpty(content)) {
            rs.put("content", "%" + content + "%");
         }
        if (null != type) {
            rs.put("type", type);
        }
        if (null != count) {
            rs.put("count", count);
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
        result.setObj(tabCustomerAlarmRecordService.selectTabCustomerAlarmRecordList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerAlarmRecord", method = RequestMethod.POST)
    public JsonResult<Object> addTabCustomerAlarmRecord(
                @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
                @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
                @ApiParam(value = "报警部位 (非必传参数)") @RequestParam(required = false) String partName,
                @ApiParam(value = "报警标记 (非必传参数)") @RequestParam(required = false) String mark,
                @ApiParam(value = "发送消息内容 (非必传参数)") @RequestParam(required = false) String content,
                @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
                @ApiParam(value = "报警次数 (非必传参数)") @RequestParam(required = false) Integer count,
                @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
                @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
                @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomerAlarmRecord tabCustomerAlarmRecord = new TabCustomerAlarmRecord();
        if (null != tabCustomerId) {
            tabCustomerAlarmRecord.setTabCustomerId(tabCustomerId);
        }
        if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
           tabCustomerAlarmRecord.setDeviceSeriaNumber(deviceSeriaNumber);
        }
        if (StringUtil.isNotEmpty(partName)) {
           tabCustomerAlarmRecord.setPartName(partName);
        }
        if (StringUtil.isNotEmpty(mark)) {
           tabCustomerAlarmRecord.setMark(mark);
        }
        if (StringUtil.isNotEmpty(content)) {
           tabCustomerAlarmRecord.setContent(content);
        }
        if (null != type) {
            tabCustomerAlarmRecord.setType(type);
        }
        if (null != count) {
            tabCustomerAlarmRecord.setCount(count);
        }
        if (null != createdDate) {
            tabCustomerAlarmRecord.setCreatedDate(createdDate);
        }
        if (null != modifiedDate) {
            tabCustomerAlarmRecord.setModifiedDate(modifiedDate);
        }
        if (null != isDeleted) {
            tabCustomerAlarmRecord.setIsDeleted(isDeleted);
        }
        //tabCustomerAlarmRecord.setCreatedDate(new Date());
        tabCustomerAlarmRecordService.addTabCustomerAlarmRecord(tabCustomerAlarmRecord);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerAlarmRecord/{tabCustomerAlarmRecordId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabCustomerAlarmRecordById(
            @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerAlarmRecordId,
             @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
             @ApiParam(value = "设备序列号 (非必传参数)") @RequestParam(required = false) String deviceSeriaNumber,
             @ApiParam(value = "报警部位 (非必传参数)") @RequestParam(required = false) String partName,
             @ApiParam(value = "报警标记 (非必传参数)") @RequestParam(required = false) String mark,
             @ApiParam(value = "发送消息内容 (非必传参数)") @RequestParam(required = false) String content,
             @ApiParam(value = "1微信 2短信 (非必传参数)") @RequestParam(required = false) Integer type,
             @ApiParam(value = "报警次数 (非必传参数)") @RequestParam(required = false) Integer count,
             @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
             @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomerAlarmRecord tabCustomerAlarmRecord = new TabCustomerAlarmRecord();
        tabCustomerAlarmRecord.setTabCustomerAlarmRecordId(tabCustomerAlarmRecordId);

         if (null != tabCustomerId) {
            tabCustomerAlarmRecord.setTabCustomerId(tabCustomerId);
         }
         if (StringUtil.isNotEmpty(deviceSeriaNumber)) {
            tabCustomerAlarmRecord.setDeviceSeriaNumber(deviceSeriaNumber);
         }
         if (StringUtil.isNotEmpty(partName)) {
            tabCustomerAlarmRecord.setPartName(partName);
         }
         if (StringUtil.isNotEmpty(mark)) {
            tabCustomerAlarmRecord.setMark(mark);
         }
         if (StringUtil.isNotEmpty(content)) {
            tabCustomerAlarmRecord.setContent(content);
         }
         if (null != type) {
            tabCustomerAlarmRecord.setType(type);
         }
         if (null != count) {
            tabCustomerAlarmRecord.setCount(count);
         }
         if (null != createdDate) {
            tabCustomerAlarmRecord.setCreatedDate(createdDate);
         }
         if (null != modifiedDate) {
            tabCustomerAlarmRecord.setModifiedDate(modifiedDate);
         }
         if (null != isDeleted) {
            tabCustomerAlarmRecord.setIsDeleted(isDeleted);
         }
        //tabCustomerAlarmRecord.setModifiedDate(new Date());
        tabCustomerAlarmRecordService.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecord);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerAlarmRecord/{tabCustomerAlarmRecordId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabCustomerAlarmRecordById(
             @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerAlarmRecordId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabCustomerAlarmRecordService.delTabCustomerAlarmRecordById(tabCustomerAlarmRecordId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 给用户发送警报纪录表")
    @RequestMapping(value = "/tabCustomerAlarmRecord/{tabCustomerAlarmRecordId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getTabCustomerAlarmRecordById(
            @ApiParam(value = "给用户发送警报纪录表Id (必传参数)") @PathVariable Long  tabCustomerAlarmRecordId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabCustomerAlarmRecordService.getTabCustomerAlarmRecordById(tabCustomerAlarmRecordId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}

