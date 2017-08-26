package com.atat.soft.admin.service.impl;

import com.atat.core.db.model.PageTurn;
import com.atat.core.db.service.impl.BaseSupportServiceImpl;
import com.atat.soft.admin.bean.TabAdmin;
import com.atat.soft.admin.dao.TabAdminDao;
import com.atat.soft.admin.service.TabAdminService;
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
public class TabAdminServiceImpl extends BaseSupportServiceImpl implements TabAdminService {

    @Autowired
    private TabAdminDao tabAdminDao;

    private String daoNamespace = TabAdminDao.class.getName();

    @Override
    public void  addTabAdmin(TabAdmin tabAdmin) {
        tabAdminDao.addTabAdmin(tabAdmin);
    }

    @Override
    public void  updateTabAdminById(TabAdmin tabAdmin) {
        tabAdminDao.updateTabAdminById(tabAdmin);
    }

    @Override
    public List<Map<String, Object>> selectTabAdminList(Map<String, Object> param) {
        return tabAdminDao.selectTabAdminList(param);
    }

    @Override
    public PageTurn<Map<String, Object>> getTabAdminPageTurn(Map<String, Object> param, int pageNo, int pageSize) {
        return this.getDao().pagedQuery(daoNamespace + ".selectTabAdminList", pageNo, pageSize, param);
    }

    @Override
    public Map<String, Object> getTabAdminById(Integer tabAdminId) {
        Map<String, Object> tabAdmininfo = new HashMap<String, Object>();
        Map<String, Object> rs = new HashMap<String, Object>();
        rs.put("tabAdminId", tabAdminId);
        List<Map<String, Object>> tabAdminList = tabAdminDao.selectTabAdminList(rs);
        if ((null != tabAdminList) && (tabAdminList.size() > 0)) {
            tabAdmininfo = tabAdminList.get(0);
        }
        return tabAdmininfo;
    }

    @Override
    public void delTabAdminById(Integer tabAdminId) {

     //TabAdmin tabAdmin = new TabAdmin();
     //tabAdmin.setIsDeleted(1);
     //tabAdmin.setTabAdminId(tabAdminId);
     //tabAdminDao.updateTabAdminById(tabAdmin);

        tabAdminDao.delTabAdminById(tabAdminId);

    }

}
