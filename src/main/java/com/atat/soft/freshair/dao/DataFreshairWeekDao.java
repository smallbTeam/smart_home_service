package com.atat.soft.freshair.dao;

import com.atat.soft.freshair.bean.DataFreshairWeek;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairWeekDao {

    /**
     * 添加 空气监测设备每周数据表
     * @param dataFreshairWeek
     */
    public void  addDataFreshairWeek(DataFreshairWeek dataFreshairWeek);

    /**
     * 依据主键更新 空气监测设备每周数据表
     * @param dataFreshairWeek
     */
    public void  updateDataFreshairWeekById(DataFreshairWeek dataFreshairWeek);

    /**
     * 依据条件查找 空气监测设备每周数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairWeekList(Map<String, Object> param);

    /**
     * 依据主键删除 空气监测设备每周数据表 记录
     * @param dataFreshairWeekId
     */
    public void  delDataFreshairWeekById(Long dataFreshairWeekId);

    /**
     * 批量插入空气检测设备周节点数据
     * @param list
     */
    public void addDataWeekList(List<Map<String, Object>> list);

}
