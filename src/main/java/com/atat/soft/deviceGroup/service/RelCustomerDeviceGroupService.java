package com.atat.soft.deviceGroup.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.deviceGroup.bean.RelCustomerDeviceGroup;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface RelCustomerDeviceGroupService {

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
     * 依据条件查找分页 用户设备分组关系表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getRelCustomerDeviceGroupPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 用户设备分组关系表 详情
     * @param relCustomerDeviceGroupId
     * @return
     */
    public Map<String, Object> getRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId);

    /**
     * 依据Id删除 用户设备分组关系表 记录
     * @param relCustomerDeviceGroupId
     */
    public void delRelCustomerDeviceGroupById(Long relCustomerDeviceGroupId);

    /**
     * 依据Ip获取设备及设备分组信息
     * @param ip
     */
    public Map<String, Object> findDeviceByIp(String ip);
}
