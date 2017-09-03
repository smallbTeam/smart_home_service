package com.atat.soft.record.dao;

import com.atat.soft.record.bean.TabCustomerArarmRecord;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabCustomerArarmRecordDao {

    /**
     * 添加 用户设备分组关系表
     * @param tabCustomerArarmRecord
     */
    public void  addTabCustomerArarmRecord(TabCustomerArarmRecord tabCustomerArarmRecord);

    /**
     * 依据主键更新 用户设备分组关系表
     * @param tabCustomerArarmRecord
     */
    public void  updateTabCustomerArarmRecordById(TabCustomerArarmRecord tabCustomerArarmRecord);

    /**
     * 依据条件查找 用户设备分组关系表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabCustomerArarmRecordList(Map<String, Object> param);

    /**
     * 依据主键删除 用户设备分组关系表 记录
     * @param tabCustomerArarmRecordId
     */
    public void  delTabCustomerArarmRecordById(Long tabCustomerArarmRecordId);

}
