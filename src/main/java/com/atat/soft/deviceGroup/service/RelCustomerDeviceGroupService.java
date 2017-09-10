package com.atat.soft.deviceGroup.service;

import com.alibaba.fastjson.JSONObject;
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

    /**
     * 设备发出报警信息
     * @param deviceSeriaNumber 设备序列号
     * @param wxWarnModel 微信模板
     * @param putData 消息内容
     * @return
     */
    public Integer deviceSendAlarmToCustomer(String deviceSeriaNumber,String wxWarnModel,JSONObject putData);

    /**
     * 设备发送报警信息
     * @param deviceSeriaNumber 设备序列号
     * @param partName 报警部位名称
     * @param mark 报警状态 轻度:mildly 中度：moderate 高度:severe
     * @param content 给用户的消息内容
     * @return
     */
    public Integer deviceSendAlarm(String deviceSeriaNumber,String partName,String mark,String content);

    /**
     * 空气检测设备监控 检查状态并报警
     * @return
     */
    public Integer freshairAlarmMonitor();

    /**
     * 空气监测设备给用户推送前一天的空气状况
     * @return
     */
    public Integer freshAirDeviceSendOneDayReport();

    /**
     * 推送轻度警报
     */
    public Integer SendMildlyAlarm();
}
