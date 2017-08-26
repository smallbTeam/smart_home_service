package com.atat.soft.freshair.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.freshair.bean.TabDeviceFreshair;
import com.atat.soft.freshair.dao.TabDeviceFreshairDao;
import com.atat.soft.freshair.service.TabDeviceFreshairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author whaosoft
 */
@Service
@Transactional
public class TabDeviceFreshairServiceImpl extends BaseSupportServiceImpl implements TabDeviceFreshairService {

    @Autowired
    private TabDeviceFreshairDao tabDeviceFreshairDao;

    private String daoNamespace = TabDeviceFreshairDao.class.getName();

    @Override
    public void addTabDeviceFreshair(TabDeviceFreshair tabDeviceFreshair) {
        tabDeviceFreshairDao.addTabDeviceFreshair(tabDeviceFreshair);
    }

    @Override
    public void updateTabDeviceFreshairById(TabDeviceFreshair tabDeviceFreshair) {
        tabDeviceFreshairDao.updateTabDeviceFreshairById(tabDeviceFreshair);
    }

    @Override
    public List<Map<String, Object>> selectTabDeviceFreshairList(Map<String, Object> param) {
        return tabDeviceFreshairDao.selectTabDeviceFreshairList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getTabDeviceFreshairPageTurn(Map<String, Object> param, int pageNo,
            int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectTabDeviceFreshairList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getTabDeviceFreshairById(Long tabDeviceFreshairId) {
        Map<String, Object> tabDeviceFreshairinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("tabDeviceFreshairId", tabDeviceFreshairId);
        List<Map<String, Object>> tabDeviceFreshairList = tabDeviceFreshairDao.selectTabDeviceFreshairList(rs);
        if ((null != tabDeviceFreshairList) && (tabDeviceFreshairList.size() > 0)) {
            tabDeviceFreshairinfo = tabDeviceFreshairList.get(0);
        }
        return tabDeviceFreshairinfo;
    }

    @Override
    public void delTabDeviceFreshairById(Long tabDeviceFreshairId) {
         TabDeviceFreshair tabDeviceFreshair = new TabDeviceFreshair();
         tabDeviceFreshair.setIsDeleted(1);
         tabDeviceFreshair.setModifiedDate(new Date());
         tabDeviceFreshair.setTabDeviceFreshairId(tabDeviceFreshairId);
         tabDeviceFreshairDao.updateTabDeviceFreshairById(tabDeviceFreshair);
    }

    @Override
    public Map<String, Object> getTabDeviceFreshairByDeviceSeriaNumber(String deviceSeriaNumber) {
        Map<String, Object> tabDeviceFreshairinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("deviceSeriaNumber", deviceSeriaNumber);
        List<Map<String, Object>> tabDeviceFreshairList = tabDeviceFreshairDao.selectTabDeviceFreshairList(rs);
        if ((null != tabDeviceFreshairList) && (tabDeviceFreshairList.size() > 0)) {
            tabDeviceFreshairinfo = tabDeviceFreshairList.get(0);
            return tabDeviceFreshairinfo;
        }
        else {
            return null;
        }
    }
}
