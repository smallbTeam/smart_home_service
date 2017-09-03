package com.atat.soft.record.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.record.bean.TabCustomerArarmRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerArarmRecordService {

    /**
     * 添加 给用户发送警报纪录表
     * @param tabCustomerArarmRecord
     */
    public void  addTabCustomerArarmRecord(TabCustomerArarmRecord tabCustomerArarmRecord);

    /**
     * 依据主键更新 给用户发送警报纪录表
     * @param tabCustomerArarmRecord
     */
    public void  updateTabCustomerArarmRecordById(TabCustomerArarmRecord tabCustomerArarmRecord);

    /**
     * 依据条件查找 给用户发送警报纪录表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabCustomerArarmRecordList(Map<String, Object> param);

    /**
     * 依据条件查找分页 给用户发送警报纪录表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabCustomerArarmRecordPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 给用户发送警报纪录表 详情
     * @param tabCustomerArarmRecordId
     * @return
     */
    public Map<String, Object> getTabCustomerArarmRecordById(Long tabCustomerArarmRecordId);

    /**
     * 依据Id删除 给用户发送警报纪录表 记录
     * @param tabCustomerArarmRecordId
     */
    public void delTabCustomerArarmRecordById(Long tabCustomerArarmRecordId);
}
