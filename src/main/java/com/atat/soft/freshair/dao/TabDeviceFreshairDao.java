package com.atat.soft.freshair.dao;

import com.atat.soft.freshair.bean.TabDeviceFreshair;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabDeviceFreshairDao {

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
     * 依据主键删除 空气监测设备表 记录
     * @param tabDeviceFreshairId
     */
    public void  delTabDeviceFreshairById(Long tabDeviceFreshairId);

}
