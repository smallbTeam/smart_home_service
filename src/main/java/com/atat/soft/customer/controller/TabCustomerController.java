package com.atat.soft.customer.controller;

import com.atat.core.config.SpringBeanFactoryUtils;
import com.atat.soft.common.bean.JsonResult;
import com.atat.soft.common.prop.WxPlatformProperty;
import com.atat.soft.message.action.WeixinAction;
import com.atat.soft.util.CollectionUtil;
import com.atat.soft.util.StringUtil;
import com.atat.soft.common.bean.ResultCode;
import com.atat.soft.customer.bean.TabCustomer;
import com.atat.soft.customer.service.TabCustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhaosoft
 * @version $Id TabCustomerController.java, 2017-08-10 00:19:02 wuhaosoft Exp
 */
@Api("用户表接口")
@RestController
@RequestMapping("/customer")
public class TabCustomerController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TabCustomerService tabCustomerService;

    /**
     * 微信跳转页面
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("微信登陆验证WxId是否已注册表")
    @RequestMapping(value = "/wxUidIsExit", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> wxUidIsExit(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 判断微信Id是否已存在
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        WxPlatformProperty wxPlatformProperty = (WxPlatformProperty) SpringBeanFactoryUtils.getBean("wxPlatformProperty");
        String appid = wxPlatformProperty.getWxAppId();
        String wxId = WeixinAction.getUserwxId(code);
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(wxId)) {
            // 判定openId是否已经在表中存在
            Map<String, Object> customerMap = tabCustomerService.getCustomerByWxId(wxId);
            if (CollectionUtil.isNotEmpty(customerMap)) {
                String mainurl = "http://s-357114.gotocdn.com/smart_home/customer/wxUidIsExit";
                Map<String, Object> weixinInfoMap = WeixinAction.getSignature(mainurl);
                rs.put("isExit", "true");
                rs.put("appid", appid);
                rs.put("noncestr", weixinInfoMap.get("noncestr"));
                rs.put("timestamp", weixinInfoMap.get("timestamp"));
                rs.put("signaturet", weixinInfoMap.get("signaturet"));
            }
            else {
                rs.put("isExit", "false");
                rs.put("wxId", wxId);
            }
        }
        result.setObj(rs);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("用户登录 0 手机号或密码错误 1 成功")
    @RequestMapping(value = "/accountLogin", method = RequestMethod.POST)
    public JsonResult<Object> accountLogin(@ApiParam(value = "用户名(必传)") @PathVariable String mobelPhone,
            @ApiParam(value = "密码(必传)") @RequestParam String password,
            @ApiParam(value = "微信Id(非必传 传的话表示绑定)") @RequestParam String wxId) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        try {
            Integer loginRes = tabCustomerService.accountLogin(mobelPhone, password, wxId);
            result.setCode(ResultCode.SUCCESS.getCode());
            result.setObj(loginRes);
        }
        catch (Exception e) {
            result.setCode(ResultCode.ERROR.getCode());
            result.setErrorMsg(ResultCode.ERROR.getInfo());
        }
        return result;
    }

    @ApiOperation("依据手机号较验账户是否存在")
    @RequestMapping(value = "/accountIsExit/{mobelPhone}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>> accountIsExit(@ApiParam(value = "用户手机号 (必传参数)") @PathVariable String mobelPhone)
            throws IOException {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
            try {
                // 依据手机号查询用户是否已存在
                Map<String, Object> customer = tabCustomerService.getCustomerByMobelPhone(mobelPhone);
                if (CollectionUtil.isNotEmpty(customer)) {
                    result.setCode(ResultCode.SUCCESS.getCode());
                    rs.put("isExit", true);
                    rs.put("customer", customer);
                    result.setObj(rs);
                }
                else {
                    result.setCode(ResultCode.SUCCESS.getCode());
                    rs.put("isExit", false);
                    result.setObj(rs);
                }
            }
            catch (NumberFormatException e) {
                result.setCode(ResultCode.ERROR.getCode());
                result.setErrorMsg(ResultCode.ERROR.getInfo());
            }
        return result;
    }

    /**
     *
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation("用户更换手机号")
    @RequestMapping(value = "/accountUpdateMobile", method = RequestMethod.POST)
    public JsonResult<Object> accountUpdateMobile(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        JsonResult<Object> result = new JsonResult<Object>();
        String newMobelPhone = request.getParameter("newMobelPhone");
        String tabCustomerId = request.getParameter("tabCustomerId");
        String veridateMsg = request.getParameter("veridateMsg");
        if ((StringUtil.isNotEmpty(veridateMsg)) && (StringUtil.isNotEmpty(newMobelPhone))
                && (StringUtil.isNotEmpty(tabCustomerId))) {
            // 验证session
            String msmRandomCode = (String) request.getSession().getAttribute("msgCodeSsion" + newMobelPhone);
            String randomCode = "";
            String timeStamp = "";
            long timePath = 600001;
            if (StringUtil.isNotEmpty(msmRandomCode)) {
                String temp[] = msmRandomCode.split("&&");
                randomCode = temp[0];
                timeStamp = temp[1];
            }
            // 计算时间差
            if ((StringUtil.isNotEmpty(randomCode)) && (StringUtil.isNotEmpty(timeStamp))) {
                timePath = (Long) ((new Date()).getTime()) - Long.parseLong(timeStamp);
            }
            if ((StringUtil.isNotEmpty(randomCode)) && (veridateMsg.equals(randomCode)) && (timePath < 600000)) {
                // 结果返回前台
                try {
                    // 依据手机号查询用户是否已存在
                    Map<String, Object> customer = tabCustomerService.getCustomerByMobelPhone(newMobelPhone);
                    if (CollectionUtil.isEmpty(customer)) {
                        Map<String, Object> param = new HashMap<String, Object>();
                        TabCustomer tabCustomer = new TabCustomer();
                        tabCustomer.setTabCustomerId(Long.parseLong(tabCustomerId));
                        tabCustomer.setMobelPhone(newMobelPhone);
                        tabCustomerService.updateTabCustomerById(tabCustomer);
                        result.setCode(ResultCode.SUCCESS.getCode());
                        result.setObj(1);
                    }
                    else {
                        result.setCode(ResultCode.SUCCESS.getCode());
                        result.setObj(0);
                    }
                }
                catch (NumberFormatException e) {
                    logger.error("依据手机号查询用户是否已存在出错" + e, e);
                    result.setCode(ResultCode.SYSTEM_ERROR.getCode());
                    result.setErrorMsg(ResultCode.SYSTEM_ERROR.getInfo());
                }
            }
            else {
                result.setCode(ResultCode.ERROR.getCode());
                result.setErrorMsg("验证码错误");
            }
        }
        else {
            result.setCode(ResultCode.PARAM_EMPTY_ERROR.getCode());
            result.setErrorMsg("重要参数为空");
        }
        return  result;
    }

    @ApiOperation("查询 所有 用户表 分页")
    @RequestMapping(value = "/tabCustomers", method = RequestMethod.POST)
    public Object getTabCustomerPageTurn(
            @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
            @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
            @ApiParam(value = "微信Id (非必传参数)") @RequestParam(required = false) String wxId,
            @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
            @ApiParam(value = "出生日期 (非必传参数)") @RequestParam(required = false) Date birthday,
            @ApiParam(value = "性别 1男 2 女 0其他 (非必传参数)") @RequestParam(required = false) Integer sex,
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
        if (StringUtil.isNotEmpty(wxId)) {
            rs.put("wxId", "%" + wxId + "%");
        }
        if (StringUtil.isNotEmpty(nickName)) {
            rs.put("nickName", "%" + nickName + "%");
        }
        if (null != birthday) {
            rs.put("birthday", birthday);
        }
        if (null != sex) {
            rs.put("sex", sex);
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
        return tabCustomerService.getTabCustomerPageTurn(rs, pageNo, pageSize);
    }

    @ApiOperation("查询 所有 用户表")
    @RequestMapping(value = "/allTabCustomers", method = RequestMethod.POST)
    public JsonResult<Object> selectTabCustomerList(
            @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
            @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
            @ApiParam(value = "微信Id (非必传参数)") @RequestParam(required = false) String wxId,
            @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
            @ApiParam(value = "出生日期 (非必传参数)") @RequestParam(required = false) Date birthday,
            @ApiParam(value = "性别 1男 2 女 0其他 (非必传参数)") @RequestParam(required = false) Integer sex,
            @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token,
            @ApiParam(value = "创建时间 (非必传参数)") @RequestParam(required = false) Date createdDate,
            @ApiParam(value = "修改时间 (非必传参数)") @RequestParam(required = false) Date modifiedDate) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(mobelPhone)) {
            rs.put("mobelPhone", "%" + mobelPhone + "%");
        }
        if (StringUtil.isNotEmpty(password)) {
            rs.put("password", "%" + password + "%");
        }
        if (StringUtil.isNotEmpty(wxId)) {
            rs.put("wxId", "%" + wxId + "%");
        }
        if (StringUtil.isNotEmpty(nickName)) {
            rs.put("nickName", "%" + nickName + "%");
        }
        if (null != birthday) {
            rs.put("birthday", birthday);
        }
        if (null != sex) {
            rs.put("sex", sex);
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
        result.setObj(tabCustomerService.selectTabCustomerList(rs));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("新增 用户表")
    @RequestMapping(value = "/tabCustomer", method = RequestMethod.POST)
    public JsonResult<Object> addTabCustomer(@ApiParam(value = "电话 (必传参数)") @RequestParam String mobelPhone,
            @ApiParam(value = "密码 (必传参数)") @RequestParam String password,
            @ApiParam(value = "微信Id (非必传参数)") @RequestParam(required = false) String wxId,
            @ApiParam(value = "昵称 (非必传参数)") @RequestParam String nickName,
            @ApiParam(value = "出生日期 (必传参数)") @RequestParam Date birthday,
            @ApiParam(value = "性别 1男 2 女 0其他 (必传参数)") @RequestParam Integer sex,
            @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token) throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomer tabCustomer = new TabCustomer();
        tabCustomer.setMobelPhone(mobelPhone);
        tabCustomer.setPassword(password);
        if (StringUtil.isNotEmpty(wxId)) {
            tabCustomer.setWxId(wxId);
        }
        tabCustomer.setNickName(nickName);
        tabCustomer.setBirthday(birthday);
        tabCustomer.setSex(sex);
        if (StringUtil.isNotEmpty(token)) {
            tabCustomer.setToken(token);
        }
        tabCustomer.setCreatedDate(new Date());
        tabCustomerService.addTabCustomer(tabCustomer);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("更新 用户表")
    @RequestMapping(value = "/tabCustomer/{tabCustomerId}", method = RequestMethod.PUT)
    public JsonResult<Object> updateTabCustomerById(
            @ApiParam(value = "用户ID (必传参数)") @PathVariable Long tabCustomerId,
            @ApiParam(value = "电话 (非必传参数)") @RequestParam(required = false) String mobelPhone,
            @ApiParam(value = "密码 (非必传参数)") @RequestParam(required = false) String password,
            @ApiParam(value = "微信Id (非必传参数)") @RequestParam(required = false) String wxId,
            @ApiParam(value = "昵称 (非必传参数)") @RequestParam(required = false) String nickName,
            @ApiParam(value = "出生日期 (非必传参数)") @RequestParam(required = false) Date birthday,
            @ApiParam(value = "性别 1男 2 女 0其他 (非必传参数)") @RequestParam(required = false) Integer sex,
            @ApiParam(value = "token (非必传参数)") @RequestParam(required = false) String token,
            @ApiParam(value = "是否删除 1:是 2:否 (非必传参数)") @RequestParam(required = false) Integer isDeleted)
            throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        TabCustomer tabCustomer = new TabCustomer();
        tabCustomer.setTabCustomerId(tabCustomerId);
        if (StringUtil.isNotEmpty(mobelPhone)) {
            tabCustomer.setMobelPhone(mobelPhone);
        }
        if (StringUtil.isNotEmpty(password)) {
            tabCustomer.setPassword(password);
        }
        if (StringUtil.isNotEmpty(wxId)) {
            tabCustomer.setWxId(wxId);
        }
        if (StringUtil.isNotEmpty(nickName)) {
            tabCustomer.setNickName(nickName);
        }
        if (null != birthday) {
            tabCustomer.setBirthday(birthday);
        }
        if (null != sex) {
            tabCustomer.setSex(sex);
        }
        if (StringUtil.isNotEmpty(token)) {
            tabCustomer.setToken(token);
        }
        if (null != isDeleted) {
            tabCustomer.setIsDeleted(isDeleted);
        }
        tabCustomer.setModifiedDate(new Date());
        tabCustomerService.updateTabCustomerById(tabCustomer);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("删除 用户表")
    @RequestMapping(value = "/tabCustomer/{tabCustomerId}", method = RequestMethod.DELETE)
    public JsonResult<Object> delTabCustomerById(@ApiParam(value = "用户ID (必传参数)") @PathVariable Long tabCustomerId)
            throws Exception {
        JsonResult<Object> result = new JsonResult<Object>();
        tabCustomerService.delTabCustomerById(tabCustomerId);
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }

    @ApiOperation("查询 用户表")
    @RequestMapping(value = "/tabCustomer/{tabCustomerId}", method = RequestMethod.GET)
    public JsonResult<Map<String, Object>>
            getTabCustomerById(@ApiParam(value = "用户ID (必传参数)") @PathVariable Long tabCustomerId) throws Exception {
        JsonResult<Map<String, Object>> result = new JsonResult<Map<String, Object>>();
        Map<String, Object> rs = new HashMap<String, Object>();
        result.setObj(tabCustomerService.getTabCustomerById(tabCustomerId));
        result.setCode(ResultCode.SUCCESS.getCode());
        return result;
    }
}
