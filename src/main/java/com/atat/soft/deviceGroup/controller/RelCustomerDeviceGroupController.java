package com.atat.soft.deviceGroup.controller;

import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.common.bootitem.MinaServer;
import com.atat.soft.common.bootitem.MinaServerHandler;
import com.atat.soft.util.IpUtil;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;
import com.atat.soft.deviceGroup.service.RelCustomerDeviceGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author wuhaosoft
 * @version $Id RelCustomerDeviceGroupController.java, 2017-08-10 01:19:29 wuhaosoft Exp
 *
 */
@Api("用户设备分组关系表接口")
@RestController
@RequestMapping("/deviceGroup")
public class RelCustomerDeviceGroupController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RelCustomerDeviceGroupService relCustomerDeviceGroupService;

    @ApiOperation("查询 所有 用户设备分组关系表 分页")
    @RequestMapping(value = "/relCustomerDeviceGroups", method = RequestMethod.POST)
    public Object getRelCustomerDeviceGroupPageTurn(
            @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
            @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
            @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String groupName,
            @ApiParam(value = "用户是否为设备组拥有着 (非必传参数)") @RequestParam(required = false) Integer isOnwer,
            @ApiParam(value = "是否接受消息推送 (非必传参数)") @RequestParam(required = false) Integer isSendMsg,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate,
            @ApiParam(value = "页码(必传)") @RequestParam Integer pageNo,
            @ApiParam(value = "每页显示多少数据(必传)") @RequestParam Integer pageSize) throws Exception {
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabCustomerId) {
           rs.put("tabCustomerId", tabCustomerId);
        }
        if (null != tabDeviceGroupId) {
           rs.put("tabDeviceGroupId", tabDeviceGroupId);
        }
        if (StringUtil.isNotEmpty(groupName)) {
           rs.put("groupName", "%" + groupName + "%");
        }
        if (null != isOnwer) {
           rs.put("isOnwer", isOnwer);
        }
        if (null != isSendMsg) {
           rs.put("isSendMsg", isSendMsg);
        }
        if (null != createdDate) {
           rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
           rs.put("modifiedDate", modifiedDate);
        }
        return relCustomerDeviceGroupService.getRelCustomerDeviceGroupPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 用户设备分组关系表")
    @RequestMapping(value = "/allRelCustomerDeviceGroups", method = RequestMethod.POST)
    public JsonResult<Object> selectRelCustomerDeviceGroupList(
        @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
        @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
        @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String groupName,
        @ApiParam(value = "用户是否为设备组拥有着 (非必传参数)") @RequestParam(required = false) Integer isOnwer,
        @ApiParam(value = "是否接受消息推送 (非必传参数)") @RequestParam(required = false) Integer isSendMsg,
        @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
        @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate
        )throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        if (null != tabCustomerId) {
            rs.put("tabCustomerId", tabCustomerId);
        }
         if (null != tabDeviceGroupId) {
            rs.put("tabDeviceGroupId", tabDeviceGroupId);
         }
         if (StringUtil.isNotEmpty(groupName)) {
            rs.put("groupName", "%" + groupName + "%");
         }
        if (null != isOnwer) {
            rs.put("isOnwer", isOnwer);
        }
        if (null != isSendMsg) {
            rs.put("isSendMsg", isSendMsg);
        }
        if (null != createdDate) {
            rs.put("createdDate", createdDate);
        }
        if (null != modifiedDate) {
            rs.put("modifiedDate", modifiedDate);
        }
        result.setObj(relCustomerDeviceGroupService.selectRelCustomerDeviceGroupList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 用户设备分组关系表")
    @RequestMapping(value = "/relCustomerDeviceGroup", method = RequestMethod.POST)
    public JsonResult<Object> addRelCustomerDeviceGroup(
                @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
                @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
                @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String groupName,
                @ApiParam(value = "用户是否为设备组拥有着 (非必传参数)") @RequestParam(required = false) Integer isOnwer,
                @ApiParam(value = "是否接受消息推送 (非必传参数)") @RequestParam(required = false) Integer isSendMsg
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        RelCustomerDeviceGroup relCustomerDeviceGroup = new RelCustomerDeviceGroup();
        if (null != tabCustomerId) {
            relCustomerDeviceGroup.setTabCustomerId(tabCustomerId);
        }
        if (null != tabDeviceGroupId) {
           relCustomerDeviceGroup.setTabDeviceGroupId(tabDeviceGroupId);
        }
        if (StringUtil.isNotEmpty(groupName)) {
           relCustomerDeviceGroup.setGroupName(groupName);
        }
        if (null != isOnwer) {
            relCustomerDeviceGroup.setIsOnwer(isOnwer);
        }
        if (null != isSendMsg) {
            relCustomerDeviceGroup.setIsSendMsg(isSendMsg);
        }
        relCustomerDeviceGroup.setCreatedDate(new Date());
        relCustomerDeviceGroupService.addRelCustomerDeviceGroup(relCustomerDeviceGroup);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 用户设备分组关系表")
    @RequestMapping(value = "/relCustomerDeviceGroup/{relCustomerDeviceGroupId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateRelCustomerDeviceGroupById(
            @ApiParam(value = "用户-设备分组关联Id (必传参数)") @PathVariable Long  relCustomerDeviceGroupId,
             @ApiParam(value = "用户Id (非必传参数)") @RequestParam(required = false) Long tabCustomerId,
             @ApiParam(value = "设备分组Id (非必传参数)") @RequestParam(required = false) Long tabDeviceGroupId,
             @ApiParam(value = "组名称 (非必传参数)") @RequestParam(required = false) String groupName,
             @ApiParam(value = "用户是否为设备组拥有着 (非必传参数)") @RequestParam(required = false) Integer isOnwer,
             @ApiParam(value = "是否接受消息推送 (非必传参数)") @RequestParam(required = false) Integer isSendMsg,
             @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        RelCustomerDeviceGroup relCustomerDeviceGroup = new RelCustomerDeviceGroup();
        relCustomerDeviceGroup.setRelCustomerDeviceGroupId(relCustomerDeviceGroupId);

         if (null != tabCustomerId) {
            relCustomerDeviceGroup.setTabCustomerId(tabCustomerId);
         }
         if (null != tabDeviceGroupId) {
            relCustomerDeviceGroup.setTabDeviceGroupId(tabDeviceGroupId);
         }
         if (StringUtil.isNotEmpty(groupName)) {
            relCustomerDeviceGroup.setGroupName(groupName);
         }
         if (null != isOnwer) {
            relCustomerDeviceGroup.setIsOnwer(isOnwer);
         }
         if (null != isSendMsg) {
            relCustomerDeviceGroup.setIsSendMsg(isSendMsg);
         }
         if (null != isDeleted) {
            relCustomerDeviceGroup.setIsDeleted(isDeleted);
         }
        relCustomerDeviceGroup.setModifiedDate(new Date());
        relCustomerDeviceGroupService.updateRelCustomerDeviceGroupById(relCustomerDeviceGroup);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 用户设备分组关系表")
    @RequestMapping(value = "/relCustomerDeviceGroup/{relCustomerDeviceGroupId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delRelCustomerDeviceGroupById(
             @ApiParam(value = "用户-设备分组关联Id (必传参数)") @PathVariable Long  relCustomerDeviceGroupId
            ) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        relCustomerDeviceGroupService.delRelCustomerDeviceGroupById(relCustomerDeviceGroupId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 用户设备分组关系表")
    @RequestMapping(value = "/relCustomerDeviceGroup/{relCustomerDeviceGroupId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> getRelCustomerDeviceGroupById(
            @ApiParam(value = "用户-设备分组关联Id (必传参数)") @PathVariable Long  relCustomerDeviceGroupId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(relCustomerDeviceGroupService.getRelCustomerDeviceGroupById(relCustomerDeviceGroupId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 当前IP下设备")
    @RequestMapping(value = "/findDeviceByIp", method = RequestMethod.GET)
    public JsonResult<Map<String,Object>> findDeviceByIp(
            @ApiParam(value = "用户-设备分组关联Id (必传参数)") @RequestParam String ip) throws Exception{
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        result.setObj(relCustomerDeviceGroupService.findDeviceByIp(ip));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("发送信息给设备")
    @RequestMapping(value = "/SendToDevice", method = RequestMethod.GET)
    public JsonResult<Object> SendToDevice(HttpServletRequest request) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        //获取IP及其他信息
        Map<String, Object> ipInfoMap  = IpUtil.getIpInfo(request);
        String ip = (String) ipInfoMap.get("ipNet");
        logger.info("用户当前外网Ip为:"+ip);
        try {
            MinaServerHandler.SendToDevice();
            result.setCode(ResultCode.SUCCESS.getCode());
        } catch (Exception e){
            result.setCode(ResultCode.ERROR.getCode());
            result.setErrorMsg("mina请求异常");
        }
        return result;
    }
}

