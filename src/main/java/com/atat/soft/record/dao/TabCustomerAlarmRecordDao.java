package com.atat.soft.record.dao;

import com.atat.soft.record.bean.TabCustomerAlarmRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerAlarmRecordDao {

    /**
     * 添加 给用户发送警报纪录表
     * @param tabCustomerAlarmRecord
     */
    public void  addTabCustomerAlarmRecord(TabCustomerAlarmRecord tabCustomerAlarmRecord);

    /**
     * 依据主键更新 给用户发送警报纪录表
     * @param tabCustomerAlarmRecord
     */
    public void  updateTabCustomerAlarmRecordById(TabCustomerAlarmRecord tabCustomerAlarmRecord);

    /**
     * 依据条件查找 给用户发送警报纪录表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabCustomerAlarmRecordList(Map<String, Object> param);

    /**
     * 依据主键删除 给用户发送警报纪录表 记录
     * @param tabCustomerAlarmRecordId
     */
    public void  delTabCustomerAlarmRecordById(Long tabCustomerAlarmRecordId);

}
