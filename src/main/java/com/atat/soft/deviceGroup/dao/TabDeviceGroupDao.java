package com.atat.soft.deviceGroup.dao;

import com.atat.soft.deviceGroup.bean.TabDeviceGroup;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabDeviceGroupDao {

    /**
     * 添加 设备分组表(家)
     * @param tabDeviceGroup
     */
    public void  addTabDeviceGroup(TabDeviceGroup tabDeviceGroup);

    /**
     * 依据主键更新 设备分组表(家)
     * @param tabDeviceGroup
     */
    public void  updateTabDeviceGroupById(TabDeviceGroup tabDeviceGroup);

    /**
     * 依据条件查找 设备分组表(家) 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabDeviceGroupList(Map<String, Object> param);

    /**
     * 依据主键删除 设备分组表(家) 记录
     * @param tabDeviceGroupId
     */
    public void  delTabDeviceGroupById(Long tabDeviceGroupId);

}
