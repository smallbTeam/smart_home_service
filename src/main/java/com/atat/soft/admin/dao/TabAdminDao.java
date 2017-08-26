package com.atat.soft.admin.dao;

import com.atat.soft.admin.bean.TabAdmin;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabAdminDao {

    /**
     * 添加 管理员表
     * @param tabAdmin
     */
    public void  addTabAdmin(TabAdmin tabAdmin);

    /**
     * 依据主键更新 管理员表
     * @param tabAdmin
     */
    public void  updateTabAdminById(TabAdmin tabAdmin);

    /**
     * 依据条件查找 管理员表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabAdminList(Map<String, Object> param);

    /**
     * 依据主键删除 管理员表 记录
     * @param tabAdminId
     */
    public void  delTabAdminById(Integer tabAdminId);

}
