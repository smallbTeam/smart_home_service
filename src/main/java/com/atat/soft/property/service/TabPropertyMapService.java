package com.atat.soft.property.service;

import com.atat.core.db.model.PageTurn;
import com.atat.soft.property.bean.TabPropertyMap;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabPropertyMapService {

    /**
     * 添加 配置表
     * @param tabPropertyMap
     */
    public void  addTabPropertyMap(TabPropertyMap tabPropertyMap);

    /**
     * 依据主键更新 配置表
     * @param tabPropertyMap
     */
    public void  updateTabPropertyMapById(TabPropertyMap tabPropertyMap);

    /**
     * 依据条件查找 配置表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectTabPropertyMapList(Map<String, Object> param);

    /**
     * 依据条件查找分页 配置表 列表
     * @param param
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageTurn<Map<String, Object>> getTabPropertyMapPageTurn(Map<String, Object> param, int pageNo, int pageSize);

    /**
     * 依据Id查找 配置表 详情
     * @param tabPropertyMapId
     * @return
     */
    public Map<String, Object> getTabPropertyMapById(String tabPropertyMapId);

    /**
     * 依据Id删除 配置表 记录
     * @param tabPropertyMapId
     */
    public void delTabPropertyMapById(String tabPropertyMapId);
}
