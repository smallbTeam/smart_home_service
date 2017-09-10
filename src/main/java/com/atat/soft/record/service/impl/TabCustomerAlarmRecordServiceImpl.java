package com.atat.soft.record.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.record.bean.TabCustomerAlarmRecord;
import com.atat.soft.record.dao.TabCustomerAlarmRecordDao;
import com.atat.soft.record.service.TabCustomerAlarmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author whaosoft
 *
 */
@Service
@Transactional
public class TabCustomerAlarmRecordServiceImpl extends BaseSupportServiceImpl implements TabCustomerAlarmRecordService {

    @Autowired
    private TabCustomerAlarmRecordDao tabCustomerAlarmRecordDao;

    private String daoNamespace = TabCustomerAlarmRecordDao.class.getName();

    @Override
    public void  addTabCustomerAlarmRecord(TabCustomerAlarmRecord tabCustomerAlarmRecord) {
        tabCustomerAlarmRecordDao.addTabCustomerAlarmRecord(tabCustomerAlarmRecord);
    }

    @Override
    public void  updateTabCustomerAlarmRecordById(TabCustomerAlarmRecord tabCustomerAlarmRecord) {
        tabCustomerAlarmRecordDao.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecord);
    }

    @Override
    public List<Map<String, Object>> selectTabCustomerAlarmRecordList(Map<String, Object> param) {
        return tabCustomerAlarmRecordDao.selectTabCustomerAlarmRecordList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getTabCustomerAlarmRecordPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectTabCustomerAlarmRecordList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getTabCustomerAlarmRecordById(Long tabCustomerAlarmRecordId) {
        Map<String, Object> tabCustomerAlarmRecordinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("tabCustomerAlarmRecordId", tabCustomerAlarmRecordId);
        List<Map<String, Object>> tabCustomerAlarmRecordList = tabCustomerAlarmRecordDao.selectTabCustomerAlarmRecordList(rs);
        if ((null != tabCustomerAlarmRecordList) && (tabCustomerAlarmRecordList.size() > 0)) {
            tabCustomerAlarmRecordinfo = tabCustomerAlarmRecordList.get(0);
        }
        return tabCustomerAlarmRecordinfo;
    }

    @Override
    public void delTabCustomerAlarmRecordById(Long tabCustomerAlarmRecordId) {

     //TabCustomerAlarmRecord tabCustomerAlarmRecord = new TabCustomerAlarmRecord();
     //tabCustomerAlarmRecord.setIsDeleted(1);
     //tabCustomerAlarmRecord.setTabCustomerAlarmRecordId(tabCustomerAlarmRecordId);
     //tabCustomerAlarmRecordDao.updateTabCustomerAlarmRecordById(tabCustomerAlarmRecord);

        tabCustomerAlarmRecordDao.delTabCustomerAlarmRecordById(tabCustomerAlarmRecordId);

    }

}
