package com.atat.soft.deviceGroup.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.common.bootitem.MinaServerHandler;
import com.atat.soft.customer.service.TabCustomerService;
import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;
import com.atat.soft.deviceGroup.dao.RelCustomerDeviceGroupDao;
import com.atat.soft.deviceGroup.dao.TabDeviceGroupDao;
import com.atat.soft.deviceGroup.service.RelCustomerDeviceGroupService;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
import com.atat.soft.message.action.WeixinAction;
import com.atat.soft.record.bean.TabCustomerArarmRecord;
import com.atat.soft.record.service.TabCustomerArarmRecordService;
import com.atat.soft.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author whaosoft
 */
@Service
@Transactional
public class RelCustomerDeviceGroupServiceImpl extends BaseSupportServiceImpl implements RelCustomerDeviceGroupService {

    @Autowired
    private RelCustomerDeviceGroupDao relCustomerDeviceGroupDao;

    @Autowired
    private TabDeviceGroupDao tabDeviceGroupDao;

    @Autowired
    private TabDeviceFreshairService tabDeviceFreshairService;

    @Autowired
    private TabCustomerArarmRecordService tabCustomerArarmRecordService;

    @Autowired
    private WeixinAction weixinAction;

    @Autowired
    private TabCustomerService tabCustomerService;

    private String daoNamespace = RelCustomerDeviceGroupDao.class.getName();

    @Override
    public void addRelCustomerDeviceGroup(RelCustomerDeviceGroup relCustomerDeviceGroup) {
        relCustomerDeviceGroupDao.addRelCustomerDeviceGroup(relCustomerDeviceGroup);
    }

    @Override
    public void updateRelCustomerDeviceGroupById(RelCustomerDeviceGroup relCustomerDeviceGroup) {
        relCustomerDeviceGroupDao.updateRelCustomerDeviceGroupById(relCustomerDeviceGroup);
    }

    @Override
    public List<Map<String, Object>> selectRelCustomerDeviceGroupList(Map<String, Object> param) {
        return relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getRelCustomerDeviceGroupPageTurn(Map<String, Object> param, int pageNo,
            int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectRelCustomerDeviceGroupList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId) {
        Map<String, Object> relCustomerDeviceGroupinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("relCustomerDeviceGroupId", relCustomerDeviceGroupId);
        List<Map<String, Object>> relCustomerDeviceGroupList = relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(rs);
        if ((null != relCustomerDeviceGroupList) && (relCustomerDeviceGroupList.size() > 0)) {
            relCustomerDeviceGroupinfo = relCustomerDeviceGroupList.get(0);
        }
        return relCustomerDeviceGroupinfo;
    }

    @Override
    public void delRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId) {
        RelCustomerDeviceGroup relCustomerDeviceGroup = new RelCustomerDeviceGroup();
        relCustomerDeviceGroup.setIsDeleted(1);
        relCustomerDeviceGroup.setModifiedDate(new Date());
        relCustomerDeviceGroup.setRelCustomerDeviceGroupId(relCustomerDeviceGroupId);
        relCustomerDeviceGroupDao.updateRelCustomerDeviceGroupById(relCustomerDeviceGroup);
    }

    @Override
    public Map<String, Object> findDeviceByIp(String ip) {
        // 依次检索各种类型设别下相同Ip设备 并检索分组
        Map<String, Object> param = new HashMap<String, Object>();
        // 相同Ip下设备分组列表
        List<Long> deviceGroupIdList = new ArrayList<Long>();
        List<Map<String, Object>> deviceGroupList = new ArrayList<Map<String, Object>>();
        param.put("ip", ip);
        // 相同Ip下设备信息列表
        List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();
        // 取出空气检测系统下相同Ip设备
        List<String> freshairSeriaNumberList = MinaServerHandler.GetNowDataForIp(ip);
        List<Map<String, Object>> freshairList = new ArrayList<Map<String, Object>>();
        // tabDeviceFreshairService.selectTabDeviceFreshairList(param);
        for (String freshairSeriaNumber : freshairSeriaNumberList) {
            Map<String, Object> tabDeviceFreshairinfo = new HashMap<String, Object>();
            tabDeviceFreshairinfo = tabDeviceFreshairService.getTabDeviceFreshairByDeviceSeriaNumber(freshairSeriaNumber);
            freshairList.add(tabDeviceFreshairinfo);
        }
        if (CollectionUtil.isNotEmpty(freshairList)) {
            deviceList.addAll(freshairList);
            // 取出设别分组 已存在则不必添加
            for (Map<String, Object> freshair : freshairList) {
                if (null != freshair.get("tabDeviceGroupId")) {
                    Long tabDeviceGroupId = Long.parseLong(freshair.get("tabDeviceGroupId").toString());
                    if (!deviceGroupIdList.contains(tabDeviceGroupId)) {
                        deviceGroupIdList.add(tabDeviceGroupId);
                        // 查询当前分组信息并添加
                        Map<String, Object> tabDeviceGroupParam = new HashMap<String, Object>();
                        tabDeviceGroupParam.put("tabDeviceGroupId", tabDeviceGroupId);
                        List<Map<String, Object>> tabDeviceGroupList = tabDeviceGroupDao.selectTabDeviceGroupList(tabDeviceGroupParam);
                        if ((null != tabDeviceGroupList) && (tabDeviceGroupList.size() > 0)) {
                            deviceGroupList.add(tabDeviceGroupList.get(0));
                        }
                    }
                }
                else {
                    freshair.put("tabDeviceGroupId", null);
                }
            }
        }
        // 取出**类设备下设备
        // 封装结果集
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("deviceList", deviceList);
        resultMap.put("deviceGroupList", deviceGroupList);
        resultMap.put("ip", ip);
        return resultMap;
    }

