package com.atat.soft.record.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.record.bean.TabCustomerArarmRecord;
import com.atat.soft.record.dao.TabCustomerArarmRecordDao;
import com.atat.soft.record.service.TabCustomerArarmRecordService;
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
public class TabCustomerArarmRecordServiceImpl extends BaseSupportServiceImpl implements TabCustomerArarmRecordService {

    @Autowired
    private TabCustomerArarmRecordDao tabCustomerArarmRecordDao;

    private String daoNamespace = TabCustomerArarmRecordDao.class.getName();

    @Override
    public void  addTabCustomerArarmRecord(TabCustomerArarmRecord tabCustomerArarmRecord) {
        tabCustomerArarmRecordDao.addTabCustomerArarmRecord(tabCustomerArarmRecord);
    }

    @Override
    public void  updateTabCustomerArarmRecordById(TabCustomerArarmRecord tabCustomerArarmRecord) {
        tabCustomerArarmRecordDao.updateTabCustomerArarmRecordById(tabCustomerArarmRecord);
    }

    @Override
    public List<Map<String, Object>> selectTabCustomerArarmRecordList(Map<String, Object> param) {
        return tabCustomerArarmRecordDao.selectTabCustomerArarmRecordList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getTabCustomerArarmRecordPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectTabCustomerArarmRecordList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getTabCustomerArarmRecordById(Long tabCustomerArarmRecordId) {
        Map<String, Object> tabCustomerArarmRecordinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("tabCustomerArarmRecordId", tabCustomerArarmRecordId);
        List<Map<String, Object>> tabCustomerArarmRecordList = tabCustomerArarmRecordDao.selectTabCustomerArarmRecordList(rs);
        if ((null != tabCustomerArarmRecordList) && (tabCustomerArarmRecordList.size() > 0)) {
            tabCustomerArarmRecordinfo = tabCustomerArarmRecordList.get(0);
        }
        return tabCustomerArarmRecordinfo;
    }

    @Override
    public void delTabCustomerArarmRecordById(Long tabCustomerArarmRecordId) {

     //TabCustomerArarmRecord tabCustomerArarmRecord = new TabCustomerArarmRecord();
     //tabCustomerArarmRecord.setIsDeleted(1);
     //tabCustomerArarmRecord.setTabCustomerArarmRecordId(tabCustomerArarmRecordId);
     //tabCustomerArarmRecordDao.updateTabCustomerArarmRecordById(tabCustomerArarmRecord);

        tabCustomerArarmRecordDao.delTabCustomerArarmRecordById(tabCustomerArarmRecordId);

    }

}
