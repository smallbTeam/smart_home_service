package com.atat.soft.property.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.property.bean.TabPropertyMap;
import com.atat.soft.property.dao.TabPropertyMapDao;
import com.atat.soft.property.service.TabPropertyMapService;
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
public class TabPropertyMapServiceImpl extends BaseSupportServiceImpl implements TabPropertyMapService {

    @Autowired
    private TabPropertyMapDao tabPropertyMapDao;

    private String daoNamespace = TabPropertyMapDao.class.getName();

    @Override
    public void  addTabPropertyMap(TabPropertyMap tabPropertyMap) {
        tabPropertyMapDao.addTabPropertyMap(tabPropertyMap);
    }

    @Override
    public void  updateTabPropertyMapById(TabPropertyMap tabPropertyMap) {
        tabPropertyMapDao.updateTabPropertyMapById(tabPropertyMap);
    }

    @Override
    public List<Map<String, Object>> selectTabPropertyMapList(Map<String, Object> param) {
        return tabPropertyMapDao.selectTabPropertyMapList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getTabPropertyMapPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectTabPropertyMapList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getTabPropertyMapById(String tabPropertyMapId) {
        Map<String, Object> tabPropertyMapinfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("tabPropertyMapId", tabPropertyMapId);
        List<Map<String, Object>> tabPropertyMapList = tabPropertyMapDao.selectTabPropertyMapList(rs);
        if ((null != tabPropertyMapList) && (tabPropertyMapList.size() > 0)) {
            tabPropertyMapinfo = tabPropertyMapList.get(0);
        }
        return tabPropertyMapinfo;
    }

    @Override
    public void delTabPropertyMapById(String tabPropertyMapId) {

     //TabPropertyMap tabPropertyMap = new TabPropertyMap();
     //tabPropertyMap.setIsDeleted(1);
     //tabPropertyMap.setTabPropertyMapId(tabPropertyMapId);
     //tabPropertyMapDao.updateTabPropertyMapById(tabPropertyMap);

        tabPropertyMapDao.delTabPropertyMapById(tabPropertyMapId);

    }

}
