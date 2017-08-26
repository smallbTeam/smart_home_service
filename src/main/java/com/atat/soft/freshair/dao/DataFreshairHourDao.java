package com.atat.soft.freshair.dao;

import com.atat.soft.freshair.bean.DataFreshairHour;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairHourDao {

    /**
     * 添加 空气监测设备每小时数据表
     * @param dataFreshairHour
     */
    public void  addDataFreshairHour(DataFreshairHour dataFreshairHour);

    /**
     * 依据主键更新 空气监测设备每小时数据表
     * @param dataFreshairHour
     */
    public void  updateDataFreshairHourById(DataFreshairHour dataFreshairHour);

    /**
     * 依据条件查找 空气监测设备每小时数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairHourList(Map<String, Object> param);

    /**
     * 依据主键删除 空气监测设备每小时数据表 记录
     * @param dataFreshairHourId
     */
    public void  delDataFreshairHourById(Long dataFreshairHourId);

    /**
     * 批量插入空气检测设备小时节点数据
     * @param list
     */
    public void addDataHourList(List<Map<String, Object>> list);

    /**
     * 计算空气检测设备时间段内平均值
     * @param param
     * @return
     */
    List<Map<String, Object>> timingHourAverageData(Map<String, Object> param);

    /**
     * 删除时间节点之前的数据
     * @param recordTimeEnd
     */
    public void  delDataHourByEndTime(Long recordTimeEnd);

}
