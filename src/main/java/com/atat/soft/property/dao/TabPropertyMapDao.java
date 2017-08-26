package com.atat.soft.property.dao;

import com.atat.soft.property.bean.TabPropertyMap;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface TabPropertyMapDao {

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
     * 依据主键删除 配置表 记录
     * @param tabPropertyMapId
     */
    public void  delTabPropertyMapById(String tabPropertyMapId);

}
