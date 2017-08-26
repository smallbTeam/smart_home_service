package com.atat.soft.freshair.dao;

import com.atat.soft.freshair.bean.DataFreshairNow;

import java.util.List;
import java.util.Map;

/**
 *
 * @author whaosoft
 *
 */
public interface DataFreshairNowDao {

    /**
     * 添加 空气监测设备实时数据表
     * @param dataFreshairNow
     */
    public void  addDataFreshairNow(DataFreshairNow dataFreshairNow);

    /**
     * 依据主键更新 空气监测设备实时数据表
     * @param dataFreshairNow
     */
    public void  updateDataFreshairNowById(DataFreshairNow dataFreshairNow);

    /**
     * 依据条件查找 空气监测设备实时数据表 列表
     * @param param
     * @return
     */
    public List<Map<String, Object>> selectDataFreshairNowList(Map<String, Object> param);

    /**
     * 依据主键删除 空气监测设备实时数据表 记录
     * @param dataFreshairNowId
     */
    public void  delDataFreshairNowById(Long dataFreshairNowId);

    /**
     * 空气监测设备时间段内平均值
     * @param param
     * @return
     */
    public List<Map<String, Object>> timingNowAverageData(Map<String, Object> param);

    /**
     * 删除时间点之前的数据
     * @param recordTimeEnd
     */
    public void  delDataNowByEndTime(Long recordTimeEnd);

}
