package com.atat.soft.deviceGroup.dao;

import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface RelCustomerDeviceGroupDao {

    /**
     * 添加 用户设备分组关系表
     * @param relCustomerDeviceGroup
     */
    public void  addRelCustomerDeviceGroup(RelCustomerDeviceGroup relCustomerDeviceGroup);

    /**
     * 依据主键更新 用户设备分组关系表
     * @param relCustomerDeviceGroup
     */
    public void  updateRelCustomerDeviceGroupById(RelCustomerDeviceGroup relCustomerDeviceGroup);

    /**
     * 依据条件查找 用户设备分组关系表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectRelCustomerDeviceGroupList(Map<String, Object> param);

    /**
     * 依据主键删除 用户设备分组关系表 记录
     * @param relCustomerDeviceGroupId
     */
    public void  delRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId);

}