    @Override
    public Integer deviceSendAlarmToCustomer(String deviceSeriaNumber, String wxWarnModel, JSONObject putData) {
        // 依据设备序列号获取所有用户微信ID
        Map<String, Object> tabDeviceFreshairinfo = tabDeviceFreshairService.getTabDeviceFreshairByDeviceSeriaNumber(deviceSeriaNumber);
        if (CollectionUtil.isNotEmpty(tabDeviceFreshairinfo) && null != tabDeviceFreshairinfo.get("tabDeviceGroupId")) {
            // 获取设备分组
            Long tabDeviceFreshairId = Long.parseLong(tabDeviceFreshairinfo.get("tabDeviceFreshairId").toString());
            Long tabDeviceGroupId = Long.parseLong(tabDeviceFreshairinfo.get("tabDeviceGroupId").toString());
            // 依据分组获取用户列表
            Map<String, Object> findDeviceGroupParam = new HashMap<String, Object>();
            findDeviceGroupParam.put("tabDeviceGroupId", tabDeviceGroupId);
            findDeviceGroupParam.put("isSendMsg", 1);
            List<Map<String, Object>> relCustomerDeviceGroupList = relCustomerDeviceGroupDao.selectRelCustomerDeviceGroupList(findDeviceGroupParam);
            if (CollectionUtil.isNotEmpty(relCustomerDeviceGroupList)) {
                List<String> tousers = new ArrayList<String>();
                Integer cont = 0;
                for (Map<String, Object> relCustomerDeviceGroup : relCustomerDeviceGroupList) {
                    Long tabCustomerId = Long.parseLong(relCustomerDeviceGroup.get("tabCustomerId").toString());
                    Map<String, Object> tabCustomer = tabCustomerService.getTabCustomerById(tabCustomerId);
                    String wxId = (String) tabCustomer.get("wxId");
                    // 依据用户信息和system字段查找最近一次发送时间
                    Map<String, Object> findCustomerArarmRecordParam = new HashMap<String, Object>();
                    findCustomerArarmRecordParam.put("tabCustomerId", tabCustomerId);
                    findCustomerArarmRecordParam.put("deviceSeriaNumber", deviceSeriaNumber);
                    findCustomerArarmRecordParam.put("content", putData.get("system").toString());
                    List<Map<String, Object>> tabCustomerArarmRecordList = tabCustomerArarmRecordService.selectTabCustomerArarmRecordList(findCustomerArarmRecordParam);
                    TabCustomerArarmRecord tabCustomerArarmRecordBean = new TabCustomerArarmRecord();
                    tabCustomerArarmRecordBean.setContent(putData.get("system").toString());
                    tabCustomerArarmRecordBean.setTabCustomerId(tabCustomerId);
                    tabCustomerArarmRecordBean.setDeviceSeriaNumber(deviceSeriaNumber);
                    tabCustomerArarmRecordBean.setType(1);
                    if (CollectionUtil.isNotEmpty(tabCustomerArarmRecordList)) {
                        Map<String, Object> tabCustomerArarmRecord = tabCustomerArarmRecordList.get(0);
                        if (CollectionUtil.isNotEmpty(tabCustomerArarmRecord)) {
                            Long tabCustomerArarmRecordId = Long.parseLong(tabCustomerArarmRecord.get("tabCustomerArarmRecordId").toString());
                            Date recordDate = (Date) tabCustomerArarmRecord.get("modifiedDate");
                            if ((new Date().getTime() - recordDate.getTime()) > 10800000) {
                                // 发送给时间超过 则添加微信Id 并更新记录
                                tousers.add(wxId);
                                cont++;
                                tabCustomerArarmRecordBean.setTabCustomerArarmRecordId(tabCustomerArarmRecordId);
                                tabCustomerArarmRecordBean.setModifiedDate(new Date());
                                tabCustomerArarmRecordService.updateTabCustomerArarmRecordById(tabCustomerArarmRecordBean);
                            } // 已发送且上次发送时间未超过则不发送
                        }
                        else {
                            // 没有记录 则添加微信Id 并记录
                            tousers.add(wxId);
                            cont++;
                            tabCustomerArarmRecordBean.setCreatedDate(new Date());
                            tabCustomerArarmRecordService.addTabCustomerArarmRecord(tabCustomerArarmRecordBean);
                        }
                    }
                    else {
                        // 没有记录 则添加微信Id 并记录
                        tousers.add(wxId);
                        cont++;
                        tabCustomerArarmRecordBean.setCreatedDate(new Date());
                        tabCustomerArarmRecordService.addTabCustomerArarmRecord(tabCustomerArarmRecordBean);
                    }
                }
                weixinAction.sendWeixinMessage(tousers, "", wxWarnModel, putData);
                return cont;
            }
            else {
                return 0;
            }
        }
        else {
            return 0;
        }
    }
}
