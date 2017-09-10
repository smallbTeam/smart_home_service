package com.atat.soft.record.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.record.bean.TabCustomerAlarmRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerAlarmRecordService {

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
     * 依据条件查找分页 给用户发送警报纪录表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabCustomerAlarmRecordPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 给用户发送警报纪录表 详情
     * @param tabCustomerAlarmRecordId
     * @return
     */
    public Map<String, Object> getTabCustomerAlarmRecordById(Long tabCustomerAlarmRecordId);

    /**
     * 依据Id删除 给用户发送警报纪录表 记录
     * @param tabCustomerAlarmRecordId
     */
    public void delTabCustomerAlarmRecordById(Long tabCustomerAlarmRecordId);
}
