package com.atat.soft.deviceGroup.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.common.bootitem.MinaServer;
import com.atat.soft.common.bootitem.MinaServerHandler;
import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;
import com.atat.soft.deviceGroup.dao.RelCustomerDeviceGroupDao;
import com.atat.soft.deviceGroup.service.RelCustomerDeviceGroupService;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
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
    private TabDeviceFreshairService tabDeviceFreshairService;

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
        List<Long> deviceGroupList = new ArrayList<Long>();
        param.put("ip", ip);
        // 相同Ip下设备信息列表
        List<Map<String, Object>> deviceList = new ArrayList<Map<String, Object>>();
        // 取出空气检测系统下相同Ip设备
        List<String> freshairSeriaNumberList = MinaServerHandler.GetNowDataForIp(ip);
        List<Map<String, Object>> freshairList = new ArrayList<Map<String, Object>>();
                //tabDeviceFreshairService.selectTabDeviceFreshairList(param);
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
                    if (!deviceGroupList.contains(deviceGroupList)) {
                        deviceGroupList.add(tabDeviceGroupId);
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
}
