package com.atat.soft.deviceGroup.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.deviceGroup.bean.TabDeviceGroup;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabDeviceGroupService {

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
     * 依据条件查找分页 设备分组表(家) 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabDeviceGroupPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 设备分组表(家) 详情
     * @param tabDeviceGroupId
     * @return
     */
    public Map<String, Object> getTabDeviceGroupById(Long tabDeviceGroupId);

    /**
     * 依据Id删除 设备分组表(家) 记录
     * @param tabDeviceGroupId
     */
    public void delTabDeviceGroupById(Long tabDeviceGroupId);
}
