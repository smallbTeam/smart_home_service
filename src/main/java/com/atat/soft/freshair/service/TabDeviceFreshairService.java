package com.atat.soft.freshair.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.freshair.bean.TabDeviceFreshair;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabDeviceFreshairService {

    /**
     * 添加 空气监测设备表
     * @param tabDeviceFreshair
     */
    public void  addTabDeviceFreshair(TabDeviceFreshair tabDeviceFreshair);

    /**
     * 依据主键更新 空气监测设备表
     * @param tabDeviceFreshair
     */
    public void  updateTabDeviceFreshairById(TabDeviceFreshair tabDeviceFreshair);

    /**
     * 依据条件查找 空气监测设备表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabDeviceFreshairList(Map<String, Object> param);

    /**
     * 依据条件查找分页 空气监测设备表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabDeviceFreshairPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 空气监测设备表 详情
     * @param tabDeviceFreshairId
     * @return
     */
    public Map<String, Object> getTabDeviceFreshairById(Long tabDeviceFreshairId);

    /**
     * 依据Id删除 空气监测设备表 记录
     * @param tabDeviceFreshairId
     */
    public void delTabDeviceFreshairById(Long tabDeviceFreshairId);

    /**
     * 依据设备序列号获取设备Id
     * @param deviceSeriaNumber
     * @return
     */
    public Map<String, Object> getTabDeviceFreshairByDeviceSeriaNumber(String deviceSeriaNumber);

    /**
     * 依据设备序列号获取空气检测实时数据
     * @param deviceSeriaNumber
     * @return
     */
    public Map<String, Object> getFreshairNowData(String deviceSeriaNumber);
}
