package com.atat.soft.freshair.dao;

import com.atat.soft.freshair.bean.DataFreshairDay;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairDayDao {

    /**
     * 添加 空气监测设备每日数据表
     * @param dataFreshairDay
     */
    public void  addDataFreshairDay(DataFreshairDay dataFreshairDay);

    /**
     * 依据主键更新 空气监测设备每日数据表
     * @param dataFreshairDay
     */
    public void  updateDataFreshairDayById(DataFreshairDay dataFreshairDay);

    /**
     * 依据条件查找 空气监测设备每日数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairDayList(Map<String, Object> param);

    /**
     * 依据主键删除 空气监测设备每日数据表 记录
     * @param dataFreshairDayId
     */
    public void  delDataFreshairDayById(Long dataFreshairDayId);

    /**
     * 批量插入空气检测设备天节点数据
     * @param list
     */
    public void addDataDayList(List<Map<String, Object>> list);

    /**
     * 计算空气检测设备时间段内平均值
     * @param param
     * @return
     */
    List<Map<String, Object>> timingDayAverageData(Map<String, Object> param);

    /**
     * 删除时间节点之前的数据
     * @param recordTimeEnd
     */
    public void  delDataDayByEndTime(Long recordTimeEnd);

}
